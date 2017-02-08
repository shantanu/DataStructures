package Queue;

import java.util.NoSuchElementException;

public class Queue<T extends Comparable<T>> {
	Node<T> tail;
	
	public void enqueue (T data) {
		Node<T> n = new Node(data);
		if (tail == null){
			tail = n;
			n.next = tail;
		}
		n.next = tail.next;
		tail.next = n;
		tail = n;
	}
	
	public T dequeue () {
		if (tail == null) 
			throw new NoSuchElementException();
		
		T tmp = tail.next.data;
		tail.next = tail.next.next;
		
		if (tail == tail.next)
			tail = null;
		
		return tmp;
	}
}
