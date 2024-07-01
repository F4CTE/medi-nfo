package com.b3al.med.medi_nfo.address;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AddressService {

    Page<AddressDTO> findAll(String filter, Pageable pageable);

    AddressDTO get(Long id);

    Long create(AddressDTO addressDTO);

    void update(Long id, AddressDTO addressDTO);

    void delete(Long id);

}
