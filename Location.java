
public class Location {
	private String name;
	private LinkedList martyrs;

	{
		martyrs = new LinkedList();
	}

	public Location() {

	}

	public Location(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList getMartyr() {
		return martyrs;
	}

	public void setMartyr(LinkedList martyrs) {
		this.martyrs = martyrs;
	}

	public int getTotalNumOfMartyrs() {
		return martyrs.size();
	}

	public int getMales() {
		int male = 0;
		SNode node = martyrs.getFront();
		for (int i = 0; i < martyrs.size(); i++) {
			Martyr mart = (Martyr) node.getElement();
			if (mart.getGender() == 'M')
				male++;
			node = node.getNext();
		}
		return male;
	}

	public int getFemales() {
		int female = 0;
		SNode node = martyrs.getFront();
		for (int i = 0; i < martyrs.size(); i++) {
			Martyr mart = (Martyr) node.getElement();
			if (mart.getGender() == 'F')
				female++;
			node = node.getNext();
		}
		return female;
	}

	public int getTotalAge() {
		int age = 0;
		SNode node = martyrs.getFront();
		for (int i = 0; i < martyrs.size(); i++) {
			Martyr mart = (Martyr) node.getElement();
			age += mart.getAge();
			node = node.getNext();
		}
		return age;
	}

	public double getAverageAge() {
		int marts = getTotalNumOfMartyrs();
		if (marts == 0)
			return 0;
		return getTotalAge() / (float) marts;
	}

	/* To get the youngest martyr */
	public String youngest() {
		if ((Martyr) martyrs.getFirst() == null)
			return "--";
		return ((Martyr) martyrs.getFirst()).getName();
	}

	/* To get the oldest martyr */
	public String oldest() {
		if ((Martyr) martyrs.getLast() == null)
			return "--";
		return ((Martyr) martyrs.getLast()).getName();
	}
}
