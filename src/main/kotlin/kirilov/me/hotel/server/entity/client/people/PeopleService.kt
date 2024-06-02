package kirilov.me.hotel.server.entity.client.people

import kirilov.me.hotel.server.exception.PeopleEntityException
import kirilov.me.hotel.server.exception.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class PeopleService(
    @Autowired private val peopleRepository: PeopleRepository,
    @Autowired private val peopleCrudRepository: CrudRepository<People, UUID>
) {
    fun getAll(): Iterable<People> {
        return peopleCrudRepository.findAll()
    }

    fun add(people: People): People {
        if (peopleRepository.findByPassportNumber(people.passportNumber) != null) {
            throw PeopleEntityException("Клиент с таким номером паспорта уже существует")
        }

        return peopleCrudRepository.save(people)
    }

    fun edit(people: People): People {
        val id = people.id ?: throw ResourceNotFoundException("no id")

        val optPeopleToUpdate = peopleCrudRepository.findById(id)
        if (optPeopleToUpdate.isEmpty) {
            throw ResourceNotFoundException()
        }

        val peopleToUpdate = optPeopleToUpdate.get()

        if (peopleToUpdate.passportNumber != people.passportNumber
            && peopleRepository.findByPassportNumber(people.passportNumber) != null) {
            throw PeopleEntityException("Клиент с таким номером паспорта уже существует")
        }

        return peopleCrudRepository.save(People(
            peopleToUpdate.id,
            people.firstName,
            people.lastName,
            people.birthdayDate,
            people.passportNumber
        ))
    }

    fun delete(id: UUID) {
        peopleCrudRepository.deleteById(id)
    }

    fun getById(peopleId: UUID): People {
        val res = peopleCrudRepository.findById(peopleId)
        if (res.isEmpty) throw ResourceNotFoundException()
        return res.get()
    }
}