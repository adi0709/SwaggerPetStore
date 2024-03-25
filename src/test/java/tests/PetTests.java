package tests;

import assertions.Assertions;
import endpoints.PetEndpoints;
import enums.PetStatus;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import payload.petPayload.PetData;
import payload.petPayload.PetDataCategory;
import payload.petPayload.PetDataTag;
import setUp.BaseTest;

import java.io.File;


public class PetTests extends BaseTest {


    Faker faker;
    PetData petPayload;
    PetData updatedPetResponse;
    PetDataTag petTag1;
    PetDataTag petTag2;
    File file;

    @BeforeMethod
    public void setup() {
        faker = new Faker();

        //Creating the data for create Pet endpoint
        PetDataCategory petCategory = new PetDataCategory(faker.number().positive(), faker.text().text());
        petTag1 = new PetDataTag(faker.number().positive(), faker.text().text());
        petTag2 = new PetDataTag(faker.number().positive(), faker.text().text());

        petPayload = new PetData(
                faker.number().positive(),
                faker.text().text(),
                petCategory,
                PetStatus.AVAILABLE
        );
        petPayload.setUrls(faker.internet().url(), faker.internet().url(), faker.internet().url());
        petPayload.setTag(petTag1, petTag2);


        //Creating the data for update Pet endpoint
        String updateName = faker.animal().name();
        PetStatus updateStatus = PetStatus.SOLD;
        updatedPetResponse = new PetData(this.petPayload.getId(), updateName, this.petPayload.getCategory(), updateStatus);
        PetDataTag updatePetTag1 = new PetDataTag(faker.number().positive(), faker.text().text());
        PetDataTag updatePetTag2 = new PetDataTag(faker.number().positive(), faker.text().text());
        updatedPetResponse.setUrls(faker.internet().url(), faker.internet().url());
        updatedPetResponse.setTag(updatePetTag1, updatePetTag2);

        //Creating file data for upload photo endpoint
        file = new File("./src/test/resources/photo/pet.jpg");

        //Create new pet for the tests
        Response createPetResponse = PetEndpoints.createPet(petPayload);
        createPetResponse.then().log().all();

        //Adding Assertions to the create Pet endpoint
        Assertions.statusCodeAssertion(200, createPetResponse);
        Assertions.createPetAssertions(createPetResponse, petPayload, config.getProperty("createPetResponseSchema"));
    }


    @Test
    public void createPetTest() {

        //Create Pet
        Response createPetResponse = PetEndpoints.createPet(this.petPayload);
        createPetResponse.then().log().all();

        //Adding Assertions to the create Pet endpoint
        Assertions.statusCodeAssertion(200, createPetResponse);
        Assertions.createPetAssertions(createPetResponse, this.petPayload, config.getProperty("createPetResponseSchema"));
    }

    @Test
    public void findPetByStatus() {

        //Find pet by status tests
        //for loop to get the values of petStatus
        for (PetStatus status : PetStatus.values()) {
            //for loop for the iterations
            for (int i = 0; i < PetStatus.values().length; i++) {
                petPayload.setStatus(status);
                //Created Pet for each status type
                Response createPetResponse = PetEndpoints.createPet(this.petPayload);
            }

            Response getPetByStatusResponse = PetEndpoints.getPetByStatus(status, null, null);
            getPetByStatusResponse.then().log().all();

            //Adding Assertion to confirm all the provided haven the same status as provided in test
            Assertions.statusCodeAssertion(200, getPetByStatusResponse);
            Assertions.getPetsByStatusAssertions(getPetByStatusResponse, config.getProperty("getPetByStatusResponseSchema"), status);
        }
    }

    @Test
    public void updatePet() {
        //Updated the created pet data
        Response updatePetResponse = PetEndpoints.updatePet(this.updatedPetResponse);
        updatePetResponse.then().log().all();
        Assertions.statusCodeAssertion(200, updatePetResponse);
        Assertions.updatePetAssertions(updatePetResponse, this.updatedPetResponse, config.getProperty("createPetResponseSchema"));
    }

    @Test
    public void updatePetByPetId() {
        //Create data for the update pet endpoint
        String updateName = faker.animal().name();
        String updateStatus = "SOLD";

        //Update pet endpoint by petId
        Response updatePetByPetIdResponse = PetEndpoints.updatePetByPetId(this.petPayload.getId(), updateName, updateStatus);
        updatePetByPetIdResponse.then().log().all();
        Assertions.statusCodeAssertion(200, updatePetByPetIdResponse);
        Assertions.updatePetByPetIdAssertions(updatePetByPetIdResponse, this.petPayload, config.getProperty("updatePetByPetIdResponseSchema"));

    }


    @Test
    public void deletePet() {
        //Delete pet
        Response deletePetResponse = PetEndpoints.deletePet(this.petPayload.getId());
        deletePetResponse.then().log().all();
        Assertions.statusCodeAssertion(200, deletePetResponse);
        Assertions.deletePetByPetIdAssertions(deletePetResponse, this.petPayload, config.getProperty("deletePetResponseSchema"));
    }

    @Test
    public void deletingSameUser() {
        //Delete the user
        Response deletePetResponse = PetEndpoints.deletePet(this.petPayload.getId());
        deletePetResponse.then().log().all();
        Assertions.statusCodeAssertion(200, deletePetResponse);
        Assertions.deletePetByPetIdAssertions(deletePetResponse, this.petPayload, config.getProperty("deletePetResponseSchema"));

        //Trying to Delete the user again
        Response deleteAgainResponse = PetEndpoints.deletePet(this.petPayload.getId());
        deleteAgainResponse.then().log().all();
        Assertions.statusCodeAssertion(404, deleteAgainResponse);

    }

    @Test
    public void uploadPetPhoto() {

        //Create Pet endpoint
        Response createUserResponse = PetEndpoints.createPet(this.petPayload);
        createUserResponse.then().log().all();
        Assertions.statusCodeAssertion(200, createUserResponse);

        //Upload the photo for created Pet
        Response uploadPetPhotoResponse = PetEndpoints.uploadPetImage(this.petPayload.getId(), this.file);
        uploadPetPhotoResponse.then().log().all();
        Assertions.statusCodeAssertion(200, uploadPetPhotoResponse);
    }

}