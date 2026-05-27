package com.automation.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    int count = 0;
    int maxRetry = 2;

    @Override
    public boolean retry(ITestResult result) {
        if(count < maxRetry) {
            count++;
            System.out.println("🔄 Retrying: "
                + result.getName()
                + " → Attempt " + count + " of " + maxRetry);
            return true;
        }
        return false;
    }
}