package redb;

import java.awt.Color;

public class RBNode {
	public static boolean RED = false;
	public static boolean BLACK = true;

	private boolean color = RED;
	private RBNode left;
	private RBNode right;
	private RBNode parent;
	private int data;
	
	public RBNode() {
	}

	public RBNode(int data) {
		this.data = data;
	}

	public void SetData(int data) {
		this.data = data;
	}
	public void Left_Set(RBNode x) {

		// Re-arranging the parents is required
		if (Get_Left_Node() != null)
			Get_Left_Node().Set_Parent_Node(null);

		if (x != null) {
			x.DeleteFromParent();
			x.Set_Parent_Node(this);
		}

		this.left = x;
	}

	public void Right_Set(RBNode x) {

		// Re-arranging the parents is required
		if (Get_Right_Node() != null) {
			Get_Right_Node().Set_Parent_Node(null);
		}

		if (x != null) {
			x.DeleteFromParent();
			x.Set_Parent_Node(this);
		}

		this.right = x;
	}
	public void DeleteFromParent() {
		if (Get_Parent_Node() == null)
			return;

		// Remove current node's links from the parent
		if (parent.Get_Left_Node() == this)
			parent.Left_Set(null);
		else if (parent.Get_Right_Node() == this)
			parent.Right_Set(null);

		this.parent = null;
	}

	

	public int getData() {
		return data;
	}

	public RBNode Get_Left_Node() {
		return left;
	}

	public static RBNode Get_Left_Node(RBNode node) {
		return node == null ? null : node.Get_Left_Node();
	}

	public RBNode Get_Right_Node() {
		return right;
	}

	public static RBNode Get_Right_Node(RBNode node) {
		return node == null ? null : node.Get_Right_Node();
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	@Override
	public String toString() {
		return data+"";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int compare(RBNode node, Comparable<?> b) {
		return ((Comparable) node.getData()).compareTo(b);
	}

	/* Red Black Node Functions */

	public boolean Is_Red() {
		return Get_Node_Color() == RED;
	}

	public boolean Is_Black() {
		return !Is_Red();
	}

	public static boolean isRed(RBNode node) {
		return Get_Node_Color(node) == RED;
	}

	public static boolean isBlack(RBNode node) {
		return !isRed(node);
	}

	public void Set_Node_Color(boolean color) {
		this.color = color;
	}

	public static void Set_Node_Color(RBNode node, boolean color) {
		if (node == null)
			return;
		node.Set_Node_Color(color);
	}

	public boolean Get_Node_Color() {
		return color;
	}

	public static boolean Get_Node_Color(RBNode node) {
		// As null node is considered to be black
		return node == null ? BLACK : node.Get_Node_Color();
	}

	public Color getActualColor() {
		if (Is_Red())
			return new Color(250, 70, 70);
		else
			return new Color(70, 70, 70);

	}

	public void Change_Color() {
		color = !color;
	}

	public static void Change_Color(RBNode node) {
		if (node == null)
			return;

		node.Set_Node_Color(!node.Get_Node_Color());
	}

	public void Set_Parent_Node(RBNode parent) {
		this.parent = parent;
	}

	public RBNode Get_Parent_Node() {
		return parent;
	}

	public static RBNode Get_Parent_Node(RBNode node) {
		return (node == null) ? null : node.Get_Parent_Node();
	}

	public RBNode Get_GrandParent_Node() {
		RBNode parent = Get_Parent_Node();
		return (parent == null) ? null : parent.Get_Parent_Node();
	}

	public static RBNode Get_GrandParent_Node(RBNode node) {
		return (node == null) ? null : node.Get_GrandParent_Node();
	}

	public RBNode Get_Sibling() {
		RBNode parent = Get_Parent_Node();
		if (parent != null) { 
			if (this == parent.Get_Right_Node())
				return (RBNode) parent.Get_Left_Node();
			else
				return (RBNode) parent.Get_Right_Node();
		}
		return null;
	}

	public static RBNode Get_Sibling(RBNode node) {
		return (node == null) ? null : node.Get_Sibling();
	}

	public RBNode Get_Uncle_Node() {
		RBNode parent = Get_Parent_Node();
		if (parent != null) { 
			return parent.Get_Sibling();
		}
		return null;
	}

	public static RBNode Get_Uncle_Node(RBNode node) {
		return (node == null) ? null : node.Get_Uncle_Node();
	}

}

