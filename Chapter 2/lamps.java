/*
ID: vshao981
LANG: JAVA
TASK: lamps
*/

import java.io.*;
import java.util.*;

public class lamps {

	public static void main(String[] args) throws IOException {
    	BufferedReader f = new BufferedReader(new FileReader("lamps.in"));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lamps.out")));

    	int n = Integer.parseInt(f.readLine());
    	int c = Integer.parseInt(f.readLine());
    	ArrayList<Integer> onLamps = new ArrayList<>(), offLamps = new ArrayList<>();
    	StringTokenizer st1 = new StringTokenizer(f.readLine()), st2 = new StringTokenizer(f.readLine());
    	int l = Integer.parseInt(st1.nextToken());
    	while (l != -1) {
    		onLamps.add(l);
    		l = Integer.parseInt(st1.nextToken());
    	}
    	l = Integer.parseInt(st2.nextToken());
    	while (l != -1) {
    		offLamps.add(l);
    		l = Integer.parseInt(st2.nextToken());
    	}

    	BitSet allOn = new BitSet(n+1);
    	BitSet even = new BitSet(n+1);
    	BitSet threes = new BitSet(n+1);
    	allOn.set(1, n+1);
    	for (int i = 1; i <= n/2; i++)
    		even.set(i*2);
    	BitSet odd = (BitSet)even.clone();
    	odd.flip(1, n+1);
    	for (int i = 0; i <= (n-1)/3; i++)
    		threes.set(i*3+1);

    	ArrayList<BitSet> possible = new ArrayList<>();

    	if (c % 2 == 0) {
    		possible.add(allOn);
    		if (c >= 2) {
    			possible.add(even);
    			possible.add(odd);
    			possible.add(threes);
    			BitSet t = (BitSet)allOn.clone();
    			t.flip(1, n+1);
    			possible.add(t);
    			t = (BitSet)odd.clone();
    			t.xor(threes);
    			possible.add(t);
    			t = (BitSet)even.clone();
    			t.xor(threes);
    			possible.add(t);
    		}
    		if (c >= 4) {
    			BitSet t = (BitSet)allOn.clone();
    			t.xor(threes);
    			possible.add(t);
    		}
    	} else {
    		if (c >= 1) {
    			BitSet t = (BitSet)allOn.clone();
    			t.flip(1, n+1);
    			possible.add(t);
    			t = (BitSet)allOn.clone();
    			t.xor(even);
    			possible.add(t);
    			t = (BitSet)allOn.clone();
    			t.xor(odd);
    			possible.add(t);
    			t = (BitSet)allOn.clone();
    			t.xor(threes);
    			possible.add(t);
	    	}
	    	if (c >= 3) {
	    		possible.add(threes);
    			BitSet t = (BitSet)odd.clone();
    			t.xor(threes);
    			possible.add(t);
    			t = (BitSet)even.clone();
    			t.xor(threes);
    			possible.add(t);
	    		possible.add(allOn);
	    	}
    	}

    	ArrayList<String> sorted = new ArrayList<>();
    	for (BitSet bs : possible) {
    		boolean skip = false;
    		for (int on : onLamps) {
    			if (!bs.get(on)) {
    				skip = true;
    				break;
    			}
    		}
    		if (skip)
    			continue;
    		for (int off : offLamps) {
    			if (bs.get(off)) {
    				skip = true;
    				break;
    			}
    		}
    		if (!skip) {
    			StringBuilder sb = new StringBuilder();
    			for (int i = 1; i <= n; i++) {
    				sb.append(bs.get(i) ? 1 : 0);
    			}
    			sorted.add(sb.toString());
    		}
    	}

    	if (sorted.size() == 0)
    		out.println("IMPOSSIBLE");
    	else {
			Collections.sort(sorted);
			for (String s : sorted)
				out.println(s);
    	}
        
        out.close();
	}
}