
package roboRallyPackage.pathFindingClasses;

import java.util.Iterator;
import java.util.NoSuchElementException;
import be.kuleuven.cs.som.annotate.*;

/**
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class IndexMinPQ<Key extends Comparable<Key>> implements Iterable<Integer>
{
    /**
     * Variable representing the number of elements in this Priority Queue.
     */
	private int N;

	/**
	 * Variable representing an array that is heap-sorted on a key.
	 */
    private int[] pq;        // binary heap using 1-based indexing
    
    /**
     * Variable representing an array that is the inverse of pq[], that is: qp[pq[i]] = pq[qp[i]] = i.
     * When you fill in a value of pq, say k, qp[k] will give the index of that value in pq, say i; so pq[i] = k
     */
    private int[] qp;
    
    /**
     * Variable representing an array that stores the key-values of the elements.
     * That is, the value that determines the order of one element, relative to the other elements.
     * 
     * keys[i] = priority of i
     */
    private Key[] keys;
    
	/**
	 * Initializes an IndexMin priority queue with a given maximum number of elements.
	 * All the values of qp[] are set to -1.
	 * 
	 * @param	NMAX
	 * 			The maximum number of elements in this PQ
	 * @post	...
	 * 			| keys == (Key[]) new Comparable[NMAX + 1]
	 * @post	...
	 * 			| pq   == new int[NMAX + 1]
	 * @post	...
	 * 			| qp   == new int[NMAX + 1]
	 * @post	...
	 * 			| for each i in 0..(NMAX + 1): qp[i] = -1
	 */
    public IndexMinPQ(int NMAX)
	{
		keys = (Key[]) new Comparable[NMAX + 1];    // make this of length NMAX??
        pq   = new int[NMAX + 1];
        qp   = new int[NMAX + 1];                   // make this of length NMAX??
        for (int i = 0; i <= NMAX; i++) qp[i] = -1;
	}
	
	/**
	 * Checks whether this priority queue is empty.
	 * 
	 * @return	...
	 * 			| result == (size() == 0)
	 */
	public boolean isEmpty()
	{
		return N == 0;
	}
	
	/**
	 * Checks whether k is an index/a value on the priority queue.
	 * 
	 * @param	k
	 * 			The index/value to be checked.
	 * @return	...
	 * 			| result == (qp[k] != -1)
	 */
	public boolean contains(int k)
	{
		return qp[k] != -1;
	}
	
	/**
	 * Number of keys in the priority queue.
	 */
	@Basic
	public int size()
	{
		return N;
	}
	
	/**
	 * Add a new element k with a given key to the PQ.
	 * 
	 * @param	k
	 * 			The element to be added to the PQ.
	 * @param	key
	 * 			The key that belongs to the given k.
	 * @post	...
	 * 			(new this).size() == this.size()++
	 * @post	...
	 * 			| (new this).contains(k)
	 * @throw	NoSuchElementException
	 * 			...
	 * 			| this.contains(k)
	 */
	public void insert(int k, Key key) throws NoSuchElementException
	{
		if (this.contains(k))
		{
			throw new NoSuchElementException("item is already in pq");
		}
		
		N++;
		qp[k] = N;
		pq[N] = k;
		keys[k] = key;
		swim(N);
	}
	
	/**
	 * Returns the index associated with a minimal key.
	 * 
	 * @return	...
	 * 			| result == pq[1]
	 * @throws	NoSuchElementException
	 * 			...
	 * 			| size() == 0
	 */
	public int minIndex() throws NoSuchElementException
	{
		if (N == 0)
		{
			throw new NoSuchElementException("Priority queue underflow");
		}
		return pq[1]; 
	}
	
	/**
	 * Returns a minimal key.
	 * 
	 * @return	...
	 * 			| result == keys(minIndex())
	 * @throws	NoSuchElementException
	 * 			...
	 * 			| size() == 0
	 */
	public Key minKey() throws NoSuchElementException
	{
		if (N == 0)
		{
			throw new NoSuchElementException("Priority queue underflow");
		}
		return keys[pq[1]];    
	}
	
	/**
	 * Deletes a minimal key and returns its associated index.
	 * 
	 * @return	...
	 * 			| this.pq[1]
	 * @post	...
	 * 			| 
	 * @throws	NoSuchElementException
	 * 			...
	 * 			| size() == 0
	 */
	public int delMin() throws NoSuchElementException
	{
		if (N == 0)
		{
			throw new NoSuchElementException("Priority queue underflow");
		}
		
        int min = pq[1];
        // the first and the last element of pq[] are swapped (N-- because an array starts to count from 0?)
        exch(1, N--);
        sink(1);
        qp[min] = -1;            // delete
        keys[pq[N+1]] = null;    // to help with garbage collection
        pq[N+1] = -1;            // not needed
        return min; 
	}
	
    /**
     * Returns the key associated with index k.
     * 
     * @param	k
     * 			The value of pq[] from which the key will be returned.
     * @return	...
     * 			| keys[k]
     * @throws	NoSuchElementException
     * 			...
     * 			| !this.contains(k)
     */
    public Key keyOf(int k) throws NoSuchElementException
    {
    	if (!contains(k))
    	{
    		throw new NoSuchElementException("item is not in pq");
    	}
    	else return keys[k];
    }

//    /**
//     * Changes the key associated with the given index k.
//     */
//    public void change(int k, Key key)
//    {
//        changeKey(k, key);
//    }

    /**
     * Changes the key associated with the given index k to the given key.
     * 
     * @param	k
     * 			The index for which the key value will be changed.
     * @param	key
     * 			The new value for the key of the given index k.
     * @post	...
     * 			| (new this)
     * @throws	NoSuchElementException
     * 			...
     * 			| !this.contains(k)
     */
    // this method was originally named changeKey.
    public void change(int k, Key key) throws NoSuchElementException
    {
    	if (!contains(k))
    	{
    		throw new NoSuchElementException("item is not in pq");
    	}
    	keys[k] = key;
    	swim(qp[k]);
    	sink(qp[k]);
    }

    /**
     * Decreases the key associated with the given index k.
     * 
     * @param	k
     * 			The index for which the key value will be decreased.
     * @param	key
     * 			The new value for the key of the given index k.
     * @effect	...
     * 			| if(keys[k].compareTo(key) > 0) then change(k, key)
     * @throws	NoSuchElementException
     * 			...
     * 			| !this.contains(k)
     * @throws	RuntimeException
     * 			...
     * 			| keys[k].compareTo(key) <= 0
     */
    public void decreaseKey(int k, Key key) throws NoSuchElementException, RuntimeException
    {
    	if (!contains(k))
    	{
    		throw new NoSuchElementException("item is not in pq");
    	}
    	if (keys[k].compareTo(key) <= 0)
    	{
    		throw new RuntimeException("illegal decrease");
    	}
        keys[k] = key;
        swim(qp[k]);
    }

    /**
     * Increases the key associated with the given index k.
     * 
     * @param 	k
     * 			The index for which the key value will be increased.
     * @param	key
     * 			The new value for the key of the given index k.
     * @effect	...
     * 			| if(keys[k].compareTo(key) < 0) then change(k, key)
     * @throws	NoSuchElementException
     * 			...
     * 			| !this.contains(k)
     * @throws	RuntimeException
     * 			...
     * 			| keys[k].compareTo(key) >= 0
     */
    public void increaseKey(int k, Key key) throws NoSuchElementException, RuntimeException
    {
    	if (!contains(k))
    	{
    		throw new NoSuchElementException("item is not in pq");
    	}
    	if (keys[k].compareTo(key) >= 0)
    	{
    		throw new RuntimeException("illegal decrease");
    	}
    	keys[k] = key;
    	sink(qp[k]);
    }

    /**
     * Deletes the key associated with the given index k.
     * 
     * @param	k
     * 			| The index for which the key is to be deleted.
     * @post	...
     * 			| !(new this).contains(k)
     * @throws	NoSuchElementException
     * 			...
     * 			| !this.contains(k)
     */
    public void delete(int k) 
    {
    	if (!contains(k))
    	{
    		throw new NoSuchElementException("item is not in pq");
    	}
        int index = qp[k];
        exch(index, N--);
        swim(index);
        sink(index);
        keys[k] = null;
        qp[k] = -1;
    }
    
    /**
     * True if the key of the element of index i in pq[] is greater than the key of the element of index j in pq[] (not equal)
     * 
     * @param	i
     * 			The key of the element of this index in pq[] is to be compared with the key of the element of the other given index in pq[].
     * @param	j
     * 			The key of the element of this index in pq[] is to be compared with the key of the element of the other given index in pq[].
     * @return	...
     * 			| result == keys[pq[i]].compareTo(keys[pq[j]]) > 0
     */
    private boolean greater(int i, int j)
    {
        // compareTo: returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
    	return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
    }

    /**
     * Exchanges the integers saved in the array pq[] on indices i and j.
     * Afterwards, the swap is also executed on the array qp[], which is the inverse of pq[].
     * 
     * @param	i
     * 			An index representing one of the two elements to be swapped.
     * @param	j
     * 			An index representing one of the two elements to be swapped.
     * @post	...
     * 			| (new this).pq[i] == this.pq[j]
     * 			|  && (new this).pq[j] == this.pq[i]
     * @post	...
     * 			| (new this).qp[pq[i]] == i
     * 			|  && (new this).qp[pq[j]] == j
     */
    private void exch(int i, int j)
    {
        int swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
        
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }
    
    /**
     * To restore the heap condition when an item's key (priority) is increased, the item moves up the heap,
     * exchanging its position k with its parent's (at position k/2) if necessary,
     * continuing as long as the item at position k/2 is less than the node at position k or until it reaches the top of the heap.
     * The methods greater and exch compare and exchange (respectively) the items at the heap indices specified by their parameters.
     * 
     * @param	k
     * 			The index of the element that has to swim.
     * @post	...
     * 			| let m be (new this).qp[this.pq[k]] in
     * 			|  !greater(m/2, m) || m == 1
     */
    private void swim(int k)
    {
        while (k > 1 && greater(k/2, k))
        {
            exch(k, k/2);
            k = k/2;
        }
    }

    /**
     * To restore the heap condition when an item's key (priority) is decreased, the item moves down the heap,
     * exchanging its position k with the position of its child node with the smallest key if necessary,
     * continuing as long as the item at position 2*k or 2*k+1 is greater than the node at position k or until it reaches the bottom of the heap.
     * The methods greater and exch compare and exchange (respectively) the items at the heap indices specified by their parameters.
     * 
     * @param	k
     * 			The index of the element that has to sink.
     * @post	...
     * 			| let m be (new this).qp[this.pq[k]] in
     * 			|  greater(2*m, m) || greater(2*m+1,m) || 2*m <= size()
     */
    private void sink(int k)
    {
        while (2*k <= N)
        {
        	int j = 2*k;
        	if (j < N && greater(j, j+1))
        	{
        		j++;
        	}
        	if (!greater(k, j))
        	{
        		break;
        	}
        	exch(k, j);
        	k = j;
        }
    }
    
	
	/**
	 * Return an iterator that iterates over all of the elements on the priority queue in ascending order.
     * The iterator doesn't implement remove() since it's optional.
     * 
     * @return	...
     * 			| result == new HeapIterator()
	 */
    public Iterator<Integer> iterator()
    {
    	return new HeapIterator(); 
    }

    /**
     * Iterator, doesn't implement remove() since it's optional
     * 
     * @author Jonas
     *
     */
    private class HeapIterator implements Iterator<Integer> 
    {
        /**
         * Variable representing a PQ.
         */
    	private IndexMinPQ<Key> copy;

        /**
         * Initializes this new HeapIterator.
         * Adds all elements to copy of heap.
         * Takes linear time since already in heap order so no keys move.
         * 
         * @effect	...
         * 			| for each i in 1..size(): copy.insert(pq[i], keys[pq[i]])
         * @post	...
         * 			| copy.lenght == pq.length
         */
    	public HeapIterator()
        {
            copy = new IndexMinPQ<Key>(pq.length - 1);
            for (int i = 1; i <= N; i++)
            {
            	copy.insert(pq[i], keys[pq[i]]);
            } 
        }

        /**
		 * Returns true if the iteration has more elements.
		 */
		@Basic
        public boolean hasNext() 
		{
			return !copy.isEmpty();             
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
		 * 			| result == copy.delMin()
		 * @throws	java.util.NoSuchElementException
		 * 			...
		 * 			| ! this.hasNext()
		 */
		public Integer next() throws UnsupportedOperationException
		{
			if (!hasNext()) 
			{
				throw new NoSuchElementException();
			}
			return copy.delMin();
		}
    }
}

