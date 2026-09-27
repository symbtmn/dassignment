import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Experiment {
    public static void runAll() {
        testCorrectness();
        runPerformanceTests();
    }

    private static void testCorrectness() {
        System.out.println("--- Correctness Tests ---");

        // Sorting test
        int[] arr1 = {5, 2, 8, 1, 9, 4};
        int[] arr2 = arr1.clone();
        MergeSorter.sort(arr1);
        Arrays.sort(arr2);
        assert Arrays.equals(arr1, arr2) : "MergeSort Error";


        int[] arr3 = {12, 3, 5, 7, 19, 1, 4, 26};
        int val = DeterministicSelector.select(arr3.clone(), 3);
        int[] sorted3 = arr3.clone();
        Arrays.sort(sorted3);
        assert val == sorted3[3] : "Deterministic Select Error";

        System.out.println("All correctness tests passed successfully!\n");
    }

    private static void runPerformanceTests() {
        int[] sizes = {1000, 5000, 10000, 50000};
        String[] types = {"Random", "Sorted", "Reverse", "Duplicates"};

        // 1.results папкасы жоқ болса, оны құрамыз:
        File resultsDir = new File("results");
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("results/results.csv"))) {
            writer.println("Algorithm,InputType,Size,Time_ns,MaxDepth,Comparisons");

            for (int n : sizes) {
                for (String type : types) {
                    int[] data = generateArray(n, type);

                    // MergeSort
                    int[] copyMS = data.clone();
                    long start = System.nanoTime();
                    MergeSorter.sort(copyMS);
                    long timeMS = System.nanoTime() - start;
                    writer.printf("MergeSort,%s,%d,%d,%d,%d\n", type, n, timeMS, MergeSorter.maxRecursionDepth, MergeSorter.comparisons);

                    // QuickSort
                    int[] copyQS = data.clone();
                    start = System.nanoTime();
                    QuickSorter.sort(copyQS);
                    long timeQS = System.nanoTime() - start;
                    writer.printf("QuickSort,%s,%d,%d,%d,%d\n", type, n, timeQS, QuickSorter.maxRecursionDepth, QuickSorter.comparisons);

                    // Deterministic Select
                    int[] copyDS = data.clone();
                    start = System.nanoTime();
                    DeterministicSelector.select(copyDS, n / 2);
                    long timeDS = System.nanoTime() - start;
                    writer.printf("DeterministicSelect,%s,%d,%d,%d,%d\n", type, n, timeDS, DeterministicSelector.maxRecursionDepth, DeterministicSelector.comparisons);
                }

                // Closest Pair
                point[] pts = generatePoints(n);
                long start = System.nanoTime();
                ClosestPairSolver.fastClosestPair(pts);
                long timeCP = System.nanoTime() - start;
                writer.printf("ClosestPair,Points,%d,%d,%d,0\n", n, timeCP, ClosestPairSolver.maxRecursionDepth);
            }
            System.out.println("Results successfully written to results/results.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] generateArray(int n, String type) {
        Random rand = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rand.nextInt(100000);

        switch (type) {
            case "Sorted": Arrays.sort(a); break;
            case "Reverse":
                Arrays.sort(a);
                for (int i = 0; i < n / 2; i++) {
                    int t = a[i]; a[i] = a[n - 1 - i]; a[n - 1 - i] = t;
                }
                break;
            case "Duplicates":
                for (int i = 0; i < n; i++) a[i] = rand.nextInt(10);
                break;
        }
        return a;
    }

    private static point[] generatePoints(int n) {
        Random r = new Random();
        point[] pts = new point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new point(r.nextDouble() * 1000, r.nextDouble() * 1000);
        }
        return pts;
    }
}