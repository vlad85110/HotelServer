package kirilov.me.hotel.server.config

import kirilov.me.hotel.server.exception.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [ResourceNotFoundException::class])
    protected fun handleConflict(
        ex: RuntimeException, request: WebRequest
    ): ResponseEntity<Any?>? {
        return ResponseEntity.badRequest().body(ex.message)
    }

    @ExceptionHandler(value = [ListAddException::class])
    protected fun handleListException(ex: ListAddException, request: WebRequest): ResponseEntity<Any?>? {
        return ResponseEntity.badRequest().body(ex.body)
    }

    @ExceptionHandler(value = [BookingEntityException::class, PeopleEntityException::class,
        OrganizationEntityException::class])
    protected fun handleEntityException(ex: RuntimeException, request: WebRequest): ResponseEntity<Any?>? {
        return ResponseEntity.badRequest().body(ex)
    }
}