package createProject;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_OK;

public class endToEndApiTestProject {

    // In TestNG what is one of the annotations that allows us to runs the Tests before each Test

    @BeforeTest
    public String token(){
        RestAssured.baseURI = "https://api.octoperf.com";
        String path = "/public/users/login";

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("username", "tla.jiraone@gmail.com");
        map.put("password", "test12");

        return RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .statusCode(SC_OK)
                .extract() //Method that extracts the response JSON DATA
                .body() // Body extracted as a JSON format
                .jsonPath() // Navigate using jsonPath
                .get("expiresInSec"); // Get the value as a key token
    }
}
