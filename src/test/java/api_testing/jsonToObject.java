package api_testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class jsonToObject {

    //Create a scenario where user is Deserializing JSON -> Object
    @Test(description = "Deserialization -> JSON to Object")
    public void readFile() throws FileNotFoundException {
        // Read File using FileReader which will read from the Json File
        FileReader fileReader = new FileReader("src/main/resources/data.json");

        // converting the json file into an object so that we can use it more easily
        // Create a Map from this Json Object
        // We use Gson Library to make this conversion Gson serealizes object to json or json to object
        // json --> its a file type (like xml, pdf, doc, csv)
        // gson --> Library that is used to convert json to object ( serialisation and deserialisation)

        Gson gson = new Gson();

        // We convert the file into a MAP what does it mean
        // data.json contains json data which will be converted into java object(MAP)

        Map<String, Object> mylist = gson.fromJson(fileReader, Map.class);
        System.out.println(mylist);
        Assert.assertEquals("tla", mylist.get("company") );
    }
        // Serialization Object into JSON
    @Test(description = "Serialize --> Object to JSON")
    public static void serializeIntoJson() throws IOException {
        Map<String, Object> newList = new HashMap<>();
        newList.put("name", "Kevin");
        newList.put("id", "123");
        newList.put("course", "API");

        // We will write a file to serialize it.
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter newListJson = new FileWriter("src/test/java/files/newList.json");
        gson.toJson(newList, newListJson); // toJson() will convert Object to Json

        newListJson.flush();
        newListJson.close();
    }
}
