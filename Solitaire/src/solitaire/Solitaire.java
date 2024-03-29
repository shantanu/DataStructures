package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		// COMPLETE THIS METHOD
		if (deckRear.next.cardValue == 27) {
			CardNode joA = deckRear.next;
			CardNode swap = joA.next;
			deckRear.next = swap;
			joA.next = swap.next;
			swap.next = joA;
			return;
		}
		for (CardNode n = deckRear.next; n != deckRear; n = n.next) {
			if (n.next.cardValue == 27) {
				CardNode joA = n.next;
				CardNode swap = joA.next;
				if (joA == deckRear) {
					deckRear = swap;
				}
				else if (swap == deckRear) {
					deckRear = joA;
				}
				n.next = swap;
				joA.next = swap.next;
				swap.next = joA;
				return;
			}
		}
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
	    // COMPLETE THIS METHOD
		for (int i = 0; i < 2; i++) {
			if (deckRear.next.cardValue == 28) {
				CardNode joB = deckRear.next;
				CardNode swap = joB.next;
				deckRear.next = swap;
				joB.next = swap.next;
				swap.next = joB;
				continue;
			}
			for (CardNode n = deckRear.next; n != deckRear; n = n.next) {
				if (n.next.cardValue == 28) {
					CardNode joB = n.next;
					CardNode swap = joB.next;
					if (joB == deckRear) {
						deckRear = swap;
					}
					else if (swap == deckRear) {
						deckRear = joB;
					}
					n.next = swap;
					joB.next = swap.next;
					swap.next = joB;
					break;
				}
			}
		}
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		// COMPLETE THIS METHOD
		if (deckRear.next.cardValue == 27 || deckRear.next.cardValue == 28) {
			for (CardNode n = deckRear.next.next; n != deckRear; n = n.next) {
				if (n.cardValue == 27 || n.cardValue == 28) {
					deckRear = n;
					return;
				}
			}
			if (deckRear.cardValue == 27 || deckRear.cardValue == 28) {
				return;
			}
		}
		if (deckRear.cardValue == 27 || deckRear.cardValue == 28) {
			for (CardNode n = deckRear.next; n != deckRear; n = n.next) {
				if (n.next.cardValue == 27 || n.next.cardValue == 28) {
					deckRear = n;
					return;
				}
			}
		}
		CardNode endFirst = null, jokB = null, startSec = null;
		for (CardNode n = deckRear.next; n != deckRear; n = n.next) {
			if (endFirst == null && ( n.next.cardValue == 27 || n.next.cardValue == 28)) {
				endFirst = n;
				n=n.next;
				continue;
			}
			if (jokB == null && ( n.cardValue == 27 || n.cardValue == 28)) {
				jokB = n;
				startSec = n.next;
			}
		}
		
		jokB.next = deckRear.next;
		deckRear.next = endFirst.next;
		
		deckRear = endFirst;
		deckRear.next = startSec;
		
		
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		// COMPLETE THIS METHOD
		int count = deckRear.cardValue;
		if (count == 27 || count == 28) {
			return;
		}
		
		CardNode startSplit = deckRear.next;
		CardNode endSplit = deckRear;
		for (int i = 0; i < count; i++) {
			endSplit = endSplit.next;
		}
		
		CardNode secondToLast = deckRear;
		for (secondToLast = deckRear; secondToLast.next != deckRear; secondToLast = secondToLast.next);
		
		deckRear.next = endSplit.next;
		endSplit.next = deckRear;
		secondToLast.next = startSplit;
		
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		while (true) {
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			
			int n = deckRear.next.cardValue;
			n = Math.min(n, 27);
			CardNode keyCard = deckRear;
			for (int i = 0; i < n + 1; i++) {
				keyCard = keyCard.next;
			}
			if (keyCard.cardValue != 27 && keyCard.cardValue != 28) {
				return keyCard.cardValue;
			}
			else {
				continue;
			}
			
		}
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
	    char[] encrypted = new char[message.length()];
	    int lastFilledIndex = 0;
		
	    for (int i = 0; i < message.length(); i++) {
	    	int ascii = (int) message.charAt(i);
	    	
	    	if (ascii >= 'A' && ascii <='Z') {
	    		int key = getKey();
	    		
	    		int charPosition = ascii - 'A' + 1;
	    		int finalPos;
	    		if (charPosition + key > 26) {
	    			finalPos = charPosition + key - 26;
	    		}
	    		else {
	    			finalPos = charPosition + key;
	    		}
	    		encrypted [lastFilledIndex] = (char) (finalPos + 'A' - 1);
	    		lastFilledIndex++;
	    	}
	    }
		String answer = new String(encrypted);
		answer.trim();
		
		return answer;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		char[] decrypted = new char[message.length()];
	    int lastFilledIndex = 0;
		
	    for (int i = 0; i < message.length(); i++) {
	    	int ascii = (int) message.charAt(i);
	    	
	    	
    		int key = getKey();
    		
    		int charPosition = ascii - 'A' + 1;
    		int finalPos;
    		if (charPosition <= key) {
    			finalPos = charPosition - key + 26;
    		}
    		else {
    			finalPos = charPosition - key;
    		}
    		decrypted [lastFilledIndex] = (char) (finalPos + 'A' -1);
    		lastFilledIndex++;
	    	
	    }
		String answer = new String(decrypted);
		answer.trim();
		
		return answer;
	}
}
