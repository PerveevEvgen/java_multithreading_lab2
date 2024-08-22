import java.util.Arrays;

public class Thread1 extends Thread {
    SynchronizationMonitor syncMon;
    ResourceMonitor resMon;

    public Thread1(SynchronizationMonitor syncMon, ResourceMonitor resMon) {
        this.syncMon = syncMon;
        this.resMon = resMon;
    }

    private static int start = 0;
    private static int end = Data.H;

    @Override
    public void run() {
        // Повідомлення про початок виконання потоку T1
        System.out.println("T1 started");

        // Введення MV, MC
        Data.MV = Data.writeMatrix(Data.N);
        Data.MC = Data.writeMatrix(Data.N);

        // Сигнал потокам T2, T3, T4 про завершення введення даних
        syncMon.signalInput();
        // Очікування завершення введення даних потоками T2, T3, T4
        syncMon.waitInput();

        // Обчислення 1
        int[] Z_h = new int[Data.H];
        System.arraycopy(Data.B, start, Z_h, 0, Data.H);

        int a1 = Data.findMinVector(Z_h);

        // Обчислення 2
        resMon.min_a(a1); // КД1

        // Сигнал потокам T2, T3, T4 про завершення обчислення 2
        syncMon.signalDefineMin_a();
        // Очікування завершення обчислення 2 у потоках T2, T3, T4
        syncMon.waitDefineMin_a();

        // Копіювання a1 = a
        a1 = resMon.copy_a(); // КД2
        // Копіювання e1 = e
        int e1 = resMon.copy_e(); // КД3

        // Обчислення 3
        int[][] MV_h = Data.submatrixFromRangeOfColumns(Data.MV, start, end);
        int[][] MC_h = Data.submatrixFromRangeOfColumns(Data.MC, start, end);

        int[] A_h = Data.calculationThree(a1, e1, MV_h, MC_h);
        Data.combineA(A_h, start, end);

        // Сигнал потоку T3 про завершення обчислення 3
        syncMon.signalFinalCalc();
        // Повідомлення про завершення виконання потоку T1
        System.out.println("T1 finished");
    }
}
