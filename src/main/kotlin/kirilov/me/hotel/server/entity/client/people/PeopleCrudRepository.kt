package kirilov.me.hotel.server.entity.client.people

import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface PeopleCrudRepository: CrudRepository<People, UUID> {}