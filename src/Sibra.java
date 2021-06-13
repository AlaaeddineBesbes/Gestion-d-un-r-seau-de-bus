

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Sibra  {

	private static ArrayList<Graph> listOfBusLines;
	private static Graph theGraph;
	
	private static String stopBefore = null;
	private static String stopAfter = null;
	
	private static String departTime = null;
	private static int currentTime = 0;
	
	public static int getCurrentTime() {
		return currentTime;
	}

	public static void setCurrentTime(int timeOfDepart) {
		Sibra.currentTime = timeOfDepart;
	}

	public static String getStopBefore() {
		return stopBefore;
	}

	public static void setStopBefore(String stopBefore) {
		Sibra.stopBefore = stopBefore;
	}

	public static String getStopAfter() {
		return stopAfter;
	}

	public static String getDepartTime() {
		return departTime;
	}

	public static void setDepartTime(String departTime) {
		Sibra.departTime = departTime;
	}

	public static void setStopAfter(String stopAfter) {
		Sibra.stopAfter = stopAfter;
	}


	
	public static void main (String[] args) {
	
		FileInputStream file = null;
		setListOfBusLines(new ArrayList<Graph>());
		
		try {		

			file = new FileInputStream("src/2_Piscine-Patinoire_Campus.txt");
			Scanner scanner = new Scanner(file);
			Graph graph = Graph.getGraphfromFile(scanner);
			graph.buildArcs();
			
			listOfBusLines.add(graph);
			

			file = new FileInputStream("src/1_Poisy-ParcDesGlaisins.txt");
			scanner = new Scanner(file);
			graph = Graph.getGraphfromFile(scanner);
			graph.buildArcs();
			
			listOfBusLines.add(graph);
			

	
			
			
			theGraph = Graph.fuseGraphs(listOfBusLines);
			
			

			

			if (file != null) {
				file.close();
			}
	      } catch (IOException e) {
			e.printStackTrace();
		}
		askTheClient();
		
	}
	
	public static Graph getTheGraph() {
		return theGraph;
	}

	public static void setTheGraph(Graph theGraph) {
		Sibra.theGraph = theGraph;
	}



	//Ask the client where he wants to go and when (optional, if this option is not set we return all the trips with the times related)
	@SuppressWarnings("resource")
	public static void askTheClient() {
		
		//First we need to display where the person wants to go
		System.out.print("\nWhere do you want to go ? ");
		Scanner scanner = new Scanner(System.in);
		
		Node nodeB = null;
		nodeB = theGraph.getNodefromString(scanner.nextLine());
		
		//Then from where
		System.out.print("\nFrom Where ? ");
		scanner = new Scanner(System.in);
		
		Node nodeA = null;
		nodeA = theGraph.getNodefromString(scanner.nextLine());
		
		if (nodeA == null || nodeB == null) {
			System.out.println("ERROR : One of the stops you entered is not valid...");
			return;
		}
		
		System.out.println("\nYou want to go from " + nodeA.getName() + " to " + nodeB.getName());
		
		//And ask after with hours and minute with the hh:mm format (if the client sets -1)
		System.out.print("\nDo you want to start your trip in a certain time ? Thus just type the hour and minute (eg 12:45). Else you let empty... ");
		scanner = new Scanner(System.in);
		
		//Informations
		
		
		
		String hourChoice = scanner.nextLine();

		
			
			if (!hourChoice.matches("[0-9][0-9]:[0-9][0-9]")) {
				System.out.println("ERROR : The hh:mm format is incorect");
				return;
			}

			UsefullFunc uff = new UsefullFunc();
			 int hc=uff.TimetoInt(hourChoice);
		/*
			if (theGraph.isTheBusTravelingOnTheFirstWay(nodeA, nodeB)) {
				System.out.println(
						uff.getTImeHMFormat(nodeA.getListTimeOfStopFirstWay().get(nodeA.getClosestIdOfListOfTime(hc,0))));
			} else {
				System.out.println(uff.getTImeHMFormat(nodeA.getListTimeOfStopSecondWay().get(nodeA.getClosestIdOfListOfTime(hc,0))));
			}
			
		*/
		
	
	
		Dijkstra dijkstra = new Dijkstra(theGraph);
        dijkstra.execute(nodeA);
        ArrayList<Node> path = dijkstra.getPath(nodeB);
        
        if (path == null) {
			
        	System.out.println("ERROR : You cannot reach this bus stop from " + nodeA.getName());
        	return;
		}
        
        
        System.out.print("\nThe path will be : ");
        for(int i = 0; i < path.size() - 1; i++) {
        	System.out.print(path.get(i).getName() + " > ");
        }
        System.out.println(path.get(path.size() - 1).getName());
		
	
		
		theGraph.findPath(nodeA, nodeB);
		
	}
	
	

	public static ArrayList<Graph> getListOfBusLines() {
		return listOfBusLines;
	}

	public static void setListOfBusLines(ArrayList<Graph> listOfBusLines) {
		Sibra.listOfBusLines = listOfBusLines;
	}
	
	public static void addBusLineToListOfBusLines(Graph BusLines) {
		Sibra.listOfBusLines.add(BusLines);
	}

}
