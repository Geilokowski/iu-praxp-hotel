package org.iu.study.hotel.service;

import lombok.RequiredArgsConstructor;
import org.iu.study.hotel.dtos.BookingDTO;
import org.iu.study.hotel.dtos.CustomerDTO;
import org.iu.study.hotel.entities.Customer;
import org.iu.study.hotel.exceptions.DatabaseEntityNotFoundException;
import org.iu.study.hotel.repository.BookingRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public List<BookingDTO> getAllBookingDTOs(@NotNull Customer customer){
        return customer.getBookings()
                .stream()
                .map(BookingDTO::new)
                .toList();
    }

    public ModelAndView getBookingEditModel(long bookingId){
        final var bookingToEdit = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("booking", bookingId));
        final var bookingDTOs = bookingRepository.findAll()
                .stream()
                .filter(savedBooking -> savedBooking.getId() != bookingId)
                .map(BookingDTO::new)
                .toList();

        ModelAndView mav = new ModelAndView();
        mav.addObject("editedBooking", new BookingDTO(bookingToEdit));
        mav.addObject("bookings", bookingDTOs);
        mav.addObject("customer", new CustomerDTO(bookingToEdit.getCustomer()));
        mav.setViewName("edit-booking");
        return mav;
    }
}
