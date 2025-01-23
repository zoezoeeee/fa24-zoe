import edu.princeton.cs.algs4.In;
import jh61b.utils.Reflection;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }

    @Test
    public void testBasicAdd() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();  // ad1 = [ ]
        Deque61B<Integer> ad2 = new ArrayDeque61B<>(); // ad2 = [ ]

        // Check that addFirst works on an empty deque.
        ad1.addFirst("a");                          // ad1 = ["a"]
        assertThat(ad1.toList()).containsExactly("a").inOrder();

        // Check that addLast works on an empty deque.
        ad2.addLast(1);                             // ad2 = [1]
        assertThat(ad2.toList()).containsExactly(1).inOrder();

        // Check that addFirst works on a non-empty deque.
        ad1.addFirst("b");                          // ad1 = ["b", "a"]
        assertThat(ad1.toList()).containsExactly("b", "a").inOrder();

        // Check that addLast works on a non-empty deque.
        ad2.addLast(2);                             // ad2 = [1, 2]
        assertThat(ad2.toList()).containsExactly(1, 2).inOrder();

        // Check that addFirst works when called on a full underlying array
        ad1.addFirst("c");
        ad1.addFirst("d");
        ad1.addFirst("e");
        ad1.addFirst("f");
        ad1.addFirst("g");
        ad1.addFirst("h");
        ad1.addFirst("i");                          // trigger resize up
        assertThat(ad1.toList()).containsExactly("i", "h", "g", "f", "e", "d", "c", "b", "a").inOrder();

        // Check that addLast works when called on a full underlying array
        ad2.addLast(3);
        ad2.addLast(4);
        ad2.addLast(5);
        ad2.addLast(6);
        ad2.addLast(7);
        ad2.addLast(8);
        ad2.addLast(9);                             // trigger resize up
        assertThat(ad2.toList()).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9).inOrder();
    }

    @Test
    public void testAddAfterRemove() {
        Deque61B<Integer> ad1 = new ArrayDeque61B<>();  // ad1 = [ ]
        Deque61B<String > ad2 = new ArrayDeque61B<>(); // ad2 = [ ]

        // Add some elements to a deque and remove them all, then check that addFirst still works.
        ad1.addFirst(1);
        ad1.addFirst(2);
        ad1.removeFirst();
        ad1.removeFirst();
        ad1.addFirst(3);
        ad1.addFirst(4);
        assertThat(ad1.toList()).containsExactly(4, 3).inOrder();

        // Add some elements to a deque and remove them all, then check that addLast still works.
        ad2.addFirst("a");
        ad2.addFirst("b");
        ad2.removeLast();
        ad2.removeFirst();
        ad2.addLast("c");
        ad2.addLast("d");
        assertThat(ad2.toList()).containsExactly("c", "d").inOrder();
    }

    @Test
    public void testRemove_first() {
        // Check that removeFirst works.
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(2);
        ad.addFirst(1);
        assertThat(ad.toList()).containsExactly(1,2).inOrder();
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(2).inOrder();
    }

    @Test
    public void testRemove_last() {
        // Check that removeLast works.
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(2);
        ad.addFirst(1);
        assertThat(ad.toList()).containsExactly(1,2).inOrder();
        ad.removeLast();
        assertThat(ad.toList()).containsExactly(1).inOrder();
    }

    @Test
    public void testRemove_first_to_empty () {
        // Add some elements to a deque and remove almost all of them.
        // Check that removing the last element with removeFirst works.
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);  // ad = [3, 2, 1]
        ad.removeFirst();
        ad.removeFirst();   // ad = [1]
        ad.removeFirst();
        assertThat(ad.toList()).isEmpty();
    }

    @Test
    public void testRemove_last_to_empty () {
        // Add some elements to a deque and remove almost all of them.
        // Check that removing the last element with removeLast works.
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);  // ad = [3, 2, 1]
        ad.removeLast();
        ad.removeLast();   // ad = [1]
        ad.removeLast();
        assertThat(ad.toList()).isEmpty();
    }

    @Test
    public void testRemove_first_to_one () {
        // Add some elements to a deque and remove almost all of them.
        // Check that removing the second to last element with removeFirst works.
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);  // ad = [3, 2, 1]
        ad.removeFirst();    // ad = [2, 1]
        ad.removeFirst();
        assertThat(ad.toList()).containsExactly(1).inOrder();
    }

    @Test
    public void testRemove_last_to_one () {
        // Add some elements to a deque and remove almost all of them.
        // Check that removing the second to last element with removeLast works.

        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);  // ad = [3, 2, 1]
        ad.removeLast();    // ad = [3, 2]
        ad.removeLast();
        assertThat(ad.toList()).containsExactly(3).inOrder();
    }

    @Test
    public void testRemove_first_trigger_resize() {
        // Called when usage factor is <= 25% and array size > 8.
        // Checks that the array resizes appropriately.
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(8);
        ad.addFirst(7);
        ad.addFirst(6);
        ad.addFirst(5);
        ad.addFirst(4);
        ad.addFirst(3);
        ad.addFirst(2);
        ad.addFirst(1);   // [0, 1, 2, 3, 4, 5, 6, 7]
        ad.addFirst(0);   // trigger resize up -> [0, 1, 2, 3, 4, 5, 6, 7, 8]
        ad.removeFirst();    // [1, 2, 3, 4, 5, 6, 7, 8]
        ad.removeFirst();    // [2, 3, 4, 5, 6, 7, 8]
        ad.removeFirst();    // [3, 4, 5, 6, 7, 8]
        ad.removeFirst();    // [4, 5, 6, 7, 8]
        ad.removeFirst();    // [5, 6, 7, 8]
        ad.removeFirst();     // trigger resize down -> [6, 7, 8]
        assertThat(ad.toList()).containsExactly(6, 7, 8).inOrder();
    }

    @Test
    public void testRemove_last_trigger_resize() {
        // Called when usage factor is <= 25% and array size > 8.
        // Checks that the array resizes appropriately.
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addLast(8);
        ad.addLast(7);
        ad.addLast(6);
        ad.addLast(5);
        ad.addLast(4);
        ad.addLast(3);
        ad.addLast(2);
        ad.addLast(1);   // [8, 7, 6, 5, 4, 3, 2, 1]
        ad.addLast(0);   // trigger resize up -> [8, 7, 6, 5, 4, 3, 2, 1, 0]
        ad.removeLast();    // [8, 7, 6, 5, 4, 3, 2, 1]
        ad.removeLast();    // [8, 7, 6, 5, 4, 3, 2]
        ad.removeLast();    // [8, 7, 6, 5, 4, 3]
        ad.removeLast();    // [8, 7, 6, 5, 4]
        ad.removeLast();    // [8, 7, 6, 5]
        ad.removeLast();    // trigger resize down -> [8, 7, 6]
        assertThat(ad.toList()).containsExactly(8, 7, 6).inOrder();
    }


    @Test
    public void testGet() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        ad.addFirst(2);
        ad.addFirst(1);
        ad.addFirst(0);
        // check that get works on a valid index
        assertThat(ad.get(2)).isEqualTo(2);

        // check that get works on a large, out of bound index
        assertThat(ad.get(ad.size())).isEqualTo(null);

        // check that get works on a negative index
        assertThat(ad.get(-1)).isEqualTo(null);
    }

    @Test
    public void testSize() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        // Add some elements to a deque and remove them all, then check that size still works.
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        assertThat(ad.size()).isEqualTo(0);

        // Remove from an empty deque, then check that size still works.
        ad.removeFirst();
        assertThat(ad.size()).isEqualTo(-1);
    }

    @Test
    public void testIsEmpty() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();
        assertThat(ad.toList()).containsExactly();
        assertThat(ad.isEmpty()).isTrue();
        ad.addFirst(1);
        assertThat(ad.isEmpty()).isFalse();
    }

    @Test
    public void testEquals() {
        Deque61B<String> ad1 = new ArrayDeque61B<>();
        ad1.addLast("a");
        ad1.addLast("b");
        ad1.addLast("c");
        Deque61B<String> ad2 = new ArrayDeque61B<>();
        ad2.addLast("a");
        ad2.addLast("b");
        ad2.addLast("c");
        assertThat(ad1.equals(ad2)).isTrue();
    }

    @Test
    public void testToString() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");

        assertThat(ad.toString()).isEqualTo("[front, middle, back]");
    }
}

