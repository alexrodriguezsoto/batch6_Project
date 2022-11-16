package practice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class day_2 {

    // TODO: send a request and validate Status code and Content-type if any.

    // GET pet/findPetsByStatus
    // GET store/getInventory
    // GET https://jsonplaceholder.typicode.com/posts
    // GET https://jsonplaceholder.typicode.com/posts/5


    @Test(description = "content type assertion")
    public void petFindByStatus() {
        RestAssured.given()
                .when()
                .get("https://petstore.swagger.io/v2/pet/findByStatus?status=available")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(200);
        System.out.println("Verified Status code is 200 and Content-type is JSON");
    }

    @Test(description = "content type assertion")
    public void petFindByStatus2() {
        RestAssured.given()
                .when()
                .get("https://petstore.swagger.io/v2/store/inventory")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(200);
        System.out.println("Verified Content-type JSON and status code for inventory successfully");
    }

    @Test(description = "get assertion")
    public void typicodePosts() {
        RestAssured.given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/56")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(200);
        System.out.println("Verified Content-type JSON successfully");
    }
}
