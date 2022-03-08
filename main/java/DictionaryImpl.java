package ru.hse.fmcs;

import org.jetbrains.annotations.NotNull;

import java.util.*;

import static java.lang.Math.abs;

public class DictionaryImpl<K, V> extends AbstractMap<K, V> implements Dictionary<K,V> {
    private int countOfElements = 0;
    private int capacity;
    private final double loadFactor;
    private final static double DEFAULT_LOAD_FACTOR = 0.85;
    private final static int INITIAL_CAPACITY = 128;
    private final static int CAPACITY_MULTIPLIER = 2;
    private List<List<MapEntry<K, V>>> memory;

    private static class MapEntry<K, V> extends AbstractMap.SimpleEntry<K, V> {
        public MapEntry(K key, V value) {
            super(key, value);
        }
    }

    public DictionaryImpl() {
        this(DEFAULT_LOAD_FACTOR, INITIAL_CAPACITY);
    }

    public DictionaryImpl(int capacity) {
        this(DEFAULT_LOAD_FACTOR, capacity);
    }

    public DictionaryImpl(double loadFactor, int capacity) {
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        memory = new ArrayList<>(capacity);
        fillMemory(memory, capacity);
    }

    private int hashFunction(Object key, int capacity) {
        return abs(key.hashCode()) % capacity;
    }

    private List<MapEntry<K, V>> getBucket(Object key) {
        return memory.get(hashFunction(key, capacity));
    }

    private boolean isCapacityEnough() {
        return countOfElements < (int) (capacity * loadFactor);
    }

    private void resize() {
        if (isCapacityEnough()) {
            return;
        }
        int newCapacity;
        if (capacity >= Integer.MAX_VALUE / CAPACITY_MULTIPLIER) {
            newCapacity = Integer.MAX_VALUE;
        } else {
            newCapacity = capacity * CAPACITY_MULTIPLIER;
        }
        List<List<MapEntry<K, V>>> newMemory = new ArrayList<>(newCapacity);
        fillMemory(newMemory, newCapacity);
        for (var bucket : memory) {
            for (var pair : bucket) {
                newMemory.get(hashFunction(pair.getKey(), newCapacity)).add(pair);
            }
        }
        memory = newMemory;
        capacity = newCapacity;
    }

    private void fillMemory(List<List<MapEntry<K, V>>> memory, int capacity) {
        for (int i = 0; i < capacity; i++) {
            memory.add(new ArrayList<>());
        }
    }

    @Override
    public int size() {
        return countOfElements;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public V get(Object key) {
        var bucket = getBucket(key);
        for (var pair : bucket) {
            if (pair.getKey().equals(key)) {
                return pair.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        resize();
        var bucket = getBucket(key);
        for (var pair : bucket) {
            if (pair.getKey().equals(key)) {
                return pair.setValue(value);
            }
        }
        countOfElements++;
        bucket.add(new MapEntry<>(key, value));
        return null;
    }

    @Override
    public V remove(Object key) {
        resize();
        var bucket = getBucket(key);
        for (var pair : bucket) {
            if (pair.getKey().equals(key)) {
                V lastValue = pair.getValue();
                bucket.remove(pair);
                countOfElements--;
                return lastValue;
            }
        }
        return null;
    }

    @Override
    public void clear() {
        memory.clear();
        countOfElements = 0;
        fillMemory(memory, capacity);
    }

    private enum Type{
        ENTRY, KEY, VALUE
    }

    private class MapIterator<I> implements Iterator<I> {
        Type type;
        Iterator<List<MapEntry<K, V>>> iteratorOfBucket;
        Iterator<MapEntry<K, V>> iteratorInBucket;
        boolean canBeRemoved = false;

        public MapIterator(Type type) {
            this.type = type;
            iteratorOfBucket = memory.iterator();
            iteratorInBucket = iteratorOfBucket.hasNext() ? iteratorOfBucket.next().iterator() : null;
        }


        @Override
        public boolean hasNext() {
            if (iteratorInBucket == null) {
                return false;
            }
            if (iteratorInBucket.hasNext()) {
                return true;
            }
            while (iteratorOfBucket.hasNext()) {
                iteratorInBucket = iteratorOfBucket.next().listIterator();
                if (iteratorInBucket.hasNext()) {
                    return true;
                }
            }
            return false;
        }

        @Override
        @SuppressWarnings("unchecked")
        public I next() throws NoSuchElementException {
            if (!hasNext()) {
                canBeRemoved = false;
                throw new NoSuchElementException();
            }
            canBeRemoved = true;
            if (type.equals(Type.ENTRY)) {
                return (I) iteratorInBucket.next();
            }
            if (type.equals(Type.KEY)) {
                return (I) iteratorInBucket.next().getKey();
            }
            if (type.equals(Type.VALUE)) {
                return (I) iteratorInBucket.next().getValue();
            }
            canBeRemoved = false;
            throw new NoSuchElementException();
        }

        @Override
        public void remove() throws IllegalStateException {
            if (!canBeRemoved) {
                throw new IllegalStateException();
            }
            canBeRemoved = false;
            if (iteratorInBucket == null) {
                return;
            }
            iteratorInBucket.remove();
            countOfElements--;
        }
    }

    @Override
    @NotNull
    public Set<K> keySet() {
        return new AbstractSet<>() {
            @Override
            public @NotNull Iterator<K> iterator() {
                return new MapIterator<>(Type.KEY);
            }

            @Override
            public int size() {
                return DictionaryImpl.this.size();
            }
        };
    }

    @Override
    @NotNull
    public Collection<V> values() {
        return new AbstractCollection<>() {
            @Override
            public @NotNull Iterator<V> iterator() {
                return new MapIterator<>(Type.VALUE);
            }

            @Override
            public int size() {
                return DictionaryImpl.this.size();
            }
        };
    }


    @Override
    @NotNull
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new MapIterator<>(Type.ENTRY);
            }

            @Override
            public int size() {
                return DictionaryImpl.this.size();
            }
        };
    }
}
