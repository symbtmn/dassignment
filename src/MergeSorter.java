import java.util.Arrays;

public class MergeSorter {
    private static final int CUTOFF = 15;
    public static long comparisons = 0;
    public static int maxRecursionDepth = 0;

    public static void sort(int[] a) {
        comparisons = 0;
        maxRecursionDepth = 0;
        int[] aux = new int[a.length];
        sort(a, aux, 0, a.length - 1, 1);
    }

    private static void sort(int[] a, int[] aux, int low, int high, int depth) {
        maxRecursionDepth = Math.max(maxRecursionDepth, depth);

        if (high - low <= CUTOFF) {
            insertionSort(a, low, high);
            return;
        }

        int mid = low + (high - low) / 2;
        sort(a, aux, low, mid, depth + 1);
        sort(a, aux, mid + 1, high, depth + 1);

        if (a[mid] <= a[mid + 1]) return; // уже отсортировано

        merge(a, aux, low, mid, high);
    }

    private static void merge(int[] a, int[] aux, int low, int mid, int high) {
        System.arraycopy(a, low, aux, low, high - low + 1);

        int i = low, j = mid + 1;
        for (int k = low; k <= high; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > high) a[k] = aux[i++];
            else {
                comparisons++;
                if (aux[j] < aux[i]) a[k] = aux[j++];
                else a[k] = aux[i++];
            }
        }
    }

    private static void insertionSort(int[] a, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= low && a[j] > key) {
                comparisons++;
                a[j + 1] = a[j];
                j--;
            }
            if (j >= low) comparisons++;
            a[j + 1] = key;
        }
    }
}