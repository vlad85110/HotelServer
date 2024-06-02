package kirilov.me.hotel.server.entity.service_type

interface ServiceTypeRepository {
    fun findByName(name: String): ServiceType?
}