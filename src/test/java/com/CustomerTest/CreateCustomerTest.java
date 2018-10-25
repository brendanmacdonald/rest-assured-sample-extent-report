package com.CustomerTest;

import Base.BaseCustomer;
import Model.Customer;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static Helpers.Helper.getLast;
import static Helpers.Steps.GetStep;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

@Epic("Customers")
@Feature("Customer")
// Based on - https://github.com/EvgenyMarchuk/Rest-Assured-Allure
public class CreateCustomerTest extends BaseCustomer {

    private Faker faker = new Faker();

    @Test(description="Create a Customer")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create a Customer")
    @Story("This is a story")
    public void createCustomer() {
        GetStep("Create a customer test step");

        Customer customer = Customer.createCustomer();

        Response response = given()
                .contentType(ContentType.JSON)
                .log().all()  // write logs to terminal (all logs in this case)
                .body(customer)
                .post();

        response.then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // Verify new Customer firstname.
        int id = getLast(get().as(Customer[].class)).getId();
        Customer actual = given().get("/{id}", id).as(Customer.class);

        assertEquals(customer.getFirstname(), actual.getFirstname());
        //test.log(LogStatus.PASS, "Step details");
    }

    @Test
    public void createCustomerUsingSet() {

        Customer customer = new Customer();
        customer.setFirstname("Sherlock");
        customer.setLastname("Holmes");
        customer.setPhone("0123 456789");

        Response response = given()
                .contentType(ContentType.JSON)
                .log().all()  // write logs to terminal (all logs in this case)
                .body(customer)
                .post();

        response.then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // Verify new Customer firstname.
        int id = getLast(get().as(Customer[].class)).getId();
        Customer actual = given().get("/{id}", id).as(Customer.class);

        assertEquals(customer.getFirstname(), actual.getFirstname());
    }

    @Test
    public void createCustomerUsingMap() {

        Map<String, String> customer = new HashMap<String, String>();
        customer.put("firstname", faker.name().firstName());
        customer.put("lastname", faker.name().lastName());
        customer.put("phone", faker.phoneNumber().cellPhone());

        Response response = given()
                .contentType(ContentType.JSON)
                .body(customer)
                .post();

        response.then().assertThat().statusCode(HttpStatus.SC_CREATED);

        // Verify new Customer firstname.
        int id = getLast(get().as(Customer[].class)).getId();
        Customer actual = given().log().all().get("/{id}", id).as(Customer.class);

        assertEquals(customer.get("firstname") , actual.getFirstname());
    }
}
