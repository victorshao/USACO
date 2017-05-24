/*
ID: vshao981
LANG: JAVA
TASK: subset
*/

import java.io.*;
import java.util.*;

public class subset {

    static int[][] waysToSum;

	public static void main(String[] args) throws IOException {
    	BufferedReader f = new BufferedReader(new FileReader("subset.in"));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("subset.out")));
    	
    	int n = Integer.parseInt(f.readLine()), total = n*(n+1)/4;
        waysToSum = new int[total+1][n+1];

        if (n % 4 == 1 || n % 4 == 2)
            out.println("0");
        else {
            for (int i = 0; i <= total; i++)
                for (int j = 2; j <= n; j++)
                    waysToSum[i][j] = -1;

            for (int i = 0; i <= total; i++)
                waysToSum[i][0] = 0;
            
            for (int i = 1; i <= n; i++) {
                waysToSum[0][i] = 1;
                waysToSum[1][i] = 1;
            }

            out.println(findPartitions(total-n, n-1));
        }

        out.close();
    }

    static int findPartitions(int n, int k) {
        if (waysToSum[n][k] != -1)
            return waysToSum[n][k];

        int t;
        if (k > n)
            t = findPartitions(n, n);
        else if (k*(k+1)/2 >= n)
            t = findPartitions(n, k-1) + findPartitions(n-k, k-1);
        else
            t = 0;
        waysToSum[n][k] = t;
        return t;
    }
}