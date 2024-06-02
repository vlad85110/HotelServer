package kirilov.me.hotel.server.config

import kirilov.me.hotel.server.entity.Entity
import kirilov.me.hotel.server.entity.client.people.People
import kirilov.me.hotel.server.entity.client.people.PeopleRepository
import kirilov.me.hotel.server.entity.service_type.ServiceType
import kirilov.me.hotel.server.entity.service_type.ServiceTypeRepository
import kirilov.me.hotel.server.summer.framework.MyInvocationHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.relational.core.mapping.event.BeforeConvertCallback
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.reflect.Proxy
import java.util.*

@Configuration
class Config(
    @Autowired private val jdbcTemplate: JdbcTemplate
) {
    @Bean
    fun beforeConvertCallback(): BeforeConvertCallback<Entity> {
        return BeforeConvertCallback<Entity> { entity: Entity ->
            if (entity.id == null) {
                entity.id = UUID.randomUUID()
            }
            entity
        }
    }

    @Bean
    fun peopleRepository(): PeopleRepository {
        return Proxy.newProxyInstance(
            ClassLoader.getSystemClassLoader(), arrayOf<Class<*>>(PeopleRepository::class.java),
            MyInvocationHandler(jdbcTemplate, People::class)
        ) as PeopleRepository
    }

    @Bean
    fun serviceTypeRepository(): ServiceTypeRepository {
        return Proxy.newProxyInstance(
            ClassLoader.getSystemClassLoader(), arrayOf<Class<*>>(ServiceTypeRepository::class.java),
            MyInvocationHandler(jdbcTemplate, ServiceType::class)
        ) as ServiceTypeRepository
    }
}