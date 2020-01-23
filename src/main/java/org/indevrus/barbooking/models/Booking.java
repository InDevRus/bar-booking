package org.indevrus.barbooking.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Calendar;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"tableNumber", "date", "timespan"}))
public class Booking {
    @GeneratedValue
    @Id
    private int id;

    @Positive
    @Max(10)
    @Column(nullable = false)
    private int tableNumber;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Calendar date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingTimeSpan timespan;

    @Pattern(regexp = "\\d{10}")
    @Column(nullable = false)
    private String phoneNumber;

    @Pattern(regexp = "[А-ЯЁ]{1,12}")
    @Column(nullable = false)
    private String name;

    public Calendar getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public BookingTimeSpan getTimespan() {
        return timespan;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void setTimespan(BookingTimeSpan timespan) {
        this.timespan = timespan;
    }
}
