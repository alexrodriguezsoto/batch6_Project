package createProject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class endToEndApiTestProject {

    String memberOf = "/workspaces/member-of";
    String Id;
    String userId;
    Map<String, Object> variables;

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
                .get("token"); // Get the value as a key token
    }

    @Test
    public void memberOf(){
        Response response = RestAssured.given()
                .header("Authorization",token())
                .when()
                .get(memberOf)
                .then()
                .statusCode(200)
                .log().all()
                .extract()
                .response();
        System.out.println(response.jsonPath().getString("name[0]"));

        Assert.assertEquals(SC_OK,response.statusCode());
        // TASK add assertion for name
        Assert.assertEquals("new workspace",response.jsonPath().getString("name[1]"));
        Assert.assertEquals("Default",response.jsonPath().getString("name[0]"));

        Id = response.jsonPath().getString("id[1]"); //fasdfjaskdfj2123
        userId = response.jsonPath().getString("userId[1]");// 123k2j34hkjjsdf

        System.out.println(Id);
        System.out.println(userId);

        // Id and userID :( are not accessible in a different Test+
        // What else can I use so I can access those values in other Test
        // 1 - POJOS
        // 2 - ARRAY int [] name = {1,12,3};
        // 3 - Create a Util class with some method that allows us to get data
        // 4 - MAPs -> stores key and value using .put() based on the key we get values by using .get()

        variables = new HashMap<>();
        variables.put("id",Id);
        variables.put("userID", userId);
    }
    @Test(dependsOnMethods = {"memberOf"})
    public void createProject(){
        String requestBody = "{\"id\":\"\",\"created\":\"2022-11-09T01:58:29.666Z\",\"lastModified\":\"2001-11-09T01:58:29.666Z\",\"userId\":\"00000\",\"workspaceId\":\"00000\",\"name\":\"request1beforeupdate\",\"description\":\"requestnumber1\",\"type\":\"DESIGN\",\"tags\":[]}";
        System.out.println(variables.get("id"));

    }



}
