package com.b3al.med.medi_nfo.patient;

import com.b3al.med.medi_nfo.address.Address;
import com.b3al.med.medi_nfo.address.AddressRepository;
import com.b3al.med.medi_nfo.security.MediCalAuthenticationservice;
import com.b3al.med.medi_nfo.util.NotFoundException;
import com.b3al.med.medi_nfo.util.ReferencedWarning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@Slf4j
public class PatientServiceImpl implements PatientService {

    @Value("${medical.server.url}")
    private String medicalserverUrl;

    @Value("${medical.server.patientEndpoint}")
    private String patientsEndpoint;

    @Value("${medical.envtype}")
    private String envtype;


    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AddressRepository addressRepository;

    private final MediCalAuthenticationservice mediCalAuthenticationservice;

    public PatientServiceImpl(final PatientRepository patientRepository,
                              final PatientMapper patientMapper, final AddressRepository addressRepository, MediCalAuthenticationservice mediCalAuthenticationservice) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.addressRepository = addressRepository;
        this.mediCalAuthenticationservice = mediCalAuthenticationservice;
    }

    @Override
    public Page<PatientDTO> findAll(final String filter, final Pageable pageable) {
        Page<Patient> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = patientRepository.findAllById(longFilter, pageable);
        } else {
            page = patientRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(patient -> patientMapper.updatePatientDTO(patient, new PatientDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public PatientDTO get(final Long id) {
        return patientRepository.findById(id)
                .map(patient -> patientMapper.updatePatientDTO(patient, new PatientDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final PatientDTO patientDTO) throws RuntimeException {
        final Patient patient = new Patient();
        patientMapper.updatePatient(patientDTO, patient);
        if(this.envtype.equals("production")) {
            this.addToMedical(patientDTO);
            log.info("successfully added patient to medi-Cal server");
            return patientRepository.save(patient).getId();
        }else {
            log.warn("failed to add patient to medi-Cal server");
            throw new RuntimeException("failed to add patient to medi-Cal server");
        }
    }

    @Override
    public Boolean addToMedical(PatientDTO patientDTO) {
        String apiUrl = medicalserverUrl + patientsEndpoint;

        String token = mediCalAuthenticationservice.authenticate();

        log.info(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        PatientExtDTO patientExtDTO = new PatientExtDTO();
        patientExtDTO = patientMapper.dtoToExt(patientDTO, patientExtDTO);

        HttpEntity<PatientExtDTO> entity = new HttpEntity<>(patientExtDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
        String responseBody = response.getBody();
        log.info(responseBody);
        return response.getStatusCode() == HttpStatusCode.valueOf(201);
    }

    @Override
    public void update(final Long id, final PatientDTO patientDTO) {
        final Patient patient = patientRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        patientMapper.updatePatient(patientDTO, patient);
        patientRepository.save(patient);
    }

    @Override
    public void delete(final Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public boolean ssnExists(final String ssn) {
        return patientRepository.existsBySsnIgnoreCase(ssn);
    }

    @Override
    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Patient patient = patientRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Address userAddress = addressRepository.findFirstByUser(patient);
        if (userAddress != null) {
            referencedWarning.setKey("patient.address.user.referenced");
            referencedWarning.addParam(userAddress.getId());
            return referencedWarning;
        }
        return null;
    }

}
