/*
ID: vshao981
LANG: JAVA
TASK: nocows
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class nocows {

  private static int[][] pedigrees;
  private static int k;

  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("nocows.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocows.out")));

    StringTokenizer st = new StringTokenizer(f.readLine());
    int n = Integer.parseInt(st.nextToken());
    k = Integer.parseInt(st.nextToken());

    pedigrees = new int[n + 1][k + 1];
    for (int i = 0; i <= n; i++) {
      // No trees of height 0
      pedigrees[i][0] = 0;
      // The only tree of height 1 has 1 node
      pedigrees[i][1] = i == 1 ? 1 : 0;
      
      for (int j = 2; j <= k; j++) {
        if (i == 0 || i > Math.pow(2, j) - 1 || i < 2 * j - 1) {
          pedigrees[i][j] = 0;
        } else {
          pedigrees[i][j] = -1;
        }
      }
    }
    pedigrees[3][2] = 1;

    out.println(countTrees(n, k));
    out.close();
  }

  static int countTrees(int n, int curK) {
    if (pedigrees[n][curK] != -1) {
      return pedigrees[n][curK];
    }

    long sum = 0;
    for (int i = n - 2; i > n / 2; i -= 2) {
      for (int j = 1; j < curK - 1; j++) {
        sum += countTrees(i, j) * countTrees(n - 1 - i, curK - 1);
        sum += countTrees(i, curK - 1) * countTrees(n - 1 - i, j);
      }
      sum += countTrees(i, curK - 1) * countTrees(n - 1 - i, curK - 1);
    }
    sum *= 2;
    if ((n - 1) % 4 == 2) {
      for (int j = 1; j < curK - 1; j++) {
        sum += countTrees(n / 2, j) * countTrees(n / 2, curK - 1);
        sum += countTrees(n / 2, curK - 1) * countTrees(n / 2, j);
      }
      sum += countTrees(n / 2, curK - 1) * countTrees(n / 2, curK - 1);
    }
    pedigrees[n][curK] = (int)(sum % 9901);
    return (int)(sum % 9901);
  }

}