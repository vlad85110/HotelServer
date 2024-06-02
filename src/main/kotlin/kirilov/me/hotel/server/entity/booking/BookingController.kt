package kirilov.me.hotel.server.entity.booking

import org.json.JSONArray
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.log


@RestController
@RequestMapping("booking")
@CrossOrigin(origins = ["http://localhost:3000"])
class BookingController(
    @Autowired private val service: BookingService,
) {
    @PostMapping
    fun addBooking(
        @RequestBody body: String,
    ): Booking {
        val jsonBody = JSONObject(body)
        val startDate = jsonBody["startDate"].toString().toLong()
        val endDate = jsonBody["endDate"].toString().toLong()
        val ownerId = UUID.fromString(jsonBody["ownerId"].toString())
        val rooms: JSONArray = jsonBody.getJSONArray("rooms")

        val roomsMap = HashMap<UUID, List<UUID>>()

        for (i in 0 until rooms.length()) {
            val room = rooms.getJSONObject(i)

            val id = UUID.fromString(room.getString("id"))

            val peopleIds = room.getJSONArray("people").map { peopleId ->
                UUID.fromString(peopleId.toString())
            }

            roomsMap[id] = peopleIds
        }

        return service.add(startDate, endDate, ownerId, roomsMap)
    }

    @GetMapping
    fun getByRoomAndPeriod(
        @RequestParam roomId: UUID,
        @RequestParam startDate: Long,
        @RequestParam endDate: Long
    ): List<Booking> {
        return service.getByRoomAndPeriod(roomId, startDate, endDate)
    }

    @GetMapping("/roomPeople")
    fun getRoomAndPeopleByBookingId(@RequestParam id: UUID): String {
        val pair = service.getRoomAndPeopleByBookingId(id)

        val response = JSONObject()

        response.put("rooms", JSONArray(pair.first))
        response.put("people", JSONArray(pair.second))

        return response.toString()
    }

    @DeleteMapping
    fun deleteBookingById(@RequestParam id: UUID) {
        service.deleteById(id)
    }

    @GetMapping("/busyRooms")
    fun getBusyRoomsCount(): Int {
        return service.getBusyRoomsCount()
    }
}