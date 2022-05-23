package main.java;

public class Displays {

    public void display(Mage mage, int lvl ){
        if(mage != null){
            for(int i = 0; i < lvl; i++){
                System.out.print("-");
            }
            System.out.println(mage.toString());
        }

        for(Mage other : mage.getApprentices()){
            display(other, lvl + 1);
        }
    }
}
