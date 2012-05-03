/**
 * 
 */
package roboRallyPackage.pathFindingClasses;

import be.kuleuven.cs.som.annotate.*;

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * The <tt>Stack</tt> class represents a last-in-first-out (LIFO) stack of generic items.
 * It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 * for peeking at the top item, testing if the stack is empty, and iterating through
 * the items in LIFO order.
 * <p>
 * All stack operations except iteration are constant time.
 * <p>
 *  
 * It maintains the stack as a linked list, with the top of the stack at the beginning, referenced by an instance variable first.
 * To push() an item, we add it to the beginning of the list; to pop() an item, we remove it from the beginning of the list.
 *  
 * For additional documentation, see <a href="/algs4/13stacks">Section 1.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Stack<Item> implements Iterable<Item> {
   
	/**
	 * Variable representing the size of the stack.
	 */
	private int N;
	
	/**
	 * Variable representing the top of the stack (the item most recently added to the stack).
	 */
    private Node first;
    
    
    /**
     * Linked list class
     */
    private class Node {
    	/**
    	 * Variable representing the item of this node.
    	 */
    	
        private Item item;
        /**
         * Variable representing the next node relative to this node.
         */
        private Node next;
    }

   /**
     * Create an empty stack.
     * 
     * @post	...
     * 			| this.size() == 0
     */
    public Stack() {
        first = null;
        N = 0;
        assert check();
    }

   /**
     * Is the stack empty?
     */
    public boolean isEmpty() {
        return first == null;
    }

   /**
     * Return the number of items in the stack.
     */
    @Basic
    public int size() {
        return N;
    }

   /**
     * Add the item to the stack.
     */
    public void push(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        N++;
        assert check();
    }

   /**
     * Delete and return the item most recently added to the stack.
     * Throw an exception if no such item exists because the stack is empty.
     */
    public Item pop() {
        if (isEmpty()) throw new RuntimeException("Stack underflow");
        Item item = first.item;        // save item to return
        first = first.next;            // delete first node
        N--;
        assert check();
        return item;                   // return the saved item
    }


   /**
     * Return the item most recently added to the stack.
     * Throw an exception if no such item exists because the stack is empty.
     */
    public Item peek() {
        if (isEmpty()) throw new RuntimeException("Stack underflow");
        return first.item;
    }

   /**
     * Return string representation.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item + " ");
        return s.toString();
    }
       

    // check internal invariants
    private boolean check() {
        if (N == 0) {
            if (first != null) return false;
        }
        else if (N == 1) {
            if (first == null)      return false;
            if (first.next != null) return false;
        }
        else {
            if (first.next == null) return false;
        }

        // check internal consistency of instance variable N
        int numberOfNodes = 0;
        for (Node x = first; x != null; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != N) return false;

        return true;
    } 


   /**
     * Return an iterator to the stack that iterates through the items in LIFO order.
     */
    public Iterator<Item> iterator()  { return new ListIterator();  }

    // an iterator, doesn't implement remove() since it's optional
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }


//   /**
//     * A test client.
//     */
//    public static void main(String[] args) {
//        Stack<String> s = new Stack<String>();
//        while (!StdIn.isEmpty()) {
//            String item = StdIn.readString();
//            if (!item.equals("-")) s.push(item);
//            else if (!s.isEmpty()) StdOut.print(s.pop() + " ");
//        }
//        StdOut.println("(" + s.size() + " left on stack)");
//    }
}
