package kirilov.me.hotel.server.exception

class BookingEntityException(
    val dateError: String?,
    val roomNumberError: String?
): RuntimeException() {
}