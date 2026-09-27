import java.util.Arrays;
import java.util.Random;

public class AlgorithmTest {

    public static void main(String[] args) {
        System.out.println("=== STARTING ALL TESTS ===");

        testSorting();
        testDeterministicSelect100Times();
        testClosestPairWithBruteForce();

        System.out.println("=== ALL TESTS PASSED SUCCESSFULLY! ===");
    }

    private static void testSorting() {
        System.out.println("Testing Sorting Algorithms...");

        int[][] testCases = {
                generateRandomArray(100),       // Random
                generateSortedArray(100),       // Sorted
                generateReverseArray(100),      // Reverse-sorted
                generateDuplicateArray(100),    // Duplicate-heavy
                new int[]{},                    // Empty
                new int[]{42}                   // Single-element
        };

        for (int[] original : testCases) {
            int[] expected = original.clone();
            Arrays.sort(expected);

            // Test MergeSort
            int[] msArr = original.clone();
            MergeSorter.sort(msArr);
            assert Arrays.equals(msArr, expected) : "MergeSort failed on array: " + Arrays.toString(original);

            // Test QuickSort
            int[] qsArr = original.clone();
            QuickSorter.sort(qsArr);
            assert Arrays.equals(qsArr, expected) : "QuickSort failed on array: " + Arrays.toString(original);
        }
        System.out.println("-> Sorting tests passed!");
    }

    // Deterministic Select (100 случайных тестов)
    private static void testDeterministicSelect100Times() {
        System.out.println("Testing Deterministic Select (100 runs)...");
        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            int n = rand.nextInt(100) + 1; // размер от 1 до 100
            int[] arr = generateRandomArray(n);
            int k = rand.nextInt(n);

            int[] sorted = arr.clone();
            Arrays.sort(sorted);
            int expected = sorted[k];

            int actual = DeterministicSelector.select(arr.clone(), k);
            assert actual == expected : "Deterministic Select failed on run #" + i;
        }
        System.out.println("-> 100 Deterministic Select tests passed!");
    }

    // Closest Pair (сравнение с brute-force для n <= 2000)
    private static void testClosestPairWithBruteForce() {
        System.out.println("Testing Closest Pair against Brute-Force (n <= 2000)...");
        Random rand = new Random();
        int n = 500;

        point[] points = new point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }

        double expected = ClosestPairSolver.bruteForce(points);
        double actual = ClosestPairSolver.fastClosestPair(points);

        assert Math.abs(actual - expected) < 1e-9 : "Closest Pair mismatch! Fast: " + actual + ", Brute: " + expected;
        System.out.println("-> Closest Pair test passed!");
    }


    private static int[] generateRandomArray(int n) {
        Random r = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt(1000);
        return a;
    }

    private static int[] generateSortedArray(int n) {
        int[] a = generateRandomArray(n);
        Arrays.sort(a);
        return a;
    }

    private static int[] generateReverseArray(int n) {
        int[] a = generateSortedArray(n);
        for (int i = 0; i < n / 2; i++) {
            int tmp = a[i];
            a[i] = a[n - 1 - i];
            a[n - 1 - i] = tmp;
        }
        return a;
    }

    private static int[] generateDuplicateArray(int n) {
        Random r = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt(5); // много повторов
        return a;
    }
}