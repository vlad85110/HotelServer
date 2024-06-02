package kirilov.me.hotel.server.entity.client.people

interface PeopleRepository {
    fun findAll(): List<People>

    fun findByPassportNumber(passportNumber: Long): People?

    fun findByFirstNameAndLastName(firstName: String, lastName: String): People?
    fun findAllByPassportNumberBetween(first: Long, second: Long): List<People>
    fun findByPassportNumberBetween(first: Long, second: Long): People?
}

