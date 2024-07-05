package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import dao.MedicineDAOImpl;
import model.Medicine;

public class Main {
	public static void main(String args[]) throws SQLException {
		
		MedicineDAOImpl dao = new MedicineDAOImpl();
		
		int choice;
		Scanner sc = new Scanner(System.in);
		int id,quantity;
		String name;
		float price;
		
		final String USERNAME = "admin";
	    final String PASSWORD = "1234";
	    
	    System.out.println("Enter username");
		String username = sc.next();
		System.out.println("Enter password");
		String pwd = sc.next();
		
		if (!username.equals(USERNAME) || !pwd.equals(PASSWORD)) {
            System.out.println("Invalid username or password. Exiting.");
            return;
        }
		
		Medicine m;
		
		do {
			System.out.println("Enter your choice :\n1.Create Table Medicines \n2.Insert Record of Medicine \n3. Show All Medicines  \n4. Read the Details of Medicine By Id  \n5.Update the details of Medicine  \n6.Delete the details of particular Medicine \n7.Find the Medicine Name by Id \n8.Exit");
			
			choice = sc.nextInt();
			switch(choice) {
			
			case 1 : 
				dao.connect();
				dao.createMedicinesTable();
				break;
			
			case 2 : 
				dao.connect();
				System.out.println("Enter the details of Medicine id,name,price,quantity ");
				id = sc.nextInt();
				name = sc.next();
				price = sc.nextFloat();
				quantity = sc.nextInt();
				
				m = new Medicine(id,name,price,quantity);
				dao.addMedicines(m);
				break; 
				
			case 3 : 
				dao.connect();
				List<Medicine> medicines = new ArrayList<>();
				medicines = dao.showAllMedicines();
				for(Medicine med1 : medicines)
					System.out.println(med1);
				break; 
				
			case 4 : 
				dao.connect();
				System.out.println("Enter id of Medicine");
				id = sc.nextInt();
				
				Medicine medicine = dao.findById(id);
			    if (medicine != null) {
			        System.out.println("Medicine details: " + medicine);
			    } else {
			        System.out.println("Medicine with id " + id + " not found.");
			    }
			    break;
				
			case 5 : 
				dao.connect();
				System.out.println("Enter the id of Medicine to which you want to perform update operation");
				id = sc.nextInt();
				System.out.println("enter the name,price and quantity for update");
				name = sc.next();
				price = sc.nextFloat();
				quantity = sc.nextInt();
				
				m = new Medicine(id,name,price,quantity);
				dao.updateMedicines(m);
				break; 
				
			case 6 : 
				dao.connect();
				System.out.println("Enter the id of medicine to be deleted");
				id = sc.nextInt();
				dao.deleteMedicines(id);
				break;  
				
			case 7 : 
				dao.connect();
                System.out.println("Enter the ID of the medicine to find:");
                id = sc.nextInt();
                String medicineInfo = dao.findMedicineNameById(id);
                System.out.println("Medicine details: " + medicineInfo);
                break;
				
				}

				
		}while(choice != 8);
			
	}
}

