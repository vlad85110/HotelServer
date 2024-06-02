package kirilov.me.hotel.server.entity.service

import kirilov.me.hotel.server.entity.Entity
import java.util.UUID

class Service(
    id: UUID?,
    val name: String,
    val cost: Int,
    val typeId: UUID,
    val description: String
): Entity(id)
