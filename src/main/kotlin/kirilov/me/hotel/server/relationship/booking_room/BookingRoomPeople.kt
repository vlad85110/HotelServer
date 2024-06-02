package kirilov.me.hotel.server.relationship.booking_room

import kirilov.me.hotel.server.entity.Entity
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table(name = "booking_room")
class BookingRoomPeople(
    id: UUID? = null,
    @Column("booking_id")
    val bookingId: UUID,
    @Column("room_id")
    val roomId: UUID,
    @Column("people_id")
    val peopleId: UUID
) : Entity(id)