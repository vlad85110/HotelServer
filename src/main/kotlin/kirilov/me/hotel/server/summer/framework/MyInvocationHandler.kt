package kirilov.me.hotel.server.summer.framework

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.reflect.KClass

class MyInvocationHandler(
    private val jdbcTemplate: JdbcTemplate,
    private val targetClass: KClass<*>,
) : InvocationHandler {

    private val dict: Map<String, String> = hashMapOf(
        "find" to "select * from %s where ",
        "And" to "and ",
        "Or" to "or ",
        "Between" to "between ? and ? "
    )

    override operator fun invoke(proxy: Any?, method: Method, args: Array<Any>?): Any? {
        val methodName = method.name

        val sqlString = toSqlString(methodName)

        val clazz = EntityMapper::class.java
        val entityMapper = clazz.constructors.first().newInstance(targetClass) as EntityMapper<*>

        return try {
            if (!methodName.contains("findAll")) {
                val res = jdbcTemplate.query(sqlString, args, entityMapper).first()
                res
            } else {
                jdbcTemplate.query(sqlString, args, entityMapper)
            }
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }


    private fun toSqlString(methodName: String): String {
        val fields = targetClass.java.declaredFields
        val fieldNames = fields.associate { f ->
            Pair<String, String>(f.name.toLowerCase().capitalize(), f.name)
        }

        var methodNameStr = methodName

        val tableName = if (targetClass.java.isAnnotationPresent(Table::class.java)) {
            targetClass.java.getAnnotation(Table::class.java).value
        } else {
            targetClass.java.simpleName.toLowerCase()
        }

        if (methodName == "findAll") {
            return "select * from %s".format(tableName)
        }

        for (field in fields) {
            if (field.name.capitalize() in methodNameStr) {
                methodNameStr = methodNameStr.replace(
                    field.name.capitalize(),
                    field.name.toLowerCase().capitalize()
                )
            }
        }

        val camelCaseSplit = camelCaseSplit(methodNameStr)
        val sqlBuilder = StringBuilder()

        camelCaseSplit.forEachIndexed { index, word ->
            if (word in dict) {
                sqlBuilder.append(dict[word])
            }

            if (word in fieldNames.keys) {
                val field = targetClass.java.getDeclaredField(fieldNames[word]!!)
                var value = fieldNames[word]!!

                if (field.isAnnotationPresent(Column::class.java)) {
                    value = field.getAnnotation(Column::class.java).value
                }

                if (camelCaseSplit.size != index + 1 &&
                    camelCaseSplit[index + 1] in listOf("In", "Between")
                ) {
                    sqlBuilder.append("$value ")
                } else {
                    sqlBuilder.append("$value = ? ")
                }
            }
        }

        return sqlBuilder.toString().format(tableName)
    }

    private fun camelCaseSplit(string: String): List<String> {
        val result = ArrayList<String>()
        val pattern: Pattern = Pattern.compile("[A-Z]?[a-z]+")
        val matcher: Matcher = pattern.matcher(string)
        while (matcher.find()) {
            result.add(matcher.group())
        }
        return result
    }
}