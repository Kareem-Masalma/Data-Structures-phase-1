
public class District {
	private String name;
	private CircularLinkedList location;

	{
		location = new CircularLinkedList();
	}

	public District() {

	}

	public District(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CircularLinkedList getLocation() {
		return location;
	}

	public void setLocation(CircularLinkedList location) {
		this.location = location;
	}

	public int getTotalNumOfMartyrs() {
		int totalNumOfMartyrs = 0;
		for (int i = 0; i < location.size(); i++)
			totalNumOfMartyrs += ((Location) location.get(i)).getTotalNumOfMartyrs();
		return totalNumOfMartyrs;
	}

	public int getMales() {
		int males = 0;
		for (int i = 0; i < location.size(); i++)
			males += ((Location) location.get(i)).getMales();
		return males;
	}

	public int getFemales() {
		int females = 0;
		for (int i = 0; i < location.size(); i++)
			females += ((Location) location.get(i)).getFemales();
		return females;
	}

	public int getTotalAge() {
		int totalAge = 0;
		for (int i = 0; i < location.size(); i++)
			totalAge += ((Location) location.get(i)).getTotalAge();
		return totalAge;
	}

	public double getAverageAge() {
		if (getTotalNumOfMartyrs() == 0)
			return 0;
		return getTotalAge() / (float) getTotalNumOfMartyrs();
	}

	/* A method to check if a given location exists */
	public int exists(String locationName) {
		SNode node = location.getFront();
		for (int i = 0; i < location.size(); i++) {
			Location loc = (Location) node.getElement();
			if (locationName.equalsIgnoreCase(loc.getName()))
				return i;
			node = node.getNext();
		}
		return -1;
	}

	/* To get the number of the martyrs for a given date */
	public int numOfMarts(String date) {
		int res = 0;
		SNode nodeLoc = location.getFront();
		for (int i = 0; i < location.size(); i++) {
			Location loc = (Location) nodeLoc.getElement();
			SNode nodeMart = loc.getMartyr().getFront();
			for (int j = 0; j < loc.getMartyr().size(); j++) {
				Martyr mart = (Martyr) nodeMart.getElement();

				if (mart.getDate().equals(date))
					res++;

				nodeMart = nodeMart.getNext();
			}
			nodeLoc = nodeLoc.getNext();
		}
		return res;
	}

	/* To get the date with the max date */
	public String getMaxDate() {
		if ((Location) location.getFirst() == null)
			return "--";
		if ((Martyr) ((Location) location.getFirst()).getMartyr().getFirst() == null)
			return "--";
		String max = ((Martyr) ((Location) location.getFirst()).getMartyr().getFirst()).getDate();
		SNode nodeLoc = location.getFront();
		for (int i = 0; i < location.size(); i++) {
			Location loc = (Location) nodeLoc.getElement();
			SNode nodeMart = loc.getMartyr().getFront();
			for (int j = 0; j < loc.getMartyr().size() - 1; j++) {
				Martyr mart = (Martyr) nodeMart.getElement();
				String date = mart.getDate();
				String nextDate = ((Martyr) nodeMart.getNext().getElement()).getDate();
				if (numOfMarts(date) < numOfMarts(nextDate))
					max = nextDate;
				date = nextDate;
				nodeMart = nodeMart.getNext();
			}
			nodeLoc = nodeLoc.getNext();
		}
		return max;
	}
}
