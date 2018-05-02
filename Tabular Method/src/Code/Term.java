package Code;

import java.util.ArrayList;

public class Term {
    private String term;
    private int ones;
    private ArrayList<Integer> nums;
    public Term(int value, int length) {
        String binary = Integer.toBinaryString(value);        
        StringBuffer temp = new StringBuffer(binary);
        while (temp.length() != length) {
            temp.insert(0, 0);
        } 
        this.term = temp.toString();
        nums = new ArrayList<Integer>();
        nums.add(value);
        
        // count num of ones
        ones = 0;
        for (int i = 0; i < term.length(); i++) {
            if (term.charAt(i) == '1') {
                ones++;
            }
        }
    }
    public Term(Term t1, Term t2) {   
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < t1.getString().length(); i++) {
            if (t1.getString().charAt(i) != t2.getString().charAt(i)) {
                temp.append("-");
            } else {
                temp.append(t1.getString().charAt(i));
            }
        }
        this.term = temp.toString();
        ones = 0;
        for (int i = 0; i < term.length(); i++) {
            if (this.term.charAt(i) == '1') {
                ones++;
            }
        }
        nums = new ArrayList<Integer>();;
        for (int i = 0; i < t1.getNums().size(); i++) {
            nums.add(t1.getNums().get(i)); 
        }
        for (int i = 0; i < t2.getNums().size(); i++) {
            nums.add(t2.getNums().get(i));  
        }
    }
    
    String getString() { return term; }
    ArrayList<Integer> getNums() { return nums; }
    int getNumOnes() { return ones; }
    
}
