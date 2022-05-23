package main.java;

import java.util.Comparator;
import java.util.Set;
import java.util.*;

public class Mage implements Comparable<Mage>, Comparator<Mage>{

    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;


    public Mage(String name, int level, double power, Set<Mage> apprentices) {
        this.name = name;
        this.level = level;
        this.power = power;
        this.apprentices = apprentices;
    }


    public String getName(){
        return name;
    }

    public int getLevel(){
        return level;
    }
    public double getPower(){
        return power;
    }
    public Set<Mage> getApprentices(){
        return apprentices;
    }

    public void addApprentices(Mage other) {
        this.apprentices.add(other);
    }

    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + Math.abs(level - (int) power);
        return result;
    }

    public boolean equals(Mage other) {
        return this == other;
    }

    @Override
    public int compareTo(Mage o) {
        int ret = name.compareTo(o.name);

        if (ret == 0) {
            ret = level - o.level;
        }

        if (ret == 0) {
            double v = power - o.power;
            ret =(int) v;
        }

        return ret;
    }

    @Override
    public int compare(Mage o1, Mage o2) {

        int ret = o1.getLevel() - o2.getLevel();
        if (ret == 0) {
            ret = o1.getName().compareTo(o2.getName());
        }
        if (ret == 0) {
            double v = o1.getPower() - o2.getPower();
            ret = (int) v;
        }

        return ret;
    }

    @Override
    public String toString() {
        return "Mage{" + "name='" + name + "'" + ", level=" + level + ", power=" + power + "}";
    }

    public TreeMap<String, Mage> generateStats() {
        TreeMap<String, Mage> map = new TreeMap<String, Mage>();
        map.put(name, this);

        if (apprentices != null) {
            apprentices.forEach(mage -> {
                TreeMap<String, Mage> temp = mage.generateStats();
                map.putAll(temp);
            });
        }
        return map;
    }

    public HashMap<String, Mage> generateStatsInHash() {
        HashMap<String, Mage> map = new HashMap<String, Mage>();
        map.put(name, this);

        if (apprentices != null) {
            apprentices.forEach(mage -> {
                HashMap<String, Mage> temp = mage.generateStatsInHash();
                map.putAll(temp);
            });
        }
        return map;
    }
}
