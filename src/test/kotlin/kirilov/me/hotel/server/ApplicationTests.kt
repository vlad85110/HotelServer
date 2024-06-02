package kirilov.me.hotel.server

import kirilov.me.hotel.server.entity.client.people.PeopleRepository
import kirilov.me.hotel.server.entity.service.Service
import kirilov.me.hotel.server.summer.framework.ProcedureCaller
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTests(
    @Autowired val testRepository: PeopleRepository,
    @Autowired val procedureCaller: ProcedureCaller
) {

    @Test
    fun contextLoads() {
        val people = testRepository.findByFirstNameAndLastName(
            "Владислав",
            "Кирилов"
        )

        val peopleArr = testRepository.findAllByPassportNumberBetween(123, 3216726637)
        val people2 = testRepository.findByPassportNumberBetween(123, 3216726637)
        val allPeople = testRepository.findAll()

        Assertions.assertEquals(allPeople.size, 2)

        Assertions.assertEquals(people?.firstName, "Владислав")
        Assertions.assertEquals(people?.lastName, "Кирилов")
        Assertions.assertEquals(peopleArr.size, 2)

        Assertions.assertEquals(people2?.passportNumber!! <= 3216726637, true)
        Assertions.assertEquals(people2.passportNumber >= 123, true)
    }


    @Test
    fun sqlProcedureTest() {
        val name = "get_service_by_id"

        procedureCaller.call(name, Service::class)
    }

}
