import java.util.Random;

public class QuickSorter {
    private static final Random random = new Random();
    public static long comparisons = 0;
    public static int maxRecursionDepth = 0;

    public static void sort(int[] a) {
        comparisons = 0;
        maxRecursionDepth = 0;
        sort(a, 0, a.length - 1, 1);
    }

    private static void sort(int[] a, int low, int high, int depth) {
        while (low < high) {
            maxRecursionDepth = Math.max(maxRecursionDepth, depth);
            int pIndex = partition(a, low, high);

            if (pIndex - low < high - pIndex) {
                sort(a, low, pIndex - 1, depth + 1);
                low = pIndex + 1;
            } else {
                sort(a, pIndex + 1, high, depth + 1);
                high = pIndex - 1;
            }
        }
    }

    private static int partition(int[] a, int low, int high) {
        int pivotIndex = low + random.nextInt(high - low + 1);
        swap(a, pivotIndex, high);
        int pivot = a[high];

        int i = low - 1;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (a[j] <= pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}