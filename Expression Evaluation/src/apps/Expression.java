package apps;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	/**
	 * Expression to be evaluated
	 */
	String expr;                
    
	/**
	 * Scalar symbols in the expression 
	 */
	ArrayList<ScalarSymbol> scalars;   
	
	/**
	 * Array symbols in the expression
	 */
	ArrayList<ArraySymbol> arrays;
    
    /**
     * String containing all delimiters (characters other than variables and constants), 
     * to be used with StringTokenizer
     */
    public static final String delims = " \t*+-/()[]";
    
    /**
     * Initializes this Expression object with an input expression. Sets all other
     * fields to null.
     * 
     * @param expr Expression
     */
    public Expression(String expr) {
        this.expr = expr;
    }

    /**
     * Populates the scalars and arrays lists with symbols for scalar and array
     * variables in the expression. For every variable, a SINGLE symbol is created and stored,
     * even if it appears more than once in the expression.
     * At this time, values for all variables are set to
     * zero - they will be loaded from a file in the loadSymbolValues method.
     */
    public void buildSymbols() {
    		/** COMPLETE THIS METHOD **/
    	scalars = new ArrayList<ScalarSymbol>();
    	arrays = new ArrayList<ArraySymbol>();
    	//iterate through the expression, find variables.
    	//if the name contains a '[' after a string, it is an array
    	//otherwise, it is a scalar
    	String symbol = "";
    	for (int i = 0; i < expr.length(); i++) {
    		while (Character.isLetter(expr.charAt(i))){
    			symbol += expr.charAt(i);
	    		
    			if (i+1 < expr.length()) i = i+1;
	    		else break;
    		}
    		// array variable
    		if (!symbol.equals("") && expr.charAt(i) == '[') {
    			ArraySymbol newVar = new ArraySymbol(symbol);
    			if (!arrays.contains(newVar)) arrays.add(newVar);
    		}
    		//scalar variable
    		else if (!symbol.equals("")) {
    			ScalarSymbol newVar = new ScalarSymbol(symbol);
    			if (!scalars.contains(newVar)) scalars.add(newVar);
    		}
    		symbol = "";
    	}
    }
    
    /**
     * Loads values for symbols in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     */
    public void loadSymbolValues(Scanner sc) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String sym = st.nextToken();
            ScalarSymbol ssymbol = new ScalarSymbol(sym);
            ArraySymbol asymbol = new ArraySymbol(sym);
            int ssi = scalars.indexOf(ssymbol);
            int asi = arrays.indexOf(asymbol);
            if (ssi == -1 && asi == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                scalars.get(ssi).value = num;
            } else { // array symbol
            	asymbol = arrays.get(asi);
            	asymbol.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    String tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    asymbol.values[index] = val;              
                }
            }
        }
    }
    
    
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array 
     * subscript expressions.
     * 
     * @return Result of evaluation
     */    
    public float evaluate() {
    	expr = expr.replaceAll(" ", "");
    	expr = "(" + expr + ")";
    	
    	return subeval(0)[0];
    }
    
    // returns [a, b] where a is the evaluated expression, 
    // and b is the index after the last one read
    // b would be in the index after ] or )
    private float[] subeval(int startIndex) {
    	float ans[] = new float[2];
    	char ch = expr.charAt(startIndex);
    	
    	if (Character.isDigit(ch)) {
    		int i = startIndex;
    		String num = "";
    		while (i < expr.length() && Character.isDigit(expr.charAt(i))) {
    			num += expr.charAt(i);
    			i++;
    		}
    		ans[0] = Float.parseFloat(num);
    		ans[1] = (float)(i);
    		return ans;
    	}
    	
    	if (Character.isLetter(ch)) {
    		int i = startIndex;
    		String name = "";
    		while (i < expr.length() && Character.isLetter(expr.charAt(i))){
    			name += expr.charAt(i);
    			i++;
    		}
    		// array variable
    		if (i < expr.length() && !name.equals("") && expr.charAt(i) == '[') {
    			for (ArraySymbol as: arrays) {
    				if (as.name.equals(name)) {
    					float[] eval = subeval(i);
    					ans[1] = eval[1];
    					ans[0] = as.values[(int) eval[0]];
    					return ans;
    				}
    			}
    		}
    		//scalar variable
    		else if (!name.equals("")) {
    			for (ScalarSymbol ss: scalars) {
    				if (ss.name.equals(name)) {
    					ans[0] = ss.value;
    					ans[1] = i;
    					return ans;
    				}
    			}
    		}
    	}
    	
    	if (ch == '(' || ch == '[') {
    		char endChar = ch == '(' ? ')' : ']';
    		int i = startIndex+1;
    		Stack<Float> operands = new Stack<Float>();
        	Stack<Character> operators = new Stack<Character>();
        	while (i < expr.length() && expr.charAt(i) != endChar) {
        		char c = expr.charAt(i);
        		if (c == '*' || c == '+' || c == '-' || c == '/') {
        			if (c == '+' || c == '-') {
        				operators.push(c);
        				i++;
        			}
        			if (c == '*') {
        				float a = operands.pop();
        				float[] eval = subeval(i+1);
        				i = (int)eval[1];
        				operands.push(a*eval[0]);
        				continue;
        			}
        			
        			if (c == '/') {
        				float a = operands.pop();
        				float[] eval = subeval(i+1);
        				i = (int)eval[1];
        				operands.push(a/eval[0]);
        				continue;
        			}
        		}
        		else {
        			float[] eval = subeval(i);
        			operands.push(eval[0]);
        			i = (int)eval[1];
        		}
        	}
        	
        	Stack<Float> revOperands = new Stack<Float>();
        	Stack<Character> revOperators = new Stack<Character>();
        	while (!operands.isEmpty()) {
        		revOperands.push(operands.pop());
        	}
        	while (!operators.isEmpty()) {
        		revOperators.push(operators.pop());
        	}
        	while (!revOperators.isEmpty()) {
        		char op = revOperators.pop();
        		Float a = revOperands.pop();
        		Float b = revOperands.pop();
        		revOperands.push(op == '+' ? a + b: a-b);
        	}
        	ans[0] = revOperands.pop();
        	ans[1] = i + 1;
    	}
    	
    	
    	return ans;
    }

    /**
     * Utility method, prints the symbols in the scalars list
     */
    public void printScalars() {
        for (ScalarSymbol ss: scalars) {
            System.out.println(ss);
        }
    }
    
    /**
     * Utility method, prints the symbols in the arrays list
     */
    public void printArrays() {
    		for (ArraySymbol as: arrays) {
    			System.out.println(as);
    		}
    }

}
