package EjemploHilos;

public class Threadmain {
    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            Thread1 h1 = new Thread1();
            h1.start();
            h1.join();
            if (!h1.isAlive()){
                Thread2 h2 = new Thread2();
                h2.start();
            }

        }


    }
}
