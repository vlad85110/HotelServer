package kirilov.me.hotel.server.entity.service

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ServiceRepository: CrudRepository<Service, UUID> {
    fun findServiceByTypeId(typeId: UUID): Iterable<Service>
    fun getAllByOrderByTypeId(): Iterable<Service>

    @Query("select name from service")
    fun getAllINames(): Iterable<String>

    fun findByName(name: String): Optional<Service>
    fun findServicesByNameIn(names: List<String>): Iterable<Service>
}