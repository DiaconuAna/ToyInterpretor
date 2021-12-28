package Model.ADTs;

import Exceptions.ADTsExceptions;

import java.util.Map;

public interface DictInterface<K, V> {
    void add(K key, V value) throws ADTsExceptions;
    void update(K key, V value) throws ADTsExceptions;
    V lookup(K info);
    boolean isDefined(K id);
    void remove(K id) throws ADTsExceptions;
    String toString();
    Map<K,V> getContent();
    public DictInterface<K,V> clone();
}
