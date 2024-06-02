package kirilov.me.hotel.server.summer.framework

import org.springframework.data.relational.core.mapping.Column
import org.springframework.jdbc.core.RowMapper
import java.lang.reflect.Field
import java.sql.ResultSet
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.javaType

class EntityMapper<T : Any>(
    private val clazz: KClass<T>
) : RowMapper<T> {
    @OptIn(ExperimentalStdlibApi::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): T {
        val constructor = clazz.primaryConstructor
            ?: throw IllegalArgumentException("Class must have a primary constructor")

        val fields = HashMap<String, Field>()
        fields.putAll(clazz.java.declaredFields.map { field -> Pair(field.name, field) })

        var cls = clazz.java.superclass
        while (cls != null) {
            fields.putAll(cls.declaredFields.map { field -> Pair(field.name, field) })
            cls = cls.superclass
        }

        val args = constructor.parameters.map { param ->
            val field = fields[param.name.toString()]!!

            val name = if (field.isAnnotationPresent(Column::class.java)) {
                field.getAnnotation(Column::class.java).value
            } else {
                field.name
            }

            when (param.type.javaType.typeName) {
                "long" -> {
                    rs.getLong(name)
                }

                "java.util.UUID" -> {
                    UUID.fromString(rs.getString(name))
                }

                else -> {
                    rs.getObject(name, field.type)
                }
            }
        }
        return constructor.call(*args.toTypedArray())
    }
}