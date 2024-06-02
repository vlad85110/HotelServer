package kirilov.me.hotel.server.entity.client.organization

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("organization")
@CrossOrigin(origins = ["http://localhost:3000"])
class OrganizationController(
    @Autowired private val service: OrganizationService,
) {
    @GetMapping
    fun getAll(): Iterable<Organization> {
        return service.getAll()
    }

    @PostMapping
    fun add(@RequestBody organization: Organization): Organization {
        return service.add(organization)
    }

    @PutMapping
    fun edit(@RequestBody organization: Organization): Organization {
        return service.edit(organization)
    }

    @DeleteMapping
    fun delete(@RequestParam id: UUID) {
        return service.delete(id)
    }
}