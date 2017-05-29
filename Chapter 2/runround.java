/*
ID: vshao981
LANG: JAVA
TASK: runround
*/

import java.io.*;
import java.util.*;

public class runround {

	public static void main(String[] args) throws IOException {
    	BufferedReader f = new BufferedReader(new FileReader("runround.in"));
	    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("runround.out")));

    	String n = f.readLine();
    	out.println(getNextRRN(n));
        
        out.close();
	}

	static String getNextRRN(String n) {
		int[] intN = new int[n.length()];
		for (int i = 0; i < n.length(); i++) {
			intN[i] = Character.getNumericValue(n.charAt(i));
		}

        boolean[] hasPointer = new boolean[9];
        Arrays.fill(hasPointer, false);
        TreeSet<Integer> digits = new TreeSet<>();
        for (int i = 1; i < 10; i++)
        	digits.add(i);
        ArrayDeque<Integer> soFar = new ArrayDeque<>();

        boolean firstIsDuplicate = false;
        for (int i = 0; i < n.length(); i++) {
        	soFar.push(intN[i]);
        	if (!digits.contains(intN[i])) {
        		firstIsDuplicate = true;
        		break;
        	}
        	digits.remove(intN[i]);

        	int pointsAt = (i + intN[i]) % n.length();
        	if (hasPointer[pointsAt])
        		break;
        	hasPointer[pointsAt] = true;
        }


        int desiredLength = intN.length;
        while (true) {
        	int lastDigit = soFar.pop();
        	System.out.println("popped " + lastDigit);
        	if (!firstIsDuplicate) {
        		digits.add(lastDigit);
        		hasPointer[(soFar.size() + lastDigit) % desiredLength] = false;
        	}
        	firstIsDuplicate = false;

        	if (lastDigit == 9) {
        		if (soFar.isEmpty()) {
        			soFar.push(1);
        			digits.remove(1);
        			hasPointer[1] = true;
        			lastDigit = 0;
        			desiredLength++;
        		} else
        			continue;
        	}

        	while (soFar.size() < desiredLength) {
        		int toRemove = -1;
	        	for (int i : digits) {
	        		int index = (soFar.size() + i) % desiredLength;
	        		if (i > lastDigit && !hasPointer[index]) {
	        			if (soFar.size() < desiredLength - 1) {
		        			int checkCycle = index;
		        			boolean foundCycle = false;
		        			Integer[] soFarArray = soFar.toArray(new Integer[0]);
		        			System.out.print(desiredLength + " ");
		        			System.out.println(Arrays.toString(soFarArray));
		        			System.out.print(Integer.toString(checkCycle) + " ");
		        			while (checkCycle < soFar.size()) {
		        				checkCycle = (checkCycle + soFarArray[soFar.size() - 1 - checkCycle]) % desiredLength;
		        				System.out.print(Integer.toString(checkCycle) + " ");
		    				}
		    				System.out.print(" ");
		        			if (checkCycle == soFar.size()) {
		        				System.out.println("cycle produced by " + i);
		        				continue;
		        			}
		        		}
	        			hasPointer[index] = true;
	        			lastDigit = 0;
	        			soFar.push(i);
	        			System.out.println("pushed " + i);
	        			System.out.println(Arrays.toString(hasPointer));
	        			toRemove = i;
	        			break;
	        		} else if (hasPointer[index])
	        			System.out.println("skipped over nonunique " + i + ", pointer at " + index);
	        		else
	        			System.out.println("skipped over too small " + i);
	        	}
	        	if (toRemove > 0)
	        		digits.remove(toRemove);
	        	else {
	        		System.out.println("need backtrack");
	        		break;
	        	}
        	}

        	if(soFar.size() == desiredLength)
        		break;
        }

        StringBuilder sb = new StringBuilder();
        while (!soFar.isEmpty())
        	sb.append(soFar.pop());
    	return sb.reverse().toString();
	}

}