package Stack;

import java.util.NoSuchElementException;

public class Stack<T extends Comparable<T>> {
	/*
	 * LIFO - Last in first out
	 * Essential operations:
	 * push - put things in 
	 * pop - take things out
	 * peek - non-destructive pop
	 */
	Node<T> head;
	
	public Stack() {
		head = null;
	}
	
	public void push(T data) {
		Node<T> n = new Node<T>(data);
		n.next = head;
		head = n;
	}
	
	public T pop() throws NoSuchElementException {
		if (head == null) throw new NoSuchElementException();
		T popped = head.data;
		head = head.next;
		return popped;
	}
	
	public T peek() throws NoSuchElementException {
		if (head == null) throw new NoSuchElementException();
		return head.data;
	}

	public boolean isEmpty () {
		return head == null;
	}
	
}
