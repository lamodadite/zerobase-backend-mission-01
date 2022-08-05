package test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            public void run() {
                System.out.println("Thread: " + Thread.currentThread().getName());
            }
        };

        ExecutorService service = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            service.submit(task);
        }
    }
}
