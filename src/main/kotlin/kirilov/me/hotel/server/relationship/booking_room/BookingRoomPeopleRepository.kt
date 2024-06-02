package kirilov.me.hotel.server.relationship.booking_room

import org.springframework.data.repository.CrudRepository
import java.util.*

interface BookingRoomPeopleRepository: CrudRepository<BookingRoomPeople, UUID> {
    fun findByRoomId(roomId: UUID): Iterable<BookingRoomPeople>
    fun findByBookingId(bookingId: UUID) : Iterable<BookingRoomPeople>
    fun deleteAllByBookingId(bookingId: UUID)
}