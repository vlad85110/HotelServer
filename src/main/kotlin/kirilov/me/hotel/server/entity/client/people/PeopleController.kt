package kirilov.me.hotel.server.entity.client.people

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("people")
@CrossOrigin(origins = ["http://localhost:3000"])
class PeopleController(
    @Autowired private val service: PeopleService
) {
    @GetMapping
    fun getAll(): Iterable<People> {
        return service.getAll()
    }

    @PostMapping
    fun add(@RequestBody people: People): People {
        return service.add(people)
    }

    @PutMapping
    fun edit(@RequestBody people: People): People {
        return service.edit(people)
    }

    @DeleteMapping
    fun delete(@RequestParam id: UUID) {
        return service.delete(id)
    }
}