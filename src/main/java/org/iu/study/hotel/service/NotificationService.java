package org.iu.study.hotel.service;

import lombok.RequiredArgsConstructor;
import org.iu.study.hotel.repository.CustomerRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final CustomerService customerService;

    public ModelAndView returnSimpleError(@NotNull String error){
        final var mav = new ModelAndView();
        mav.addObject("isError", true);
        mav.addObject("errorMessage", error);
        mav.addObject("customers", customerService.getAllCustomerDTOs());
        mav.setViewName("customers");
        return mav;
    }

    public void addSuccessProperties(@NotNull ModelAndView mav, @NotNull String successMessage){
        mav.addObject("isSuccess", true);
        mav.addObject("successMessage", successMessage);
    }
}
