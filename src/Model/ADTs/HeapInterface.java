package Model.ADTs;

import Exceptions.ADTsExceptions;

import java.util.Map;

public interface HeapInterface<V> {

   int allocate(V value);
   V deallocate(int address) throws ADTsExceptions;
   V getValue(int address);
   void put(int address, V value);
   boolean isAddress(int address);
   Map<Integer, V> getContent();
   void setContent(Map<Integer, V> newContent);
   void update(int address, V value);
   void remove(int address);
}
