package com.cornellsatech.o_week;

import android.util.Log;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserData
{
	public static final Map<LocalDate, List<Event>> allEvents;
	public static final Map<LocalDate, List<Event>> selectedEvents;
	public static final List<LocalDate> DATES;
	public static LocalDate selectedDate;
	private static final int YEAR = 2017;
	private static final int MONTH = 8;
	private static final int START_DAY = 19;    //Dates range: [START_DAY, END_DAY], inclusive
	private static final int END_DAY = 24;      //Note: END_DAY must > START_DAY
	public static final String TAG = "UserData";

	//initialize DATES
	static
	{
		ImmutableList.Builder<LocalDate> tempDates = ImmutableList.builder();
		ImmutableMap.Builder<LocalDate, List<Event>> tempAllEvents = ImmutableMap.builder();
		ImmutableMap.Builder<LocalDate, List<Event>> tempSelectedEvents = ImmutableMap.builder();
		LocalDate today = LocalDate.now();
		for (int i = START_DAY; i <= END_DAY; i++)
		{
			LocalDate date = new LocalDate(YEAR, MONTH, i);
			if (date.isEqual(today))
				selectedDate = date;
			tempDates.add(date);
			tempAllEvents.put(date, new ArrayList<Event>());
			tempSelectedEvents.put(date, new ArrayList<Event>());
		}
		DATES = tempDates.build();
		allEvents = tempAllEvents.build();
		selectedEvents = tempSelectedEvents.build();

		if (selectedDate == null)
			selectedDate = DATES.get(0);
	}

	//suppress instantiation
	private UserData(){}
	
	static boolean allEventsContains(Event event)
	{
		List<Event> eventsForDate = allEvents.get(event.date);
		return eventsForDate != null && eventsForDate.contains(event);
	}
	static boolean selectedEventsContains(Event event)
	{
		List<Event> eventsForDate = selectedEvents.get(event.date);
		return eventsForDate != null && eventsForDate.contains(event);
	}
	static void appendToAllEvents(Event event)
	{
		List<Event> eventsForDate = allEvents.get(event.date);
		if (eventsForDate == null)
		{
			Log.e(TAG, "appendToAllEvents: attempted to add event with date outside orientation");
			return;
		}
		if (!eventsForDate.contains(event))
			eventsForDate.add(event);
	}
	static void insertToSelectedEvents(Event event)
	{
		List<Event> eventsForDate = selectedEvents.get(event.date);
		if (eventsForDate == null)
		{
			Log.e(TAG, "insertToSelectedEvents: attempted to add event with date outside orientation");
			return;
		}
		if (!eventsForDate.contains(event))
			eventsForDate.add(event);
	}
	static void removeFromSelectedEvents(Event event)
	{
		List<Event> eventsForDate = selectedEvents.get(event.date);
		if (eventsForDate == null)
			Log.e(TAG, "removeFromSelectedEvents: No selected events for date");
		else
			eventsForDate.remove(event);
	}

	static void loadData()
	{
		//TODO fetch data from DB and compare to saved to remove outdated events or add new events. Adding temp data for testing
		LocalDate date = new LocalDate(2017, 8, 19);
		Event[] events = new Event[]{
				new Event("A", "A", "", null, date, new LocalTime(9, 30), new LocalTime(10, 30), false, 1),
				new Event("B", "B", "", null, date, new LocalTime(10, 30), new LocalTime(12, 0), false, 2),
				new Event("C", "C", "", null, date, new LocalTime(11, 45), new LocalTime(15, 30), false, 3),
				new Event("D", "D", "", null, date, new LocalTime(12, 0), new LocalTime(14, 0), false, 4),
				new Event("E", "E", "", null, date, new LocalTime(13, 30), new LocalTime(14, 0), false, 5),
				new Event("F", "F", "", null, date, new LocalTime(14, 0), new LocalTime(15, 40), false, 6),
				new Event("G", "G", "", null, date, new LocalTime(14, 30), new LocalTime(15, 0), false, 7),
				new Event("H", "H", "", null, date, new LocalTime(15, 30), new LocalTime(16, 0), false, 8),
				new Event("I", "I", "", null, date, new LocalTime(16, 0), new LocalTime(16, 30), false, 9),
				new Event("J", "J", "", null, date, new LocalTime(15, 50), new LocalTime(16, 40), false, 10),
				new Event("K", "K", "", null, date, new LocalTime(17, 0), new LocalTime(17, 30), false, 11),
				new Event("L", "L", "", null, date, new LocalTime(23, 0), new LocalTime(0, 30), false, 12),
				new Event("M", "M", "", null, date, new LocalTime(0, 0), new LocalTime(1, 30), false, 13)
		};
		/*Event[] events = new Event[]{
				new Event("Move In", "Multiple locations", "Students should plan to move into their residence halls between 9:00am and 12:00pm on Thursday, January 19. Orientation volunteers will help you move your belongings and answer any questions that you may have. Plan on picking up your key to your room at your service center before heading over to your residence hall. If you are living off campus, we also recommend moving in on Thursday so you can attend First Night at 8:00pm that evening.", null,
						new LocalDate(2017, 8, 19), new LocalTime(9, 0), new LocalTime(12, 0), false, 1),
				new Event("New Student Check-In and Welcome Reception", "Willard Straight Hall, 4th Floor Rooms", "You are required to attend New Student Check-In in the Memorial Room to verify your matriculation and registration requirements. Please arrive anytime between 1:00pm and 2:30pm as representatives from across campus will also be available to answer questions and to better acquaint you with university services. Light refreshments will be available for students and parents throughout the fourth floor of Willard Straight Hall.", null,
						new LocalDate(2017, 8, 19), new LocalTime(13, 0), new LocalTime(15, 0), false, 2),
				new Event("First Night", "Klarman Atrium, Klarman Hall", "It's your first night at Cornell! Meet your January Orientation Leader (JOL) and then mingle with your classmates and get a taste of what Ithaca has to offer - literally! There will be free food and drinks as well as games and activities. You won't want to miss it!", null,
						new LocalDate(2017, 8, 19), new LocalTime(19, 0), new LocalTime(20, 0), false, 3),
				new Event("Cornell Essentials", "Kaufman Auditorium, G64 Goldwin Smith Hall", "Hear from upper-level students and alumni about their own introduction to Cornell. Learn how to navigate the university, deal with setbacks, find balance, and take advantage of the multitude of campus resources available. All new first-year and transfer students must attend this event. First year students will walk to the FYSA Class Photo event from Goldwin Smith Hall.", null,
						new LocalDate(2017, 8, 20), new LocalTime(15, 0), new LocalTime(16, 0), false, 4),
				new Event("Welcome Dinner", "Becker House, Robert Purcell Marketplace Eatery", "Join us on West Campus in the Becker House Dining Room or on North Campus in the Robert Purcell Marketplace Eatery. If you don’t have a meal plan, don’t worry, we’ve got you covered at the door. Students living in the Collegetown area and West Campus are encouraged to go to Becker House. FYSAs and students living on North Campus are encouraged to go to RPCC.", null,
						new LocalDate(2017, 8, 20), new LocalTime(6, 0), new LocalTime(7, 30), false, 5),
				new Event("Coffee Hour", "Café Jennie, The Cornell Store", "Visit Café Jennie in The Cornell Store for free coffee and hot chocolate! Join in on casual conversation with both new and current students to discuss life at Cornell.", null,
						new LocalDate(2017, 8, 21), new LocalTime(10, 0), new LocalTime(11, 0), false, 6),
				new Event("Laser Tag", "2nd Floor, RPCC", "Calling all First Years! You've proven you can handle yourself in a classroom, but how will you fair in a blood pumping, heart racing laser fight? Join your fellow Cornellians at Barskis Xtreme Lazer Tag for an adrenaline-fueled test of agility, precision, and wit.", null,
						new LocalDate(2017, 8, 21), new LocalTime(13, 0), new LocalTime(15, 0), false, 7),
				new Event("Study Smarter, Not Harder", "Lewis Auditorium, G76 Goldwin Smith Hall", "Are you ready to conquer procrastination and stress while maximizing your learning experience? Join the Learning Strategies Center's Mile Chen and learn how to make the most of your study skills. Get ahead of the game!", null,
						new LocalDate(2017, 8, 22), new LocalTime(11, 0), new LocalTime(12, 0), false, 8),
				new Event("Explore Downtown Ithaca", "Risley or Schwartz Center Bus Stop", "Interested in learning about downtown Ithaca? Want to take advantage of your free bus pass? Come learn about the TCAT bus system and get acquainted with downtown Ithaca through a series of group activities on the Commons. Win free samples and prizes. We will meet at the bus stop in front of Risley Hall or Schwartz Center and take the bus down together, snow or shine.", null,
						new LocalDate(2017, 8, 22), new LocalTime(12, 0), new LocalTime(15, 0), false, 9),
				new Event("Cuddles and Chocolate", "Memorial Room, WSH", "Play with puppies from Guiding Eyes for the Blind at Cornell during this afternoon of hot chocolate and cuddles! Guiding Eyes for the Blind at Cornell strives to teach students to learn more about guide dog training and provide support for the Guiding Eyes for the Blind Finger Lakes Region.", null,
						new LocalDate(2017, 8, 23), new LocalTime(13, 0), new LocalTime(14, 0), false, 10),
				new Event("Learning Where You Live", "3331 Tatkon Center", "Want to take a small class where you get to know the professor and the other students? Curious to learn about a subject that has nothing to do with your intended major? Want to explore a really interesting subject without the pressure of grades? Come check out a few of the one-credit courses being taught on North Campus this year.", null,
						new LocalDate(2017, 8, 23), new LocalTime(16, 0), new LocalTime(17, 0), false, 11),
				new Event("Orientation Finale at the Tatkon Center", "Tatkon Center", "Join us for a celebration! Orientation may be coming to a close, but your first semester at Cornell is just getting started. Mingle with friends, meet current students, and get excited for a great semester. Don’t miss the refreshments and giveaways. JOLs will also introduce you to the Tatkon Center, Cornell’s academic resource center for first-year students.", null,
						new LocalDate(2017, 8, 24), new LocalTime(11, 0), new LocalTime(13, 0), false, 12)
		};*/

		for (Event event : events)
		{
			appendToAllEvents(event);
			insertToSelectedEvents(event);
		}

		//Telling other classes to reload their data
		NotificationCenter.DEFAULT.post(new NotificationCenter.EventReload());
	}
}
