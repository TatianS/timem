import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.RequestBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
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

        getCookie();

        suSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookie("sessionid",suToken)
                .build();
    }



    private static void getCookie() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("src\\test\\resources\\app.properties"));

        suToken = given()
                .with()
                .body(new JSONObject()
                        .put("username", properties.get("su.name"))
                        .put("password", properties.get("su.password")).toString())
                .contentType(ContentType.JSON)
                .post("/login/").getCookie("sessionid");
        System.out.println("Tokens retrieved!");
    }



}
