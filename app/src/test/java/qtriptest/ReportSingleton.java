package qtriptest;

import java.io.File;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ReportSingleton {
    private static ReportSingleton instance = null;
    private static ExtentReports reports;
    private static ExtentTest test;

    private ReportSingleton(){
        reports = new ExtentReports(System.getProperty("user.dir")+"/ExtentReportResults.html", true);
        reports.loadConfig(new File(System.getProperty("user.dir")+"/extent_customization_configs.xml"));
    }

    public static ReportSingleton getInstanceOfSingletonReport(){
        if(instance == null){
            instance = new ReportSingleton();
        }        
        return instance;
    }

    public ExtentTest createTest(String testName) {
        test = reports.startTest(testName);
        return test;
    }

    public void flushReport() {
        reports.endTest(test);
        reports.flush();
    }
}