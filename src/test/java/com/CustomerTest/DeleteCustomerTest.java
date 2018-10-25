package com.CustomerTest;

import Base.BaseCustomer;
import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Epic("Customers")
@Feature("Customer")
public class DeleteCustomerTest extends BaseCustomer {

    private Faker faker = new Faker();

    @Test
    public void deleteCustomer() {
        Map<String, String> customer = new HashMap<String, String>();
        customer.put("firstname", faker.name().firstName());
        customer.put("lastname", faker.name().lastName());
        customer.put("phone", faker.phoneNumber().cellPhone());

        Response response = given()
                .body(customer)
                .post();

        given().
                pathParam("id", response.jsonPath().get("id")).
                when().
                delete("/{id}").
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteCustomerUsingExtract() {

        Map<String, String> customer = new HashMap<String, String>();
        customer.put("firstname", faker.name().firstName());
        customer.put("lastname", faker.name().lastName());
        customer.put("phone", faker.phoneNumber().cellPhone());

        int id = given()
                .body(customer)
                .post()
                .then()
                .extract().path("id");

        given().
                pathParam("id", id).
                when().
                delete("/{id}").
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);
    }
}
