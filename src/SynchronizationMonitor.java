public class SynchronizationMonitor {
    private int inputF = 0;
    private int define_a_F = 0;
    private int finalF = 0;

    public synchronized void signalInput() {
        inputF += 1;
        if(inputF == 4) notifyAll();
    }
    public synchronized void waitInput() {
        try {
            if(inputF != 4) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void signalDefineMin_a() {
        define_a_F += 1;
        if(define_a_F == 4) notifyAll();
    }

    public synchronized void waitDefineMin_a() {
        try {
            if(define_a_F != 4) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void signalFinalCalc() {
        finalF += 1;
        if(finalF == 3) notify();
    }

    public synchronized void waitFinalCalc() {
        try {
            if(finalF != 3) wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
