package com.pluralsight.dealershipmanager;

import model.Contract;
import model.SalesContract;
import model.Vehicle;
import model.Dealership;
import model.LeaseContract;
import ui.UserInterface;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Program {
    public static void main(String[] args) {

        Vehicle vehicle1 = new Vehicle(1234,2024, "Honda", "Civic", "Car", "blue", 200023, new BigDecimal("995.00"));
        SalesContract s1 = new SalesContract(LocalDate.now(), "JP", "gmail.com", vehicle1, false);
        System.out.println(s1.getTotalPrice());
        System.out.println(s1.getMonthlyPayment());
        System.out.println(s1.getContractDate());

        UserInterface ui = new UserInterface();
        ui.display();

    }
}
