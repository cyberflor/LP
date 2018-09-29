/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labPLANET.utilities;

/**
 * LabPLANETMath is a library for adding extra maths to the standard ones.
 * @author Fran Gomez
 * @version 0.1
 */
public class LabPLANETMath {
    
/**
 * 
 * Calc the nth root. Common application on sampling plans.
 * @param n int - The nth root 
 * @param A double - for this value
 * @param p double 
 * @return double. returns 0 if A = 0, returns -1 in case of error.
 */    
    public static double nthroot(int n, double A, double p) {
	if(A < 0) {
		System.err.println("A < 0");// we handle only real positive numbers
		return -1;
	} else if(A == 0) {
		return 0;
	}
	double x_prev = A;
	double x = A / n;  // starting "guessed" value...
	while(Math.abs(x - x_prev) > p) {
		x_prev = x;
		x = ((n - 1.0) * x + A / Math.pow(x, n - 1.0)) / n;
	}
	return x;
    }  
    
} // end class
