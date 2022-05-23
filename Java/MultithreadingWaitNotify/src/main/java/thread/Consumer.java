package thread;

import results.Printer;

public class Consumer implements Runnable {
    private Resource resource;
    private Printer printer;
    public Consumer(Resource resource,  Printer printer) {
        this.resource = resource;
        this.printer = printer;
    }

    @Override
    public void run() {
        try {
            while(true){
                int take = resource.take();
                System.out.println("Watek o id: " + Thread.currentThread().getId() + " pobral liczbe " + take);
                checkNumer(take);
            }
        } catch (InterruptedException e) {
            finishTheJob();
        }
    }
    public void finishTheJob(){     //posprawdzaj ostatnie liczby
        while(!resource.getValue().isEmpty()){
            try{
                int take = resource.take();
                checkNumer(take);
            }
            catch(InterruptedException e){
                return;
            }
        }
    }
    public void checkNumer(int number) throws InterruptedException {

        int counter = 0;
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                counter++;
            }
        }
        if (counter == 0) {
            printer.add( number + " :jest pierwsza");
            Thread.sleep(3000);
            System.out.println(number + ": jest liczba pierwsza! ");
        }
        else{
            printer.add(number + " :NIE jest pierwsza");
            Thread.sleep(3000); //symulowanie skomplikowanych obliczen
            System.out.println(number + ": nie jest liczba pierwsza! ");
        }
    }
}

