import java.util.Arrays;

public class DeterministicSelector {
    public static long comparisons = 0;
    public static int maxRecursionDepth = 0;

    public static int select(int[] a, int k) {
        comparisons = 0;
        maxRecursionDepth = 0;
        return select(a, 0, a.length - 1, k, 1);
    }

    private static int select(int[] a, int low, int high, int k, int depth) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        if (low == high) return a[low];

        int pivot = medianOfMedians(a, low, high);
        int pivotIndex = partition(a, low, high, pivot);

        if (k == pivotIndex) return a[k];
        else if (k < pivotIndex) return select(a, low, pivotIndex - 1, k, depth + 1);
        else return select(a, pivotIndex + 1, high, k, depth + 1);
    }

    private static int medianOfMedians(int[] a, int low, int high) {
        int n = high - low + 1;
        if (n <= 5) return findMedian5(a, low, high);

        int numGroups = (int) Math.ceil((double) n / 5);
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int subLow = low + i * 5;
            int subHigh = Math.min(subLow + 4, high);
            medians[i] = findMedian5(a, subLow, subHigh);
        }

        return select(medians, 0, medians.length - 1, medians.length / 2, 1);
    }

    private static int findMedian5(int[] a, int low, int high) {
        int[] sub = Arrays.copyOfRange(a, low, high + 1);
        Arrays.sort(sub);
        return sub[sub.length / 2];
    }

    private static int partition(int[] a, int low, int high, int pivot) {
        for (int i = low; i <= high; i++) {
            if (a[i] == pivot) {
                swap(a, i, high);
                break;
            }
        }
        int p = a[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            comparisons++;
            if (a[j] <= p) {
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