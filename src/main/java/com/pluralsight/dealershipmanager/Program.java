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
        System.out.println("===Sale Transaction===");
        System.out.println(s1.getContractDate());
        System.out.println(s1.getCustomerName());
        System.out.println("Total price: $" + s1.getTotalPrice());
        System.out.println("Monthly payment: $" + s1.getMonthlyPayment()); // this should return 0 since customer isn't financing
        System.out.println();

        Vehicle vehicle2 = new Vehicle(5678, 2000, "Acura", "RSX", "Coupe", "yellow", 500, new BigDecimal("31995.00"));
        LeaseContract l2 = new LeaseContract(LocalDate.now(), "Doug Dimmadome", "@hotmail.com", vehicle2);
        System.out.println("===Lease Transaction===");
        System.out.println(l2.getContractDate());
        System.out.println(l2.getCustomerName());
        System.out.println("Total price: $" + l2.getTotalPrice());
        System.out.println("Monthly payment: $" + l2.getMonthlyPayment());

//        UserInterface ui = new UserInterface();
//        ui.display();

    }
}
