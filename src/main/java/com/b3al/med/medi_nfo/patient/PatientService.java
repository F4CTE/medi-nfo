package com.b3al.med.medi_nfo.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PatientService {

    Page<PatientDTO> findAll(String filter, Pageable pageable);

    PatientDTO get(Long id);

    Long create(PatientDTO patientDTO);

    void update(Long id, PatientDTO patientDTO);

    void delete(Long id);

    boolean ssnExists(String ssn);

}
