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
  private static int[][] pedigreesNoMin;
  private static int k;

  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("nocows.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("nocows.out")));

    StringTokenizer st = new StringTokenizer(f.readLine());
    int n = Integer.parseInt(st.nextToken());
    k = Integer.parseInt(st.nextToken());

    pedigrees = new int[n + 1][k + 1];
    pedigreesNoMin = new int[n + 1][k + 1];
    for (int i = 0; i <= n; i++) {
      // No trees of height 0
      pedigrees[i][0] = 0;
      pedigreesNoMin[i][0] = 0;
      // The only tree of height 1 has 1 node
      pedigrees[i][1] = i == 1 ? 1 : 0;
      pedigreesNoMin[i][1] = i == 1 ? 1 : 0;
      
      for (int j = 2; j <= k; j++) {
        if (i == 0 || i > Math.pow(2, j) - 1) {
          pedigrees[i][j] = 0;
          pedigreesNoMin[i][j] = 0;
        } else if (i < 2 * j - 1) {
          pedigrees[i][j] = 0;
        } else {
          pedigrees[i][j] = -1;
          pedigreesNoMin[i][j] = -1;
        }
      }
    }

    out.println(countTrees(n, k, true));
    out.close();
  }

  static int countTrees(int oldN, int curK, boolean heightMatters) {
    // System.out.print("Called countTrees(" + oldN + ", " + curK + ")");
    // if (heightMatters) {
    //   System.out.println(" HM");
    // } else {
    //   System.out.println("");
    // }

    if (heightMatters && pedigrees[oldN][curK] != -1) {
      return pedigrees[oldN][curK];
    } else if (!heightMatters && pedigreesNoMin[oldN][curK] != -1) {
      return pedigreesNoMin[oldN][curK];
    }

    int sum = 0;
    for (int i = n ; i > n / 2; i -= 2) {
      sum += 2 * (countTrees(i, kLeft - 1, heightMatters) * countTrees(n - i, kLeft - 1, false)); 
    }
    if (n % 4 == 0) {
      int a = countTrees(n / 2, kLeft - 1, heightMatters);
      sum += a * a;
    }
    sum %= 9901;
    if (heightMatters) {
      pedigrees[oldN][kLeft] = sum;
      System.out.println("Count for (" + oldN + ", " + kLeft + ") HM: " + sum);
    } else {
      pedigreesNoMin[oldN][kLeft] = sum;
      System.out.println("Count for (" + oldN + ", " + kLeft + "): " + sum);
    }
    return sum;
  }

}