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
            System.out.println(toAdd.data+" is added");
            int situation = addCase(toAdd);
            while(situation != 1 && situation != 2 ){
                System.out.println("toAdd item: "+toAdd.data+" The situation is: "+ situation);
                toAdd = fixAdd(situation, toAdd);
                situation = addCase(toAdd);
            }
        }
        root.isBlack = true;
        showInfo(root);
        return toReturn;
    }

    private boolean add(RBNode toAdd, RBNode subRoot){
        if (toAdd.data.compareTo(subRoot.data) == 0){return  false;}
        if (toAdd.data.compareTo(subRoot.data) < 0){
            if(subRoot.left == null){
                subRoot.left = toAdd;
                toAdd.parent = subRoot;
                count++;
                return  true;
            } else {
                return add(toAdd, subRoot.left);
            }
        }else {
            if(subRoot.right == null){
                subRoot.right = toAdd;
                toAdd.parent = subRoot;
                count++;
                return  true;
            } else {
                System.out.println("toAdd is: "+ toAdd.data+" subRoot.right is "+ subRoot.right.data);
                return add(toAdd, subRoot.right);
            }
        }
    }
    private int addCase(RBNode toAdd){
        if(toAdd == root || toAdd.isBlack){return 1;}
        if(toAdd.parent.isBlack){return 2;}
        //parent is red
        boolean parentIsRight = false;
         if(toAdd.parent.parent.right != null && toAdd.parent.parent.right == toAdd.parent){
             parentIsRight = true;
        }

        //uncle is red too
        if(parentIsRight) {
            if (toAdd.parent.parent.left != null && !toAdd.parent.parent.left.isBlack)
            {return 3;}

        } else if(toAdd.parent.parent.right != null && !toAdd.parent.parent.right.isBlack){
            return 3;
        }

        boolean toAddIsRight = false;
        if(toAdd.parent.right != null && toAdd.parent.right == toAdd){
            toAddIsRight = true;
        }
        //parent:red, uncle:black, toAdd is in right of parent
        if(((toAdd.parent.parent.left == null || toAdd.parent.parent.left.isBlack ) && toAddIsRight)
                ||((toAdd.parent.parent.right == null || toAdd.parent.parent.right.isBlack ) && toAddIsRight)) {
            return 4;
        }
        //parent:red, uncle:black, toAdd is in left of parent
        return 5;
    }

    private RBNode fixAdd(int situation, RBNode toAdd){
        boolean parentIsRight = false;
        if(toAdd.parent.parent.right != null && toAdd.parent.parent.right == toAdd.parent){
            parentIsRight = true;
        }

        if(situation == 3){
            toAdd.parent.isBlack = true;
            toAdd.parent.parent.isBlack = false;
            //make uncle black
            if(parentIsRight && toAdd.parent.parent.left!= null ){
                toAdd.parent.parent.left.isBlack = true;
            } else{
                if(toAdd.parent.parent.right!= null) {toAdd.parent.parent.right.isBlack = true;}
            }
            return toAdd.parent.parent;
        }
        boolean selfIsRight = false;
        if(toAdd.parent.right != null && toAdd.parent.right == toAdd){
            selfIsRight = true;
        }
        if(situation == 4){
            if(selfIsRight){return rotateLeft(toAdd.parent);}
            return rotateRight(toAdd.parent);

        }
        //if(situation == 5)
        toAdd.parent.isBlack = true;
        toAdd.parent.parent.isBlack = false;
        if(!parentIsRight){
            rotateRight(toAdd.parent.parent);
        }else{
            rotateLeft(toAdd.parent.parent);
        }
        return toAdd;
    }

    private RBNode rotateLeft(RBNode oob){
        System.out.println("rotateLeft oob is "+ oob.data);
        RBNode newRoot = oob.right;
        oob.right = newRoot.left;
        newRoot.left = oob;
        if(oob.parent!= null){
            boolean oobIsRight = false;
            if(oob.parent.right == oob){
                oobIsRight = true;
            }
            if(oobIsRight){
                oob.parent.right = newRoot;
            }else {
                oob.parent.left = newRoot;
            }
        }
        newRoot.parent = oob.parent;
        oob.parent = newRoot;
        if(oob == root) {root = newRoot;}
        return newRoot;
    }

    private RBNode rotateRight(RBNode oob){
        System.out.println("rotateRight oob is "+ oob.data);
        RBNode newRoot = oob.left;
        oob.left = newRoot.right;
        newRoot.right = oob;
        if(oob.parent!= null){
            boolean oobIsRight = false;
            if(oob.parent.right == oob){
                oobIsRight = true;
            }
            if(oobIsRight){
                oob.parent.right = newRoot;
            }else {
                oob.parent.left = newRoot;
            }
        }
        newRoot.parent = oob.parent;
        oob.parent = newRoot;
        if(oob == root) {root = newRoot;}
        return newRoot;
    }





    /**
     * returns the maximum element held in the tree.  null if tree is empty. O(log n)
     *
     * @return maximum item or null if empty
     */
    public E max() {
        if (isEmpty()) {return null;}
        RBNode currentNode = root;
        while (currentNode.right != null) {currentNode = currentNode.right;}
        return currentNode.data;
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
    public boolean remove(E item){
        if(item == null) return false;
        boolean result = contains(item);
        root = removeRecurse(root, item);
        if(result)count--;
        return result;
    }

    private RBNode removeRecurse( RBNode node, E item){
        if(node == null) return null;
        if(item.compareTo(node.data) < 0){
            node.left = removeRecurse(node.left, item);
        } else if (item.compareTo(node.data) > 0){
            node.right = removeRecurse(node.right,item);
        }else if(node.right != null && node.left != null){//toRemove has two subtrees
            node.data = max(node.left);
            node.right = removeRecurse(node.right, node.data);//remove the succ node
        } else{//one subtree or leaf node
            node = (node.left != null) ? node.left : node.right;
        }
        return fixRemove(node);
    }
    private  E max(RBNode current){
        if (current.right == null){return  current.data;}
        return max(current.right);
    }
    private RBNode fixRemove(RBNode node){return null;}

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
        if (root == null) {return list;}
        preOrderRecursive(root, list);
        return list;
    }
    private void preOrderRecursive(RBNode node, List<E> list) {
        if (node == null) {return;}
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
    public boolean getRoot() {
        return root.isBlack;
    }

    public void showInfo(RBNode toShow){
        System.out.println("Root: "+ toShow.data+" black: "+toShow.isBlack);
    }
}
