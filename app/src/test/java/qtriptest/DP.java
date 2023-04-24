package qtriptest;

import java.io.IOException;
import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

// public class DP {
//     @DataProvider(name = "data-provider")
//     public static Object[][] dataProviderMethod() throws IOException{
//         int sheetNum=0;
//         return ExcelUtility.getData(sheetNum);
//     }
// }



public class DP {
    @DataProvider(name = "data-provider")
    public static Object[][] dataProviderMethod(Method m) throws IOException{
        return ExcelUtility.getData(m);
    }
}