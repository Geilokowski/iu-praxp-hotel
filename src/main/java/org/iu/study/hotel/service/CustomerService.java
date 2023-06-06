package org.iu.study.hotel.service;

import lombok.RequiredArgsConstructor;
import org.iu.study.hotel.dtos.CustomerDTO;
import org.iu.study.hotel.exceptions.DatabaseEntityNotFoundException;
import org.iu.study.hotel.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomerDTOs(){
        return customerRepository.findAll()
                .stream()
                .map(CustomerDTO::new)
                .toList();
    }

    public ModelAndView getCustomerEditModel(long customerId){
        final var customerToEdit = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new DatabaseEntityNotFoundException("customer", customerId));
        final var customerDTOs = customerRepository.findAll()
                .stream()
                .filter(savedCustomer -> savedCustomer.getId() != customerId)
                .map(CustomerDTO::new)
                .toList();

        ModelAndView mav = new ModelAndView();
        mav.addObject("editedCustomer", new CustomerDTO(customerToEdit));
        mav.addObject("customers", customerDTOs);
        mav.setViewName("edit-customer");
        return mav;
    }
}
