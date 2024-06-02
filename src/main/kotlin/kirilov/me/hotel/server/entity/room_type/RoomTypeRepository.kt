package kirilov.me.hotel.server.entity.room_type

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RoomTypeRepository: CrudRepository<RoomType, UUID> {
}