import edu.princeton.cs.algs4.In;

import java.util.ArrayList; // import the ArrayList class
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private IntNode sentinel;
    private int size;

    public class IntNode {
        public IntNode prev;
        public T item;
        public IntNode next;

        public IntNode(T i) {
            prev = null;
            item = i;
            next = null;
        }
    }

    public LinkedListDeque61B() {
        sentinel = new IntNode(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        size += 1;

        IntNode FirstNode = new IntNode(x);
        IntNode temp = sentinel.next;

        sentinel.next = FirstNode; // sentinel.next points to the first item.
        temp.prev = FirstNode;
        FirstNode.prev = sentinel; // insert the item between sentinel and sentinel.next.
        FirstNode.next = temp;
    }

    @Override
    public void addLast(T x) {
        size += 1;

        IntNode LastNode = new IntNode(x);
        IntNode temp = sentinel.prev;

        sentinel.prev = LastNode; // sentinel.prev points to the last item.
        temp.next = LastNode;
        LastNode.prev = temp; // insert the item between sentinel and sentinel.prev
        LastNode.next = sentinel;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        IntNode p = sentinel.next; // start at the first node. (Don't modify sentinel)

        while (p != sentinel) {     // traverse until we loop back to sentinel
            returnList.add(p.item); // add the current node's item to the list
            p = p.next;             // move to the next node
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
    // Remove and return the element at the front of the deque, if it exists.
    public T removeFirst() {
        if (size == 0){
            return null; // no element inside the list
        }
        else {
            size -= 1;
            T temp = sentinel.next.item;               // store the first element to return
            IntNode newFirstNode = sentinel.next.next; // the second node becomes the new first node
            sentinel.next = newFirstNode;              // update the first element without modifying sentinel
            newFirstNode.prev = sentinel;              // update the backward link
            return temp;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        else {
            size -= 1;
            T temp = sentinel.prev.item;
            IntNode newLastNode = sentinel.prev.prev;
            sentinel.prev = newLastNode;
            newLastNode.next = sentinel;
            return temp;
        }
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        IntNode curr = sentinel.next;

        for (int i = 0; i < index; i += 1) {
            curr = curr.next;
        }
        return curr.item;
    }

    @Override
    public T getRecursive(int index) {
        return getRecursiveHelper(sentinel.next, index); // Don't modify sentinel!
    }

    /** In Java, methods cannot be declared inside other methods. */
    private T getRecursiveHelper(IntNode sentinelNext, int index) {
        if (sentinelNext == null) {
            return null;
        }

        if (index == 0) {
            return sentinelNext.item;
        }
        return getRecursiveHelper(sentinelNext, index-1);
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDeque61BIterator();
    }

    public class LinkedListDeque61BIterator implements Iterator<T> {
        public IntNode WizPos;

        public LinkedListDeque61BIterator() {
            WizPos = sentinel;
        }

        public boolean hasNext() {
            return WizPos.next != sentinel;
        }

        public T next() {
            T temp = WizPos.next.item;
            WizPos = WizPos.next;
            return temp;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) { return true; }
        if (other instanceof LinkedListDeque61B<?> otherLLD) {
            if (this.size != otherLLD.size) {
                return false;
            }
            Iterator<T> thisIte = this.iterator();
            Iterator<?> otherIte = otherLLD.iterator();
            while (thisIte.hasNext() && otherIte.hasNext()) {
                T thisEle = thisIte.next();
                Object otherEle = otherIte.next();
                if (!thisEle.equals(otherEle)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }
}
