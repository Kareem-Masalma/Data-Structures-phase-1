
public class Node {
	private Object element;
	private Node next, prev;

	public Node(Object element) {
		this(element, null, null);
	}

	public Node(Object element, Node next) {
		this(element, next, null);
	}

	public Node(Object element, Node next, Node prev) {
		this.element = element;
		this.next = next;
		this.prev = prev;
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrev() {
		return prev;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}
}
