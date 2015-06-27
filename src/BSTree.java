import java.util.Iterator;
import java.util.List;


public interface BSTree<E extends Comparable>  extends Iterable<E> {
	
	/**
	 * Adds the item to the tree.  Duplicate items and null items should not be added. O(log n)
	 * 
	 * @param item the item to add
	 * @return true if item added, false if it was not
	 */
	boolean add(E item);
	
	/**
	 * returns the maximum element held in the tree.  null if tree is empty. O(log n)
	 * @return maximum item or null if empty
	 */
	E max();
	
	/**
	 * returns the number of items in the tree O(1) with variable
	 *
	 * @return 
	 */
	int size();
	
	/**
	 * O(1)
	 * @return true if tree has no elements, false if tree has anything in it.
	 */
	boolean isEmpty();
	
	/**
	 * O(log n)
	 * @return the minimum element in the tree or null if empty
	 */
	E min();
	
	/**
	 * Checks for the given item in the tree. O(log n)
	 *
	 * @param item the item to look for
	 * @return true if item is in tree, false otherwise
	 */
	boolean contains(E item);
	
	/**
	 * removes the given item from the tree O(log n)
	 *
	 * @param item the item to remove
	 * @return true if item removed, false if item not found
	 */
	boolean remove(E item);
	
    /**
     * returns an iterator over this collection
     * iterator is based on an in-order traversal
     */
	Iterator<E> iterator();
	
	/**
	 * O(n)
	 * @return a list of the data in post-order traversal order
	 */
    List<E> getPostOrder();
      
   /**
    * O(n)
    * @return a list of the data in level-order traversal order
    */
    List<E> getLevelOrder();
    
    /**
	 * O(n)
     * @return a list of the data in pre-order traversal order
     */
    List<E> getPreOrder();
    
    /**
	 * O(1) [ignore garbage collection costs]
	 *
     * Removes all the elements from this tree
     */
    void clear();
}
