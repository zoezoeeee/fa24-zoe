import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> { // K must be a comparable type
    private int size = 0;
    private Entry treemap;

    // Represent one node in BSTMap: key, value, left subtree, right subtree
    private class Entry {
        K key;
        V value;
        Entry left;
        Entry right;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        // Returns the Entry in this treemap of key-value pair whose key is equal to k
        // or null if no such Entry exists
        Entry get(Entry treemap, K key) {
            if (treemap == null) {
                return null;
            }
            if (key.equals(treemap.key)) {
                return treemap;
            }
            else if (key.compareTo(treemap.key) < 0) {
                return get(treemap.left, key);
            }
            else {
                return get(treemap.right, key);
            }
        }

        Entry put(Entry treemap, K key, V value) {
            if (treemap == null) {
                treemap = new Entry(key, value);
                size += 1;
            }
            else if (key.equals(treemap.key)) {
                treemap.value = value;
            }
            else if (key.compareTo(treemap.key) < 0) {
                treemap.left = put(treemap.left, key, value);
            }
            else if (key.compareTo(treemap.key) > 0) {
                treemap.right = put(treemap.right, key, value);
            }
            return treemap;
        }
    }

        /**
         * Associates the specified value with the specified key in this map.
         * If the map already contains the specified key, replaces the key's mapping
         * with the value specified.
         */
        @Override
        public void put(K key, V value) {
            if (treemap == null) {
                treemap = new Entry(key, value);
                size += 1;
            } else {
                treemap.put(treemap, key, value);
            }
        }


        /**
         * Returns the value to which the specified key is mapped, or null if this
         * map contains no mapping for the key.
         */
        @Override
        public V get(K key) {
            if (treemap == null) {
                return null;
            }
            else {
                Entry lookup = treemap.get(treemap, key);
                return lookup.value;
            }
        }

        /**
         * Returns whether this map contains a mapping for the specified key.
         */
        @Override
        public boolean containsKey(K key) {
            if (treemap == null) {
                return false;
            }
            Entry lookup = treemap.get(treemap, key);
            if (lookup == null) {
                return false;
            }
            return true;
        }

        /**
         * Returns the number of key-value mappings in this map.
         */
        @Override
        public int size() {
            return size;
        }

        /**
         * Removes every mapping from this map.
         */
        @Override
        public void clear() {
            size = 0;
            treemap = null;
        }

        /**
         * Returns a Set view of the keys contained in this map. Not required for Lab 7.
         * If you don't implement this, throw an UnsupportedOperationException.
         */
        @Override
        public Set<K> keySet() {
            throw new UnsupportedOperationException();
        }


        /**
         * Removes the mapping for the specified key from this map if present,
         * or null if there is no such mapping.
         * Not required for Lab 7. If you don't implement this, throw an
         * UnsupportedOperationException.
         */
        @Override
        public V remove(K key) {
            throw new UnsupportedOperationException();
        }

        /**
         * Returns an iterator over elements of type {@code T}.
         */
        @Override
        public Iterator<K> iterator() {
            throw new UnsupportedOperationException();
        }
}
