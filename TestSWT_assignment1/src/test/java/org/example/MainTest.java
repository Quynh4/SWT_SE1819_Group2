package org.example;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MainTest {

    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private Main main; // You might not need this, as Main contains only static methods

    private Employee nonSalariedEmployee;
    private Employee salariedEmployee;
    private Customer regularCustomer;
    private Customer nonRegularCustomer;
    private Item highEndComputer;
    private Item ultraComputer;
    private Item luxuryCar;
    private Item greenTomato;
    private Item normalCar;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize employees
        nonSalariedEmployee = new Employee(1, "Army", EmployeeType.NON_SALARIED);
        salariedEmployee = new Employee(2, "Senery", EmployeeType.SALARIED);

        // Initialize customers
        regularCustomer = new Customer(1, "Lana", CustomerType.REGULAR);
        nonRegularCustomer = new Customer(2, "Josh", CustomerType.NON_REGULAR);

        // Initialize items
        highEndComputer = new Item(1, "High-end computer", ItemType.OTHER, 10000.0);
        ultraComputer = new Item(2, "Ultra computer", ItemType.OTHER, 10001.0);
        luxuryCar = new Item(3, "Luxury car", ItemType.BONUS, 1001.0);
        greenTomato = new Item(4, "Green tomato", ItemType.STANDARD, 1850.0);
        normalCar = new Item(5, "Normal car", ItemType.BONUS, 1000.0);
    }


    @Test
    void testGetEmployeeById() throws SQLException {
        int employeeId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(employeeId);
        when(mockResultSet.getString("name")).thenReturn("Army");
        when(mockResultSet.getString("employee_type")).thenReturn("NON_SALARIED");

        Employee actualEmployee = Main.getEmployeeById(mockConnection, employeeId);

        assertEquals(employeeId, actualEmployee.getId());
    }

    @Test
    void testGetItemById() throws SQLException {
        int itemId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(itemId);
        when(mockResultSet.getString("name")).thenReturn("High-end computer");
        when(mockResultSet.getString("item_type")).thenReturn("OTHER");
        when(mockResultSet.getDouble("price")).thenReturn(10000.0);

        Item actualItem = Main.getItemById(mockConnection, itemId);

        assertEquals(itemId, actualItem.getId());
    }

    @Test
    void testGetCustomerById() throws SQLException {
        int customerId = 1;

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(customerId);
        when(mockResultSet.getString("name")).thenReturn("Lana");
        when(mockResultSet.getString("customer_type")).thenReturn("REGULAR");

        Customer actualCustomer = Main.getCustomerById(mockConnection, customerId);

        assertEquals(customerId, actualCustomer.getId());
    }

    // Test cases for calculateCommission
    @Test
    void testCalculateCommission1() {
        Transaction transaction = new Transaction(nonSalariedEmployee, greenTomato, nonRegularCustomer);

        double expectedCommission = 0.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission2() {
        Transaction transaction = new Transaction(salariedEmployee, highEndComputer, regularCustomer);

        double expectedCommission = 0.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission3() {
        Transaction transaction = new Transaction(nonSalariedEmployee, highEndComputer, nonRegularCustomer);

        double expectedCommission = 1000.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission4() {
        Transaction transaction = new Transaction(nonSalariedEmployee, ultraComputer, nonRegularCustomer);

        double expectedCommission = 500.05;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission5() {
        Transaction transaction = new Transaction(nonSalariedEmployee, luxuryCar, nonRegularCustomer);

        double expectedCommission = 75.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission6() {
        Transaction transaction = new Transaction(nonSalariedEmployee, normalCar, nonRegularCustomer);

        double expectedCommission = 100.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission7() {
        Transaction transaction = new Transaction(salariedEmployee, normalCar, nonRegularCustomer);

        double expectedCommission = 50.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission8() {
        Transaction transaction = new Transaction(salariedEmployee, luxuryCar, nonRegularCustomer);

        double expectedCommission = 25.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission9() {
        Transaction transaction = new Transaction(salariedEmployee, highEndComputer, nonRegularCustomer);

        double expectedCommission = 1000.0;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testCalculateCommission10() {
        Transaction transaction = new Transaction(salariedEmployee, ultraComputer, nonRegularCustomer);

        double expectedCommission = 500.05;
        double actualCommission = Main.calculateCommission(transaction);

        assertEquals(expectedCommission, actualCommission);
    }

    @Test
    void testMain() throws SQLException {
        String input = "1\n1\n2\n"; // Inputs for employeeId, itemId, customerId
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        try {
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            // Employee mock setup
            when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(1).thenReturn(2);
            when(mockResultSet.getString("name")).thenReturn("Army").thenReturn("High-end computer").thenReturn("Josh");
            when(mockResultSet.getString("employee_type")).thenReturn("NON_SALARIED");
            when(mockResultSet.getString("item_type")).thenReturn("OTHER");
            when(mockResultSet.getDouble("price")).thenReturn(10000.0);
            when(mockResultSet.getString("customer_type")).thenReturn("NON_REGULAR");

            Main.main(null);

            String output = out.toString();
            String lastLine = output.substring(output.lastIndexOf("The calculated commission is: $")).trim();
            assertEquals("The calculated commission is: $1000.0", lastLine);
        } finally {
            System.setIn(System.in);
            System.setOut(originalOut);
        }
    }

}
