package kirilov.me.hotel.server.entity.client.organization

import kirilov.me.hotel.server.entity.Entity
import java.util.*

class Organization(
    id: UUID? = null,
    val name: String,
    val inn: String,
) : Entity(id)