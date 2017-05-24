/*
ID: vshao981
LANG: JAVA
TASK: hamming
*/

import java.io.*;
import java.util.*;

public class hamming {
	public static void main(String[] args) throws IOException {
    	BufferedReader f = new BufferedReader(new FileReader("hamming.in"));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("hamming.out")));
    	
    	StringTokenizer st = new StringTokenizer(f.readLine());
		int n = Integer.parseInt(st.nextToken());
		int b = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		int maxN = (int)Math.pow(2, b);

		boolean[][] graph = new boolean[maxN][maxN];
		for (int i = 0; i < maxN; i++) {
			graph[i][i] = false;
			for (int j = i+1; j < maxN; j++)
				if (hDist(i, j) >= d)
					graph[i][j] = graph[j][i] = true;
				else
					graph[i][j] = graph[j][i] = false;
		}

		ArrayList<Integer> soFar = new ArrayList<Integer>(n);
		soFar.add(0);
		for (int i = 1; i < maxN; i++) {
			boolean shouldAdd = true;
			for (int j : soFar) {
				if (!graph[i][j]) {
					shouldAdd = false;
					break;
				}
			}
			if (shouldAdd) {
				soFar.add(i);
				if (soFar.size() == n)
					break;
			}
		}

		for (int i = 0; i < n; i++) {
			if ((i+1) % 10 == 0 || i == n-1)
				out.println(soFar.get(i));
			else {
				out.print(soFar.get(i));
				out.print(' ');
			}
		}
		out.close();
	}

	public static int hDist(int f, int s) {
		int dist = 0;
		int xor = f ^ s;
		while (xor != 0) {
			if ((xor & 1) == 1)
				dist++;
			xor >>>= 1;
		}
		return dist;
	}
}