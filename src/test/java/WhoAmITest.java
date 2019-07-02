import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WhoAmITest extends TestBase {

    private RequestSpecification whoReqSpec;
    private ResponseSpecification whoResSpec;

    @Before
    public void setup() {
        whoResSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
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
                .spec(whoResSpec)
                .body("id", equalTo(352))
        ;


    }
}
