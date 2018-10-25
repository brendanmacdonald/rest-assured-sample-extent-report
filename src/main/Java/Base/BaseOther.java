package Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import static Helpers.Common.BASE_OTHER_PATH;

public class BaseOther extends BaseRest{

    @BeforeClass
    public void SetUpClass(){
        RestAssured.basePath = BASE_OTHER_PATH;
    }
}
