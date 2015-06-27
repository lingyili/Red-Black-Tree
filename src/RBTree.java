import java.util.*;

/**
 *
 */
public class RBTree <E extends Comparable> implements BSTree<E> {
    private RBNode root;
    private int count;
    private class RBNode {
        E data;
        RBNode left,right,parent;
        boolean isBlack;
        public RBNode(E data) {
            this.data = data;
            this.isBlack = false;
        }
    }
    /**
     * Adds the item to the tree.  Duplicate items and null items should not be added. O(log n)
     *
     * @param item the item to add
     * @return true if item added, false if it was not
     */
    public boolean add(E item) {
        if(item == null || contains(item)) {return  false;}
        boolean toReturn;
        RBNode toAdd = new RBNode(item);
        if(root == null) {
            root = toAdd;
            root.isBlack = true;
            count++;
            return true;
        } else {
            toReturn =  add(toAdd, root);
        }
        if(toReturn){
            int situation = addCase(toAdd);
            while(situation != 1 && situation != 2 ){
                toAdd = rebanlance(situation,toAdd);
                situation = addCase(toAdd);
            }
        }
        return toReturn;
    }

    private boolean add(RBNode toAdd, RBNode root){
        if (toAdd.data.compareTo(root.data) == 0){return  false;}
        if (toAdd.data.compareTo(root.data) < 0){
            if(root.left == null){
                root.left = toAdd;
                toAdd.parent = root;
                count++;
                return  true;
            } else {
                return add(toAdd, root.left);
            }
        }else {
            if(root.right == null){
                root.right = toAdd;
                toAdd.parent = root;
                count++;
                return  true;
            } else {
                return add(toAdd, root.right);
            }
        }
    }
    private int addCase(RBNode toAdd){
        if(toAdd == root){return 1;}
        if(toAdd.parent.isBlack){return 2;}
        //parent is red
        boolean parentIsRight = false;
         if(toAdd.parent.parent.right != null && toAdd.parent.parent.right == toAdd.parent){
             parentIsRight = true;
        }

        //uncle is red too
        if(parentIsRight){
            if(  toAdd.parent.right != null && !toAdd.parent.left.isBlack)return 3;
        } else if(toAdd.parent.right != null && !toAdd.parent.right.isBlack){
            return 3;
        }

        boolean toAddIsRight = false;
        if(toAdd.parent.right != null && toAdd.parent.right == toAdd.parent){
            toAddIsRight = true;
        }
        //parent:red, uncle:black, toAdd is in right of parent
        if((toAdd.parent.left == null || toAdd.parent.left.isBlack ) && toAddIsRight) return 4;
        //parent:red, uncle:black, toAdd is in left of parent
        return 5;
    }

    private RBNode rebanlance(int situation, RBNode toAdd){
        boolean parentIsRight = false;
        if(toAdd.parent.parent.right != null && toAdd.parent.parent.right == toAdd.parent){
            parentIsRight = true;
        }

        if(situation == 3){
            toAdd.parent.isBlack = true;
            toAdd.parent.parent.isBlack = false;
            //make uncle black
            if(parentIsRight && toAdd.parent.left!= null ){
                toAdd.parent.left.isBlack = true;
            } else{
                if(toAdd.parent.right!= null) {toAdd.parent.right.isBlack = true;}
            }
            return toAdd.parent.parent;
        }
        if(situation == 4){
            return rotateLeft(toAdd);

        }
        //if(situation == 5)
        toAdd.parent.isBlack = true;
        toAdd.parent.parent.isBlack = false;
        return rotateRight(toAdd.parent);
    }

    private RBNode rotateLeft(RBNode oob){
        RBNode newRoot = oob.right;
        oob.right = newRoot.left;
        newRoot.left = oob;
        if(oob == root) root = newRoot;
        return newRoot;
    }

    private RBNode rotateRight(RBNode oob){
        RBNode newRoot = oob.left;
        oob.left = newRoot.right;
        newRoot.right = oob;
        if(oob == root) root = newRoot;
        return newRoot;
    }





    /**
     * returns the maximum element held in the tree.  null if tree is empty. O(log n)
     *
     * @return maximum item or null if empty
     */
    public E max() {
        if (isEmpty()) {
            return null;
        } else {
            RBNode currentNode = root;
            while (currentNode.right != null) {
                currentNode = currentNode.right;
            }
            return currentNode.data;
        }
    }

    /**
     * returns the number of items in the tree O(1) with variable
     *
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * O(1)
     *
     * @return true if tree has no elements, false if tree has anything in it.
     */
    public boolean isEmpty() {
        return count == 0 && root == null;
    }

    /**
     * O(log n)
     *
     * @return the minimum element in the tree or null if empty
     */
    public E min() {
        if (isEmpty()) {
            return null;
        } else {
            RBNode currentNode = root;
            while (currentNode.left != null) {
                currentNode = currentNode.left;
            }
            return currentNode.data;
        }
    }

    /**
     * Checks for the given item in the tree. O(log n)
     *
     * @param item the item to look for
     * @return true if item is in tree, false otherwise
     */
    public boolean contains(E item) {
        if (item == null) {
            return false;
        }
        if (isEmpty()) {
            return false;
        } else {
            return ifContains(root, item);
        }
    }
    private boolean ifContains(RBNode currentNode, E item) {
        if (currentNode != null) {
            int compare = item.compareTo(currentNode.data);
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                return ifContains(currentNode.left, item);
            } else {
                return ifContains(currentNode.right, item);
            }
        }
        return false;
    }

    /**
     * removes the given item from the tree O(log n)
     *
     * @param item the item to remove
     * @return true if item removed, false if item not found
     */
    public boolean remove(E item) {
        return false;
    }

    /**
     * returns an iterator over this collection
     * iterator is based on an in-order traversal
     */
    public Iterator<E> iterator() {
        List<E> list = getInOrder();
        return list.iterator();
    }

    /**
     *
     * @return a list of the data in order traversal order
     */
    public List<E> getInOrder() {
        List<E> list = new ArrayList<>();
        if (root == null) {return list;}
        inOrderRecursive(root,list);
        return list;
    }
    private void inOrderRecursive(RBNode node, List<E> list) {
        if (node == null) {return;}
        inOrderRecursive(node.left, list);
        list.add(node.data);
        inOrderRecursive(node.right, list);
    }

    /**
     * O(n)
     *
     * @return a list of the data in post-order traversal order
     */
    public List<E> getPostOrder() {
        List<E> list = new ArrayList<>();
        if (isEmpty()) {return list;}
        postOrderRecursive(root, list);
        return list;
    }
    private void postOrderRecursive(RBNode node, List<E> list) {
        if (node == null) {return;}
        postOrderRecursive(node.left, list);
        postOrderRecursive(node.right, list);
        list.add((E) node.data);
    }
    /**
     * O(n)
     *
     * @return a list of the data in level-order traversal order
     */
    public List<E> getLevelOrder(){
        ArrayList<E> levelOrder = new ArrayList<>();
        if(root == null){return levelOrder;}
        Queue<RBNode> visited = new ArrayDeque<>();
        visited.add(root);
        RBNode current;
        while(!visited.isEmpty()){
            current = visited.poll();
            if(current.left != null) {visited.add(current.left);}
            if(current.right != null) {visited.add(current.right);}
            levelOrder.add(current.data);
        }
        return levelOrder;
    }

    /**
     * O(n)
     *
     * @return a list of the data in pre-order traversal order
     */
    public List<E> getPreOrder() {
        List<E> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        preOrderRecursive(root, list);
        return list;
    }
    private void preOrderRecursive(RBNode node, List<E> list) {
        if (node == null) {
            return;
        }
        list.add(node.data);
        preOrderRecursive(node.left,list);
        preOrderRecursive(node.right,list);
    }

    /**
     * O(1) [ignore garbage collection costs]
     * <p>
     * Removes all the elements from this tree
     */
    public void clear() {
        count = 0;
        root = null;
    }
}
