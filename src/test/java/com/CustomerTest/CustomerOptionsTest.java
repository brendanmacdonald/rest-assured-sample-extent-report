package com.CustomerTest;

import Base.BaseCustomer;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isEmptyString;

@Epic("Customers")
@Feature("Customer")
public class CustomerOptionsTest extends BaseCustomer {

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Customer Options Test")
    @Story("This is a CustomerOptionsTest story")
    public void verifyCustomerOptionsTest() {
        given().
                when().
                options().
                then().
                statusCode(204).
                header("Access-Control-Allow-Methods",
                        containsString("GET,HEAD,PUT,PATCH,POST,DELETE")).
                body(isEmptyString());
    }
}
