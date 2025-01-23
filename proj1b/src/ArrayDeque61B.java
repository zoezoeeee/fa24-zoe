import java.lang.Math;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    public final int RFACTOR = 2;
    public T[] items;
    public final int initFirst = 4;
    public final int initLast = 5;
    private int nextFirst;
    private int nextLast;
    public int size;


    // Constructor
    public ArrayDeque61B () {
        items = (T[]) new Object[8];
        nextFirst = initFirst;
        nextLast = initLast;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        if (items.length == size) {
            resize(items.length * RFACTOR);
        }
        items[nextFirst] = x;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
        size += 1;
    }

    @Override
    public void addLast(T x) {
        if (items.length == size) {
            resize(items.length * RFACTOR);
        }
        items[nextLast] = x;
        nextLast = Math.floorMod(nextLast + 1, items.length);
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        // Use modular arithmetic to handle wrapped-around when nextFirst + 1 >= items.length
        int currIndex = Math.floorMod(nextFirst + 1, items.length);

        // The number of iterations (size) ensures we visit all valid elements
        for (int i = 0; i < size; i += 1) {
            returnList.add(items[currIndex]);                       // Add the current element to the list
            currIndex = Math.floorMod(currIndex + 1, items.length); // Move to the next with wrapped-around
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        int firstIndex = Math.floorMod(nextFirst + 1, items.length);
        T temp = items[firstIndex];
        nextFirst = firstIndex;
        size -= 1;

        if ((items.length >= 16) && (size <= items.length / 4)) {
            resize(items.length / RFACTOR);
        }
        return temp;
    }

    @Override
    public T removeLast() {
        int lastIndex = Math.floorMod(nextLast - 1, items.length);
        T temp = items[lastIndex];
        nextLast = lastIndex;
        size -= 1;

        if ((items.length >= 16) && (size <= items.length / 4)) {
            resize(items.length / RFACTOR);
        }
        return temp;
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            return items[Math.floorMod(nextFirst + 1 + index, items.length)];
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    public void resize(int newCapacity) {
        // Create a longer array
        T[] newItems = (T[]) new Object[newCapacity];
        // Copy and rearrange elements to new array
        int currIndex = Math.floorMod(nextFirst + 1, items.length);

        for (int i = 0; i < size; i += 1) {
            newItems[i] = items[currIndex];
            currIndex = Math.floorMod(currIndex + 1, items.length); // Move to the next with wrapped-around
        }

        // Redefine other instance variables;
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    public class ArrayDeque61BIterator implements Iterator<T> {
        private int WizPos;

        public ArrayDeque61BIterator() {
            WizPos = 0;   // Points to current position
        }

        public boolean hasNext() {
            return WizPos < size;
        }

        public T next() {
            T returnItem = items[Math.floorMod(nextFirst + 1 + WizPos, items.length)];
            WizPos += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object other) {   // `other` is declared as `Object`
        // Basic identity check
        if (this == other) { return true; }

        // Type checking and casting
        if (other instanceof ArrayDeque61B<?> otherAD) {
            // Size comparison
            if (this.size != otherAD.size) {
                return false;
            }
            // Compare elements using iterators
            Iterator<T> thisIte = this.iterator();
            Iterator<?> otherIte = otherAD.iterator();
            while (thisIte.hasNext() && otherIte.hasNext()) {
                T thisEle = thisIte.next();
                Object otherEle = otherIte.next();
                if (!thisEle.equals(otherEle)) {
                    return false;
                }
            }
            // All elements match, return true
            return true;
        }
        // other is not an ArrayDeque, so return false
        return false;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }
}
