package Queue;

public class QueueMain {
	static public void main(String args[]) {
		Queue<Integer> q = new Queue<Integer>();
		q.enqueue(1);
		q.enqueue(8);
		q.enqueue(3);
		//q.enqueue(4);
		//q.enqueue(5);
		System.out.println(q);;
		//System.out.println(q.size());
		Queue<Integer> evens = q.evenSplit();
		System.out.println(q);
		System.out.println(evens);
	}
}
