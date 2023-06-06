package org.iu.study.hotel.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.iu.study.hotel.entities.Booking;
import org.iu.study.hotel.utilities.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class BookingDTO {
    private long id;
    private long customerId;
    private int personCount;

    private String formattedStartDate;
    private String formattedEndDate;

    @DateTimeFormat(pattern = DateUtils.HTML_DATE_PATTER)
    private LocalDate endDate;
    @DateTimeFormat(pattern = DateUtils.HTML_DATE_PATTER)
    private LocalDate startDate;

    public BookingDTO(@NotNull Booking booking){
        this.id = booking.getId();
        this.customerId = booking.getCustomer().getId();

        this.personCount = booking.getPersonCount();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();

        this.formattedStartDate = DateUtils.formatDateGerman(booking.getStartDate());
        this.formattedEndDate = DateUtils.formatDateGerman(booking.getEndDate());
    }
}
