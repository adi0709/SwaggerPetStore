package endpoints;

import enums.PetStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payload.petPayload.PetData;
import setUp.BaseTest;

import java.io.File;

public class PetEndpoints extends BaseTest {
    public static Response createPet(PetData payload) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(config.getProperty("createPetEndpoint"));
    }

    public static Response deletePet(int petId) {

        return RestAssured
                .given()
                .pathParam("petId", petId)
                .when()
                .delete(config.getProperty("deletePetByPetIdEndpoint"));
    }

    public static Response getPetByStatus(PetStatus status1, PetStatus status2, PetStatus status3) {
        return RestAssured
                .given()
                .queryParam("status", status1, status2, status3)
                .log().all()
                .when()
                .get(config.getProperty("getPetByStatusEndpoint"));
    }

    public static Response updatePetByPetId(int petId, String name, String status) {
        return RestAssured
                .given()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .pathParam("petId", petId)
                .formParam("name", name)
                .formParam("status", status)
                .when()
                .post(config.getProperty("updatePetByPetIdEndpoint"));
    }

    public static Response updatePet(PetData updatePayload) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(updatePayload)
                .when()
                .put(config.getProperty("updatePetEndpoint"));
    }

    public static Response uploadPetImage(int petId, File file){

        return RestAssured
                .given()
                .multiPart("file", file, "multipart/form-data")
                .pathParam("petId", petId)
                .post(config.getProperty("uploadPetPhotoEndpoint"));

    }

}
