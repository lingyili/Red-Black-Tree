import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
/**
 * Created by lingyi on 6/28/15.
 */
public class RBTreeTest {
	RBTree<Integer> tree;
	int[] first = {54,23,76,45,98,12,43,25,86,73};
	int[] firstLevelOrder = {54,23,86,12,43,76,98,25,45,73};
	int[] firstLevelOrder2 = {54,23,76,12,43,98,25,45};
	int[] firstLevelOrder3 = {54,23,86,12,43,76,98,25,45};

	@Before
	public void setup() {
		tree = new RBTree<Integer>();
	}
	@Test
	public void testAddNull() {
		assertFalse(tree.add(null));
	}
	@Test
	public void testAddOne() {
		assertTrue(tree.add(1));
		assertEquals(1, tree.size());
		assertFalse(tree.isEmpty());
	}
	@Test
	public void testAddManyWithRotate() {
		for (int i = 0; i < first.length ; ++i) {
			assertTrue(tree.add(first[i]));
		}
		assertEquals(first.length, tree.size());
		assertFalse(tree.isEmpty());
	}
	@Test
	public void testAddManySingle() {
		//this will cause two single rotations, one in each direction
		for (int i = 0; i < firstLevelOrder.length; ++i) {
			boolean flag = tree.add(first[i]);
			assertTrue(flag);
		}
		//assertEquals(first.length, tree.size());
		//assertFalse(tree.isEmpty());

		//test the structure using the traversals
		List<Integer> list = tree.getLevelOrder();
		for (int i = 0; i < firstLevelOrder.length; ++ i) {
			assertEquals((int)firstLevelOrder[i], (int)list.get(i));
		}

	}
}

