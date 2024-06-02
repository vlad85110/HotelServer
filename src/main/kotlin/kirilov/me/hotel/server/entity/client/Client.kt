package kirilov.me.hotel.server.entity.client

import kirilov.me.hotel.server.entity.Entity
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

open class Client(
    id: UUID? = null,
): Entity(id)