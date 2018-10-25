package Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.github.javafaker.Faker;

public class Customer {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("phone")
    @Expose
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Customer() {
    }

    public Customer(String firstname, String lastname, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

    public static Customer createCustomer() {
        Customer customer = new Customer();
        Faker faker = new Faker();
        customer.setFirstname(faker.name().firstName());
        customer.setLastname(faker.name().lastName());
        customer.setPhone(faker.phoneNumber().phoneNumber());

        return customer;
    }

    @Override
    public String toString() {
        StringBuilder str =new StringBuilder();
        str.append("Customer: \n");
        str.append(String.format("Firstname: %s\n", getFirstname()));
        str.append(String.format("Lastname: %s\n", getLastname()));
        str.append(String.format("Phone: %s\n", getPhone()));

        return str.toString();
    }

//    public Customer generateCustomer() {
//        String firstname = faker.name().firstName();
//        String lastname = faker.name().lastName();
//        String phone = faker.phoneNumber().cellPhone();
//        return new Customer(firstname, lastname, phone);
//    }
}

