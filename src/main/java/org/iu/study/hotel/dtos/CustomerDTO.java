package org.iu.study.hotel.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.iu.study.hotel.entities.Customer;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private long id;
    private String firstName;
    private String lastName;

    public CustomerDTO(@NotNull Customer customer){
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
    }
}