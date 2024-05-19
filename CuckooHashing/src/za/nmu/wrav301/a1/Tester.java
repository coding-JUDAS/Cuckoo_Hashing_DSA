package za.nmu.wrav301.a1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws IOException{
        File fin = new File("Names.txt");
        FileInputStream fileInputStream = new FileInputStream(fin);
        Scanner in = new Scanner(fileInputStream);
        Pair<String, String> pair = new Pair<>("?", "?");
        HashTable hashTable = new HashTable(pair.getClass());
        String name = in.nextLine();
        while(in.hasNext()){

            hashTable.put(new Pair(name, name));
            name = in.nextLine();
        }
        fileInputStream.close();
        System.out.println("Number of items = " + hashTable.getCount());
        System.out.println(hashTable);
    }
}
