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
    Boolean[] firstColor = {true,false,false,true,true,true,true,false,false,false};
    int[] second = {190, 43, 23, 76, 58, 93, 68, 34, 66, 91};
    int[] secondLevelOrder = {43,23,76,34,66,93,58,68,91,190};
    Boolean[] secondColor = {true,true,false,false,true,true,false,false,false,false};
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
}

