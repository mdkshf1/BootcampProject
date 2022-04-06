package com.bootcampproject.dto;

import com.bootcampproject.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTO extends UserTO{
    @Id
    private Long id;
    @Digits(integer = 10, fraction = 0, message = "only numbers are allowed ")
/*    @Pattern(regexp = "((\\+91)|0)[.\\- ]?[0-9][.\\- ]?[0-9][.\\- ]?[0-9]")*/
    private String contact;

    private Date activationTokenAt;
    private String activationToken;

    private List<Address> addressList;

}