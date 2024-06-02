package kirilov.me.hotel.server.summer.framework

import kirilov.me.hotel.server.entity.service.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import org.springframework.stereotype.Component
import kotlin.reflect.KClass


@Component
class ProcedureCaller(
    @Autowired private val jdbcTemplate: JdbcTemplate
) {
    fun call(procedureName: String, resultEntityClass: KClass<*>) {
       val sql = "call get_service_iid()"
        jdbcTemplate.execute(sql)
    }
}