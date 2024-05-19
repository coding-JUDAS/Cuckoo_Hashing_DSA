package za.nmu.wrav301.a1;

import java.lang.reflect.Array;
import java.math.BigInteger;

public class HashTable<K, V> {
    //<editor-fold defaultstate="collapsed" desc="Class Fields">
    private int size = 10;
    private int count;
    private Pair<K, V>[] dictionary_1;
    private Pair<K, V>[] dictionary_2;

    private final Class<Pair<K, V>> clazz;
    //</editor-fold>


    //<editor-fold defaultstate="collapsed" desc="Do Not Change These Methods">

    public HashTable(Class<Pair<K, V>> c) {
        count = 0;
        clazz = c;
        dictionary_1 = (Pair<K, V>[]) Array.newInstance(clazz, size); //(Pair<K, V>[]) new Object[size];
        dictionary_2 = (Pair<K, V>[]) Array.newInstance(clazz, size); //(Pair<K, V>[]) new Object[size];
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HashTable details:\n");
        sb.append(String.format("Size: %d\n", 2 * getSize()));
        sb.append(String.format("Number of Elements: %d\n", getCount()));
        StringBuilder elements = new StringBuilder();
        elements.append("Elements in array 1: \n");
        int nullValues = getNullValues(elements, dictionary_1);
        elements.append("\nElements in array 2: \n");
        nullValues += getNullValues(elements, dictionary_2);
        sb.append(String.format("Number of null elements: %d", nullValues));
        sb.append("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
        sb.append(elements);
        sb.append("\n=============================================================================================================================================================================");
        sb.append("\n=============================================================================================================================================================================");
        return sb.toString();
    }

    private int getNullValues(StringBuilder sb, Pair<K, V>[] dictionary_1) {
        int nullValues = 0;
        for (Pair<K, V> value : dictionary_1) {
            if (value != null) {
                sb.append(String.format("%s - %s, ", value.getKey().toString(), value.getValue().toString()));
            }
            else{
                nullValues++;
            }
        }
        sb.replace(sb.length() - 2, sb.length(), "");
        return nullValues;
    }

    public int getCount() {
        return count;
    }

    public int getSize() {
        return size;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Modify These Methods (DO NOT CHANGE THE METHOD SIGNATURE)">

    /**
     * Associates the specified value with the specified key in this HashTable.
     *
     * @param pair a key-value pair with which is to be inserted into the HashTable
     * @return <tt>true</tt> if a key-value pair was successfully inserted
     * into the HashTable as a result of this call.
     */
    public boolean put(Pair<K, V> pair) {
        //boolean inserted = false;
        Pair temp;
        if(dictionary_1[hash_1(pair.getKey())] != null && dictionary_1[hash_1(pair.getKey())].getKey().equals(pair.getKey())){
            dictionary_1[hash_1(pair.getKey())] = pair;
            return true;

        }
        if(dictionary_2[hash_2(pair.getKey())] != null && dictionary_2[hash_2(pair.getKey())].getKey().equals(pair.getKey())){
            dictionary_2[hash_2(pair.getKey())] = pair;
            return true;
        }

        return insert(pair, dictionary_1, 0, 0);
    }
    private boolean insert(Pair<K, V> pair, Pair<K, V>[] directory, int counter, int function){
        if(counter > (size-count)){
            System.out.println("Cycle detected. Rehashing sequence initialized. . .");
            reHash();
            toString();
            return insert(pair, dictionary_1, counter, function);
        }
        K key = pair.getKey();
        int i = counter;

        if(directory[hash(key, function)] != null){

            Pair<K, V> temp = directory[hash(key, function)];
            directory[hash(key, function)] = pair;
            return insert(temp, chooseDirectory((i+1)%2), counter+1, ((function + 1)%2));
        }
        else{
            directory[hash(key, function)] = pair;
            count++;
        }
        return true;
    }
    private Pair<K, V>[] chooseDirectory(int dictionary){
        switch (dictionary){
            case 0:
                return dictionary_1;
            case 1:
                return dictionary_2;
        }
        return null;
    }
    private int hash(K key, int hashFunction){
        switch (hashFunction){
            case 0:
                return hash_1(key);
            case 1:
                return hash_2(key);
        }
        return 0;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     * {@code null} if this map contains no mapping for the key
     */
    public Pair<K, V> get(K key) {
        if(dictionary_1[hash_1(key)] != null && dictionary_1[hash_1(key)].getKey().equals(key)){
            return dictionary_1[hash_1(key)];
        }
        if(dictionary_2[hash_2(key)] != null && dictionary_2[hash_2(key)].getKey().equals(key)){
            return dictionary_2[hash_2(key)];
        }
        return null;
    }

    /**
     * Removes the mapping for a key from this HashTable if it is present
     *
     * @param key key whose mapping is to be removed from the HashTable
     * @return <tt>true</tt> if an key-value pair was successfully removed
     * from this HashTable as a result of this call
     */
    public Pair<K, V> remove(K key) {
        Pair temp;
        if(dictionary_1[hash_1(key)] != null && dictionary_1[hash_1(key)].getKey() == key){
            temp = dictionary_1[hash_1(key)];
            dictionary_1[hash_1(key)] = null;
            count--;
            return temp;
        }
        if(dictionary_2[hash_2(key)] != null && dictionary_2[hash_2(key)].getKey() == key){
            temp = dictionary_2[hash_2(key)];
            dictionary_2[hash_2(key)] = null;
            count--;
            return temp;
        }
        return null;
    }

    /**
     * Maps the input key (represented in string form) into an integer that denotes the index for which the
     * associated key-value pair is to be stored in the first array.
     *
     * @param key the identifier associated with a particular value
     * @return an {@code int} in between 0 (inclusive) and the size
     * (exclusive) of the HashTable denoting the position to be stored
     * in the first array.
     */
    private int hash_1(K key) {
        String s = key.toString().trim();
        char[] chars=s.toCharArray();
        BigInteger hashCode = BigInteger.valueOf(5381);
        for(int i = 0; i < s.length(); i++){
            hashCode = hashCode.multiply(BigInteger.valueOf(33)).add(BigInteger.valueOf((int)chars[i]));
        }
        int h = hashCode.mod(BigInteger.valueOf(size)).intValue();
        return (h);
    }

    /**
     * Maps the input key (represented in byte form) into an integer that denotes
     * the index for which the associated key-value pair is to be stored in the
     * second array.
     *
     * @param key the identifier associated with a particular value
     * @return an {@code int} in between 0 (inclusive) and the size
     * (exclusive) of the HashTable denoting the position to be stored
     * in the second array.
     */
    private int hash_2(K key) {
        int a = 11;
        int b = 7;
        byte[] bits = key.toString().getBytes();
        BigInteger aggregateValue = BigInteger.valueOf(0);
        for(int i = 0; i < bits.length; i++){
            aggregateValue = (aggregateValue
                    .xor(BigInteger.valueOf(bits[i])))
                    .multiply(BigInteger.valueOf(i));
        }
        int h = ((aggregateValue
                .multiply(BigInteger.valueOf(a)))
                .mod(BigInteger.valueOf(size)))
                .intValue();
        return h;
    }

    /**
     * Doubles the size of the HashTable and re-inserts all elements into the new HashTable
     */
    private void reHash() {
        int oldSize = size;
        size = size*2;
        //Code to instantiate generic array:
        Pair<K, V>[] pairArray1 = (Pair<K, V>[]) Array.newInstance(clazz, size);
        Pair<K, V>[] pairArray2 = (Pair<K, V>[]) Array.newInstance(clazz, size);
        Pair<K,V> temp1;
        Pair<K, V> temp2;
        K key;
        //dictionary_1 = pairArray1;
        //dictionary_2 = pairArray2;
        for(int i = 0; i < oldSize; i++){
            if(dictionary_1[i] != null){
                temp1 = dictionary_1[i];
                pairArray1[hash_1(temp1.getKey())] = temp1;
                //put(temp1);
            }
        }
        for(int i = 0; i < oldSize; i++){
            if(dictionary_2[i] != null){
                temp2 = dictionary_2[i];
                pairArray2[hash_2(temp2.getKey())] = temp2;
                //put(temp2);
            }
        }

        dictionary_1 = pairArray1;
        dictionary_2 = pairArray2;

    }
    //</editor-fold>
}
