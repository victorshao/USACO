/*
ID: vshao981
LANG: JAVA
TASK: castle
*/

import java.io.*;
import java.util.*;

public class castle {
	public static void main(String[] args) throws IOException {
    	BufferedReader f = new BufferedReader(new FileReader("castle.in"));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("castle.out")));
    	
    	StringTokenizer st = new StringTokenizer(f.readLine());
    	int m = Integer.parseInt(st.nextToken());
    	int n = Integer.parseInt(st.nextToken());

        ArrayList<ArrayDeque<Integer>> graph = new ArrayList<ArrayDeque<Integer>>(m*n);
        int[][] walls = new int[n][m];
        for (int i = 0; i < n*m; i++)
            graph.add(new ArrayDeque<Integer>()); 
    	for (int i = 0; i < n; i++) {
    		st = new StringTokenizer(f.readLine());
    		for (int j = 0; j < m; j++) {
    			int mod = Integer.parseInt(st.nextToken());
                int modnum = i*m+j;
    			if (mod < 8) {
                    graph.get(modnum).add(modnum+m);
                    graph.get(modnum+m).add(modnum);
                }
                else
                    mod -= 8;
                walls[i][j] = mod;
                if (mod < 4) {
                    graph.get(modnum).add(modnum+1);
                    graph.get(modnum+1).add(modnum);
                }
    		}
    	}

        ArrayDeque<Integer> q = new ArrayDeque<Integer>();
        q.push(0);
        ArrayList<Integer> roomSizes = new ArrayList<Integer>();
        boolean[] visited = new boolean[n*m];
        for (int i = 1; i < visited.length; i++)
            visited[i] = false;
        visited[0] = true;
        int[] comp = new int[n*m];
        int currComp = 0;
        int maxSize = 1;
        while (true) {
            int curSize = 0;
            while (!q.isEmpty()) {
                int cur = q.pop();
                curSize++;
                comp[cur] = currComp;
                for (int neighbor : graph.get(cur))
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        q.push(neighbor);
                    }
            }
            roomSizes.add(curSize);
            if (curSize > maxSize)
                maxSize = curSize;
            int i;
            for (i = 0; i < comp.length; i++) {
                if (!visited[i]) {
                    q.push(i);
                    visited[i] = true;
                    break;
                }
            }
            if (i == comp.length) break;
            currComp++;
        }

        int maxNewSize = 1, x = m+1, y = 1;
        char dir = ' ';
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int mod = walls[i][j];
                int modNum = i*m+j;
                if (j < m-1 && mod >= 4 && j < m-1 && comp[modNum] != comp[modNum+1]) {
                    int newRoomSize = roomSizes.get(comp[modNum]) + roomSizes.get(comp[modNum+1]);
                    if (newRoomSize > maxNewSize || (newRoomSize == maxNewSize && (j+1 < x || (j+1 == x && i+1 > y)))) {
                        maxNewSize = newRoomSize;
                        x = j+1;
                        y = i+1;
                        dir = 'E';
                    }
                    if (mod > 4) mod -= 4;
                }
                if (i > 0 && mod >= 2 && comp[modNum] != comp[modNum-m]) {
                    int newRoomSize = roomSizes.get(comp[modNum]) + roomSizes.get(comp[modNum-m]);
                    if (newRoomSize > maxNewSize || (newRoomSize == maxNewSize && (j+1 < x || (j+1 == x && i+1 >= y)))) {
                        maxNewSize = newRoomSize;
                        x = j+1;
                        y = i+1;
                        dir = 'N';
                    }
                }
            }
        }

        out.println(currComp+1);
        out.println(maxSize);
        out.println(maxNewSize);
        out.print(y);
        out.print(' ');
        out.print(x);
        out.print(' ');
        out.println(dir);
        out.close();
    }
}