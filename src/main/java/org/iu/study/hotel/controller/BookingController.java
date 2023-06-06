package org.iu.study.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.iu.study.hotel.dtos.BookingDTO;
import org.iu.study.hotel.dtos.CustomerDTO;
import org.iu.study.hotel.entities.Booking;
import org.iu.study.hotel.entities.Customer;
import org.iu.study.hotel.exceptions.DatabaseEntityNotFoundException;
import org.iu.study.hotel.repository.BookingRepository;
import org.iu.study.hotel.repository.CustomerRepository;
import org.iu.study.hotel.service.BookingService;
import org.iu.study.hotel.utilities.NotificationMessages;
import org.iu.study.hotel.service.NotificationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;

    @GetMapping(path = "/customers/{customerId}/bookings")
    public ModelAndView getCustomerBookings(@PathVariable long customerId){
        final var customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("customer", customerId));
        ModelAndView mav = new ModelAndView("bookings");
        mav.addObject("customer", new CustomerDTO(customer));
        mav.addObject("bookings", bookingService.getAllBookingDTOs(customer));
        return mav;
    }

    @PostMapping(path = "/customers/{customerId}/bookings")
    public ModelAndView createBooking(
            @PathVariable long customerId,
            @ModelAttribute("editedCustomer") @NotNull BookingDTO newBooking){
        final var customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("customer", customerId));

        Booking bookingToCreate = new Booking();
        bookingToCreate.setPersonCount(newBooking.getPersonCount());
        bookingToCreate.setStartDate(newBooking.getStartDate());
        bookingToCreate.setEndDate(newBooking.getEndDate());
        bookingToCreate.setCustomer(customer);
        bookingRepository.save(bookingToCreate);

        ModelAndView mav = new ModelAndView("bookings");
        mav.addObject("bookings", bookingService.getAllBookingDTOs(customer));
        mav.addObject("customer", new CustomerDTO(customer));
        mav.addObject("newCustomer", new CustomerDTO());
        notificationService.addSuccessProperties(mav, NotificationMessages.CREATED_BOOKING_SUCCESSFULLY);
        return mav;
    }

    @GetMapping(path = "/bookings/{bookingId}/edit")
    public ModelAndView editCustomerBooking(@PathVariable long bookingId){
        return bookingService.getBookingEditModel(bookingId);
    }

    @PostMapping(path = "/bookings/{bookingId}/edit")
    public ModelAndView editCustomerBooking(
            @PathVariable long bookingId,
            @ModelAttribute("editedBooking") @NotNull BookingDTO editedBooking){
        final var bookingToEdit = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("booking", bookingId));
        bookingToEdit.setPersonCount(editedBooking.getPersonCount());
        bookingToEdit.setEndDate(editedBooking.getEndDate());
        bookingToEdit.setStartDate(editedBooking.getStartDate());
        bookingRepository.save(bookingToEdit);

        final var mav = bookingService.getBookingEditModel(bookingId);
        notificationService.addSuccessProperties(mav, NotificationMessages.SAVED_BOOKING_SUCCESSFULLY);
        return mav;
    }

    @GetMapping(path = "/bookings/{bookingId}/delete")
    public ModelAndView deleteBooking(@PathVariable long bookingId){
        final var bookingToDelete = bookingRepository
                .findById(bookingId)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("booking", bookingId));
        final var customer = bookingToDelete.getCustomer();
        bookingRepository.delete(bookingToDelete);

        ModelAndView mav = new ModelAndView();
        mav.addObject("bookings", bookingService.getAllBookingDTOs(customer));
        mav.addObject("customer", new CustomerDTO(customer));
        notificationService.addSuccessProperties(mav, NotificationMessages.DELETED_BOOKING_SUCCESSFULLY);
        mav.setViewName("bookings");
        return mav;
    }
}
