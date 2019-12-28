package mk.ukim.finki.konsultacii;
//package com.company;

import java.util.*;
class Name
{
    String name;
    TreeSet<Character> letters;
    int count;

    public Name(String name,TreeSet<Character> letters, int count) {
        this.letters = letters;
        this.count = count;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<Character> getLetters() {
        return letters;
    }

    public void setLetters(TreeSet<Character> letters) {
        this.letters = letters;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

class Names
{
    TreeMap<String,Name> myMap;

    public Names() {
        myMap=new TreeMap<>();
    }
    public void addName(String name)
    {
        String merx=name.toLowerCase();
        TreeSet<Character> set=new TreeSet<>();
        for(int i=0;i<merx.length();i++)
        {
            set.add(name.toLowerCase().charAt(i));
        }

        myMap.putIfAbsent(name, new Name(name, set, 0));
        myMap.computeIfPresent(name, (k,v) -> {
            v.setCount(v.getCount()+1);
            return v;
        });
//        Name myName=new Name(name,set,0);
//        myMap.putIfAbsent(name,myName);
//        //myMap.computeIfPresent(name,(key,val)->val.setCount(val.getCount()+1));
//        for(String macbook:myMap.keySet())
//        {
//            if(macbook.equals(name))
//            {
//                myMap.get(name).setCount(myMap.get(name).getCount()+1);
//                break;
//            }
//        }
    }
    public void printN(int n)
    {
        for(String key:myMap.keySet())
        {
            if(myMap.get(key).getCount()>n)
            {
                System.out.println(myMap.get(key).getName()+" ("+myMap.get(key).getCount()+") "+(myMap.get(key).getLetters().size()));
            }
        }
    }
    public String findName(int len,int x)
    {
        ArrayList<String> uniqueNames=new ArrayList<>();
        for(String name:myMap.keySet())
        {
            if(name.length()<len)
            {
                uniqueNames.add(name);
            }
        }
        int i=0,pomosna=0;
        //System.out.println(uniqueNames);
        while(true)
        {
            if(pomosna==uniqueNames.size())
            {
                pomosna=0;
            }
            if(i==x)
            {
                return uniqueNames.get(pomosna);
            }
            i++;
            pomosna++;
        }
    }

}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde