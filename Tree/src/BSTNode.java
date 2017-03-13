
public class BSTNode<T extends Comparable<T>> {
	T data;
	BSTNode<T> left;
	BSTNode<T> right;
	public BSTNode (T data) {
		this.data = data;
	}
}
