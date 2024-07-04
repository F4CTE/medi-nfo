package com.b3al.med.medi_nfo.patient;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientExtDTO {

        @NotNull
        @Size(max = 50)
        @PatientSsnUnique
        private String ssn;

        @NotNull
        @Size(max = 255)
        private String firstName;

        @NotNull
        @Size(max = 255)
        private String lastName;

        @NotNull
        private LocalDate dob;

        @NotNull
        private Gender gender;


}
