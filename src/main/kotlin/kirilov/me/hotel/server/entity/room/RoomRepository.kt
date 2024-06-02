package kirilov.me.hotel.server.entity.room

import kirilov.me.hotel.server.entity.room.Room
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface RoomRepository: CrudRepository<Room, UUID> {
    fun findRoomsByTypeId(typeId: UUID): Iterable<Room>
    fun getAllByOrderByTypeId(): Iterable<Room>

    @Query("select name from room")
    fun getAllINames(): Iterable<String>

    fun findByName(name: String): Optional<Room>
    fun findRoomsByNameIn(names: List<String>): Iterable<Room>
}