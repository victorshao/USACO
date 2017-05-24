/*
ID: vshao981
LANG: JAVA
TASK: preface
*/

import java.io.*;
import java.util.*;

public class preface {
	public static void main(String[] args) throws IOException {
    	BufferedReader f = new BufferedReader(new FileReader("preface.in"));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("preface.out")));
    	
    	int n = Integer.parseInt(f.readLine());
        char[] letters = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};
        int[] count = new int[7];
        for (int i = 0; i < 7; i++)
            count[i] = 0;

        for (int i = 1; i <= n; i++)
            prefNum(i, count);

        int lim;
        for (lim = 6; lim >= 0; lim--)
            if (count[lim] > 0)
                break;
        for (int i = 0; i <= lim; i++)
            out.println(letters[i] + " " + count[i]);
        out.close();
    }

    public static void prefNum(int n, int[] count) {
        if (n > 9) {
            int[] newCount = new int[7];
            for (int i = 0; i < 7; i++)
                newCount[i] = 0;
            prefNum(n / 10, newCount);
            for (int i = 4; i >= 0; i--)
                count[i+2] += newCount[i];
            n %= 10;
        }

        if (1 <= n && n <= 3)
            count[0] += n;
        if (n == 4 || n == 9)
            count[0]++;
        if (6 <= n && n <= 8)
            count[0] += (n-5);
        if (4 <= n && n <= 8)
            count[1]++;
        if (n == 9)
            count[2]++;
    }
}