import org.junit.jupiter.api.*;

import java.util.Comparator;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class MaxArrayDeque61BTest {
    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    @Test
    public void max_defaultTest() {
        // Check that max function works when not providing a comparator
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        m.addFirst(1);
        m.addFirst(2);
        m.addFirst(3);

        assertThat(m.max()).isEqualTo(3);
    }

    @Test
    public void max_different_compTest() {
        // Check the max function works
        // when providing a comparator different from the one provided for the constructor
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        Comparator<Integer> reverseComparator = (a, b) -> Integer.compare(b, a);
        m.addFirst(1);
        m.addFirst(2);
        m.addFirst(3);
        assertThat(m.max(reverseComparator)).isEqualTo(1);

        MaxArrayDeque61B<String> str = new MaxArrayDeque61B<String>(Comparator.naturalOrder());
        str.addFirst("I");
        str.addFirst("love");
        str.addFirst("computer science");
        Comparator<String> strComparator = new StringLengthComparator();
        assertThat(str.max(strComparator)).isEqualTo("computer science");
    }

    @Test
    public void max_emptyTest() {
        // Check the max function works on an empty MaxArrayDeque
        MaxArrayDeque61B<Integer> m = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        assertThat(m.max()).isEqualTo(null);
    }
}
