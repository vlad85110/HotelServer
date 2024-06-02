package kirilov.me.hotel.server.entity.room

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("room")
@CrossOrigin(origins = ["http://localhost:3000"])
class RoomController(
    @Autowired private val service: RoomService
) {
    @PostMapping
    fun add(@RequestBody room: Room): Room {
        return service.add(room)
    }

    @GetMapping
    fun getAll(): Iterable<Room> {
        return service.getAll()
    }

    @GetMapping("orderByType")
    fun getAllOrderByType(): Iterable<Room> {
        return service.getAllOrderByTypes()
    }

    @DeleteMapping
    fun deleteById(@RequestParam id: UUID) {
        return service.deleteById(id)
    }

    @GetMapping("type")
    fun getByType(@RequestParam typeId: UUID): Iterable<Room> {
        return service.getByType(typeId)
    }

    @PostMapping("list")
    fun addRooms(@RequestBody rooms: List<Room>) {
        service.addList(rooms)
    }

    @PutMapping
    fun editRoom(@RequestBody roomDto: Room): Room {
        return service.editRoom(roomDto)
    }
}