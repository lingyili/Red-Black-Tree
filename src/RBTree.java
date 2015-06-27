import java.util.*;

/**
 * Created by lingyi on 6/27/15.
 */
public class RBTree <E extends Comparable> implements BSTree<E> {
    RBNode root;
    int count;
    private class RBNode {
        E data;
        RBNode left;
        RBNode right;
        boolean isBlack;
        public RBNode(E data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.isBlack = false;
        }
        public boolean getColor() {
            return isBlack;
        }
    }
    public RBTree() {
        root = null;
        count = 0;
    }
    /**
     * Adds the item to the tree.  Duplicate items and null items should not be added. O(log n)
     *
     * @param item the item to add
     * @return true if item added, false if it was not
     */
    public boolean add(E item) {
        if (isEmpty()) {
            RBNode newNode = new RBNode(item);
            newNode.isBlack = true;
            root = newNode;
        }
        return false;
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
        if (root == null) {
            return list;
        }
        inOrderRecursive(root,list);
        return list;
    }
    private void inOrderRecursive(RBNode node, List<E> list) {
        if (node == null) {
            return;
        }
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
        if (isEmpty()) {
            return list;
        }
        postOrderRecursive(root, list);
        return list;
    }
    private void postOrderRecursive(RBNode node, List<E> list) {
        if (node == null) {
            return;
        }
        postOrderRecursive(node.left, list);
        postOrderRecursive(node.right, list);
        list.add((E) node.data);
    }
    /**
     * O(n)
     *
     * @return a list of the data in level-order traversal order
     */
    public List<E> getLevelOrder() {
        Queue<RBNode> nodequeue = new ArrayDeque<>();
        List<E> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        if (root != null)
            nodequeue.add(root);
        list.add(root.data);
        while (!nodequeue.isEmpty()) {
            RBNode next = nodequeue.remove();
            if (next.left != null)
            {
                nodequeue.add(next.left);
                list.add(next.left.data);
            }
            if (next.right != null)
            {
                nodequeue.add(next.right);
                list.add(next.right.data);
            }
        }
        return list;
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
