package kirilov.me.hotel.server.entity.booking

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.awt.print.Book
import java.util.UUID

@Repository
interface BookingRepository : CrudRepository<Booking, UUID> {
    fun findByStartDateAndEndDate(startDate: Long, endDate: Long): List<Booking>

    @Query(
        "SELECT * FROM booking WHERE " +
                "(id IN (:ids)) " +
                "AND ((start_date BETWEEN :sd AND :ed) " +
                "OR (end_date BETWEEN :sd AND :ed) " +
                "OR (start_date <= :sd AND " +
                "end_date >= :ed)) " +
                "ORDER BY start_date;"
    )
    fun findByIdInAndPeriodOrderByStartDate(
        @Param("ids") ids: List<UUID>,
        @Param("sd") startDate: Long,
        @Param("ed") endDate: Long
    ): List<Booking>


    @Query("select count(*) from (select r.id\n" +
            "from (select *\n" +
            "      from booking\n" +
            "      where start_date <= (SELECT EXTRACT(EPOCH FROM clock_timestamp()) * 1000::BIGINT)\n" +
            "        and end_date >= (SELECT EXTRACT(EPOCH FROM clock_timestamp()) * 1000::BIGINT)) t\n" +
            "         join booking_room on t.id = booking_room.booking_id\n" +
            "         join room r on booking_room.room_id = r.id\n" +
            "group by r.id) res;")
    fun getRoomsBusyCount(): Int
}
