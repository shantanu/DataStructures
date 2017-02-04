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
	
	public boolean isEmpty () {
		return head==null;
	}
	
	public void push(T data) {
		Node<T> n = new Node<T>(data);
		n.next = head;
		head = n;
	}
	
	public T pop() {
		if (head == null) throw new NoSuchElementException();
		T popped = head.data;
		head = head.next;
		return popped;
	}
	
	public T peek() {
		if (head == null) return null;
		return head.data;
	}
}
