package api_testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class _02_LogInTypes {

    /**
     * Log in to octoperf and use full path url to perform a sign in
     */

    @Test
    public void testUsingFullPath(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?username=tla.jiraone@gmail.com&password=test12")
                .then()
                .assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    /**
     * Log in Using Map to verity contenty type and status code
     * Map stores => key an value pairs Hashmap implements Maps.
     */
    @Test
    public void useMapsToLogIn(){
        RestAssured.baseURI = "https://api.octoperf.com";
        String path = "/public/users/login";

        // Writing Map with String, String values
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("username", "tla.jiraone@gmail.com");
        map.put("password", "test12");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(200).log();
    }

    /**
     * Log in using query param
     */

    @Test
    public void useParam(){
        RestAssured.baseURI = "https://api.octoperf.com";
        String path = "/public/users/login";

        RestAssured.given()
                .queryParam("username","tla.jiraone@gmail.com")
                .queryParam("password", "test12")
                .when()
                .post(path)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .statusCode(200);
    }
}
