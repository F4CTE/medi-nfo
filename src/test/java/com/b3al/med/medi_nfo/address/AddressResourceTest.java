package com.b3al.med.medi_nfo.address;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.b3al.med.medi_nfo.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class AddressResourceTest extends BaseIT {

    @Test
    @Sql({"/data/patientData.sql", "/data/addressData.sql"})
    void getAllAddresses_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/addresses")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).id", Matchers.equalTo(1200));
    }

    @Test
    @Sql({"/data/patientData.sql", "/data/addressData.sql"})

    void getAllAddresses_filtered() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/addresses?filter=1201")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).id", Matchers.equalTo(1201));
    }

    @Test
    @Sql({"/data/patientData.sql", "/data/addressData.sql"})
    void getAddress_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/addresses/1200")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("street", Matchers.equalTo("Quis nostrud exerci."));
    }

    @Test
    void getAddress_notFound() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/addresses/1866")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql("/data/patientData.sql")

    void createAddress_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/addressDTORequest.json"))
                .when()
                    .post("/api/addresses")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, addressRepository.count());
    }

    @Test
    void createAddress_missingField() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/addressDTORequest_missingField.json"))
                .when()
                    .post("/api/addresses")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("street"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/patientData.sql", "/data/addressData.sql"})

    void updateAddress_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/addressDTORequest.json"))
                .when()
                    .put("/api/addresses/1200")
                .then()
                    .statusCode(HttpStatus.OK.value());
        assertEquals("At vero eos.", addressRepository.findById(((long)1200)).orElseThrow().getStreet());
        assertEquals(2, addressRepository.count());
    }

    @Test
    @Sql({"/data/patientData.sql", "/data/addressData.sql"})

    void deleteAddress_success() {
        RestAssured
                .given()
                    .header(HttpHeaders.AUTHORIZATION, adminJwtToken())
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/addresses/1200")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, addressRepository.count());
    }

}
