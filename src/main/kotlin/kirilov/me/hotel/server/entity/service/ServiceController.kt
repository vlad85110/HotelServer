package kirilov.me.hotel.server.entity.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("service")
@CrossOrigin(origins = ["http://localhost:3000"])
class ServiceController(
    @Autowired private val service: ServiceService
) {
    @PostMapping
    fun add(@RequestBody service: Service): Service {
        return this.service.add(service)
    }

    @GetMapping
    fun getAll(): Iterable<Service> {
        return service.getAll()
    }

    @GetMapping("orderByType")
    fun getAllOrderByType(): Iterable<Service> {
        return service.getAllOrderByTypes()
    }

    @DeleteMapping
    fun deleteById(@RequestParam id: UUID) {
        return service.deleteById(id)
    }

    @GetMapping("type")
    fun getByType(@RequestParam typeId: UUID): Iterable<Service> {
        return service.getByType(typeId)
    }

    @PostMapping("list")
    fun addServices(@RequestBody services: List<Service>) {
        service.addList(services)
    }

    @PutMapping
    fun editService(@RequestBody service: Service): Service {
        return this.service.editService(service)
    }
}