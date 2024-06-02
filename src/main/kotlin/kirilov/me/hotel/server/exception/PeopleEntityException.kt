package kirilov.me.hotel.server.exception

class PeopleEntityException(
    val passportNumberError: String?
) : RuntimeException()