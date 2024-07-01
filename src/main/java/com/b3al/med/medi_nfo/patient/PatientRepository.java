package com.b3al.med.medi_nfo.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findAllById(Long id, Pageable pageable);

    boolean existsBySsnIgnoreCase(String ssn);

}
