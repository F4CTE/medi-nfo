package com.b3al.med.medi_nfo.address;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressDTO {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String street;

    @NotNull
    @Size(max = 50)
    private String zipcode;

    @NotNull
    @Size(max = 255)
    private String country;

    @Size(max = 100)
    private String city;

    @NotNull
    private Long user;

}
