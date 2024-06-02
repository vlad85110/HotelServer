package kirilov.me.hotel.server.entity.client

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ClientRepository: CrudRepository<Client, UUID> {
}