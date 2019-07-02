import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.RequestBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.runner.Request;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.*;

public class TestBase {

    static RequestSpecification suSpec;

    private static String suToken;


    @Before
    public void setupTests() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://timemaster-dev2.sidenis.local";
        RestAssured.basePath = "/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        System.out.println("Befor!");

//        retrieveTokens();
//        suSpec = new RequestSpecBuilder()
//                .setContentType(ContentType.JSON)
//                .addCookie(suToken)
//                .build();
    }


    private void retrieveTokens() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("C:\\Users\\amil\\TimeMastaTests\\src\\test\\resources\\app.properties"));

        //TODO add another users with different roles

        suToken = getCookie(new JSONObject()
                .put("username", properties.get("su.name"))
                .put("password", properties.get("su.password")));
        System.out.printf("");
    }

    private static String getCookie(JSONObject json) {
    //    Response r = given().with().body(json.toString()).post(Endpoints.login);
        Response r = given().with().body(json.toString()).contentType(ContentType.JSON).post(Endpoints.login);

//        String cookie = given().body(json.toString()).post(Endpoints.login).getCookie("sessionid");
        String cookie = given().contentType(ContentType.JSON).body("{\n" +
                "  \"username\": \"su\",\n" +
                "  \"password\": \"susu\"\n" +
                "}").post(Endpoints.login).getCookie("sessionid");

        //TODO - this probably is not nesessary here
//        int attempts = 0;
//        while (cookie == null && attempts < 10) {
//            cookie = given().body(json.toString()).post(Endpoints.login).getCookie("sessionid");
//            attempts++;
//        }

        return cookie;
    }

    Response getWithToken(String path, UserType user) {
        switch (user) {
            case SU:
                return given().cookie("sessionid", suToken).get(path);
            case USR:
                return null;
        }
        return null;
    }

    public enum UserType {
        SU,
        USR
    }

}
