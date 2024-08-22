public class ResourceMonitor {
    private int e;
    private int a = Integer.MAX_VALUE;
    public synchronized void write_e(int e) {
        this.e = e;
    }
    public synchronized int copy_e() {
        return e;
    }
    public synchronized void min_a(int ai) {
        this.a = Math.min(this.a, ai);
    }
    public synchronized int copy_a() {
        return a;
    }
}
