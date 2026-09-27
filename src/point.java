public class point {
    public final double x;
    public final double y;

    public point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(point that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}