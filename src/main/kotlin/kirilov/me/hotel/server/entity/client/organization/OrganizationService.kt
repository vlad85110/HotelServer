package kirilov.me.hotel.server.entity.client.organization

import kirilov.me.hotel.server.exception.OrganizationEntityException
import kirilov.me.hotel.server.exception.PeopleEntityException
import kirilov.me.hotel.server.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrganizationService(
    @Autowired private val organizationRepository: OrganizationRepository
) {
    fun getAll(): Iterable<Organization> {
        return organizationRepository.findAll()
    }

    fun add(organization: Organization): Organization {
        if (organizationRepository.findByInn(organization.inn).isPresent) {
            throw OrganizationEntityException("Организация с таким ИНН уже существует")
        }

        return organizationRepository.save(organization)
    }

    fun edit(organization: Organization): Organization {
        val id = organization.id ?: throw ResourceNotFoundException("no id")

        val optOrgToUpdate = organizationRepository.findById(id)
        if (optOrgToUpdate.isEmpty) {
            throw ResourceNotFoundException()
        }

        val orgToUpdate = optOrgToUpdate.get()

        if (orgToUpdate.inn != organization.inn
            && organizationRepository.findByInn(organization.inn).isPresent
        ) {
            throw OrganizationEntityException("Организация с таким ИНН уже существует")
        }

        return organizationRepository.save(
            Organization(
                orgToUpdate.id,
                organization.name,
                organization.inn
            )
        )
    }

    fun delete(id: UUID) {
        organizationRepository.deleteById(id)
    }
}