import java.util.*;

/**
 *
 */
public class RBTree <E extends Comparable> implements BSTree<E> {
    private RBNode root;
    private int count;

    private class RBNode {
        E data;
        RBNode left, right, parent;
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
        if (item == null || contains(item)) {
            return false;
        }
        boolean toReturn;
        RBNode toAdd = new RBNode(item);
        if (root == null) {
            root = toAdd;
            root.isBlack = true;
            count++;
            return true;
        } else {
            toReturn = add(toAdd, root);
        }
        if (toReturn) {
            System.out.println(toAdd.data + " is added. its parent is: " + toAdd.parent.data);
            fixAdd(toAdd);
        }
        root.isBlack = true;
        showInfo(root);
        return toReturn;
    }

    private boolean add(RBNode toAdd, RBNode subRoot) {
        if (toAdd.data.compareTo(subRoot.data) == 0) {
            return false;
        }
        if (toAdd.data.compareTo(subRoot.data) < 0) {
            if (subRoot.left == null) {
                subRoot.left = toAdd;
                toAdd.parent = subRoot;
                count++;
                return true;
            } else {
                System.out.println("toAdd is: " + toAdd.data + " subRoot.left is " + subRoot.left.data);
                return add(toAdd, subRoot.left);
            }
        } else {
            if (subRoot.right == null) {
                subRoot.right = toAdd;
                toAdd.parent = subRoot;
                count++;
                return true;
            } else {
                System.out.println("toAdd is: " + toAdd.data + " subRoot.right is " + subRoot.right.data);
                return add(toAdd, subRoot.right);
            }
        }
    }


    private void fixAdd(RBNode toAdd) {
        RBNode parent, gparent, uncle;
        parent = toAdd.parent;

        //Case1 & case 2 don't need to fix if toAdd is root or its parent is black
        while (parent != null && !parent.isBlack) {
            boolean parentIsRight = false;
            gparent = parent.parent;
            if (toAdd.parent.parent.right != null && toAdd.parent.parent.right == toAdd.parent) {
                parentIsRight = true;
            }
            if (!parentIsRight) {
                //parent is left
                uncle = gparent.right;
                if (uncle != null) {
                    System.out.println("uncle color is: " + uncle.isBlack);
                }
                //case 3 uncle is red
                if (uncle != null && !uncle.isBlack) {
                    uncle.isBlack = true;
                    parent.isBlack = true;
                    gparent.isBlack = false;
                    toAdd = gparent;
                } else{
                    // case4: uncle is black and toAdd is in right
                    if (parent.right == toAdd) {
                        // the current node is the parent now
                        toAdd = parent;
                        parent = rotateLeft(parent);
                    } else {
                        //case5: else uncle is black and toAdd is in left
                        parent.isBlack = true;
                        gparent.isBlack = false;
                        rotateRight(gparent);
                    }
                }
            } else {
                //parent is right
                uncle = gparent.left;
                //case 3 uncle is red
                if (uncle != null && !uncle.isBlack) {
                    uncle.isBlack = true;
                    parent.isBlack = true;
                    gparent.isBlack = false;
                    toAdd = gparent;
                } else{
                    if (parent.left == toAdd) {
                        // case4: uncle is black and toAdd is in left
                        toAdd = parent;
                        parent = rotateRight(parent);
                    } else{
                        //case5: else uncle is black and toAdd is in right
                        parent.isBlack = true;
                        gparent.isBlack = false;
                        rotateLeft(gparent);
                    }
                }
            }
        }
    }

    private RBNode rotateLeft(RBNode oob) {
        System.out.println("rotateLeft oob is " + oob.data);
        RBNode newRoot = oob.right;
        oob.right = newRoot.left;
        newRoot.left = oob;
        if (oob.parent != null) {
            boolean oobIsRight = false;
            if (oob.parent.right == oob) {
                oobIsRight = true;
            }
            if (oobIsRight) {
                oob.parent.right = newRoot;
            } else {
                oob.parent.left = newRoot;
            }
        }
        newRoot.parent = oob.parent;
        oob.parent = newRoot;
        if (oob == root) {
            root = newRoot;
        }
        return newRoot;
    }

    private RBNode rotateRight(RBNode oob) {
        System.out.println("rotateRight oob is " + oob.data);
        RBNode newRoot = oob.left;
        oob.left = newRoot.right;
        newRoot.right = oob;
        if (oob.parent != null) {
            boolean oobIsRight = false;
            if (oob.parent.right == oob) {
                oobIsRight = true;
            }
            if (oobIsRight) {
                oob.parent.right = newRoot;
            } else {
                oob.parent.left = newRoot;
            }
        }
        newRoot.parent = oob.parent;
        oob.parent = newRoot;
        if (oob == root) {
            root = newRoot;
        }
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
        }
        RBNode currentNode = root;
        while (currentNode.right != null) {
            currentNode = currentNode.right;
        }
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


    public RBNode find(E item) {
        if (item == null) {
            return null;
        }
        if (isEmpty()) {
            return null;
        } else {
            return findHelper(root, item);
        }
    }

    private RBNode findHelper(RBNode currentNode, E item) {
        if (currentNode != null) {
            int compare = item.compareTo(currentNode.data);
            if (compare == 0) {
                return currentNode;
            } else if (compare < 0) {
                return findHelper(currentNode.left, item);
            } else {
                return findHelper(currentNode.right, item);
            }
        }
        return null;
    }

    /**
     * removes the given item from the tree O(log n)
     *
     * @param item the item to remove
     * @return true if item removed, false if item not found
     */
    public boolean remove(E item) {
        if (item == null) {return false;}
        if (isEmpty()) {
            return false;
        } else if (!contains(item)) {
            return false;
        }
        count--;
        RBNode toDelete = find(item);
        removeHelper(toDelete);
        return true;
    }

    private  RBNode removeHelper(RBNode toRemove){
        RBNode toDelete;
        //toFix is the child node
        RBNode toFix;
        if(toRemove.left == null || toRemove.right == null ){
            toDelete = toRemove;
        } else {
            //two subtrees, we need successor
            toDelete = max(toRemove.left);
        }
        if(toDelete.left != null){
            toFix = toDelete.left;
        } else {
            toFix = toDelete.right;
        }
        if(toFix!= null){
            toFix.parent = toDelete.parent;
        }
        if(toDelete.parent == null){
            root = toFix;
        } else {
            if( toDelete.parent.left == toDelete){
                toDelete.parent.left = toFix;
            } else{
                toDelete.parent.right = toFix;
            }
        }
        //set the parent pointer
        if(toRemove != toDelete){
            toRemove.isBlack = toDelete.isBlack;
            toRemove.data = toDelete.data;
        }
        if(toDelete.isBlack){
            fixRemove(toFix);
        }
        return toDelete;
    }
    //find the min in the subtree of current

    private  RBNode min(RBNode current){
        if (current.left == null){return  current;}
        return min(current.left);
    }

    private void fixRemove(RBNode node) {
        RBNode brother;
        while ((node != null && node.isBlack) && (node != root )) {
            //Case 1,2 return
            if(!selfIsRight(node)){
                brother = node.parent.right;
                if(brother != null && !brother.isBlack){
                    //Case 3:brother is red
                    brother.isBlack = true;
                    node.parent.isBlack = false;
                    rotateLeft(node.parent);
                    brother = node.parent.right;
                    //change to case 4
                }
                if( (brother.left == null || brother.left.isBlack)
                        && (brother.right == null || brother.right.isBlack)){
                    //case 4: node,brother, sons of brother are all black
                    brother.isBlack = false;
                    node = node.parent;
                }else{
                    //case 5: node is black, brother is black,brother left is red,right is black
                    if((brother.right == null || brother.right.isBlack)){
                        brother.left.isBlack = true;
                        brother.isBlack = false;
                        rotateRight(brother);
                        brother = node.parent.right;
                    }
                        //case 6: node is black, brother is black,brother right is red
                    brother.isBlack = node.parent.isBlack;
                    node.parent.isBlack = true;
                    if(brother.right != null){brother.right.isBlack = true;}
                    rotateLeft(node.parent);
                    node = root;
                }
            } else {
                brother = node.parent.left;
                if(!brother.isBlack){
                    //Case 3:brother is red
                    brother.isBlack = true;
                    node.parent.isBlack = false;
                    rotateRight(node.parent);
                    brother = node.parent.left;
                    //change to case 4
                }
                if((brother.left == null || brother.left.isBlack)
                        && (brother.right == null || brother.right.isBlack)){
                    //case 4: node,brother, sons of brother are all black
                    brother.isBlack = false;
                    node = node.parent;

                }else{
                    //case 5: node is black, brother is black,brother left is red,right is black
                    if((brother.left != null && !brother.left.isBlack)){
                        brother.left.isBlack = true;
                        brother.isBlack = false;
                        rotateLeft(brother);
                        brother = node.parent.left;
                    }
                    //case 6: node is black, brother is black,brother right is red
                    brother.isBlack = node.parent.isBlack;
                    node.parent.isBlack = true;
                    rotateRight(node.parent);
                    if(brother.left != null){brother.left.isBlack = true;}
                    node = root;
                }
            }
        }
//        if(node != null && node.parent == null){
//            root = node;
//        }
        if(node != null){
            node.isBlack = true;
        }
    }
    private  RBNode max(RBNode current){
        if (current.right == null){return  current;}
        return max(current.right);
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

    public List<Boolean> getLevelOrderColor(){
        ArrayList<Boolean> levelOrder = new ArrayList<>();
        if(root == null){return levelOrder;}
        Queue<RBNode> visited = new ArrayDeque<>();
        visited.add(root);
        RBNode current;
        while(!visited.isEmpty()){
            current = visited.poll();
            if(current.left != null) {visited.add(current.left);}
            if(current.right != null) {visited.add(current.right);}
            levelOrder.add(current.isBlack);
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
    private boolean parentIsRight(RBNode node){
        if(node == null) {
            return false;
        }
        boolean parentIsRight = false;
        if (node.parent.parent.right != null && node.parent.parent.right == node.parent) {
            parentIsRight = true;
        }
        return parentIsRight;
    }
    private boolean selfIsRight(RBNode node){
        if(node == null) {
            return false;
        }
        boolean selfIsRight = false;
        if (node.parent.right != null && node.parent.right == node) {
            selfIsRight = true;
        }
        return selfIsRight;
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
