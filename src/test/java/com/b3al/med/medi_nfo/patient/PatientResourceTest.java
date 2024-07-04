package com.b3al.med.medi_nfo.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.b3al.med.medi_nfo.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class PatientResourceTest extends BaseIT {

    @Test
    @Sql("/data/patientData.sql")
    void getAllPatients_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/patients")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).id", Matchers.equalTo(1100));
    }

    @Test
    @Sql("/data/patientData.sql")
    void getAllPatients_filtered() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/patients?filter=1101")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).id", Matchers.equalTo(1101));
    }

    @Test
    @Sql("/data/patientData.sql")
    void getPatient_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/patients/1100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ssn", Matchers.equalTo("No sea takimata."));
    }

    @Test
    void getPatient_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/patients/1766")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }
//
//    @Test
//    void createPatient_success() {
//        RestAssured
//                .given()
//                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
//                    .accept(ContentType.JSON)
//                    .contentType(ContentType.JSON)
//                    .body(readResource("/requests/patientDTORequest.json"))
//                .when()
//                    .post("/api/patients")
//                .then()
//                    .statusCode(HttpStatus.CREATED.value());
//        assertEquals(1, patientRepository.count());
//    }
//
//    @Test
//    void createPatient_missingField() {
//        RestAssured
//                .given()
//                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
//                    .accept(ContentType.JSON)
//                    .contentType(ContentType.JSON)
//                    .body(readResource("/requests/patientDTORequest_missingField.json"))
//                .when()
//                    .post("/api/patients")
//                .then()
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
//                    .body("fieldErrors.get(0).property", Matchers.equalTo("ssn"))
//                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
//    }

    @Test
    @Sql("/data/patientData.sql")
    void updatePatient_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/patientDTORequest.json"))
                .when()
                    .put("/api/patients/1100")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("Consetetur sadipscing.", patientRepository.findById(((long)1100)).orElseThrow().getSsn());
        assertEquals(2, patientRepository.count());
    }

    @Test
    @Sql("/data/patientData.sql")
    void deletePatient_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/patients/1100")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, patientRepository.count());
    }

}
