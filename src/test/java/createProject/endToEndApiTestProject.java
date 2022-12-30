package createProject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.*;

public class endToEndApiTestProject {

    String memberOf = "/workspaces/member-of";
    String Id;
    String userId;
    Map<String, Object> variables;
    Response response;

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
        response = RestAssured.given()
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
        System.out.println(variables.get("id"));
        System.out.println(variables.get("userID"));

        // Provide Json Payload in String format
        String requestBody = "{\"id\":\"\",\"created\":\"2022-11-09T01:58:29.666Z\",\"lastModified\":\"2001-11-09T01:58:29.666Z\",\"userId\":\""+variables.get("userID")+"\",\"workspaceId\":\""+variables.get("id")+"\",\"name\":\"I did not get the shoes\",\"description\":\"sdfasdfasd\",\"type\":\"DESIGN\",\"tags\":[]}";

        System.out.println(requestBody);
        response = RestAssured.given()
                .headers("Content-type","application/json")
                .header("Authorization",token())
                .and()
                .body(requestBody)
                .when()
                .post("/design/projects")
                .then().log().all()
                .extract()
                .response();

        //Task Assert Name and Description:
        Assert.assertEquals("I did not get the shoes",response.jsonPath().getString("name"));
        Assert.assertEquals("sdfasdfasd",response.jsonPath().getString("description"));
        System.out.print(response.jsonPath().getString("id"));
        variables.put("projectId",response.jsonPath().getString("id"));
    }

    @Test(dependsOnMethods = {"memberOf","createProject"})
    public void updateProject(){
        String updateProject = "{\"id\":\""+variables.get("projectId")+"\",\"created\":\"2022-11-09T01:58:29.666Z\",\"lastModified\":\"2001-11-09T01:58:29.666Z\",\"userId\":\""+variables.get("userID")+"\",\"workspaceId\":\""+variables.get("id")+"\",\"name\":\"@!#\",\"description\":\"sdfasdfasd\",\"type\":\"DESIGN\",\"tags\":[]}";

        System.out.println(updateProject);

        RestAssured.given()
                .header("Content-type", "application/json")
                .header("Authorization", token())
                .and()
                .body(updateProject)
                .when()
                .put("/design/projects/"+variables.get("projectId"))
                .then()
                .log().all();
    }
    @Test(dependsOnMethods = {"memberOf","createProject","updateProject"})
    public void deleteProject(){
        response =  RestAssured.given()
                .header("Authorization",token())
                .when()
                .delete("/design/projects/"+variables.get("projectId"))
                .then()
                .log().all()
                .extract()
                .response();

        //TASK to write an assertion to validate status code
        System.out.println("Deleted project");
        Assert.assertEquals(SC_NO_CONTENT,response.statusCode());
    }
}
















