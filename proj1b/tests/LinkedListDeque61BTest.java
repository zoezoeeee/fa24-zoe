import edu.princeton.cs.algs4.In;
import jh61b.utils.Reflection;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class LinkedListDeque61BTest {

    @Test
    public void testEquals() {
        LinkedListDeque61B<String> lld1 = new LinkedListDeque61B<>();
        lld1.addLast("a");
        lld1.addLast("b");
        lld1.addLast("c");
        LinkedListDeque61B<String> lld2 = new LinkedListDeque61B<>();
        lld2.addLast("a");
        lld2.addLast("b");
        lld2.addLast("c");
        assertThat(lld1.equals(lld2)).isTrue();
    }

    @Test
    public void testToString() {
        LinkedListDeque61B<String> lld = new LinkedListDeque61B<>();
        lld.addLast("front");
        lld.addLast("middle");
        lld.addLast("back");

        assertThat(lld.toString()).isEqualTo("[front, middle, back]");
    }
}
