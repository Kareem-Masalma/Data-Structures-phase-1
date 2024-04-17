
public class DoubleLinkedList {
	private Node front, back;
	private int size;

	public DoubleLinkedList() {

	}

	public int size() {
		return size;
	}

	public Object getFirst() {
		if (size == 0)
			return null;
		return front.getElement();
	}

	public Object getLast() {
		if (size == 0)
			return null;
		return back.getElement();
	}

	public Node getFront() {
		return front;
	}

	public void setFront(Node front) {
		this.front = front;
	}

	public Node getBack() {
		return back;
	}

	public void setBack(Node back) {
		this.back = back;
	}

	public Object get(int index) {
		if (index >= size || index < 0)
			return null;
		else if (index == 0)
			return getFirst();
		else if (index == size - 1)
			return getLast();

		Node current = front;
		for (int i = 0; i < index; i++)
			current = current.getNext();
		return current.getElement();
	}

	public void addFirst(Node node) {
		if (size == 0)
			front = back = node;
		else {
			node.setNext(front);
			front.setPrev(node);
			front = node;
		}
		size++;
	}

	public void addLast(Node node) {
		if (size == 0)
			front = back = node;
		else {
			back.setNext(node);
			node.setPrev(back);
			back = node;
		}
		size++;
	}

	public void add(Node node) {
		addLast(node);
	}

	public void add(Node node, int index) {
		if (index <= 0)
			addFirst(node);
		else if (index >= size)
			addLast(node);
		else {
			Node current = front;
			for (int i = 0; i < index - 1; i++)
				current = current.getNext();
			node.setNext(current.getNext());
			current.getNext().setPrev(node);
			current.setNext(node);
			node.setPrev(current);
			size++;
		}
	}

	public boolean removeFirst() {
		if (size == 0)
			return false;
		else if (size == 1) {
			front = back = null;
			size--;
			return true;
		}
		front = front.getNext();
		front.setPrev(null);
		size--;
		return true;
	}

	public boolean removeLast() {
		if (size == 0)
			return false;
		back = back.getPrev();
		back.setNext(null);
		size--;
		return true;
	}

	public boolean remove(int index) {
		if (index == 0)
			return removeFirst();
		else if (index == size - 1)
			return removeLast();
		Node current = front;
		for (int i = 0; i < index; i++)
			current = current.getNext();
		current.getPrev().setNext(current.getNext());
		current.getNext().setPrev(current.getPrev());
		size--;
		return true;
	}
}
