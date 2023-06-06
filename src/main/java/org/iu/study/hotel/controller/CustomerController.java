package org.iu.study.hotel.controller;

import lombok.RequiredArgsConstructor;
import org.iu.study.hotel.dtos.CustomerDTO;
import org.iu.study.hotel.entities.Customer;
import org.iu.study.hotel.exceptions.DatabaseEntityNotFoundException;
import org.iu.study.hotel.repository.CustomerRepository;
import org.iu.study.hotel.service.CustomerService;
import org.iu.study.hotel.utilities.NotificationMessages;
import org.iu.study.hotel.service.NotificationService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final NotificationService notificationService;

    @GetMapping(path = "/")
    public RedirectView getHomepage(){
        return new RedirectView("/customers");
    }

    @GetMapping(path = "/customers")
    public String getCustomerList(@NotNull Model model){
        model.addAttribute("customers", customerService.getAllCustomerDTOs());
        model.addAttribute("newCustomer", new CustomerDTO());
        return "customers";
    }

    @PostMapping(path = "/customers")
    public ModelAndView createCustomer(@ModelAttribute("newCustomer") @NotNull CustomerDTO newCustomer){
        Customer customerToCreate = new Customer();
        customerToCreate.setFirstName(newCustomer.getFirstName());
        customerToCreate.setLastName(newCustomer.getLastName());
        customerRepository.save(customerToCreate);

        ModelAndView mav = new ModelAndView("customers");
        mav.addObject("customers", customerService.getAllCustomerDTOs());
        mav.addObject("newCustomer", new CustomerDTO());
        notificationService.addSuccessProperties(mav, NotificationMessages.CREATED_CUSTOMER_SUCCESSFULLY);
        return mav;
    }

    @PostMapping(path = "/customers/{customerId}/edit")
    public ModelAndView submitEditedCustomer(
            @PathVariable long customerId,
            @ModelAttribute("editedCustomer") @NotNull CustomerDTO editedCustomer){
        final var customerToEdit = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("customer", customerId));
        customerToEdit.setFirstName(editedCustomer.getFirstName());
        customerToEdit.setLastName(editedCustomer.getLastName());
        customerRepository.save(customerToEdit);

        final var mav = customerService.getCustomerEditModel(customerId);
        notificationService.addSuccessProperties(mav, NotificationMessages.SAVED_CUSTOMER_SUCCESSFULLY);
        return mav;
    }

    @GetMapping(path = "/customers/{customerId}/edit")
    public ModelAndView editCustomer(@PathVariable long customerId){
        return customerService.getCustomerEditModel(customerId);
    }

    @GetMapping(path = "/customers/{customerId}/delete")
    public ModelAndView deleteCustomer(@PathVariable long customerId){
        final var customerToDelete = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("customer", customerId));
        customerRepository.delete(customerToDelete);

        ModelAndView mav = new ModelAndView();
        mav.addObject("customers", customerService.getAllCustomerDTOs());
        notificationService.addSuccessProperties(mav, NotificationMessages.DELETED_CUSTOMER_SUCCESSFULLY);
        mav.setViewName("customers");
        return mav;
    }
}
