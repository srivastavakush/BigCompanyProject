package com.bigcompany;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar bigcompany.jar <path-to-csv>");
            return;
        }

        try {
            EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
            analyzer.loadFromCSV(args[0]);

            System.out.println("\n=== Salary Analysis ===");
            analyzer.analyzeSalaries();

            System.out.println("\n=== Reporting Chain Analysis ===");
            analyzer.analyzeReportingChains();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
