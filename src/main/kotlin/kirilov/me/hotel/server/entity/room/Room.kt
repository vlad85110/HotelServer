package kirilov.me.hotel.server.entity.room

import kirilov.me.hotel.server.entity.Entity
import org.springframework.data.relational.core.mapping.Column
import java.util.*

class Room(
    id: UUID?,
    var name: String?,

    @Column("type_id")
    var typeId: UUID?,

    var floor: Int?,
    var description: String?
): Entity(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Room

        if (name != other.name) return false
        if (typeId != other.typeId) return false
        if (floor != other.floor) return false
        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name?.hashCode() ?: 0
        result = 31 * result + (typeId?.hashCode() ?: 0)
        result = 31 * result + (floor ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        return result
    }
}