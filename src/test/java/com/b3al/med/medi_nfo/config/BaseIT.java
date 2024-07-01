package com.b3al.med.medi_nfo.config;

import com.b3al.med.medi_nfo.MediNfoApplication;
import com.b3al.med.medi_nfo.address.AddressRepository;
import com.b3al.med.medi_nfo.patient.PatientRepository;
import com.b3al.med.medi_nfo.user.UserRepository;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.MariaDBContainer;


/**
 * Abstract base class to be extended by every IT test. Starts the Spring Boot context with a
 * Datasource connected to the Testcontainers Docker instance. The instance is reused for all tests,
 * with all data wiped out before each test.
 */
@SpringBootTest(
        classes = MediNfoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("it")
@Sql({"/data/clearAll.sql", "/data/userData.sql"})
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @ServiceConnection
    private static final MariaDBContainer<?> mariaDBContainer = new MariaDBContainer<>("mariadb:11.4");

    static {
        mariaDBContainer.withUrlParam("serverTimezone", "UTC")
                .withReuse(true)
                .start();
    }

    @LocalServerPort
    public int serverPort;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public AddressRepository addressRepository;

    @Autowired
    public PatientRepository patientRepository;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @SneakyThrows
    public String readResource(final String resourceName) {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
    }

    public String adminJwtToken() {
        // user admin, expires 2040-01-01
        return "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOIl0sImlzcyI6ImJvb3RpZnkiLCJpYXQiOjE3MTgxOTQ5ODYsImV4cCI6MjIwODk4ODgwMH0." +
                "SVv2r_4PwCHO-zLh7yy3ac1ef_ExiXinjONfo3l_GZsrHr4BFNJ4U_U7geci4_30PlvYnlU9gK4QhA-iDc16vw";
    }

    public String userJwtToken() {
        // user user, expires 2040-01-01
        return "Bearer eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9." +
                "eyJzdWIiOiJ1c2VyIiwicm9sZXMiOlsiVVNFUiJdLCJpc3MiOiJib290aWZ5IiwiaWF0IjoxNzE4MTk0OTg2LCJleHAiOjIyMDg5ODg4MDB9." +
                "Y75g7EbgVGuq7PJJyMVYBO2LOV4bCYuXHLQCxHwOGSeX7j846Dk2SBSZHLny0Ltdm85NvZ6qJAFEyyD5WaQPdg";
    }

}
