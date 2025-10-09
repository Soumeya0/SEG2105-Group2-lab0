/*
	* Author: Ahmad Naween Samandar
	* Student ID: 300446112
	* GROUP 2
	* DATE: October 7, 2025
	* Description: client class for banking system project

*/

import java.util.*;
import java.time.LocalDate;


public class Client{
	// private variable created for each attribute of the Client class
	// these variable will be storing various values for client class that can be further used to perform function for clients
	
	private int clientID;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth; // date of birth and join birth were initially int type, but now I changed it to LocalDate type for smooth fucntioning 
	private LocalDate joinDate;
	private String email;
	private String phone;//first phone was initialized with int type, but after research I found that with int type it may not be able to store leading zero, therefore I changed it to String type
	private ClientCategory category; //this is variable of the enumeration class called ClientCategory
	private int age;
	private List<Account> accounts;
	private Reward rewardAccount;
	
	
	
	
	//Constructor
	//It basically take the important variable on which we perform functions as object
	//these objects will be further used to implement function such as Updating client information (in a method)
	public Client(int clientID, String firstName, String lastName, LocalDate dateOfBirth, String email, String phone, int age){
		this.clientID = clientID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.phone = phone;
		this.age = age;
		this.accounts = new ArrayList<>();
	}
	
	
	
	//getters started from this line 
	
	public int getClientID(){
		return clientID;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public LocalDate getDateOfBirth(){
		return dateOfBirth;
	}
	
	public String getEmail(){
		return email;
	}
	public String getPhone(){
		return phone;
	}
	public ClientCategory getCategory(){
		return category;
	}
	public int getAge(){
		return age;
	}
	public Reward getRewardAccount(){
		return rewardAccount;
	}
	
	public double getTotalBalance(){
		// a vriable total is created initially assigned to zero
		double total = 0;
		//the for loop will check for all account in accoutns list
		for (Account account : accounts){
			// using account's class getBalance getter we will sum it and store it in total
			total += account.getBalance();
		}
		return total;
	}
	
	
	public List<Account> getAccount(){
		return accounts;
	}
	//getters ended in this line
	
	
	
	
	//Setters start from this line
	public void setCategory(ClientCategory category){
		if(category != null){
			this.category = category;
		}
	}
	
	public void setRewardAccount(Reward rewardAccount){
		this.rewardAccount = rewardAccount;
	}
	
	//setters finish in this line
	
	
	
	
	//this method will update information with the help of if condition and constructor
	public void updateContactInfo(String firstName, String lastName, LocalDate dateOfBirth, String email, String phone, int age){
		
		//it checks if the object is not empty
		if(firstName != null && !firstName.isEmpty()){
			//then it will assign a new instance to the object
			this.firstName = firstName;
		}
		if(lastName != null && !lastName.isEmpty()){
			this.lastName = lastName;
		}
		if(dateOfBirth != null){
			this.dateOfBirth = dateOfBirth;
		}
		if(email != null && !email.isEmpty()){
			this.email = email;
		}
		if (phone != null && !phone.isEmpty()){
			this.phone = phone;
		}
		if (age != 0){
			this.age = age;
		}
		//whenever this method is called on the an instance of client class like: newClient.updatePersonalInfo("Ahmad", null, null, "example@gmail.com"...)
		//it will update the method information to this new included information in the method
	}
	
	//add account for the client
	public void addAccount(Account account){
		if(account != null){
			accounts.add(account);
		}
	}
	
	//remove an account for the client
	public void removeAccount(Account account){
		accounts.remove(account);
	}
	
	public void getClientInfo(){
		System.out.println("Client Information:");
		System.out.println("Client ID: " + clientID);
		System.out.println("First Name: " + firstName);
		System.out.println("Last Name: " + lastName);
		System.out.println("Date of Birth: " + dateOfBirth);
		System.out.println("Join Date: " + joinDate);
		System.out.println("Email: " + email);
		System.out.println("Phone :" + phone);
		System.out.println("Category: " + category);
		System.out.println("Age: " + age);
		System.out.println("Account List: " + accounts);
		System.out.println("Reward Account: " + rewardAccount);
	}
	
}