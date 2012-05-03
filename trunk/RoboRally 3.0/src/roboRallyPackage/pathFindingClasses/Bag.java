
package roboRallyPackage.pathFindingClasses;

import java.util.Iterator;
import be.kuleuven.cs.som.annotate.*;

/**
 * The <tt>Bag</tt> class represents a bag (or multiset) of 
 * generic items. It supports insertion and iterating over the 
 * items in arbitrary order.
 * <p>
 * The <em>add</em>, <em>isEmpty</em>, and <em>size</em>  operation 
 * take constant time. Iteration takes time proportional to the number of items.
 * <p>
 * 
 * This generic representation of a bag maintains the bag as a linked list, with the top of the bag at the beginning, referenced by an instance variable first.
 * To add() an item, we add it to the beginning of the list. It is not possible to remove an item from a bag.
 * 
 * For additional documentation, see <a href="http://algs4.cs.princeton.edu/13stacks">Section 1.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @invar	...
 * 			| check()
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Bag<Item> implements java.lang.Iterable<Item>
{
	
	/**
	 * Create an empty stack.
	 * 
	 * @post	...
	 * 			| 
	 * @post	...
	 * 			| this.size() == 0
	 * @post	...
	 * 			| 
	 */
	public Bag()
	{
		first = null;
		N = 0;
		assert check();
	}

	/**
	 * Add the item to the bag.
	 * 
	 * @param	item
	 * 			The item to be added
	 */
	public void add(Item item)
	{
		Node oldfirst = first;
	    first = new Node();
	    first.item = item;
	    first.next = oldfirst;
	    N++;
	    assert check();
	}

	/**
	 * Variable representing the first node ('the beginning') of this bag.
	 */
    private Node first;

    /**
     * Returns the variable representing the first node ('the beginning') of this bag.
     */
    @Basic
    public Node getFirst()
    {
    	return first;
    }

    /**
     * Checks whether this bag is empty.
     * 
     * @return	...
     * 			| result == (this.getFirst() == null)
     */
	public boolean isEmpty()
	{
		return this.getFirst() == null;
	}
	
	
	/**
	 * Returns the number of items in the bag.
	 */
	@Basic
	public int size()
	{
		return N;
	}
	
	/**
	 * Variable representing the number of elements in this bag.
	 */
	private int N;

	/**
	 * Checks the internal invariants.
	 * 
	 * @return	...
	 * 			| result == !((this.size() == 0 && this.getFirst() != null)
	 * 			|			  || (this.size() == 1 && (this.getFirst() == null || this.getFirst().getNext() != null))
	 * 			|			  || (this.getFirst().getNext() == null))
	 * @return	...
	 * 			| let numberOfNodes in
	 * 			|  for (Node x = this.getFirst(); x != null; x = x.getNext()): numberOfNodes++
	 * 			|  if(numberOfNodes != this.size()) then result == false
	 */
	@Model
	private boolean check()
	{
		if (this.size() == 0 && this.getFirst() != null)
		{
			return false;
		}
		else if (this.size() == 1 && (this.getFirst() == null || this.getFirst().getNext() != null) ) 
		{
			return false;
		}
		else
		{
			if (this.getFirst().getNext() == null) 
			{
				return false;
			}
		}

		// check internal consistency of instance variable N
		int numberOfNodes = 0;
		for (Node x = this.getFirst(); x != null; x = x.getNext())
		{
			numberOfNodes++;
		}
		if (numberOfNodes != this.size()) 
		{
			return false;
		}

		return true;
	} 
	
	
	/**
	 * Return an iterator that iterates over the items in the bag. 
	 * 
	 * @return  ...
	 * 			| result == new ListIterator()
	 */
	public java.util.Iterator<Item> iterator()
	{
		return new ListIterator();  
	}
	
	/**
	* Linked list class.
	* 
	* @author Jonas
	*
	*/
	private class Node
	{
	    /**
	     * Returns the variable representing the item of this node.
	     */
		@Basic
		public Item getItem()
	    {
			return item;
		}
		
		/**
		 * Variable representing the item of this node.
		 */
		private Item item;
		
		
		/**
		 * Returns the variable representing the next node.
		 */
		@Basic
		public Node getNext()
		{
			return next;
		}
		
		/**
		 * Variable representing the next node.
		 */
		private Node next;
	}

	/**
	 * Iterator, doesn't implement remove() since it's optional
	 * 
	 * @author Jonas
	 */
	private class ListIterator implements Iterator<Item> 
	{
		/**
		 * Variable representing the current node of this ListIterator.
		 */
		private Node current = getFirst();

		/**
		 * Returns true if the iteration has more elements.
		 */
		@Basic
		public boolean hasNext()  
		{
			return current != null;                    
		}
		
		/**
		 * This method is not supported here.
		 * 
		 * @throws	UnsupportedOperationExceptio,
		 * 			...
		 * 			| true
		 */
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();  
		}

		/**
		 * Returns the next element in the iteration.
		 * 
		 * @return	...
		 * 			| result == current.getItem()
		 * @throws	java.util.NoSuchElementException
		 * 			...
		 * 			| ! this.hasNext()
		 */
		public Item next() throws java.util.NoSuchElementException
		{
			if (! this.hasNext())
			{
				throw new java.util.NoSuchElementException();
			}
			Item item = current.getItem();
			current = current.getNext(); 
			return item;
		}
	}
}
