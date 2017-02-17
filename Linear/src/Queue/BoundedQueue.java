package Queue;

import java.util.NoSuchElementException;

public class BoundedQueue<T> {
	private T[] items;
	private int size, front, rear;
	
	public BoundedQueue(int cap) {
		items = (T[])new Object[cap]; //WATCH THE ODD SYNTAX TO CREATE A GENERIC ARRAY
		size = 0;
		front = 1;
		rear = 0;
	}
	
	public void enqueue(T item) throws QueueFullException {
		if (size == items.length) {
			throw new QueueFullException();
		}
		rear = (rear+1) % items.length;
		items[rear] = item;
		size++;
	}
	public T dequeue(T item) throws NoSuchElementException {
		if (size == 0)
			throw new NoSuchElementException();
		T hold = items[front];
		front = (front+1)%items.length;
		size--;
		return hold;
	}
	
	public int size() {
		return size;
	}
}
