
import java.util.ArrayList;
import java.util.Scanner;


public class Graph {
	
	//The bus line
	private int id;
	
	//The  bus stops list
	private ArrayList<Node> nodeList;
	
	private ArrayList<Arc> arcListFirstWay;
	
	private ArrayList<Arc> arcListBackWay;
	
	// === Constructor ===
	
	public Graph(int id) {
		
		//We init the lists to empty
		this.setNodeList(new ArrayList<Node>());
		this.setArcListFirstWay(new ArrayList<Arc>());
		this.setArcListBackWay(new ArrayList<Arc>());
	}

	// === Getters and Setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Node> getNodeList() {
		return nodeList;
	}

	public void setNodeList(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
	}

	public ArrayList<Arc> getArcListFirstWay() {
		return arcListFirstWay;
	}

	public void setArcListFirstWay(ArrayList<Arc> arcListFirstWay) {
		this.arcListFirstWay = arcListFirstWay;
	}

	public ArrayList<Arc> getArcListBackWay() {
		return arcListBackWay;
	}

	public void setArcListBackWay(ArrayList<Arc> arcListBackWay) {
		this.arcListBackWay = arcListBackWay;
	}

	//Get a graph from a file 
	public static Graph getGraphfromFile(Scanner scanner) {
		
		Graph graph = new Graph(Sibra.getListOfBusLines().size() + 1);
		
		//creat the graph nodes
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		for (String str : UsefullFunc.getListOfStops(scanner)) {
			nodes.add(new Node(str, graph.getId()));
		}
		
		scanner.nextLine();

		UsefullFunc.setStopTimes(scanner, nodes);
		graph.setNodeList(nodes);
		
		return graph;
	}
	
	public void buildArcs() {
		
		ArrayList<Node> nodes = this.getNodeList();
		
		//We loop all the nodes without the last one with a variable and then create the nodes
		for (int i = 0; i < nodes.size() - 1; i++) {
			this.getArcListFirstWay().add(new Arc(nodes.get(i), nodes.get(i + 1))); 
		}
		
		//We loop all the nodes without the last one with a variable and then create the nodes
		for (int i = nodes.size() - 1; i > 0; i--) {
			this.getArcListBackWay().add(new Arc(nodes.get(i), nodes.get(i - 1))); 
		}		
	}

	//Get the travel way of the bus, the first way or the other way
	public boolean isTheBusTravelingOnTheFirstWay(Node A, Node B) {
		//If the stops exists we want to check in which way the bus will travel (one way or the second way)
		int distanceOneWay = this.getDistanceBetweenTwoNodes(getArcListFirstWay(), A, B);
		int distanceSecondWay = this.getDistanceBetweenTwoNodes(getArcListBackWay(), A, B);
				
		if (distanceOneWay >= distanceSecondWay) {
			return true;
		} else {
			return false;
		}
	}
	
	//Travel from point A to point B (with a single graph for the moment)
	public void travelfromAToB(Node A, Node B, String hhmmFormat) {		
		
		//Shortest --> Print the shortest path in term of arcs from a certain time
		
		
		
		//Fastest
		
		
		//Foremost
		
	}
	
	
	
	
	public Node getNodefromString(String nodeStr) {
		for(Node n : this.getNodeList()) {
			if (n.getName().equalsIgnoreCase(nodeStr)) {
				return n;
			}
		}
		return null;
	}
	
	public int getDistanceBetweenTwoNodes(ArrayList<Arc> arcs, Node A, Node B) {
		
		boolean found = false;
		int i = 0;
		
		for(Arc arc : arcs) {
			
			if (arc.getfrom() == A) {
				found = true;
			}
			
			if (arc.getinto() == B) {
				return i + 1;
			}
			
			if (found) {
				i++;
			}
		}
		return -1;
		
	}
	
	//Fuse all the graphs in a Graph ArrayList
	public static Graph fuseGraphs(ArrayList<Graph> graphs) {
		
		Graph finalGraph = new Graph(0);
		
		//First we need to gather all the stops and then fuse the same stops and add all the arcs...
		ArrayList<Node> nodes = new ArrayList<Node>();
		ArrayList<Arc> arcsFW = new ArrayList<Arc>();
		ArrayList<Arc> arcsSW = new ArrayList<Arc>();

		for(Graph graph : graphs) {
			for(Node n : graph.getNodeList()) {
				if (!Node.nodeNameExistsInList(nodes, n.getName())) {
					Node node = new Node(n.getName(), 0);
					nodes.add(node);
				}
				
				getNodefromList(nodes, n.getName()).getBusLines().add(n.getBusLines().get(0));
				getNodefromList(nodes, n.getName()).getListTimeOfStopFirstWay().put(graph.getId(), n.getListTimeOfStopFirstWay().get(graph.getId()));
				getNodefromList(nodes, n.getName()).getListTimeOfStopSecondWay().put(graph.getId(), n.getListTimeOfStopSecondWay().get(graph.getId()));
				
			}
		}	
		
		finalGraph.setNodeList(nodes);
		
		//Here we are dealing with the arcs
		for(Graph graph : graphs) {
			for(Arc a : graph.getArcListFirstWay()) {
				arcsFW.add(new Arc(finalGraph.getNodefromString(a.getfrom().getName()), finalGraph.getNodefromString(a.getinto().getName())));
			}
			
			for(Arc a : graph.getArcListBackWay()) {
				arcsSW.add(new Arc(finalGraph.getNodefromString(a.getfrom().getName()), finalGraph.getNodefromString(a.getinto().getName())));
			}
		}
		
		finalGraph.setArcListFirstWay(arcsFW);
		finalGraph.setArcListBackWay(arcsSW);
		
		//finalGraph.calculateAverageTimeOfArcs();
		
		return finalGraph;
	}
	
	public static Node getNodefromList(ArrayList<Node> nodes, String name) {
		for(Node node : nodes) {
			if (node.getName().equalsIgnoreCase(name)) {
				return node;
			}
		}
		
		return null;
	}
	
	public String[] getStopsName() {
		ArrayList<String> stopsNames = new ArrayList<String>();
		stopsNames.add("-----");
		for(Node node : this.getNodeList()) {
			stopsNames.add(node.getName().toLowerCase());
		}
		
		return stopsNames.toArray(new String[0]);
		
	}
	
	public String findPath(Node nodeA, Node nodeB) {
		Dijkstra dijkstra = new Dijkstra(this);
        dijkstra.execute(nodeA);
        ArrayList<Node> path = dijkstra.getPath(nodeB);
        
        if (path == null) {
			
        	System.out.println("ERROR : You cannot reach this bus stop from " + nodeA.getName());
        	return "ERROR : You cannot reach this bus stop from " + nodeA.getName();
		}
        
        String string = "";
        
    
        for(int i = 0; i < path.size() - 1; i++) {
     
        	string = string.concat(path.get(i).getName() + " (" + String.valueOf(path.get(i).getBusLines().get(1)) + ") " + " --> ");
        }
        
        string = string.concat(path.get(path.size() - 1).getName() + " (" + String.valueOf(path.get(path.size() - 1).getBusLines().get(1)) + ")");
  
        
        return string;
	}
	
	//We try to get the average time bewteen two nodes (arcs) to set this to the weight of the arc
	public int getAverageTimeBetweenTwoNodes(Arc arc) {
		Node nodeA = arc.getfrom();
		Node nodeB = arc.getinto();
		
		int time = 0;
		int nbOfTimes = nodeA.getListTimeOfStopFirstWay().size();
		
		for(int i = 0; i < nbOfTimes - 1; i++) {
			time += nodeB.getListTimeOfStopFirstWay().get(0).get(i) - nodeA.getListTimeOfStopFirstWay().get(0).get(i);
		}	
		
		return (int) time/nbOfTimes;
	}
	
	
	public void calculateAverageTimeOfArcs() {
		for(Arc arc : this.getArcListFirstWay()) {
			arc.setWeight(this.averageTimeBewteenTwoNodes(arc.getfrom(), arc.getinto()));
			System.out.println(this.averageTimeBewteenTwoNodes(arc.getfrom(), arc.getinto()));
		}
		
		for(Arc arc : this.getArcListBackWay()) {
			arc.setWeight(this.averageTimeBewteenTwoNodes(arc.getfrom(), arc.getinto()));
			System.out.println(this.averageTimeBewteenTwoNodes(arc.getfrom(), arc.getinto()));
		}
	}
	
	public int averageTimeBewteenTwoNodes(Node A, Node B) {
		
		int count = 0;
		int total = 0;
		
		for(Integer i : A.getListTimeOfStopFirstWay().keySet()) {
			if (B.getListTimeOfStopFirstWay().containsKey(i)) {
				for(int j = 0; (j < A.getListTimeOfStopFirstWay().get(i).size()); j++) {				
					//for(Integer j : A.getListTimeOfStopFirstWay().get(i)) {
					if (!(A.getListTimeOfStopFirstWay().get(i).get(j) == -1 || B.getListTimeOfStopFirstWay().get(i).get(j) == -1)) {
						total += (B.getListTimeOfStopFirstWay().get(i).get(j) - A.getListTimeOfStopFirstWay().get(i).get(j));
						count++;
					}
				}
			}
		}
		
		if (count == 0) {
			return 0;
		}
		return (int) (total/count);
	}
	
	
	
}