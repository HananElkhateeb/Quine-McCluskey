package Code;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

public class TabularMethod {
	int [] minterm;
	int [] dc;
	int maxlength;
	Term [] terms;
	private ArrayList<Integer> minterms;
    private ArrayList<Integer> dcs;
    private ArrayList<Term>[] groups;
    private ArrayList<Term> finalTerms;
    private ArrayList<String>[] solution;
    private ArrayList<String> prime = new ArrayList<String>();;
 
    private  class onesComparator implements Comparator<Term> {
		public int compare(Term a, Term b) {
            return a.getNumOnes() - b.getNumOnes();
        }
    }
 
    private int[] isValid(String s) {
        s = s.replace(",", " ");
        if (s.trim().equals("")) {
            return new int[] {};
        }
        String[] a = s.trim().split(" +");
        int[] t = new int[a.length];
        for (int i = 0; i < t.length; i++) {
            try {
                int temp = Integer.parseInt(a[i]);
                t[i] = temp;
            } catch (Exception e) {
                throw new RuntimeException("Invalid Input!");
            }
        }
        HashSet<Integer> dup = new HashSet<>();
        for (int i = 0; i < t.length; i++) {
            if (dup.contains(t[i])) {
                throw new RuntimeException("Invalid Input!");
            }
            dup.add(t[i]);
        }
        return t;
    }
   public void readfromfile(File inputs){
	   try{
		   BufferedReader br = new BufferedReader(new FileReader(inputs));
		   int flag=0;
		   String line;
		   String minterms = null;
		   String dcs=null;
			while((line = br.readLine()) != null){
				if(flag==0){
					 minterms=line;
					flag=1;
				} else{
					dcs=line;
				}
			}
			lists(minterms,dcs);
			String h= printResults();
		       System.out.println(h);
	   } catch (IOException e) {
			e.printStackTrace();
		}
   }
   
    boolean check(){
    	for (int i = 0; i < dc.length; i++){
    		for (int j = 0; j < minterm.length; j++){
    			if(dc[i] == minterm[j])
    				return false;
    		}
    	}
    	return true;
    }
 
	public void lists(String mintermsString ,String dcString){
		int k;
		minterm = isValid(mintermsString);
        dc = isValid(dcString);
		Arrays.sort(minterm);
		Arrays.sort(dc);
		if(!check())
			throw new RuntimeException();
		maxlength = Integer.toBinaryString(minterm[minterm.length - 1]).length();
		terms = new Term [minterm.length + dc.length];
		dcs = new ArrayList<Integer>();
        minterms = new ArrayList<Integer>();
		k = 0;
		for(int i = 0; i < minterm.length; i++){
			minterms.add(minterm[i]);
			terms[k++] = new Term(minterm[i], maxlength);
		}
		for(int i = 0; i < dc.length; i++){
				dcs.add(dc[i]);
				terms[k++] = new Term(dc[i], maxlength);
		}
		Arrays.sort(terms, new onesComparator());
		groups();
	}
 
	public void groups () {
		groups = new ArrayList[terms[terms.length - 1].getNumOnes() + 1];
 
        for (int i = 0; i < groups.length; i++) {
            groups[i] = new ArrayList<>();
        }
        for (int i = 0; i < terms.length; i++) {
            int k = terms[i].getNumOnes();
            groups[k].add(terms[i]);
        }
        solve();
	}
 
	public void solve() {
        ArrayList<Term> untaken = new ArrayList<>();
        ArrayList<Term>[] list = groups;
        ArrayList<Term>[] results;
        boolean inserted;
        do {
            HashSet<String> taken = new HashSet<>();
            results = new ArrayList[list.length - 1];
 
            ArrayList<String> temp;
            inserted = false;
 
            for (int i = 0; i < list.length - 1; i++) {
                results[i] = new ArrayList<>();
                temp = new ArrayList<>();
                for (int j = 0; j < list[i].size(); j++) {
                    for (int k = 0; k < list[i + 1].size(); k++) {
                        if (isValidCombination(list[i].get(j), list[i + 1].get(k))) {
                            taken.add(list[i].get(j).getString());
                            taken.add(list[i + 1].get(k).getString());
 
                            Term n = new Term(list[i].get(j), list[i + 1].get(k));
                            if (!temp.contains(n.getString())) {
                                results[i].add(n);
                                inserted = true;
                            }
                            temp.add(n.getString());
                        }
                    }
                }
            }
            if (inserted) {
                for (int i = 0; i < list.length; i++) {
                    for (int j = 0; j < list[i].size(); j++) {
                        if (!taken.contains(list[i].get(j).getString())) {
                            untaken.add(list[i].get(j));
                        }
                    }
                }
                list = results;
 
            }
        } while (inserted && list.length > 1);
        finalTerms = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].size(); j++) {
                finalTerms.add(list[i].get(j));
            }
        }
        for (int i = 0; i < untaken.size(); i++) {
            finalTerms.add(untaken.get(i));
        }
        solve2();
    }
 
	boolean isValidCombination(Term t1, Term t2) {
        if (t1.getString().length() != t2.getString().length())
            return false;
 
        int k = 0;
        for (int i = 0; i < t1.getString().length(); i++) {
            if (t1.getString().charAt(i) == '-' && t2.getString().charAt(i) != '-')
                return false;
            else if (t1.getString().charAt(i) != '-' && t2.getString().charAt(i) == '-')
                return false;
            else if (t1.getString().charAt(i) != t2.getString().charAt(i))
                k++;
            else
                continue;
        }
        if (k != 1)
            return false;
        else
            return true;
    }
 
	public void solve2() {
        if (!EssentialPrime()) {
            if (!rowDominance()) {
                if (!columnDominance()) {
                    // if none succeeds go to petrick
                    petrick();
                    return;
                }
            }
        }
        // if there are still minterms to be taken call this function again
        if (minterms.size() != 0)
            solve2();
        // if all minterms taken, add to solution
        else {
            // for displaying steps
            solution = new ArrayList[1];
            solution[0] = prime;
        }
    }
 
    // petrick's method
    void petrick() {
 
        HashSet<String>[] temp = new HashSet[minterms.size()];
        // construct a table for petrick
        for (int i = 0; i < minterms.size(); i++) {
            temp[i] = new HashSet<>();
            for (int j = 0; j < finalTerms.size(); j++) {
                if (finalTerms.get(j).getNums().contains(minterms.get(i))) {
                    char t = (char) ('a' + j);
                    temp[i].add(t + "");
                }
            }
        }
 
        // multiply petrick terms
        HashSet<String> finalResult = multiply(temp, 0);
 
 
        // identify minimum terms in petrick expansion
        int min = -1;
        int count = 0;
        for (Iterator<String> t = finalResult.iterator(); t.hasNext();) {
            String m = t.next();
            if (min == -1 || m.length() < min) {
                min = m.length();
                count = 1;
            } else if (min == m.length()) {
                count++;
            }
        }
 
        // add minimum terms in petrick to solutions
        solution = new ArrayList[count];
        int k = 0;
        for (Iterator<String> t = finalResult.iterator(); t.hasNext();) {
            String c = t.next();
            if (c.length() == min) {
                solution[k] = new ArrayList<>();
                for (int i = 0; i < c.length(); i++) {
                    solution[k].add(finalTerms.get((int) c.charAt(i) - 'a').getString());
                }
                for (int i = 0; i < prime.size(); i++) {
                    solution[k].add(prime.get(i));
                }
                k++;
            }
        }
    }
 
    // multiplication for petrick expressions
    HashSet<String> multiply(HashSet<String>[] p, int k) {
        if (k >= p.length - 1) {
            return p[k];
        }
        HashSet<String> s = new HashSet<>();
        for (Iterator<String> t = p[k].iterator(); t.hasNext();) {
            String temp = t.next();
            for (Iterator<String> g = p[k + 1].iterator(); g.hasNext();) {
                String temp1 = g.next();
                HashSet<Character> r = new HashSet<>();
                for (int i = 0; i < temp.length(); i++)
                    r.add(temp.charAt(i));
                for (int i = 0; i < temp1.length(); i++)
                    r.add(temp1.charAt(i));
                String result = "";
                for (Iterator<Character> i = r.iterator(); i.hasNext();)
                    result += i.next();
                s.add(result);
            }
        }
        p[k + 1] = s;
        return multiply(p, k + 1);
    }
 
    // deletes dominating columns
    private boolean columnDominance() {
        boolean flag = false;
        // construct a table
        ArrayList<ArrayList<Integer>> cols = new ArrayList<>();
        for (int i = 0; i < minterms.size(); i++) {
            cols.add(new ArrayList<Integer>());
            for (int j = 0; j < finalTerms.size(); j++) {
                if (finalTerms.get(j).getNums().contains(minterms.get(i))) {
                    cols.get(i).add(j);
                }
            }
        }
        for (int i = 0; i < cols.size() - 1; i++) {
            for (int j = i + 1; j < cols.size(); j++) {
                if (cols.get(j).containsAll(cols.get(i)) && cols.get(j).size() > cols.get(i).size()) {
                    cols.remove(j);
                    minterms.remove(j);
                    j--;
                    flag = true;
                } else if (cols.get(i).containsAll(cols.get(j)) && cols.get(i).size() > cols.get(j).size()) {
                    cols.remove(i);
                    minterms.remove(i);
                    i--;
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
 
    private boolean rowDominance() {
        boolean flag = false;
        for (int i = 0; i < finalTerms.size() - 1; i++) {
            for (int j = i + 1; j < finalTerms.size(); j++) {
                if (contains(finalTerms.get(i), finalTerms.get(j))) {
                    finalTerms.remove(j);
                    j--;
                    flag = true;
                } else if (contains(finalTerms.get(j), finalTerms.get(i))) {
                    finalTerms.remove(i);
                    i--;
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
 
    // identify prime implicants and cross them and the minterms they represent
    private boolean EssentialPrime() {
        ArrayList<Integer>[] cols = new ArrayList[minterms.size()];
        for (int i = 0; i < minterms.size(); i++) {
            cols[i] = new ArrayList();
            for (int j = 0; j < finalTerms.size(); j++) {
                if (finalTerms.get(j).getNums().contains(minterms.get(i))) {
                    cols[i].add(j);
                }
            }
        }
        boolean flag = false;
        for (int i = 0; i < minterms.size(); i++) {
            if (cols[i].size() == 1) {
                flag = true;
                ArrayList<Integer> del = finalTerms.get(cols[i].get(0)).getNums();
 
                for (int j = 0; j < minterms.size(); j++) {
                    if (del.contains(minterms.get(j))) {
                        dcs.add(minterms.get(j));
                        minterms.remove(j);
                        j--;
                    }
                }
 
                prime.add(finalTerms.get(cols[i].get(0)).getString());
                finalTerms.remove(cols[i].get(0).intValue());
                break;
            }
        }
 
        return flag;
    }
 
 
    // checks if term t1 contains same minterms of t2
    boolean contains(Term t1, Term t2) {
        if (t1.getNums().size() <= t2.getNums().size()) {
            return false;
        }
        ArrayList<Integer> a = t1.getNums();
        ArrayList<Integer> b = t2.getNums();
        b.removeAll(dcs);
 
        if (a.containsAll(b))
            return true;
        else
            return false;
    }
 
    // convert result to symbolic notation, ex: -01- = B'C
    String toSymbolic(String s) {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-') {
                continue;
            } else if (s.charAt(i) == '1') {
                r.append((char) ('A' + i));
            } else {
                r.append((char) ('A' + i));
                r.append('\'');
            }
        }
        if (r.toString().length() == 0) {
            r.append("1");
        }
        return r.toString();
    }
 
    // used to print final results
    public String printResults() {
    	StringBuilder h = new StringBuilder();
        for (int i = 0; i < solution.length; i++) {
        	h.append(i+1);
        	
        	h.append(":(");
            for (int j = 0; j < solution[i].size(); j++) {
            	h.append(toSymbolic(solution[i].get(j)));
                if (j != solution[i].size() - 1) {
                	h.append(" + ");
                }
            }
            h.append(") ");
        }
        return h.toString();
    }
}