package kirilov.me.hotel.server.entity.service_type

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("serviceType")
@CrossOrigin(origins = ["http://localhost:3000"])
class ServiceTypeController(
    @Autowired private val service: ServiceTypeService,
) {
    @GetMapping
    fun getAll(): Iterable<ServiceType> {
        return service.getAll()
    }

    @PostMapping
    fun add(@RequestBody serviceType: ServiceType): ServiceType {
        return service.add(serviceType)
    }

    @PutMapping
    fun edit(@RequestBody serviceType: ServiceType): ServiceType {
        return service.edit(serviceType)
    }

    @DeleteMapping
    fun delete(@RequestParam id: UUID) {
        return service.delete(id)
    }
}