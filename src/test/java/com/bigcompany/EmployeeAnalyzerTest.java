package com.bigcompany;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class EmployeeAnalyzerTest {
    @Test
    public void testFileLoad() throws IOException {
        EmployeeAnalyzer analyzer = new EmployeeAnalyzer();
        analyzer.loadFromCSV("src/test/resources/employees.csv");
        assertTrue(analyzer != null);
    }
}
