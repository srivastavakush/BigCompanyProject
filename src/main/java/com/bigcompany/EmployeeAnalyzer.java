package com.bigcompany;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeAnalyzer {
    private Map<Integer, Employee> employees = new HashMap<>();
    private Map<Integer, List<Employee>> managerToSubordinates = new HashMap<>();

    public void loadFromCSV(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));
        lines.remove(0); // remove header

        for (String line : lines) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0].trim());
            String firstName = parts[1].trim();
            String lastName = parts[2].trim();
            double salary = Double.parseDouble(parts[3].trim());
            Integer managerId = parts.length > 4 && !parts[4].trim().isEmpty()
                    ? Integer.parseInt(parts[4].trim())
                    : null;

            Employee e = new Employee(id, firstName, lastName, salary, managerId);
            employees.put(id, e);
            if (managerId != null) {
                managerToSubordinates.computeIfAbsent(managerId, k -> new ArrayList<>()).add(e);
            }
        }
    }

    public void analyzeSalaries() {
        for (var entry : managerToSubordinates.entrySet()) {
            Employee manager = employees.get(entry.getKey());
            List<Employee> subs = entry.getValue();

            double avgSubSalary = subs.stream().mapToDouble(Employee::getSalary).average().orElse(0);
            double minRequired = avgSubSalary * 1.2;
            double maxAllowed = avgSubSalary * 1.5;

            if (manager.getSalary() < minRequired) {
                System.out.printf("Manager %s earns %.2f less than required.%n",
                        manager, minRequired - manager.getSalary());
            } else if (manager.getSalary() > maxAllowed) {
                System.out.printf("Manager %s earns %.2f more than allowed.%n",
                        manager, manager.getSalary() - maxAllowed);
            }
        }
    }

    public void analyzeReportingChains() {
        for (Employee e : employees.values()) {
            int depth = getDepth(e);
            if (depth > 4) {
                System.out.printf("Employee %s has %d managers too many.%n", e, depth - 4);
            }
        }
    }

    private int getDepth(Employee e) {
        int count = 0;
        Integer mgr = e.getManagerId();
        while (mgr != null) {
            count++;
            Employee manager = employees.get(mgr);
            if (manager == null) break;
            mgr = manager.getManagerId();
        }
        return count;
    }
}
