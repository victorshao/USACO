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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class prefix {

  public static void main(String[] args) throws IOException {
    BufferedReader f = new BufferedReader(new FileReader("prefix.in"));
    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("prefix.out")));

    ArrayList<String> rawPrimitives = new ArrayList<>();
    StringTokenizer st = new StringTokenizer(f.readLine());
    while (true) {
      String p = st.nextToken();
      if (p.equals(".")) {
        break;
      }
      rawPrimitives.add(p);
      if (!st.hasMoreTokens()) {
        st = new StringTokenizer(f.readLine());
      }
    }

    ArrayList<String> primitives = new ArrayList<>();
    int longestPrimitive = 1;
    for (String p : rawPrimitives) {
      if (isUniquePrimitive(p, rawPrimitives)) {
        longestPrimitive = Math.max(longestPrimitive, p.length());
        primitives.add(p);
      }
    }

    for (String p : primitives) {
      System.out.println(p);
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
      for (int count = previousMark; count < currentMark; count++) {
        window.poll();
        next = f.read();
        if ((char)next == '\n') {
          next = f.read();
        }
        if (next != -1) {
          window.add((char) next);
        }
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
          int newMark = currentMark + primitive.length();
          if (matches && !markSet.contains(newMark)) {
            markSet.add(newMark);
            marks.add(newMark);
          }
        }
      }
    }

    out.println(currentMark);
    out.close();
  }

  static void printWindow(ArrayDeque<Character> window) {
    System.out.print("Current window:");
    for (char c : window) {
      System.out.print(" " + c);
    }
    System.out.println();
  }

  static boolean isUniquePrimitive(String primitive, ArrayList<String> rawShortPrims) {
    PriorityQueue<Integer> marks = new PriorityQueue<>();
    HashSet<Integer> markSet = new HashSet<>();

    ArrayList<String> shortPrims = new ArrayList<>();
    for (String p : rawShortPrims) {
      if (p.length() < primitive.length()) {
        shortPrims.add(p);
      }
    }

    marks.add(0);
    int next;
    int currentMark = 0;
    int previousMark = 0;
    while (marks.size() > 0) {
      previousMark = currentMark;
      currentMark = marks.poll();
      for (String p : shortPrims) {
        if (p.length() <= (primitive.length() - currentMark)) {
          boolean matches = true;
          for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) != primitive.charAt(currentMark + i)) {
              matches = false;
              break;
            }
          }
          int newMark = currentMark + p.length();
          if (matches && !markSet.contains(newMark)) {
            markSet.add(newMark);
            marks.add(newMark);
          }
        }
      }
    }
    if (currentMark == primitive.length()) {
      return false;
    }
    return true;
  }

}