public class Thread2 extends Thread {
    SynchronizationMonitor syncMon;
    ResourceMonitor resMon;
    public Thread2( SynchronizationMonitor syncMon, ResourceMonitor resMon ) {
        this.syncMon = syncMon;
        this.resMon = resMon;
    }

    private static int start = Data.H;
    private static int end = Data.H * 2;
    @Override
    public void run() {
        // Повідомлення про початок виконання потоку T2
        System.out.println("T2 started");

        // Введення MM, e
        Data.MM = Data.writeMatrix(Data.N);
        Data.e = Data.writeScalar();
        resMon.write_e(Data.e);

        // Сигнал потокам T1, T3, T4 про завершення введення даних
        syncMon.signalInput();
        // Очікування завершення введення даних потоками T1, T3, T4
        syncMon.waitInput();

        // Обчислення 1
        int[] Z_h = new int[Data.H];
        System.arraycopy(Data.B, start, Z_h, 0, Data.H);

        int a2 = Data.findMinVector(Z_h);

        // Обчислення 2
        resMon.min_a(a2); // КД1

        // Сигнал потокам T1, T3, T4 про завершення обчислення 2
        syncMon.signalDefineMin_a();
        // Очікування завершення обчислення 2 у потоках T1, T3, T4
        syncMon.waitDefineMin_a();

        // Копіювання a2 = a
        a2 = resMon.copy_a(); // КД2
        // Копіювання e2 = e
        int e2 = resMon.copy_e(); // КД3

        // Обчислення 3
        int[][] MV_h = Data.submatrixFromRangeOfColumns(Data.MV, start, end);
        int[][] MC_h = Data.submatrixFromRangeOfColumns(Data.MC, start, end);

        int[] A_h = Data.calculationThree(a2, e2, MV_h, MC_h);
        Data.combineA(A_h, start, end);

        // Сигнал потоку T3 про завершення обчислення 3
        syncMon.signalFinalCalc();
        // Повідомлення про завершення виконання потоку T2
        System.out.println("T2 finished");
    }
}
