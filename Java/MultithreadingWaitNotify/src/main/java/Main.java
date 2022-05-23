import results.Printer;
import thread.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Resource res = new Resource();

        int numberOfThreads = Integer.parseInt(args[0]);
        List<Thread> Consumers = new ArrayList<Thread>();

        Thread tp1 = new Thread(new Producer(res));
        Thread threadOfProducer = new Thread(tp1);
        tp1.start();
        Consumers.add(tp1);

        Printer print = new Printer();

        for(int i = 0 ;i < numberOfThreads-1 ; i++){
            Thread tc1 = new Thread(new Consumer(res, print));
            tc1.start();
            Consumers.add(tc1);
        }

        try {
            threadOfProducer.join();
            for(int j = 0; j < Consumers.size(); j++) {
                Consumers.get(j).interrupt();
                Consumers.get(j).join();
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        print.print();
    }
}
