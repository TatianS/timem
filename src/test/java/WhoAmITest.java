import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class WhoAmITest extends TestBase {

    private RequestSpecification whoReqSpec;
    private ResponseSpecification whoResSpec;

    @Before
    public void setup() {
        whoResSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    @Test
    public void suTest() {
        //Response response =
        given()
                .spec(suSpec)
                .when()
                .get("/whoami")
                .then()
                .spec(whoResSpec);


    }
}
