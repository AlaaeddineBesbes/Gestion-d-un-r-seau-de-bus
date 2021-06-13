


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class UsefullFunc {

	public static List<String> getListOfStops(Scanner sc) {
		String Stops = null;
		while (sc.hasNextLine()) {
			Stops = sc.nextLine();
			if (Stops.contains(" N ")) {
				//we need to leave the loop because we found the first stops list
				break;
			}
		}
		return Arrays.asList(Stops.split(" N "));
		
	}

	//Returns the value of the time from the String format of the time (if the string we return -1 which means the bus do not stop here)
	public static int TimetoInt(String string) {
		
		if (string.equalsIgnoreCase("-")) {
			return -1;
		}
		
		return Integer.valueOf(string.split(":")[0]) * 100 + Integer.valueOf(string.split(":")[1]);
	}
	
	public static String getTImeHMFormat(int time) {
		if (time==-1) {
			return "-";
		}
		return String.valueOf((int)(time/100))+String.valueOf((int)(time%100));
	}
	public static boolean isBusinessDay(Calendar cal){
		// check if weekend
		if( cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
			return false;
		}
		
		// check if New Year's Day
		if (cal.get(Calendar.MONTH) == Calendar.JANUARY
			&& cal.get(Calendar.DAY_OF_MONTH) == 1) {
			return false;
		}
		
		// check if Christmas
		if (cal.get(Calendar.MONTH) == Calendar.DECEMBER
			&& cal.get(Calendar.DAY_OF_MONTH) == 25) {
			return false;
		}
		
		
		
		// check Thanksgiving (4th Thursday of November)
		if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER
			&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 4
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			return false;
		}
		
		// check Memorial Day (last Monday of May)
		if (cal.get(Calendar.MONTH) == Calendar.MAY
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY
			&& cal.get(Calendar.DAY_OF_MONTH) > (31 - 7) ) {
			return false;
		}
		
		// check Labor Day (1st Monday of September)
		if (cal.get(Calendar.MONTH) == Calendar.SEPTEMBER
			&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 1
			&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
			return false;
		}
		
		// check President's Day (3rd Monday of February)
		if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
		return true;
		}
		
		// check Veterans Day (November 11)
		if (cal.get(Calendar.MONTH) == Calendar.NOVEMBER
		&& cal.get(Calendar.DAY_OF_MONTH) == 11) {
		return true;
		}
		
		// check MLK Day (3rd Monday of January)
		if (cal.get(Calendar.MONTH) == Calendar.JANUARY
		&& cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) == 3
		&& cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
		return true;
		}
		
		// IF NOTHING ELSE, IT'S A BUSINESS DAY
		return true;
	}
	
	public static String IntToTime(int time) {
		
		if (time == -1) {
			return "-";
		}

		int hours = (int)(time/100);
		int minutes = (int) (time - (hours*100));
		
		return "".concat(String.format("%02d", hours).concat(":").concat(String.format("%02d", minutes)));
	}
	
	public static void setStopTimes(Scanner sc, ArrayList<Node> nodes) {
		
		int i = 0;
		String stopName = null;
		String line = sc.nextLine();
		do {	
			stopName = null;
			String[] timeArray = line.split(" ");
			
			ArrayList<Integer> timeStops = new ArrayList<Integer>();
			for(String l : timeArray) {
				//If this is the first iteration this means this is the name of the stop
				if (stopName == null) {
					stopName = l;
				} else {					
					timeStops.add(UsefullFunc.TimetoInt(l));
				}
				}
			
			
			
			//When we reach here we do know that the list of time is full	
			HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<Integer, ArrayList<Integer>>();
			// the stop has 2 names not one we need to make anextra node
			hashMap.put(nodes.get(i).getBusLines().get(0), timeStops);

			nodes.get(i).setListTimeOfStopFirstWay(hashMap);	
			i++;

			line = sc.nextLine();

		} while (!(line.equals(""))); //While the line is not empty (End of the time table)
		//Blank space has been read...
		
		//Second way
		line = "";
		i = nodes.size() - 1;
		line = sc.nextLine();
		do {

			String[] timeArray = line.split(" ");
			ArrayList<Integer> timeStops = new ArrayList<Integer>();
			stopName = null;
			for(String l : timeArray) {

				//If this is the first iteration this means this is the name of the stop
				if (stopName == null) {
					stopName = l;
				} else {					
					timeStops.add(UsefullFunc.TimetoInt(l));
				}
				}
			
			//When we reach here we do know that the list of time is full
			HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<Integer, ArrayList<Integer>>();
			hashMap.put(nodes.get(i).getBusLines().get(0), timeStops);
			nodes.get(i).setListTimeOfStopSecondWay(hashMap);		
			i--;
			
			line = sc.nextLine();
		} while (!(line.equals(""))); 
		//While the line is not empty (End of the time table)
		
		//Blank space has been read...
		
		//We have to read two lines because of the file format
		sc.nextLine();
		sc.nextLine();
		
		//First way
		i = 0;
		line = sc.nextLine();
		do {		
			
			String[] timeArray = line.split(" ");
			stopName = null;
			ArrayList<Integer> timeStops = new ArrayList<Integer>();
			
			for(String l : timeArray) {

				//If this is the first iteration this means this is the name of the stop
				if (stopName == null) {
					stopName = l;
				} else {					
					timeStops.add(UsefullFunc.TimetoInt(l));
				}
				}

			HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<Integer, ArrayList<Integer>>();
			hashMap.put(nodes.get(i).getBusLines().get(0), timeStops);
			nodes.get(i).setListTimeOfStopFirstWayVacations(hashMap);		
			i++;
			
			line = sc.nextLine();
		} while (!(line.equals(""))); //While the line is not empty (End of the time table)
		//Blank space has been read...
		
		i = nodes.size() - 1;
		line = sc.nextLine();
		do {	
			String[] timeArray = line.split(" ");
			ArrayList<Integer> timeStops = new ArrayList<Integer>();
			stopName = null;
			for(String l : timeArray) {
				//If this is the first iteration this means this is the name of the stop
				if (stopName == null) {
					stopName = l;
				} else {					
					timeStops.add(UsefullFunc.TimetoInt(l));
				}
				}
			
			//When we reach here we do know that the list of time is full
			HashMap<Integer, ArrayList<Integer>> hashMap = new HashMap<Integer, ArrayList<Integer>>();
			hashMap.put(nodes.get(i).getBusLines().get(0), timeStops);
			nodes.get(i).setListTimeOfStopSecondWayVacations(hashMap);		
			i--;
			
			//If we reach the end of the file we break the loop and we return
			if (!sc.hasNextLine()) {
				break;
			}
	
			line = sc.nextLine();
		} while (!(line.equals("")));  //While the line is not empty (End of the time table)
	}
	
}



