
public class BST<T extends Comparable<T>> {
	BSTNode<T> root;
	
	
	/* You need to return T because you could want to 
	 * return all objects that match certain features
	 * of a generic object. 
	 * 
	 * Ex: search for a generic red car, return a specific 
	 * car that is red, among other features.
	 */
	public T search(T target) {
		
		BSTNode<T> tmp = root;
		while(tmp != null) {
			int c = tmp.data.compareTo(target);
			
			if (c == 0) return tmp.data;
			tmp = c < 0 ? tmp.left: tmp.right;
		}
		return null;
	}
	
	public void insert(T data) {
		/*
		 * Algorithm:
		 * 1. Create Node
		 * 2. do a search
		 * 3. attach (1) to parent of target (2)
		 */
		BSTNode<T> add = new BSTNode<T>(data);
		BSTNode<T> tmp = root;
		BSTNode<T> parent = null;
		int c = 0;
		while (tmp!=null) {
			c = tmp.data.compareTo(data);
			parent = tmp;
			tmp = c < 0 ? tmp.left: tmp.right;
		}
		if (c < 0) 	parent.left = add;
		else 		parent.right = add;
		

	
	}
}
