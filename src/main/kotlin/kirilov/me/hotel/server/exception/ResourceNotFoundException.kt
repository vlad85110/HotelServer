package kirilov.me.hotel.server.exception

class ResourceNotFoundException(message: String = "wrong entity"): RuntimeException(message)