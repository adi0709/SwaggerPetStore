package assertions;

import enums.PetStatus;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import payload.petPayload.PetData;
import payload.storepayload.StoreData;
import payload.userPayload.UserData;

public class Assertions {

    public static void statusCodeAssertion(int status, Response response){
        Assert.assertEquals(response.statusCode(), status);
    }

    public static void createPetAssertions(Response createPetResponse, PetData petPayload, String path){
        Assert.assertEquals(createPetResponse.jsonPath().getInt("id"), petPayload.getId());
        Assert.assertEquals(createPetResponse.jsonPath().get("name"), petPayload.getName());
        Assert.assertEquals(createPetResponse.jsonPath().get("status"), petPayload.getStatus());
        Assert.assertEquals(createPetResponse.jsonPath().get("category.id"),petPayload.getCategory().getId());
        Assert.assertEquals(createPetResponse.jsonPath().get("category.name"),petPayload.getCategory().getName());
        Assert.assertEquals(createPetResponse.jsonPath().get("photoUrls"),petPayload.getPhotoUrls());
        for (int i = 0; i <petPayload.getTags().size() ; i++) {
            Assert.assertEquals(createPetResponse.jsonPath().getList("tags.id").get(i), petPayload.getTags().get(i).getId());
            Assert.assertEquals(createPetResponse.jsonPath().getList("tags.name").get(i), petPayload.getTags().get(i).getName());
        }
        createPetResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));

    }

    public static void getPetsByStatusAssertions(Response getPetByStatusResponse,String path) {
        for (int i = 0; i < getPetByStatusResponse.jsonPath().getList("status").size(); i++) {
            Assert.assertEquals(getPetByStatusResponse.jsonPath().getList("status").get(i),(PetStatus.AVAILABLE).toString());
        }
        getPetByStatusResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void updatePetAssertions(Response updatePetResponse, PetData petPayload, String path){
        Assert.assertEquals(updatePetResponse.jsonPath().getInt("id"), petPayload.getId());
        Assert.assertEquals(updatePetResponse.jsonPath().get("name"), petPayload.getName());
        Assert.assertEquals(updatePetResponse.jsonPath().get("status"), petPayload.getStatus());
        Assert.assertEquals(updatePetResponse.jsonPath().get("category.id"),petPayload.getCategory().getId());
        Assert.assertEquals(updatePetResponse.jsonPath().get("category.name"),petPayload.getCategory().getName());
        Assert.assertEquals(updatePetResponse.jsonPath().get("photoUrls"),petPayload.getPhotoUrls());
        for (int i = 0; i <petPayload.getTags().size() ; i++) {
            Assert.assertEquals(updatePetResponse.jsonPath().getList("tags.id").get(i), petPayload.getTags().get(i).getId());
            Assert.assertEquals(updatePetResponse.jsonPath().getList("tags.name").get(i), petPayload.getTags().get(i).getName());
        }
        updatePetResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));

    }

    public static void updatePetByPetIdAssertions(Response updatePetByPetIdResponse, PetData petPayload, String path){
        Assert.assertEquals(updatePetByPetIdResponse.jsonPath().getInt("message"), petPayload.getId());
        updatePetByPetIdResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void deletePetByPetIdAssertions(Response deletePetResponse, PetData petPayload, String path){
        Assert.assertEquals(deletePetResponse.jsonPath().getInt("message"), petPayload.getId());
        deletePetResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void createUserAssertions(Response createUserResponse, UserData userPayload, String path){
        Assert.assertEquals(Integer.parseInt(createUserResponse.jsonPath().get("message")),userPayload.getId());
        createUserResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void findUserByUserNameAssertions(Response getUserResponse, UserData userPayload, String path){
        Assert.assertEquals(getUserResponse.jsonPath().getInt("id"),userPayload.getId());
        Assert.assertEquals(getUserResponse.jsonPath().get("username"),userPayload.getUsername());
        Assert.assertEquals(getUserResponse.jsonPath().get("firstName"),userPayload.getFirstName());
        Assert.assertEquals(getUserResponse.jsonPath().get("lastName"),userPayload.getLastName());
        Assert.assertEquals(getUserResponse.jsonPath().get("email"),userPayload.getEmail());
        Assert.assertEquals(getUserResponse.jsonPath().get("password"),userPayload.getPassword());
        Assert.assertEquals(getUserResponse.jsonPath().get("phone"),userPayload.getPhone());
        Assert.assertEquals(getUserResponse.jsonPath().getInt("userStatus"),userPayload.getUserStatus());

        getUserResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void updateUserAssertions(Response updateUserResponse, UserData userPayload, String path){
        Assert.assertEquals(updateUserResponse.jsonPath().getInt("message"),userPayload.getId());
        updateUserResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void deleteUserAssertions(Response deleteUserResponse, UserData userPayload, String path){
        Assert.assertEquals(deleteUserResponse.jsonPath().get("message"),userPayload.getUsername());
        deleteUserResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void createStoreOrderAssertions(Response createOrderResponse, StoreData storePayload, String path){
        Assert.assertEquals(createOrderResponse.jsonPath().getInt("id"), storePayload.getId());
        Assert.assertEquals(createOrderResponse.jsonPath().getInt("petId"), storePayload.getPetId());
        Assert.assertEquals(createOrderResponse.jsonPath().getInt("quantity"), storePayload.getQuantity());
        Assert.assertEquals(createOrderResponse.jsonPath().get("shipDate").toString().replace("+0000", "Z"), storePayload.getShipDate());
        Assert.assertEquals(createOrderResponse.jsonPath().get("status"), storePayload.getStatus());
        Assert.assertTrue(createOrderResponse.jsonPath().getBoolean("complete"));

        createOrderResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void getOrderByOrderIdAssertions(Response createOrderResponse, StoreData storePayload, String path){
        Assert.assertEquals(createOrderResponse.jsonPath().getInt("id"), storePayload.getId());
        Assert.assertEquals(createOrderResponse.jsonPath().getInt("petId"), storePayload.getPetId());
        Assert.assertEquals(createOrderResponse.jsonPath().getInt("quantity"), storePayload.getQuantity());
        Assert.assertEquals(createOrderResponse.jsonPath().get("shipDate").toString().replace("+0000", "Z"), storePayload.getShipDate());
        Assert.assertEquals(createOrderResponse.jsonPath().get("status"), storePayload.getStatus());
        Assert.assertTrue(createOrderResponse.jsonPath().getBoolean("complete"));

        createOrderResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void deleteStoreOrderAssertions(Response deletePetOrderResponse, StoreData storePayload, String path){
        Assert.assertEquals(deletePetOrderResponse.jsonPath().getInt("message"),storePayload.getId());
        deletePetOrderResponse.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }

    public static void incorrectCreateOrderAssertions(Response createIncorrectPetOrder,String path){
        Assert.assertEquals(createIncorrectPetOrder.jsonPath().get("message"),"something bad happened");
        createIncorrectPetOrder.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(path));
    }
}
