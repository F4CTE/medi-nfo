package com.b3al.med.medi_nfo.address;

import com.b3al.med.medi_nfo.patient.Patient;
import com.b3al.med.medi_nfo.patient.PatientRepository;
import com.b3al.med.medi_nfo.util.NotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AddressMapper {

    @Mapping(target = "user", ignore = true)
    AddressDTO updateAddressDTO(Address address, @MappingTarget AddressDTO addressDTO);

    @AfterMapping
    default void afterUpdateAddressDTO(Address address, @MappingTarget AddressDTO addressDTO) {
        addressDTO.setUser(address.getUser() == null ? null : address.getUser().getId());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Address updateAddress(AddressDTO addressDTO, @MappingTarget Address address,
            @Context PatientRepository patientRepository);

    @AfterMapping
    default void afterUpdateAddress(AddressDTO addressDTO, @MappingTarget Address address,
            @Context PatientRepository patientRepository) {
        final Patient user = addressDTO.getUser() == null ? null : patientRepository.findById(addressDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        address.setUser(user);
    }

}
