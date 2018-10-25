package Helpers;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name = "provideGetCustomerId", parallel = true)
    public static Object[][] getCustomerId(){
        return new Object[][]{{1}, {3}, {4}};
    }
}
