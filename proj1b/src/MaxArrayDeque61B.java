import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private final Comparator<T> comparator;

    // Constructor: create a MaxArrayDeque61B with the given Comparator
    public MaxArrayDeque61B(Comparator<T> c) {
        super();         // Call the parent class constructor
        this.comparator = c;  // Initialize the comparator
    }

    // Return the maximum element in the deque as governed by the comparator in the constructor.
    // If the MaxArrayDeque61B is empty, simply return null.
    public T max() {
        return findMax(this.comparator);
    }

    // Return the maximum element in the deque as governed by the parameter Comparator c.
    // If the MaxArrayDeque61B is empty, simply return null.
    public T max(Comparator<T> c) {
        if (c == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }
        return findMax(c);
    }

    // Use helper function to avoid duplicates
    public T findMax(Comparator<T> c) {
        if (this.isEmpty()) { return null; }
        T max = this.get(0);
        for (T x : this) {
            if (c.compare(max, x) < 0) {
                max = x;
            }
        }
        return max;
    }
}
