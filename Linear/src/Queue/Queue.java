package Queue;

import java.util.NoSuchElementException;

public class Queue <T extends Comparable<T>> {
	
	Node<T> tail;
	
	public Queue(){
		tail = null;
	}
	
	public void enqueue(T data){
		Node<T> n = new Node<T>(data);
		if(tail == null){
			tail = n;
			tail.next = tail;
		}else{
			n.next = tail.next;
			tail.next = n;
			tail = n;
		}
	}
	public T dequeue(){
		if(tail == null)
			throw new NoSuchElementException();
		T tmp = tail.next.data;
		if(tail == tail.next)
			tail = null;
		else
			tail.next = tail.next.next;
		return tmp;
	}
	
	public boolean isEmpty(){
		return tail == null;
	}
	
	public int size(){
		int size = 0;
		if(tail == null)
			return size;
		for(Node<T> tmp = tail.next; tmp!=tail; tmp = tmp.next){
			size++;
		}
		size++;
		return size;
	}
	public Queue<T> evenSplit(){
		Queue<T> result = new Queue<T>();
		if(tail == null)
			return result;
		Node<T> tmp = tail;
		int size = size();
		for(int i = 0; i < size; i++){
			if(i%2==1){
				result.enqueue(dequeue());
			}else{
				enqueue(dequeue());
			}
		}
		return result;
	}
	public String toString(){
		if(tail==null)
			return null;
		String result = "";
		for(Node<T> tmp = tail.next; tmp!=tail; tmp = tmp.next){
			result += tmp.data;
			result += " ";
		}
		result += tail.data;
		return result;
	}
	
}
