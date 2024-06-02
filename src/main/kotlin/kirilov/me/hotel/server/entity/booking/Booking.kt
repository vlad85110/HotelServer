package kirilov.me.hotel.server.entity.booking

import kirilov.me.hotel.server.entity.Entity
import java.util.UUID

class Booking(
    id: UUID? = null,
    val startDate: Long,
    val endDate: Long,
    val ownerId: UUID
): Entity(id)