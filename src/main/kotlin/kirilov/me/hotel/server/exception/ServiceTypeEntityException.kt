package kirilov.me.hotel.server.exception

class ServiceTypeEntityException(
    val nameError: String?
) : RuntimeException() {
}