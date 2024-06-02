package kirilov.me.hotel.server.entity.booking

import kirilov.me.hotel.server.entity.client.people.People
import kirilov.me.hotel.server.entity.client.people.PeopleService
import kirilov.me.hotel.server.entity.room.Room
import kirilov.me.hotel.server.entity.room.RoomService
import kirilov.me.hotel.server.exception.BookingEntityException
import kirilov.me.hotel.server.relationship.booking_room.BookingRoomPeople
import kirilov.me.hotel.server.relationship.booking_room.BookingRoomPeopleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet


@Component
class BookingService(
    @Autowired private val repository: BookingRepository,
    @Autowired private val roomService: RoomService,
    @Autowired private val peopleService: PeopleService,
    @Autowired private val bookingRoomRepository: BookingRoomPeopleRepository
) {
    fun getBookingsByPeriod(startDate: Long, endDate: Long): List<Booking> {
        return repository.findByStartDateAndEndDate(startDate, endDate)
    }

    @Transactional
    fun add(startDate: Long, endDate: Long, ownerId: UUID, roomsPeople: Map<UUID, List<UUID>>): Booking {
        var dateError: String? = null
        var roomNumberError: String? = null

        if (startDate > endDate) {
            dateError = "Дата заезда должна быть раньше даты выезда"
        }

        roomsPeople.keys.forEach { roomId ->
            val roomBookingIds = bookingRoomRepository.findByRoomId(roomId).map { e -> e.bookingId }

            if (roomBookingIds.isNotEmpty()) {
                val bookings = repository.findByIdInAndPeriodOrderByStartDate(
                    roomBookingIds,
                    startDate, endDate
                )

                if (bookings.isNotEmpty()) {
                    roomNumberError += "Комната $roomService.getById(roomId).name уже забронирована на это время\n"
                }
            }
        }

        if (dateError != null || roomNumberError != null) {
            println(dateError)
            println(roomNumberError)

            throw BookingEntityException(dateError, roomNumberError)
        } else {
            val booking = repository.save(Booking(null, startDate, endDate, ownerId))
            val bookingId = booking.id!!

            roomsPeople.forEach { entry ->
                entry.value.forEach { peopleId ->
                    bookingRoomRepository.save(BookingRoomPeople(null, bookingId, entry.key, peopleId))
                }
            }

            return booking
        }
    }

    @Transactional
    fun getByRoomAndPeriod(roomId: UUID, startDate: Long, endDate: Long): List<Booking> {
        val bookingIds = bookingRoomRepository.findByRoomId(roomId).map { e -> e.bookingId }

        if (bookingIds.isEmpty()) {
            return emptyList()
        }

        return repository.findByIdInAndPeriodOrderByStartDate(bookingIds, startDate, endDate)
    }

    @Transactional
    fun getRoomAndPeopleByBookingId(bookingId: UUID): Pair<List<Room>, List<People>> {
        val bookingRoomPeople = bookingRoomRepository.findByBookingId(bookingId)

        val peopleIds = HashSet<UUID>()
        val roomIds = HashSet<UUID>()

        val roomList = ArrayList<Room>()
        val peopleList = ArrayList<People>()

        bookingRoomPeople.forEach { el ->
            if (el.roomId !in roomIds) {
                roomList.add(roomService.getById(el.roomId))
                roomIds.add(el.roomId)
            }

            if (el.peopleId !in peopleIds) {
                peopleList.add(peopleService.getById(el.peopleId))
                peopleIds.add(el.peopleId)
            }
        }

        return Pair(roomList, peopleList)
    }

    fun deleteById(id: UUID) {
        val bookingRoomPeopleList = bookingRoomRepository.findByBookingId(id)

        bookingRoomPeopleList.forEach {
            el -> bookingRoomRepository.deleteById(el.id!!)
        }

        repository.deleteById(id)
    }

    fun getBusyRoomsCount(): Int {
        return repository.getRoomsBusyCount()
    }
}
