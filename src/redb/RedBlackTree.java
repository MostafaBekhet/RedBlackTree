package redb;

public class RedBlackTree {
    protected RBNode root;
    protected int size = 0;

    public RBNode Get_Root() {
        return this.root;
    }
    
    
    private void insert(RBNode node, int x) {
        if (this.contains(x)) {
            return;
        }
        if (node.getData() > (x)) {
            if (node.hasLeft()) {
                this.insert(node.Get_Left_Node(), x);
            } else {
                RBNode inserted = new RBNode(x);
                node.Left_Set(inserted);
                this.RB_Balanced_Insert(inserted);
            }
        } else if (node.hasRight()) {
            this.insert(node.Get_Right_Node(), x);
        } else {
            RBNode inserted = new RBNode(x);
            node.Right_Set(inserted);
            this.RB_Balanced_Insert(inserted);
        }
    }
    
    
    public boolean CheckIsEmpty() {
        if (this.root == null) {
            return true;
        }
        return false;
    }
    public void insert(int x) {
        if (this.CheckIsEmpty()) {
            this.root = new RBNode(x);
        } else {
            this.insert(this.root, x);
        }
        this.root.Set_Node_Color(RBNode.BLACK);
        ++this.size;
    }
    
    
    private void Rotate_Right(RBNode node) {
        if (node.Get_Left_Node() == null) {
            return;
        }
        RBNode leftTree = node.Get_Left_Node();
        node.Left_Set(leftTree.Get_Right_Node());
        if (node.Get_Parent_Node() == null) {
            this.root = leftTree;
        } else if (node.Get_Parent_Node().Get_Left_Node() == node) {
            node.Get_Parent_Node().Left_Set(leftTree);
        } else {
            node.Get_Parent_Node().Left_Set(leftTree);
        }
        leftTree.Right_Set(node);
    }

  
    public void remove(int x) {
        if (!this.contains(x)) {
            return;
        }
        
        RBNode node = this.find(x);
        if (node.Get_Left_Node() != null && node.Get_Right_Node() != null) {
            RBNode successor = this.Get_Successor(node);
            node.SetData(successor.getData());
            node = successor;
        }
        
        RBNode pullUp = node.Get_Left_Node() == null ? node.Get_Right_Node() : node.Get_Left_Node();
        if (pullUp != null) {
            if (node == this.root) {
                node.DeleteFromParent();
                this.root = node;
            } else if (RBNode.Get_Left_Node(node.Get_Parent_Node()) == node) {
                node.Get_Parent_Node().Left_Set(pullUp);
            } else {
                node.Get_Parent_Node().Right_Set(pullUp);
            }
            if (RBNode.isBlack(node)) {
                this.RB_Balanced_Delete(pullUp);
            }
        } else if (node == this.root) {
            this.root = null;
        } else {
            if (RBNode.isBlack(node)) {
                this.RB_Balanced_Delete(node);
            }
            node.DeleteFromParent();
        }
    }
    
    
    private void Rotate_Left(RBNode node) {
        if (node.Get_Right_Node() == null) {
            return;
        }
        RBNode rightTree = node.Get_Right_Node();
        node.Right_Set(rightTree.Get_Left_Node());
        if (node.Get_Parent_Node() == null) {
            this.root = rightTree;
        } else if (node.Get_Parent_Node().Get_Left_Node() == node) {
            node.Get_Parent_Node().Left_Set(rightTree);
        } else {
            node.Get_Parent_Node().Right_Set(rightTree);
        }
        rightTree.Left_Set(node);
    }
    

    public void clear() {
        this.root = null;
    }

    public int getSize() {
        return this.size;
    }

    public String print() {
        return this.print(this.root , new StringBuilder()).toString();
    }

    private StringBuilder print(RBNode node , StringBuilder x) {
    	    	
        if (node != null) {
            this.print(node.Get_Left_Node() , x);
            x.append(node).append(" ");
            this.print(node.Get_Right_Node() , x);
        }
        return x;
    }

    public boolean contains(int data) {
        return this.contains(this.root, data);
    }

    private boolean contains(RBNode root, int data) {
        if (root == null) {
            return false;
        }
        if (root.getData()>(data) ) {
            return this.contains(root.Get_Left_Node(), data);
        }
        if (root.getData()<(data)) {
            return this.contains(root.Get_Right_Node(), data);
        }
        return true;
    }

    public RBNode find(int data) {
        return this.find(this.root, data);
    }

    private RBNode find(RBNode root, int data) {
        if (root == null) {
            return null;
        }
        if (root.getData()>(data)) {
            return this.find(root.Get_Left_Node(), data);
        }
        if (root.getData() < data ){
            return this.find(root.Get_Right_Node(), data);
        }
        return root;
    }

    public int getDepth() {
        return this.getDepth(this.root);
    }

    private int getDepth(RBNode node) {
        if (node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.Get_Left_Node());
            return left_depth > (right_depth = this.getDepth(node.Get_Right_Node())) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    }

    private RBNode Get_Successor(RBNode root) {
        RBNode leftTree = root.Get_Left_Node();
        if (leftTree != null) {
            while (leftTree.Get_Right_Node() != null) {
                leftTree = leftTree.Get_Right_Node();
            }
        }
        return leftTree;
    }

    private void RB_Balanced_Insert(RBNode node) {
        if (node == null || node == this.root || RBNode.isBlack(node.Get_Parent_Node())) {
            return;
        }
        if (RBNode.isRed(node.Get_Uncle_Node())) {
            RBNode.Change_Color(node.Get_Parent_Node());
            RBNode.Change_Color(node.Get_Uncle_Node());
            RBNode.Change_Color(node.Get_GrandParent_Node());
            this.RB_Balanced_Insert(node.Get_GrandParent_Node());
        } else if (this.Has_Left_Parent(node)) {
            if (this.Is_Right_Child(node)) {
                node = node.Get_Parent_Node();
                this.Rotate_Left(node);
            }
            RBNode.Set_Node_Color(node.Get_Parent_Node(), RBNode.BLACK);
            RBNode.Set_Node_Color(node.Get_GrandParent_Node(), RBNode.RED);
            this.Rotate_Right(node.Get_GrandParent_Node());
        } else if (this.Has_Right_Parent(node)) {
            if (this.Is_Left_Child(node)) {
                node = node.Get_Parent_Node();
                this.Rotate_Right(node);
            }
            RBNode.Set_Node_Color(node.Get_Parent_Node(), RBNode.BLACK);
            RBNode.Set_Node_Color(node.Get_GrandParent_Node(), RBNode.RED);
            this.Rotate_Left(node.Get_GrandParent_Node());
        }
    }

	private void RB_Balanced_Delete(RBNode node) {
        while (node != this.root && node.Is_Black()) {
            RBNode sibling = node.Get_Sibling();
            if (node == RBNode.Get_Left_Node(node.Get_Parent_Node())) {
                if (RBNode.isRed(sibling)) {
                    RBNode.Set_Node_Color(sibling, RBNode.BLACK);
                    RBNode.Set_Node_Color(node.Get_Parent_Node(), RBNode.RED);
                    this.Rotate_Left(node.Get_Parent_Node());
                    sibling = (RBNode) RBNode.Get_Right_Node(node.Get_Parent_Node());
                }
                if (RBNode.isBlack(RBNode.Get_Left_Node(sibling)) && RBNode.isBlack(RBNode.Get_Right_Node(sibling))) {
                    RBNode.Set_Node_Color(sibling, RBNode.RED);
                    node = node.Get_Parent_Node();
                    continue;
                }
                if (RBNode.isBlack(RBNode.Get_Right_Node(sibling))) {
                    RBNode.Set_Node_Color(RBNode.Get_Left_Node(sibling), RBNode.BLACK);
                    RBNode.Set_Node_Color(sibling, RBNode.RED);
                    this.Rotate_Right(sibling);
                    sibling = (RBNode) RBNode.Get_Right_Node(node.Get_Parent_Node());
                }
                RBNode.Set_Node_Color(sibling, RBNode.Get_Node_Color(node.Get_Parent_Node()));
                RBNode.Set_Node_Color(node.Get_Parent_Node(), RBNode.BLACK);
                RBNode.Set_Node_Color(RBNode.Get_Right_Node(sibling), RBNode.BLACK);
                this.Rotate_Left(node.Get_Parent_Node());
                node = this.root;
                continue;
            }
            if (RBNode.isRed(sibling)) {
                RBNode.Set_Node_Color(sibling, RBNode.BLACK);
                RBNode.Set_Node_Color(node.Get_Parent_Node(), RBNode.RED);
                this.Rotate_Right(node.Get_Parent_Node());
                sibling = (RBNode) RBNode.Get_Left_Node(node.Get_Parent_Node());
            }
            if (RBNode.isBlack(RBNode.Get_Left_Node(sibling)) && RBNode.isBlack(RBNode.Get_Right_Node(sibling))) {
                RBNode.Set_Node_Color(sibling, RBNode.RED);
                node = node.Get_Parent_Node();
                continue;
            }
            if (RBNode.isBlack(RBNode.Get_Left_Node(sibling))) {
                RBNode.Set_Node_Color(RBNode.Get_Right_Node(sibling), RBNode.BLACK);
                RBNode.Set_Node_Color(sibling, RBNode.RED);
                this.Rotate_Left(sibling);
                sibling = (RBNode) RBNode.Get_Left_Node(node.Get_Parent_Node());
            }
            RBNode.Set_Node_Color(sibling, RBNode.Get_Node_Color(node.Get_Parent_Node()));
            RBNode.Set_Node_Color(node.Get_Parent_Node(), RBNode.BLACK);
            RBNode.Set_Node_Color(RBNode.Get_Left_Node(sibling), RBNode.BLACK);
            this.Rotate_Right(node.Get_Parent_Node());
            node = this.root;
        }
        RBNode.Set_Node_Color(node, RBNode.BLACK);
    }

    private boolean Has_Right_Parent(RBNode node) {
        if (RBNode.Get_Right_Node(node.Get_GrandParent_Node()) == node.Get_Parent_Node()) {
            return true;
        }
        return false;
    }

    private boolean Has_Left_Parent(RBNode node) {
        if (RBNode.Get_Left_Node(node.Get_GrandParent_Node()) == node.Get_Parent_Node()) {
            return true;
        }
        return false;
    }

    private boolean Is_Right_Child(RBNode node) {
        if (RBNode.Get_Right_Node(node.Get_Parent_Node()) == node) {
            return true;
        }
        return false;
    }

    private boolean Is_Left_Child(RBNode node) {
        if (RBNode.Get_Left_Node(node.Get_Parent_Node()) == node) {
            return true;
        }
        return false;
    }
}
