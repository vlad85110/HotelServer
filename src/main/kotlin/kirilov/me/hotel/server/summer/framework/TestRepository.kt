package kirilov.me.hotel.server.summer.framework

import kirilov.me.hotel.server.entity.client.people.People

interface TestRepository {
    fun findAllByPassportNumber(passportNumber: Long): List<People>
    fun findByPassportNumber(passportNumber: Long): People?
    fun findByPassportNumberAndFirstNameAndLastName(passportNumber: Long, firstName: String, lastName: String): People?
}