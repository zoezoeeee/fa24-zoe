import edu.princeton.cs.algs4.In;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import deque.*;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.
    @Test
    public void testAddFirst() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        // Check that addFirst works on an empty deque.
        lld.addFirst(1);
        assertThat(lld.toList()).containsExactly(1).inOrder();

        // Check that addFirst works on a non-empty deque.
        lld.addFirst(0);
        assertThat(lld.toList()).containsExactly(0, 1).inOrder();

        // Add some elements to a deque and remove them all, then check that addFirst still works.
        lld.removeFirst();
        lld.removeLast();
        lld.addFirst(3);
        lld.addFirst(2);
        assertThat(lld.toList()).containsExactly(2, 3).inOrder();
    }

    @Test
    public void testAddLast() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        // Check that addLast works on an empty deque.
        lld.addLast(0);
        assertThat(lld.toList()).containsExactly(0).inOrder();

        //  Check that addLast works on a non-empty deque.
        lld.addLast(-1);
        assertThat(lld.toList()).containsExactly(0, -1).inOrder();

        // Add some elements to a deque and remove them all, then check that addLast still works.
        lld.removeFirst();
        lld.removeLast();
        lld.addLast(2);
        lld.addLast(3);
        assertThat(lld.toList()).containsExactly(2, 3).inOrder();
    }

    @Test
    public void testSize() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        // Check that size works.
        assertThat(lld.size()).isEqualTo(0);
        lld.addFirst(1); // [1]
        assertThat(lld.size()).isEqualTo(1);

        // Add some elements to a deque and remove them all, then check that size still works.
        lld.removeFirst();
        assertThat(lld.size()).isEqualTo(0);

        // Remove from an empty deque, then check that size still works.
        lld.removeFirst();
        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void testIsEmpty() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        //Check that isEmpty works on an empty deque.
        assertThat(lld.isEmpty()).isTrue();

        // Check that isEmpty works on a non-empty deque.
        lld.addFirst(1); // [1]
        assertThat(lld.isEmpty()).isFalse();
    }

    @Test
    public void testToList(){
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        // Check that toList works with empty LinkedListDeque61B.
        assertThat(lld.isEmpty()).isTrue();

        // Check that toList works with non-empty LinkedListDeque61B.
        lld.addFirst(1); // [1]
        lld.addFirst(0); // [0, 1]
        assertThat(lld.toList()).containsExactly(0,1).inOrder();
    }
    @Test
    public void testGet() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(1);  // [1]
        lld1.addFirst(0); // [0, 1]
        lld1.addLast(2);  // [0, 1, 2]

        // Check that get works on a valid index.
        assertThat(lld1.get(0)).isEqualTo(0); // first element
        assertThat(lld1.get(lld1.size() - 1)).isEqualTo(2); // last element
        // Check that get works on a large, out of bounds index.
        assertThat(lld1.get(lld1.size())).isEqualTo(null);
        // Check that get works on a negative index.
        assertThat(lld1.get(-1)).isEqualTo(null);
    }

    @Test
    public void testGetRecursive() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

        lld1.addLast(1);  // [1]
        lld1.addFirst(0); // [0, 1]
        lld1.addLast(2);  // [0, 1, 2]

        // Check that get works on a valid index.
        assertThat(lld1.get(0)).isEqualTo(0); // first element
        assertThat(lld1.get(lld1.size() - 1)).isEqualTo(2); // last element
        // Check that get works on a large, out of bounds index.
        assertThat(lld1.get(lld1.size())).isEqualTo(null);
        // Check that get works on a negative index.
        assertThat(lld1.get(-1)).isEqualTo(null);
    }

    @Test
    public void testRemoveFirst() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        lld.addLast(1);  // [1]
        lld.addFirst(0); // [0, 1]
        lld.addLast(2);  // [0, 1, 2]

        assertThat(lld.removeFirst()).isEqualTo(0);                // test return value
        assertThat(lld.toList()).containsExactly(1, 2).inOrder();  // test values after removed
        assertThat(lld.removeFirst()).isEqualTo(1);
        assertThat(lld.toList()).containsExactly(2).inOrder();
        assertThat(lld.removeFirst()).isEqualTo(2);
        assertThat(lld.toList()).isEmpty();

        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addFirst(1);
        assertThat(lld2.removeFirst()).isEqualTo(1);
        assertThat(lld2.toList()).isEmpty();
    }

    @Test
    public void testRemoveLast() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        lld.addLast(1);  // [1]
        lld.addFirst(0); // [0, 1]
        lld.addLast(2);  // [0, 1, 2]

        assertThat(lld.removeLast()).isEqualTo(2);                // test return value
        assertThat(lld.toList()).containsExactly(0, 1).inOrder();  // test values after removed
        assertThat(lld.removeLast()).isEqualTo(1);
        assertThat(lld.toList()).containsExactly(0).inOrder();  // test values after removed
        assertThat(lld.removeLast()).isEqualTo(0);
        assertThat(lld.toList()).isEmpty();

        Deque61B<Integer> lld2 = new LinkedListDeque61B<>();
        lld2.addFirst(1);
        assertThat(lld2.removeLast()).isEqualTo(1);
        assertThat(lld2.toList()).isEmpty();
    }
}