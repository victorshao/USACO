/*
ID: vshao981
LANG: JAVA
TASK: prefix
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class prefix {

  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("prefix.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("prefix.out")));

    ArrayList<String> primitives = new ArrayList<>();
    int longestPrimitive = 1;
    StringTokenizer st = new StringTokenizer(f.readLine());
    while (true) {
      String p = st.nextToken();
      if (p.equals(".")) {
        break;
      }
      longestPrimitive = Math.max(longestPrimitive, p.length());
      primitives.add(p);
      if (!st.hasMoreTokens()) {
        st = new StringTokenizer(f.readLine());
      }
    }

    ArrayDeque<Character> window = new ArrayDeque<>();
    PriorityQueue<Integer> marks = new PriorityQueue<>();
    HashSet<Integer> markSet = new HashSet<>();
    
    marks.add(0);
    int next;
    while (window.size() < longestPrimitive && (next = f.read()) != -1) {
      window.add((char) next);
    }

    int currentMark = 0;
    int previousMark = 0;
    while (marks.size() > 0) {
      previousMark = currentMark;
      currentMark = marks.poll();
      int count = previousMark;
      while (count < currentMark && (next = f.read()) != -1) {
        if ((char)next == '\n') {
          next = f.read();
          if (next == -1) {
            break;
          }
        }
        window.poll();
        window.add((char) next);
        count++;
      }
      for (String primitive : primitives) {
        if (primitive.length() <= window.size()) {
          boolean matches = true;
          Iterator<Character> it = window.iterator();
          for (int i = 0; i < primitive.length(); i++) {
            if (primitive.charAt(i) != it.next()) {
              matches = false;
              break;
            }
          }
          if (matches && !markSet.contains(currentMark + primitive.length())) {
            markSet.add(currentMark + primitive.length());
            marks.add(currentMark + primitive.length());
          }
        }
      }
    }

    out.println(currentMark);
    out.close();
  }
}