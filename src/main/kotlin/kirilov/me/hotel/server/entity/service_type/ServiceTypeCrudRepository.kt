package kirilov.me.hotel.server.entity.service_type

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ServiceTypeCrudRepository: CrudRepository<ServiceType, UUID>