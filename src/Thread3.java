import java.util.Arrays;

public class Thread3 extends Thread {
    SynchronizationMonitor syncMon;
    ResourceMonitor resMon;
    public Thread3( SynchronizationMonitor syncMon, ResourceMonitor resMon ) {
        this.syncMon = syncMon;
        this.resMon = resMon;
    }
    private static int start = Data.H * 2;
    private static int end = Data.H * 3;
    @Override
    public void run() {
        // Повідомлення про початок виконання потоку T3
        System.out.println("T3 started");

        // Введення A, Z
        Data.A = Data.writeVector(Data.N);
        Data.Z = Data.writeVector(Data.N);

        // Сигнал потокам T1, T2, T4 про завершення введення даних
        syncMon.signalInput();
        // Очікування завершення введення даних потоками T1, T2, T4
        syncMon.waitInput();

        // Обчислення 1
        int[] Z_h = new int[Data.H];
        System.arraycopy(Data.B, start, Z_h, 0, Data.H);

        int a3 = Data.findMinVector(Z_h);

        // Обчислення 2
        resMon.min_a(a3); // КД1

        // Сигнал потокам T1, T2, T4 про завершення обчислення 2
        syncMon.signalDefineMin_a();
        // Очікування завершення обчислення 2 у потоках T1, T2, T4
        syncMon.waitDefineMin_a();

        // Копіювання a3 = a
        a3 = resMon.copy_a(); // КД2
        // Копіювання e3 = e
        int e3 = resMon.copy_e(); // КД3

        // Обчислення 3
        int[][] MV_h = Data.submatrixFromRangeOfColumns(Data.MV, start, end);
        int[][] MC_h = Data.submatrixFromRangeOfColumns(Data.MC, start, end);

        int[] A_h = Data.calculationThree(a3, e3, MV_h, MC_h);
        Data.combineA(A_h, start, end);

        // Очікування завершення обчислення 3 у потоках T1, T2, T4
        syncMon.waitFinalCalc();

        // Виведення результату A
        System.out.printf("A = %s \n", Arrays.toString(Data.A));
        // Повідомлення про завершення виконання потоку T3
        System.out.println("T3 finished");
    }
}
