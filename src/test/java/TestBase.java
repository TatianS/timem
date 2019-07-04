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
    static RequestSpecification userSpec;
    private static String suToken;
    private static Properties properties;


    @Before
    public void setupTests() throws IOException {
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://timemaster-dev2.sidenis.local";
        RestAssured.basePath = "/api";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        properties = new Properties();
        if (properties.isEmpty()) {
            properties.load(new FileReader("src\\test\\resources\\app.properties"));
        }

        suSpec = createSpec(UserType.SU);
        userSpec = createSpec(UserType.USR);
    }

    private RequestSpecification createSpec(UserType userType) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookie("sessionid", retrieveToken(properties, userType.toString()))
                .build();
    }

    private static String retrieveToken(Properties properties, String type) {
        return given()
                .with()
                .body(new JSONObject()
                        .put("username", properties.get(type + ".name"))
                        .put("password", properties.get(type + ".password")).toString())
                .contentType(ContentType.JSON)
                .post("/login/").getCookie("sessionid");
    }


    private enum UserType {
        USR("user"),
        SU("su");

        private final String name;

        private UserType(String s) {
            name = s;
        }

        public String toString() {
            return this.name;
        }
    }


}
