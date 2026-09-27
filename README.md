# Assignment 1: Divide-and-Conquer Algorithm Analysis

## A. Project Overview
This project focuses on the implementation, empirical testing, and complexity analysis of four classic Divide-and-Conquer algorithms in Java:
1. **MergeSort** (with auxiliary buffer and insertion sort cutoff)
2. **QuickSort** (with randomized pivot and smaller-partition tail recursion)
3. **Deterministic Select** (Median-of-Medians $O(n)$ selection)
4. **Closest Pair of Points** ($\Theta(n \log n)$ 2D geometric algorithm)

## B. Algorithm Analysis

### 1. MergeSort
* **Recurrence:** $T(n) = 2T(n/2) + \Theta(n)$
* **Master Theorem:** Case 2 ($a=2, b=2, f(n)=\Theta(n)$), yielding $T(n) = \Theta(n \log n)$.
* **Space Complexity:** $O(n)$ auxiliary space using a single reused array buffer.

### 2. QuickSort
* **Recurrence:**
    * Average/Best Case: $T(n) = 2T(n/2) + \Theta(n) \implies \Theta(n \log n)$
    * Worst Case: $T(n) = T(n-1) + \Theta(n) \implies O(n^2)$
* **Space Complexity:** $O(\log n)$ max stack depth by making the recursive call on the smaller sub-array first and iterating on the larger one.

### 3. Deterministic Select (Median-of-Medians)
* **Recurrence:** $T(n) \le T(n/5) + T(7n/10) + O(n)$
* **Akra-Bazzi / Substitution:** Since $1/5 + 7/10 = 9/10 < 1$, the linear work at each level dominates, guaranteeing $T(n) = O(n)$ worst-case time complexity.

### 4. Closest Pair of Points
* **Recurrence:** $T(n) = 2T(n/2) + O(n)$ (where $O(n)$ is the strip check step).
* **Master Theorem:** Case 2, resulting in $T(n) = \Theta(n \log n)$.

## C. Experimental Results
The performance metrics were generated and exported to `results/results.csv`.

### Summary Insights:
* **Recursion Depth:** QuickSort maintained a maximum recursion depth bounded by $O(\log n)$ across all array distributions (including sorted and duplicate-heavy arrays) thanks to tail-recursion optimization.
* **Execution Time:** Closest Pair algorithm significantly outperformed $O(n^2)$ brute-force approach on large datasets ($n > 2000$).

## D. Discussion
1. **Do results match theoretical complexity?**
   Yes, the timing graphs confirm $n \log n$ growth for MergeSort and QuickSort, while Deterministic Select scales linearly.
2. **Why does smaller-first recursion help QuickSort?**
   Recursing on the smaller half guarantees that the recursion stack depth never exceeds $\log_2 n$, preventing StackOverflowError even in worst-case partitioning scenarios.
3. **Why does Median-of-Medians guarantee $O(n)$?**
   By choosing the median of groups of 5 as the pivot, it guarantees that at least 30% of the elements are smaller than the pivot and at least 30% are larger, preventing $O(n^2)$ worst-case split imbalances.
4. **Practical factors affecting performance:**
   JVM Just-In-Time (JIT) compilation warmup, memory allocation overhead, and CPU cache line efficiency heavily influence the empirical execution time.

## E. Reflection
Implementing these algorithms highlighted the importance of practical low-level optimizations. For instance, avoiding repetitive array allocations in MergeSort using a reusable buffer and managing stack depth in QuickSort drastically improved performance and robustness.

## F. Screenshots
*(Include screenshots of running execution tests and generated plots here)*