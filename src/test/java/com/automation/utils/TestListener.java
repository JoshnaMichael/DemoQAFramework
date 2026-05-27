package com.automation.utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n▶ STARTED  → " 
            + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✅ PASSED   → " 
            + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("❌ FAILED   → " 
            + result.getName());
        System.out.println("   Reason  → " 
            + result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⏭ SKIPPED  → " 
            + result.getName());
    }

    @Override
    public void onFinish(org.testng.ITestContext context) {
        System.out.println("\n========== SUMMARY ==========");
        System.out.println("✅ Passed  : " 
            + context.getPassedTests().size());
        System.out.println("❌ Failed  : " 
            + context.getFailedTests().size());
        System.out.println("⏭ Skipped : " 
            + context.getSkippedTests().size());
        System.out.println("=============================\n");
    }
}