package kirilov.me.hotel.server.entity.room_type

import kirilov.me.hotel.server.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RoomTypeService(
    @Autowired private val repository: RoomTypeRepository
) {
    fun add(roomType: RoomType): RoomType {
        return repository.save(roomType)
    }

    fun getAll(): Iterable<RoomType> {
        try {
            repository.findAll()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return repository.findAll()
    }

    fun deleteById(id: UUID) {
        return repository.deleteById(id)
    }

    fun editType(roomType: RoomType): RoomType {
        val id = roomType.id ?: throw ResourceNotFoundException("no id")

        val optTypeToUpdate = repository.findById(id)
        if (optTypeToUpdate.isEmpty) {
            throw ResourceNotFoundException("no such entity")
        }

        val typeToUpdate = optTypeToUpdate.get()
        typeToUpdate.name = roomType.name
        typeToUpdate.bedCnt = roomType.bedCnt
        typeToUpdate.cost = roomType.cost

        return repository.save(typeToUpdate)
    }
}