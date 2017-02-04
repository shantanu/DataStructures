package Stack;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ALStack<T> {
	private ArrayList<T> list;
	public ALStack () {
		list = new ArrayList<T>();
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void push(T data) {
		list.add(data);
	}
	
	public T pop () {
		if (list.size() == 0) 
			throw new NoSuchElementException();
		return list.remove(list.size()-1);
	}
	
	public T peek() {
		return list.get(list.size()-1);
	}
}
