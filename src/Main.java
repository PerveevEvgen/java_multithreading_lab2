//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        ResourceMonitor resourceMonitor = new ResourceMonitor();
        SynchronizationMonitor synchronizationMonitor = new SynchronizationMonitor();

        Thread t1 = new Thread1(synchronizationMonitor, resourceMonitor);
        Thread t2 = new Thread2(synchronizationMonitor, resourceMonitor);
        Thread t3 = new Thread3(synchronizationMonitor, resourceMonitor);
        Thread t4 = new Thread4(synchronizationMonitor, resourceMonitor);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long end = System.currentTimeMillis();

        System.out.printf("Execute time: %d ms", (end - start));

    }
}
