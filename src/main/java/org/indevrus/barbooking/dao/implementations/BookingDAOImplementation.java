package org.indevrus.barbooking.dao.implementations;

import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.indevrus.barbooking.dao.BookingDAO;
import org.indevrus.barbooking.models.Booking;
import org.indevrus.barbooking.models.BookingTimeSpan;
import org.indevrus.barbooking.models.QBooking;
import org.indevrus.barbooking.utilities.BookingTimeUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
class BookingDAOImplementation implements BookingDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private JPAQueryFactory queryFactory;

    @Transactional
    @Override
    public void persistAll(Collection<Booking> bookings) {
        bookings.forEach(entityManager::persist);
    }

    @Override
    public Optional<Booking> getByTime(int tableNumber, Calendar calendar, BookingTimeSpan timeSpan) {
        var qBooking = QBooking.booking;

        var found = queryFactory.selectFrom(qBooking)
                .where(qBooking.date.eq(calendar)
                        .and(qBooking.timespan.eq(timeSpan))
                        .and(qBooking.tableNumber.eq(tableNumber)))
                .fetchOne();
        return Optional.ofNullable(found);
    }

    @Override
    public Collection<Booking> findByRequisites(String phoneNumber, String name) {
        var qBooking = QBooking.booking;

        return queryFactory.selectFrom(qBooking)
                .where(qBooking.phoneNumber.eq(phoneNumber)
                        .and(qBooking.name.equalsIgnoreCase(name))
                        .and(qBooking.date.goe(BookingTimeUtilities.getCurrentDate())))
                .fetch();
    }

    @Override
    public Map<Calendar, List<BookingTimeSpan>> getAllBookings(int tableNumber, Calendar start, Calendar end) {
        var qBooking = QBooking.booking;

        return queryFactory.from(qBooking)
                .where(qBooking.tableNumber.eq(tableNumber).and(qBooking.date.between(start, end)))
                .transform(GroupBy.groupBy(qBooking.date).as(GroupBy.list(qBooking.timespan)));
    }

    @Autowired
    public void setQueryFactory(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }
}
