package kirilov.me.hotel.server.entity.client.organization

import org.springframework.data.repository.CrudRepository
import java.util.*

interface OrganizationRepository: CrudRepository<Organization, UUID> {
    fun findAllByInn(inn: String): List<Organization>
    fun findByInn(inn: String): Optional<Organization>
}