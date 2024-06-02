package kirilov.me.hotel.server.entity.service

import kirilov.me.hotel.server.entity.room.Room
import kirilov.me.hotel.server.exception.ResourceNotFoundException
import kirilov.me.hotel.server.exception.ListAddException
import kirilov.me.hotel.server.exception.ServiceEntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class ServiceService(
    @Autowired private val repository: ServiceRepository
) {
    fun add(service: Service): Service {
        return repository.save(service)
    }

    fun getAll(): Iterable<Service> {
        return repository.findAll()
    }

    fun deleteById(id: UUID) {
        repository.deleteById(id)
    }

    fun getByType(typeId: UUID): Iterable<Service> {
        return repository.findServiceByTypeId(typeId)
    }

    fun getIdByName(name: String): UUID? {
        val res = repository.findByName(name)
        if (res.isPresent) return res.get().id
        return null
    }

    fun addList(services: List<Service>) {
        val exceptionBody = ArrayList<Boolean>()
        val names = ArrayList<String>()

        services.forEach { service -> names.add(service.name) }
        val res = repository.findServicesByNameIn(names).toList()

        if (res.isEmpty()) {
            repository.saveAll(services)
            return
        }

        for (i in 1..services.size) {
            exceptionBody.add(true)
        }

        for (service in res) {
            val serviceIndex = services.indexOf(service)
            exceptionBody[serviceIndex] = false
        }

        throw ListAddException(exceptionBody)
    }

    fun getAllOrderByTypes(): Iterable<Service> {
        return repository.getAllByOrderByTypeId()
    }

    fun editService(service: Service): Service {
        val id = service.id ?: throw ResourceNotFoundException("no id")

        val optServiceToUpdate = repository.findById(id)
        if (optServiceToUpdate.isEmpty) {
            throw ResourceNotFoundException()
        }

        val serviceToUpdate = optServiceToUpdate.get()

        if (serviceToUpdate.name != service.name && repository.findByName(service.name).isPresent) {
            throw ServiceEntityException("Товар или услуга с таким номером уже существует")
        }

        return repository.save(
            Service(
                serviceToUpdate.id,
                service.name,
                service.cost,
                service.typeId,
                service.description,
            )
        )
    }
}