package kirilov.me.hotel.server.entity

import org.springframework.data.annotation.Id
import java.util.UUID

abstract class Entity(
    @Id var id: UUID?
)