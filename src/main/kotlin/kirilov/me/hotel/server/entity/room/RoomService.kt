package kirilov.me.hotel.server.entity.room

import kirilov.me.hotel.server.exception.ResourceNotFoundException
import kirilov.me.hotel.server.exception.RoomEntityException
import kirilov.me.hotel.server.exception.ListAddException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.ArrayList

@Service
class RoomService(
    @Autowired private val repository: RoomRepository
) {
    fun add(room: Room): Room {
        return repository.save(room)
    }

    fun getAll(): Iterable<Room> {
        return repository.findAll()
    }

    fun deleteById(id: UUID) {
        repository.deleteById(id)
    }

    fun getById(id: UUID): Room {
        val res = repository.findById(id)
        if (res.isEmpty) throw ResourceNotFoundException("no room with id $id")
        return res.get()
    }

    fun getByType(typeId: UUID): Iterable<Room> {
        return repository.findRoomsByTypeId(typeId)
    }

    fun getIdByName(name: String): UUID? {
        val res = repository.findByName(name)
        if (res.isPresent) return res.get().id
        return null
    }

    @Transactional
    fun addList(rooms: List<Room>) {
        val exceptionBody = ArrayList<Boolean>()
        val names = ArrayList<String>()

        rooms.forEach { room -> names.add(room.name!!) }
        val res = repository.findRoomsByNameIn(names).toList()

        if (res.isEmpty()) {
            repository.saveAll(rooms)
            return
        }

        for (i in 1..rooms.size) {
            exceptionBody.add(true)
        }

        for (room in res) {
            val roomIndex = rooms.indexOf(room)
            println(roomIndex)
            exceptionBody[roomIndex] = false
        }

        throw ListAddException(exceptionBody)
    }

    fun getAllOrderByTypes(): Iterable<Room> {
        return repository.getAllByOrderByTypeId()
    }

    fun editRoom(room: Room): Room {
        val id = room.id ?: throw ResourceNotFoundException("no id")
        val optRoomToUpdate = repository.findById(id)
        if (optRoomToUpdate.isEmpty) {
            throw ResourceNotFoundException()
        }

        val roomToUpdate = optRoomToUpdate.get()

        if (roomToUpdate.name != room.name && repository.findByName(room.name!!).isPresent) {
            throw RoomEntityException("Комната с таким номером уже существует")
        }

        roomToUpdate.description = room.description
        roomToUpdate.floor = room.floor
        roomToUpdate.name = room.name
        roomToUpdate.typeId = room.typeId
        return repository.save(roomToUpdate)
    }
}