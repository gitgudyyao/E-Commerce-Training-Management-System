package event.app;

import event.domain.*;

import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConsoleUI {
	private Scanner scanner;
	private Controller controller;
	
	public ConsoleUI() {
		scanner = new Scanner(System.in);
		controller = null;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	//UI
	public void start() {
		String choice;
		boolean true_input;	
		do {
			System.out.println("E-Commerce Training Managament System:");
	    	System.out.println("1. Create a new event record");
	    	System.out.println("2. Add a talk for an event");
	    	System.out.println("3. Add a guest to invite for an event");
	    	System.out.println("4. Update status of invited guests");
	    	System.out.println("5. View all events");
	    	System.out.println("6. View list of talks");
	    	System.out.println("7. View list of guest");
	    	System.out.println("8. Exit");
	    	
	    	System.out.print("Enter your choice (1-8): ");
	    	choice = scanner.nextLine();
	    	true_input = true;
	    	//Basic Selection
	    	switch(choice) 
	    	{
	    		case "1": addEvent(); break;
	    		case "2": addTalk(); break;
	    		case "3": addGuest(); break;
	    		case "4": updateGuestStatus(); break;
	    		case "5": displayAllEvent(); break;
	    		case "6": displayTalks(); break;
	    		case "7": displayGuests(); break;
	    		case "8": break;
	    		default: true_input = false;
	    	}
	    	if(true_input == false)
	    	{
	    		System.out.println("You have entered an invalid input.");
	    		System.out.println();
	    	}
		}while(choice != "8" || choice !="7" || choice !="6" || choice !="5" || choice != "4" || choice != "3" || choice != "2" || choice != "1");
    	System.out.println();   	
	}
	
	public void addEvent() 
	{
		System.out.println("Please enter event title: ");
		String theTitle = scanner.nextLine();		
		if(theTitle.equals("") || theTitle.equals(" "))
		{
			while(theTitle.equals("") || theTitle.equals(" "))
			{
				System.out.println("Please enter the event title again!");
				System.out.println("Empty input is not allowed.");
				theTitle = scanner.nextLine();
			}
		}

		System.out.println("Please enter event date in the format of 'DD/MM/YYYY': ");
		String date = scanner.nextLine();		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/202y");	
		
		boolean wrong = true;
		do {
			try 
			{
				Date d = sdf.parse(date);
				wrong = false;
			} 
			
			catch (ParseException e) 
			{
				System.out.println("Please enter again your date in the format of 'DD/MM/YYYY'.");
				System.out.println("Enter date that on or later than year 2020.");
				date = scanner.nextLine();
			}
		}while(wrong == true);
		
		
		System.out.println("Please enter event venue: ");
		String venue = scanner.nextLine();		
		if(venue.equals("") || venue.equals(" "))
		{
			while(venue.equals("") || venue.equals(" "))
			{
				System.out.println("Please enter the event venue again!");
				System.out.println("Empty input is not allowed.");
				venue = scanner.nextLine();
			}
		}

		System.out.println("Please enter event theme: ");
		String theme = scanner.nextLine();
		
		controller.addEvent(theTitle, venue, theme, date);
		System.out.println("Event successfully added!");
		System.out.println();
	}
	
	public void addTalk() {
		Event anEvent = selectEvent();
		if(anEvent != null) {
			System.out.println("Please enter talk's title: ");
			String title = scanner.nextLine();
			if(title.equals("") || title.equals(" "))
			{
				while(title.equals("") || title.equals(" "))
				{
					System.out.println("Please enter the talk's title again!");
					System.out.println("Empty input is not allowed.");
					title = scanner.nextLine();
				}
			}
			
			System.out.println("Please enter talk's speaker: ");
			String speaker = scanner.nextLine();
			if(speaker.equals("") || speaker.equals(" "))
			{
				while(speaker.equals("") || speaker.equals(" "))
				{
					System.out.println("Please enter the talk's speaker again!");
					System.out.println("Empty input is not allowed.");
					speaker = scanner.nextLine();
				}
			}
			
			System.out.println("Please enter talk's duration: ");
			String duration = scanner.nextLine();
			
			boolean wrong = true;
			do
			{
				try
				{
					int number = Integer.parseInt(duration);
					wrong = false;
				}
				
				catch(Exception e)
				{
					System.out.println("Please enter again the duration correctly.");
					duration = scanner.nextLine();
				}
			}while(wrong == true);
			
			controller.addTalk(anEvent, title, speaker, duration);
			System.out.println("Update successfully!");
			System.out.println();	
		}	
	}

	public void addGuest() {
		Event anEvent = selectEvent();
		if(anEvent != null) {
			System.out.println("Please enter guest's name: ");
			String name = scanner.nextLine();
			if(name.equals("") || name.equals(" "))
			{
				while(name.equals("") || name.equals(" "))
				{
					System.out.println("Please enter the guest's name again!");
					System.out.println("Empty input is not allowed.");
					name = scanner.nextLine();
				}
			}
			
			System.out.println("Please enter guest's contact number: ");
			String contact = scanner.nextLine();
			
			controller.addGuest(anEvent, name, contact);
			System.out.println("Guest added succesfully.");
		}
	}

	public void updateGuestStatus() {
		Guest aGuest = selectGuest();
		
		if(aGuest != null) {
			System.out.println("Please enter the latest status(accepted/rejected): ");
			String status = scanner.nextLine();
			
			while (!"accepted".equalsIgnoreCase(status) && !"rejected".equalsIgnoreCase(status)) {
				System.out.println();
				System.out.println("Invalid status.");
	        	System.out.print("Please enter the latest status(accepted/rejected): ");
	        	status = scanner.nextLine();
	    	}
			
			controller.updateGuestStatus(aGuest,status);
			
			System.out.println("Guest Status successfully updated.");
			System.out.println();
		}
	}
	
	public void displayAllEvent() {
		int count = controller.getNumberOfEvent();
		List<Event> events = controller.getAllEvent();
		
		if(count >= 1) 
		{
			for(int i = 0 ; i < count ; i++) 
			{
				System.out.println(i+1 + ". " + events.get(i).getTitle());
			}
		}
		else 
		{
			System.out.println("Error. No event found.");
		}
	}
	
	//Returns Event to fit into updateGuestStatus method
	public Event displayGuests() 
	{
		Event anEvent = selectEvent();
		
		if(anEvent!= null) 
		{		
			int count = controller.getNumberOfGuest(anEvent);
			if(count > 0) 
			{
				List<Guest> guests = controller.getAllGuest(anEvent);				
				for(int i = 0;i < count;i++) 
				{
					System.out.println(i+1 + ". " + guests.get(i).getName() + "\t Contact: " + guests.get(i).getContact() + "\t Status: " + guests.get(i).getReplyStatus());
				}
			}
			else 
			{
				System.out.println("No guest is found in this event.");
			}			
			return anEvent;
		}
		else 
		{
			return null;
		}
	}
	
	//View all talks
	public void displayTalks() 
	{
		Event anEvent = selectEvent();
		
		if(anEvent != null) 
		{		
			int count = controller.getNumberOfTalk(anEvent);
			if(count > 0) {
				List<Talk> talks = controller.getAllTalk(anEvent);
				
				for(int i = 0;i < count;i++) 
				{
					System.out.println(i+1 + ". " + talks.get(i).getTitle() + "\t Speaker: " + talks.get(i).getSpeaker() + "\t Duration: " + talks.get(i).getDuration());
				}
			}
			else 
			{
				System.out.println("No talk is found in this event.");
			}
			
			System.out.println();
		}
	}

	public Event selectEvent() 
	{
		int choice;
		String aChoice;		
		
		if(controller.getNumberOfEvent()>0)
		{
			displayAllEvent();
			System.out.print("Please select an event according to their index: ");
			aChoice = scanner.nextLine();
			
			boolean wrong = true;
			do
			{
				try
				{
					choice = Integer.parseInt(aChoice);
					while (choice < 1 || choice > controller.getNumberOfEvent()) 
					{
			        	System.out.println("Invalid choice.");
			        	System.out.print("Please enter your choice (1 to " + controller.getNumberOfEvent() +"): ");
			        	aChoice = scanner.nextLine();
			        	choice = Integer.parseInt(aChoice);
			    	}
					wrong = false;
				}
				catch(Exception e)
				{
					System.out.print("Please select an event according to their index again correctly: ");
					aChoice = scanner.nextLine();
				}
			}while(wrong == true);
			
			choice = Integer.parseInt(aChoice);
			return controller.selectEvent(choice-1);
		}
		else 
		{
			System.out.println("No event found.");
			return null;
		}
	}
	
	public Guest selectGuest() {
		String choice;
		int aChoice;
		
		Event anEvent = displayGuests();
		
		if(anEvent != null && controller.getNumberOfGuest(anEvent)>0) 
		{
			System.out.print("Please select guest according to index: ");
			choice = scanner.nextLine();
			boolean wrong = true;
			do
			{
				try
				{
					aChoice = Integer.parseInt(choice);
					while (aChoice < 1 || aChoice > controller.getNumberOfGuest(anEvent))
					{
			        	System.out.println("Invalid choice.");
			        	System.out.print("Please enter your choice (1 to " + controller.getNumberOfGuest(anEvent) +"): ");
			        	choice = scanner.nextLine();
			        	aChoice = Integer.parseInt(choice);
			    	}
					wrong = false;
				}
				catch(Exception e)
				{
					System.out.println("Invalid input.Please key in again.");
					choice = scanner.nextLine();
				}
			}while(wrong == true);
		
			aChoice = Integer.parseInt(choice);
			return controller.selectGuest(aChoice - 1, anEvent);			
		}
		else 
		{
			return null;
		}
	}
}
