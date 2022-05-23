package com.company;

import main.java.AlternativeComperator;
import main.java.Counter;
import main.java.Displays;
import main.java.Mage;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String sort = args.length == 0 ? "" : args[0];

        Mage mfirst = new Mage("Ania", 12, 0.5, createSet(sort));

        Mage m1sec = new Mage("Ola", 5, 3.0, createSet(sort));
        Mage m2sec = new Mage("Marek", 4, 5.0, createSet(sort));
        Mage m3sec = new Mage("Jarek", 1, 2.0, createSet(sort));

        Mage m1third = new Mage("Mateusz", 6, 13.0, createSet(sort));
        Mage m2third = new Mage("Karolina", 2, 7.0, createSet(sort));
        Mage m3third = new Mage("Zofia", 3, 10.0, createSet(sort));

        Mage m1four = new Mage("Arek", 9, 5.0, createSet(sort));
        Mage m2four = new Mage("Grzegorz", 6, 17.0, createSet(sort));
        Mage m3four = new Mage("Julian", 3, 1.0, createSet(sort));

        mfirst.addApprentices(m1sec);
        mfirst.addApprentices(m2sec);
        mfirst.addApprentices(m3sec);

        m1sec.addApprentices(m1third);
        m2sec.addApprentices(m2third);
        m3sec.addApprentices(m3third);

        m1third.addApprentices(m1four);
        m2third.addApprentices(m2four);
        m3third.addApprentices(m3four);

        System.out.println("\nRepresentation of a recursive structure: ");
        Displays apprentices = new Displays();
        apprentices.display(mfirst, 1);

        Map<Mage, Integer> statMap = createMap(sort);
        Counter Counter = new Counter(statMap);

        System.out.println("\nRepresentation of a structure with statistic: ");
        Counter.generateMap(mfirst);
        Counter.printMap(mfirst);
    }

    public static Set<Mage> createSet(String arg){
        Set<Mage> set;

        if(arg == "naturalne"){
            set = new TreeSet<>();
        }
        else if(arg == "alternatywne"){
            set = new TreeSet<>(new AlternativeComperator());
        }
        else {
            set = new HashSet<>();
        }

        return set;
    }

    public static Map<Mage, Integer> createMap(String arg){
        Map<Mage, Integer> map;

        if(arg == "naturalne"){
            map = new TreeMap<>();
        }
        else if(arg == "alternatywne"){
            map = new TreeMap<>(new AlternativeComperator());
        }
        else {
            map = new HashMap<>();
        }
        return map;
    }
}

