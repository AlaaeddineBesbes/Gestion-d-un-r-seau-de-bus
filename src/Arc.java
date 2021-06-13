
public class Arc {


	private Node from;
	
	//The arriving bus stop
	private Node into;
	
	//The weight
	private int weight;
	
	
	public Arc(Node from, Node into) {
		this.setfrom(from);
		this.setinto(into);
		this.setWeight(0);
	}
	


	public Node getfrom() {
		return from;
	}

	public void setfrom(Node from) {
		this.from = from;
	}

	public Node getinto() {
		return into;
	}

	public void setinto(Node into) {
		this.into = into;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	

}