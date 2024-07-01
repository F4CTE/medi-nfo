package com.b3al.med.medi_nfo.address;

import com.b3al.med.medi_nfo.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Long> {

    Page<Address> findAllById(Long id, Pageable pageable);

    Address findFirstByUser(Patient patient);

}
