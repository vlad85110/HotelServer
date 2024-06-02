package kirilov.me.hotel.server.entity.client.people

import kirilov.me.hotel.server.entity.Entity
import org.springframework.data.relational.core.mapping.Column
import java.util.*

class People(
    id: UUID? = null,
    @Column("first_name")
    val firstName: String,
    @Column("last_name")
    val lastName: String,
    @Column("birthday_date")
    val birthdayDate: Long,
    @Column("passport_number")
    val passportNumber: Long
) : Entity(id)