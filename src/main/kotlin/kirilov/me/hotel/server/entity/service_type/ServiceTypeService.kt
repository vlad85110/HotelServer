package kirilov.me.hotel.server.entity.service_type

import kirilov.me.hotel.server.exception.OrganizationEntityException
import kirilov.me.hotel.server.exception.ResourceNotFoundException
import kirilov.me.hotel.server.exception.ServiceTypeEntityException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class ServiceTypeService(
    @Autowired private val serviceTypeRepository: ServiceTypeRepository,
    @Autowired private val serviceTypeCrudRepository: CrudRepository<ServiceType, UUID>
) {
    fun getAll(): Iterable<ServiceType> {
        return serviceTypeCrudRepository.findAll()
    }

    fun add(serviceType: ServiceType): ServiceType {
        if (serviceTypeRepository.findByName(serviceType.name) != null) {
            throw ServiceTypeEntityException("Такой тип уже существует")
        }

        return serviceTypeCrudRepository.save(serviceType)
    }

    fun edit(serviceType: ServiceType): ServiceType {
        val id = serviceType.id ?: throw ResourceNotFoundException("no id")

        val optTypeToUpdate = serviceTypeCrudRepository.findById(id)
        if (optTypeToUpdate.isEmpty) {
            throw ResourceNotFoundException()
        }

        val typeToUpdate = optTypeToUpdate.get()

        if (typeToUpdate.name != serviceType.name
            && serviceTypeRepository.findByName(serviceType.name) != null
        ) {
            throw ServiceTypeEntityException("Такой тип уже существует")
        }

        return serviceTypeCrudRepository.save(
            ServiceType(
                typeToUpdate.id,
                typeToUpdate.name
            )
        )
    }

    fun delete(id: UUID) {
        serviceTypeCrudRepository.deleteById(id)
    }
}