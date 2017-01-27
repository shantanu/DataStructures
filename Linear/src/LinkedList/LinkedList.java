package LinkedList;

public class LinkedList {
	
	private StringNode head;
	private int length;
	
	public LinkedList () {
		head = null;
		length = 0;
	}
	
	public int deleteFromFront() {
		if (head != null) {
			head = head.next;
			
		}
		return 1;
	}
	
	//addToFront
	public void addToFront(String data) {
		StringNode n = new StringNode(data);
		n.next = head;
		head = n;
	}
	
	//addAfter
	public void addAfter(String s, String target) {
		if (search(s) == false) return;
		else {
			for (StringNode n = head; n != null; n = n.next) {
				if (n.data.equals(s)) {
					StringNode created = new StringNode(target);
					created.next = n.next;
					n.next = created;
					return;
				}
				else {
					continue;
				}
				
			}
		}
		
		
		
	}
	
	//addToRear
	 
	//get
	/*public String get(int x) {
		//implement
		for (int i = 0; i < x; i++) {
			
		}
	}*/
	
	//search
	
	public boolean search(String target) {
		return search(head, target);
	}
	
	private boolean search (StringNode newHead, String target) {
		if (newHead == null) return false;
		if (newHead.data.equals(target)) return true;
		else return search(newHead.next, target);
	}
	
	//delete
	
	//toString
	
	//equals
	
	
	
	
	
	
}
