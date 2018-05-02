package Code;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	private static  File inputs = new File("c:\\test\\Tabular.txt"); 
	 public static void case1(){
		 Scanner sc = new Scanner(System.in);
		 TabularMethod s = new TabularMethod();
		 System.out.print("Minterms(space or comma separated): ");
	        String minterms = sc.nextLine();       
	        System.out.print("Don'tCares(space or comma separated): ");
	        String dontCares = sc.nextLine();      
	        s.lists(minterms, dontCares);
	        System.out.println();
	        s.solve();
	       String h= s.printResults();
	       System.out.println(h);
     }
	 public static void case2(){
		 TabularMethod s = new TabularMethod();
		 s.readfromfile(inputs);
	 }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("choose if you want to enter inputs or get it from file");
        System.out.println("Enter 1 if you want to enter inputs");
        System.out.println("Enter 2 if you want to get inputs from file");
        int chose=sc.nextInt();
        switch(chose){
        case 1: case1();
                break;
        case 2: case2();
                break;
        default: System.out.println("Enter correct choose!!!");       
        }
      
    }

}
