import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class WhoAmITest extends TestBase {

    RequestSpecification whoSpec;

    @Before
    public void setup() {
       //  whoSpec = suSpec;
    }

    @Test
    public void suTest() {
        Response response = given().get("https://timemaster-dev2.sidenis.local/api/whoami");
              //  .spec(whoSpec).get(Endpoints.whoami);
        response.prettyPrint();
    }
}
