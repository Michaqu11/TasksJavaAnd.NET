package main.java;

import java.util.Map;

public class Counter {

    private Map<Mage, Integer> ststisticMap;
    public Counter(Map<Mage, Integer> ststisticMap) {
        this.ststisticMap = ststisticMap;
    };

    public int generateMap(Mage mage){
        int counter = 0;

        for(Mage M : mage.getApprentices()){
            counter++;
            counter += generateMap(M);
        }
        ststisticMap.put(mage, counter);
        return counter;
    }

    public void printMap(Mage mage){
        System.out.println(mage.toString() + " statistic: " + ststisticMap.get(mage)  );
        for(Mage M : mage.getApprentices()){
            printMap(M);
        }
    }
}
