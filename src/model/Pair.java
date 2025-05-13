package model;

public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) return false;
        Pair<?, ?> other = (Pair<?, ?>) obj;
        return key.equals(other.key) && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }
}
