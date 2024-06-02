package kirilov.me.hotel.server.entity.room_type

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("roomType")
@CrossOrigin(origins = ["http://localhost:3000"])
class RoomTypeController(
    @Autowired val service: RoomTypeService
) {
    @PostMapping
    fun addRoomType(@RequestBody roomType: RoomType): RoomType {
        return service.add(roomType)
    }

    @GetMapping
    fun getAll(): Iterable<RoomType> {
        return service.getAll()
    }

    @DeleteMapping
    fun deleteById(@RequestParam id: UUID) {
        return service.deleteById(id)
    }

    @PutMapping
    fun editType(@RequestBody roomType: RoomType): RoomType {
        return service.editType(roomType)
    }
}