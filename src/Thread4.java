public class Thread4 extends Thread {
    SynchronizationMonitor syncMon;
    ResourceMonitor resMon;
    public Thread4( SynchronizationMonitor syncMon, ResourceMonitor resMon ) {
        this.syncMon = syncMon;
        this.resMon = resMon;
    }
    private static int start = Data.H * 3;
    private static int end = Data.N;
    @Override
    public void run() {
        // Повідомлення про початок виконання потоку T4
        System.out.println("T4 started");

        // Введення B, X
        Data.B = Data.writeVector(Data.N);
        Data.X = Data.writeVector(Data.N);

        // Сигнал потокам T1, T2, T3 про завершення введення даних
        syncMon.signalInput();
        // Очікування завершення введення даних потоками T1, T2, T3
        syncMon.waitInput();

        // Обчислення 1
        int[] Z_h = new int[Data.H];
        System.arraycopy(Data.B, start, Z_h, 0, Data.H);

        int a4 = Data.findMinVector(Z_h);

        // Обчислення 2
        resMon.min_a(a4); // КД1

        // Сигнал потокам T1, T2, T3 про завершення обчислення 2
        syncMon.signalDefineMin_a();
        syncMon.waitDefineMin_a();

        // Копіювання a4 = a
        a4 = resMon.copy_a(); // КД2
        // Копіювання e4 = e
        int e4 = resMon.copy_e(); // КД3

        // Обчислення 3
        int[][] MV_h = Data.submatrixFromRangeOfColumns(Data.MV, start, end);
        int[][] MC_h = Data.submatrixFromRangeOfColumns(Data.MC, start, end);

        int[] A_h = Data.calculationThree(a4, e4, MV_h, MC_h);
        Data.combineA(A_h, start, end);

        // Сигнал потоку T3 про завершення обчислення 3
        syncMon.signalFinalCalc();
        // Повідомлення про завершення виконання потоку T4
        System.out.println("T4 finished");
    }
}
