package com.CustomerTest;

import Base.BaseCustomer;
import Helpers.DataProviderClass;
import Model.Customer;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;

import static Helpers.Helper.getFirst;
import static Helpers.Steps.GetStep;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@Epic("Customers")
@Feature("Customer")
public class SearchCustomerTest extends BaseCustomer {

    @Test
    public void getAllCustomers() {
        given().get();
    }

        @Test
    public void getSpecificCustomer() {
            int id = getFirst(get().as(Customer[].class)).getId();

        ValidatableResponse response = given()
                .get("/{id}", id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("firstname", equalTo("John"))
                .body("lastname", equalTo("Smith"))
                .body("phone", equalTo("219-839-2819"));

        System.out.println(response.extract().body().toString());
    }

    @Test
    public void getAllCustomersTiming() {
        given()
                .when()
                .get()
                .then()
                .time(lessThan(1500L));
    }

    @Test
    public void getAllCustomersUsingLog() {
        given()
                .when()
                .get()
                .then()
                .log() // write logs to terminal (body in this case)
                .body()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void verifyAllCustomersContainsNewCustomer() {
        Customer customer = Customer.createCustomer();
        given()
                .contentType(ContentType.JSON)
                .body(customer)
                .post();

        List<Customer> allCustomers = getAllCustomersList();

        assertThat(allCustomers)
                .extracting("firstname", "lastname", "phone")
                .contains(tuple(customer.getFirstname(), customer.getLastname(), customer.getPhone()));
    }

    @Test(dataProvider = "provideGetCustomerId", dataProviderClass = DataProviderClass.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("New test example Three")
    @Story("This is a data provider story")
    public void getCustomersById(int id){
        GetStep(String.format("Get specific customers by id: %d", id));
        given().
                contentType(ContentType.JSON).
                when().
                get("/{id}", id).
                then().
                statusCode(HttpStatus.SC_OK);
    }

    private List<Customer> getAllCustomersList() {
        return given().get().body().jsonPath().getList("", Customer.class);
    }
}
