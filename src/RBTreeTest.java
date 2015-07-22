import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
public class RBTreeTest {
	RBTree<Integer> tree;
	int[] first = {54,23,76,45,98,12,43,25,86,73};
	int[] firstLevelOrder = {54,23,86,12,43,76,98,25,45,73};
    Boolean[] firstColor = {true,false,false,true,true,true,true,false,false,false};
    int[] second = {190, 43, 23, 76, 58, 93, 68, 34, 66, 91};
    int[] secondLevelOrder = {43,23,76,34,66,93,58,68,91,190};
    Boolean[] secondColor = {true,true,false,false,true,true,false,false,false,false};

	int[] firstLevelOrderRemove1 = {54,23,86,12,43,76,98,25,45};//remove 73

	int[] firstLevelOrderRemove2 = {54,23,76,12,43,98,25,45};//remove 73,86
	int[] firstLevelOrderRemove3 = {45,23,76,12,43,98,25};//remove 73,86,54(root)
	int[] firstLevelOrderRemove4 = {45,25,76,23,43,98};//remove 73,86,54(root),12
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
		assertEquals(first.length, tree.size());
		assertFalse(tree.isEmpty());

		//test the structure using the traversals
		List<Integer> list = tree.getLevelOrder();
        List<Boolean> list2 = tree.getLevelOrderColor();
		for (int i = 0; i < firstLevelOrder.length; ++ i) {
			assertEquals((int)firstLevelOrder[i], (int)list.get(i));
            assertEquals(firstColor[i], list2.get(i));
		}

	}

    @Test
    public void testAddManySingle2() {
        //this will cause two single rotations, one in each direction
        for (int i = 0; i < secondLevelOrder.length; ++i) {
            boolean flag = tree.add(second[i]);
            assertTrue(flag);
        }
        assertEquals(second.length, tree.size());
        assertFalse(tree.isEmpty());

        //test the structure using the traversals
        List<Integer> list = tree.getLevelOrder();
        List<Boolean> list2 = tree.getLevelOrderColor();
        for (int i = 0; i < secondLevelOrder.length; ++ i) {
            assertEquals((int)secondLevelOrder[i], (int)list.get(i));
            assertEquals(secondColor[i], list2.get(i));
        }
    }

	@Test
	public void testRemoveleafNode() {
		//this will cause two single rotations, one in each direction
		for (int i = 0; i < firstLevelOrder.length; ++i) {
			boolean flag = tree.add(first[i]);
			assertTrue(flag);
		}
		assertTrue(tree.remove(73));
		assertEquals(firstLevelOrderRemove1.length, tree.size());
		assertFalse(tree.isEmpty());

		//test the structure using the traversals
		List<Integer> list = tree.getLevelOrder();
		//List<Boolean> list2 = tree.getLevelOrderColor();
		for (int i = 0; i < firstLevelOrderRemove1.length; ++ i) {
			assertEquals((int)firstLevelOrderRemove1[i], (int)list.get(i));
			//assertEquals(secondColor[i], list2.get(i));
		}
	}

	@Test
	public void testRemovemiddleNode() {
		//this will cause two single rotations, one in each direction
		for (int i = 0; i < firstLevelOrder.length; ++i) {
			boolean flag = tree.add(first[i]);
			assertTrue(flag);
		}
		assertTrue(tree.remove(73));
		assertTrue(tree.remove(86));
		assertEquals(firstLevelOrderRemove2.length, tree.size());
		assertFalse(tree.isEmpty());

		//test the structure using the traversals
		List<Integer> list = tree.getLevelOrder();
		//List<Boolean> list2 = tree.getLevelOrderColor();
		for (int i = 0; i < firstLevelOrderRemove2.length; ++ i) {
			assertEquals((int)firstLevelOrderRemove2[i], (int)list.get(i));
			//assertEquals(secondColor[i], list2.get(i));
		}
	}
	@Test
	public void testRemoveRootNode() {
		//this will cause two single rotations, one in each direction
		for (int i = 0; i < firstLevelOrder.length; ++i) {
			boolean flag = tree.add(first[i]);
			assertTrue(flag);
		}
		assertTrue(tree.remove(73));
		assertTrue(tree.remove(86));
		assertTrue(tree.remove(54));
		assertEquals(firstLevelOrderRemove3.length, tree.size());
		assertFalse(tree.isEmpty());

		//test the structure using the traversals
		List<Integer> list = tree.getLevelOrder();
		//List<Boolean> list2 = tree.getLevelOrderColor();
		for (int i = 0; i < firstLevelOrderRemove3.length; ++ i) {
			assertEquals((int)firstLevelOrderRemove3[i], (int)list.get(i));
			//assertEquals(secondColor[i], list2.get(i));
		}
	}
	@Test
	public void testRemoveBlackLeafNode() {
		//this will cause two single rotations, one in each direction
		for (int i = 0; i < firstLevelOrder.length; ++i) {
			boolean flag = tree.add(first[i]);
			assertTrue(flag);
		}
		assertTrue(tree.remove(73));
		assertTrue(tree.remove(86));
		assertTrue(tree.remove(54));
		assertTrue(tree.remove(12));
		assertEquals(firstLevelOrderRemove4.length, tree.size());
		assertFalse(tree.isEmpty());

		//test the structure using the traversals
		List<Integer> list = tree.getLevelOrder();
		//List<Boolean> list2 = tree.getLevelOrderColor();
		for (int i = 0; i < firstLevelOrderRemove4.length; ++ i) {
			assertEquals((int)firstLevelOrderRemove4[i], (int)list.get(i));
			//assertEquals(secondColor[i], list2.get(i));
		}
	}
}

