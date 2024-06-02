package kirilov.me.hotel.server.entity.service_type

import kirilov.me.hotel.server.entity.Entity
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("service_type")
class ServiceType(
    id: UUID?,
    val name: String
): Entity(id)