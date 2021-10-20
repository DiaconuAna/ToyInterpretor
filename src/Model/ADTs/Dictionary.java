package Model.ADTs;
import Exceptions.ADTsExceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary<K, V> implements DictInterface<K, V> {
    Map<K,V> dictionary;

    public Dictionary() {
        this.dictionary = new HashMap<K, V>();
    }

    @Override
    public void add(K key, V value) throws ADTsExceptions {
        if(this.dictionary.containsKey(key))
            throw new ADTsExceptions("Cannot add a value with an already existent key!");
        this.dictionary.put(key, value);

    }

    @Override
    public void update(K key, V value) throws ADTsExceptions{
        if(!this.dictionary.containsKey(key))
            throw new ADTsExceptions("Cannot update a nonexistent element");
        this.dictionary.replace(key, value);

    }

    @Override
    public V lookup(K info) {
        return this.dictionary.get(info);
    }

    @Override
    public boolean isDefined(String id) {
        return false;
    }
}
