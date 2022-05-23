package results;

import java.util.ArrayList;
import java.util.List;

public class Printer {
    private List<String> results = new ArrayList<String>();

    public void add(String text){
        results.add(text);
    }
    public List<String> getResults(){
        return results;
    }
    public void print(){
        System.out.println();
        System.out.println("Wyniki: ");
        for(int i = 0; i < this.getResults().size(); i++){
            System.out.println(this.getResults().get(i));
        }
    }
}
