package kirilov.me.hotel.server.entity.room_type

import kirilov.me.hotel.server.entity.Entity
import java.util.UUID

class RoomType(
    id: UUID?,
    var name: String?,
    var bedCnt: Int?,
    var cost: Int?
): Entity(id)
