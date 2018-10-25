package Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import static Helpers.Common.BASE_CUSTOMERS_PATH;

public class BaseCustomer extends BaseRest{

    @BeforeClass
    public void SetUpClass(){
        RestAssured.basePath = BASE_CUSTOMERS_PATH;
    }
}
