import java.util.Scanner;

/**
 * Created by hodoniura on 6/5/17.
 */
public class OptimalParanthesizationUsingDP {
    private int[][] m;
    private int[][] s;
    private int     n;

    public OptimalParanthesizationUsingDP(int[] p)
    {
        // how many matrices are in the chain
        n = p.length - 1;
        // overallocate m, so that we don't use index
        m = new int[n + 1][n + 1];

        // same for s
        // run the dynamic-programming algorithm
        s = new int[n + 1][n + 1];
        matrixChainOrder(p);
    }

    private void matrixChainOrder(int[] p)
    {
        // Initial the cost for the empty subproblems.
        for (int i = 1; i <= n; i++)
            m[i][i] = 0;
        // Solve for chains of increasing length l.
        for (int l = 2; l <= n; l++)
        {
            for (int i = 1; i <= n - l + 1; i++)
            {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;
                // Check each possible split to see if it's better
                // than all seen so far.
                for (int k = i; k < j; k++)
                {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j])
                    {
                        // q is the best split for this subproblem so far.
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    private String printOptimalParens(int i, int j)
    {
        if (i == j)
            return "A[" + i + "]";
        else
            return "(" + printOptimalParens(i, s[i][j])
                    + printOptimalParens(s[i][j] + 1, j) + ")";
    }

    public String toString()
    {
        return printOptimalParens(1, n);
    }

    static int MatrixChainOrder(int[] p, int n)
    {

        int m[][] = new int[n][n];

        int i, j, k, L, q;

        // cost is zero when multiplying one matrix.
        for (i = 1; i < n; i++)
            m[i][i] = 0;

        // L is chain length.
        for (L=2; L<n; L++)
        {
            for (i=1; i<n-L+1; i++)
            {
                j = i+L-1;
                if(j == n) continue;
                m[i][j] = Integer.MAX_VALUE;
                for (k=i; k<=j-1; k++)
                {
                    // q = cost/scalar multiplications
                    q = m[i][k] + m[k+1][j] + p[i-1]*p[k]*p[j];
                    if (q < m[i][j])
                        m[i][j] = q;
                }
            }
        }

        return m[1][n-1];
    }

    public static void main(String[] args)
    {

        int arr[] = {5, 10, 3, 12, 5, 50, 6};
        int size = arr.length;
        OptimalParanthesizationUsingDP opudp = new OptimalParanthesizationUsingDP(
                arr);
        System.out.println("Matrices are of order: ");
        for (int i = 1; i < arr.length; i++)
        {
            System.out.println("A" + i + "-->" + arr[i - 1] + "x" + arr[i]);
        }
        System.out.println(opudp.toString());
        System.out.println("Minimum number of multiplications is "+
                MatrixChainOrder(arr, size));

    }
}
