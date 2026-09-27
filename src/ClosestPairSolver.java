import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairSolver {
    public static int maxRecursionDepth = 0;

    public static double bruteForce(point[] points) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double d = points[i].distanceTo(points[j]);
                if (d < min) min = d;
            }
        }
        return min;
    }

    public static double fastClosestPair(point[] points) {
        maxRecursionDepth = 0;
        point[] ptsX = points.clone();
        Arrays.sort(ptsX, Comparator.comparingDouble(p -> p.x));

        point[] ptsY = points.clone();
        Arrays.sort(ptsY, Comparator.comparingDouble(p -> p.y));

        return closest(ptsX, ptsY, 1);
    }

    private static double closest(point[] ptsX, point[] ptsY, int depth) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);
        int n = ptsX.length;

        if (n <= 3) return bruteForce(ptsX);

        int mid = n / 2;
        point midPoint = ptsX[mid];

        point[] leftX = Arrays.copyOfRange(ptsX, 0, mid);
        point[] rightX = Arrays.copyOfRange(ptsX, mid, n);

        point[] leftY = Arrays.stream(ptsY).filter(p -> p.x <= midPoint.x).toArray(point[]::new);
        point[] rightY = Arrays.stream(ptsY).filter(p -> p.x > midPoint.x).toArray(point[]::new);

        double d1 = closest(leftX, leftY, depth + 1);
        double d2 = closest(rightX, rightY, depth + 1);
        double d = Math.min(d1, d2);

        final double minD = d; // Создаем неизменяемую копию для лямбды
        point[] strip = Arrays.stream(ptsY)
                .filter(p -> Math.abs(p.x - midPoint.x) < minD)
                .toArray(point[]::new);

        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < d; j++) {
                double dist = strip[i].distanceTo(strip[j]);
                if (dist < d) d = dist;
            }
        }
        return d;
    }
}