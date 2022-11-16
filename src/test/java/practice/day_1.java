package practice;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class day_1 {

    // TASK: send a request for Authors GET request
    // 1 Unit Test for  prettyPrint() 1 Unit Test for and prettyPeek();

    @Test
    public void getAuthors(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Authors")
                .prettyPrint();
    }

    @Test
    public void getAuthorsPrettyPeek(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Authors")
                .prettyPeek();
    }

    // Task validate status code

    // Task validate content-type

    // TASK: send a request for Activities GET request
    // 1 Unit Test for  prettyPrint() 1 Unit Test for and prettyPeek();
    @Test
    public void getActivities(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .prettyPrint();
    }

    @Test
    public void getActivitiesPreetyPeek(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .prettyPeek();
    }

    // Task validate status code

    // Task validate content-type

}
