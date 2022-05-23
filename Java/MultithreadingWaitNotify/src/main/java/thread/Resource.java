package thread;

import java.util.ArrayList;
import java.util.List;

public class Resource {
    private List<Integer> value = new ArrayList<Integer>();

    public List<Integer> getValue(){

        return this.value;
    }

    public synchronized int take() throws InterruptedException {
        while (value.isEmpty()) {
            wait();//Wait, maybe someone will put sth here.
        }
        int ret = value.get(0);
        value.remove(0);
        return ret;
    }

    public synchronized void put(int number) {

        value.add(number);
        notifyAll();
    }
}
