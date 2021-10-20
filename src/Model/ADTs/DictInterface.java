package Model.ADTs;

import Exceptions.ADTsExceptions;

public interface DictInterface<K, V> {
    void add(K key, V value) throws ADTsExceptions;
    void update(K key, V value) throws ADTsExceptions;
    V lookup(K info);
    boolean isDefined(String id);
    String toString();
}
