
package roboRallyPackage;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import roboRallyPackage.commandClasses.Condition;
import roboRallyPackage.commandClasses.Program;
import roboRallyPackage.exceptionClasses.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.pathFindingClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class representing a game board.
 * 
 * @invar	A Board must have a valid width.
 * 			| this.canHaveAsWidth(this.getWidth())
 * @invar	A Board must have a valid height.
 * 			| this.canHaveAsHeight(this.getHeight())
 * @invar	An element that is placed on a Board must have a valid position.
 * 			| for each element in this.getElements(Element.class): this.isValidPositionOnBoard(element.getPosition())
 * @invar	An element that is placed on a Board must have that Board as attribute.
 * 			| for each element in this.getElements(Element.class): element.getBoard() == this
 * @invar	An element that is placed on a Board may not be terminated
 * 			| for no element in this.getElements(Element.class): element.isTerminated()
 * @invar	A terminated Board is not allowed to contain elements or to change its state.
 * 			| if(this.isTerminates()) then this.getElements(Elements.class) == null
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Board extends Terminatable
{
	/**
	 * Constructs a Board with the given width and height.
	 * If these given width and height are not valid values, the width and the height will both be set to 10.
	 * 
	 * @param	width
	 * 			The width for this new Board.
	 * @param	height
	 * 			The height for this new Board.
	 * @post	...
	 * 			| if (canHaveAsWidth(width))
	 *          |  then (new this).getWidth() == width
	 *          | else (new this).getWidth() == 10
	 * @post	...
	 * 			| if (canHaveAsWidth(width))
	 *          |  then (new this).getHeight() == height
	 *          | else (new this).getHeight() == 10
	 */
	public Board(long width, long height)
	{
		// the given width is a valid width
		if (canHaveAsWidth(width))
		{
			this.width = width;
		}
		else
		{
			this.width = 10;
		}

		// the given height is a valid height
		if (canHaveAsHeight(height))
		{
			this.height = height;
		}
		else
		{
			this.height = 10;
		}
	}


	/**
	 * Terminates this board. All the elements placed on this board are terminated along with it.
	 * 
	 * @post	...
	 * 			| for each element in this.getElements(Element.class): element.terminate()
	 * @post	...
	 * 			| super.terminate()
	 */
	@Override 
	public void terminate()
	{
		for(Element element: this.getElements(Element.class))
		{
			element.terminate();
		}
		super.terminate();
	}


	/**
	 * Returns the width of this Board.
	 */
	@Basic @Immutable
	public long getWidth()
	{
		return width;
	}

	/**
	 * Checks whether the given width is a valid width.
	 * 
	 * @param	width
	 * 			The width to be checked.
	 * @return	...
	 * 			| result == width >= 0
	 * @return	...
	 * 			| result == width <= getMaxWidth()
	 */
	public boolean canHaveAsWidth(long width)
	{
		return (width > 0) && (width <= getMaxWidth());
	}
	
	/**
	 * Variable representing the width of this board.
	 */
	private final long width;

	
	/**
	 * Returns the maximum width of a Board.
	 */
	@Basic @Immutable
	public static long getMaxWidth()
	{
		return maxWidth;
	}
	
	/**
	 * Variable representing the maximum width of a Board.
	 */
	private final static long maxWidth = Long.MAX_VALUE;

	/**
	 * Returns the height of this Board.
	 */
	@Basic @Immutable
	public long getHeight()
	{
		return height;
	}

	/**
	 * Checks whether the given height is a valid height.
	 * 
	 * @param	height
	 * 			The height to be checked.
	 * @return	...
	 * 			| result == height >= 0
	 * @return	...
	 * 			| result == height <= getMaxHeight()
	 */
	public boolean canHaveAsHeight(long height)
	{
		return (height > 0) && (height <= getMaxHeight());
	}
	
	/**
	 * Variable representing the height of this board.
	 */
	private final long height;

	
	/**
	 * Returns the maximum height of a Board.
	 */
	@Basic @Immutable
	public static long getMaxHeight()
	{
		return maxHeight;
	}
	
	/**
	 * Variable representing the maximum height of a Board.
	 */
	private final static long maxHeight = Long.MAX_VALUE;

	
	/**
	 * Returns whether the given position is a valid position for this board.
	 * 
	 * @param	position
	 * 			The position to be checked
	 * @return	...
	 * 			| result == (position != null)
	 * @return	...
	 * 			| result == (Position.isValidPosition(position)
	 * @return	...
	 * 			| result == (this.canHaveAsWidth(position.getCoordX()) || position.getCoordX() == 0)
	 * @return	...
	 * 			| result == (this.canHaveAsHeight(position.getCoordY()) || position.getCoordY() == 0)
	 */
	public boolean isValidPositionOnBoard(Position position)
	{
		try
		{
			return Position.isValidPosition(position) && (this.canHaveAsWidth(position.getCoordX()) || position.getCoordX() == 0)
													  && (this.canHaveAsHeight(position.getCoordY()) || position.getCoordY() == 0);
		}
		catch(NullPointerException exc)
		{
			return false;
		}
	}
	

	/**
	 * Returns the variable representing the set of elements placed on this Board.
	 */
	@Basic
	public java.util.Map<Position, java.util.Set<Element>> getElements()
	{
		return elements;
	}
	
//	/**
//	 * Returns the variable representing the set of elements placed on this Board.
//	 */
//	@Basic
//	public java.util.Set<Element> getElements(Condition condition)
//	{
//		java.util.Set<Element> resultSet = new java.util.HashSet<Element>();
//		
//		for(Element element: this.getElements(Element.class))
//		{
//			try
//			{
//				java.lang.reflect.Method myMethod = condition.getClass().getMethod("results");
//				if(myMethod.invoke(condition, element))
//				{
//					resultSet.add(element);
//				}
//			}
//			catch(java.lang.reflect.InvocationTargetException exc)
//			{
//			}
//		}
//		
//		return resultSet;
//	}

	/**
	 * Returns a set of all the elements on this board of a given type.
	 * 
	 * @param	type
	 * 			The sort of elements to retrieve from the list of elements of this board.
	 * @return	...
	 * 			| for each element in result: type.isAssignableFrom(Element.class)
	 * @return	...
	 * 			| for each position in other.getElements().keySet(): 
	 *			|   for no element in other.getElements(position): (! result.contains(element) && type.isAssignableFrom(element.getClass()))
	 */
	public <T extends Element> java.util.Set<T> getElements(java.lang.Class<T> type)
	{
		java.util.Set<T> searchedElements = new java.util.HashSet<T>();
		
		for(java.util.Set<Element> setE: getElements().values())
		{
			for(Element e: setE)
			{
				if(type.isAssignableFrom(e.getClass()))
				{
					searchedElements.add((T) e);
				}
			}
		}
		return searchedElements;
	}

	/**
	 * Returns the elements on a certain position.
	 */
	@Basic
	public java.util.Set<Element> getElements(Position position)
	{
		if(! this.getElements().containsKey(position))
		{
			return null;
		}
		return getElements().get(position);
	}
	
	
	/**
	 * Returns an iterator that can iterate over all the element on the board that satisfy the given condition.
	 * 
	 * @param	condition
	 * 			The condition to be satisfied by the elements, represented by a string.
	 * @return	...
	 * 			| if(Parser.parse(0, null, condition) instanceof Condition)
	 * 			|   then result == this.getElementsThatSatisfyCondition((Condition) program)
	 * 			| else result == null
	 */
	public Iterator<Element> getElementsThatSatisfyCondition(String condition)
	{
		Program program = null;
		
		try
		{
			program = Parser.parse(0, null, condition);
		}
		catch(IllegalSyntaxException exc)
		{
			return null;
		}
		
		if(program instanceof Condition)
		{
			return this.getElementsThatSatisfyCondition((Condition) program);
		}
		return null;
	}
	
	/**
	 * Returns an iterator that can iterate over all the element on the board that satisfy the given condition.
	 * 
	 * @param	condition
	 * 			The condition to be satisfied by the elements
	 * @return	...
	 * 			| if(result.hasNext()) then condition.resulst(result.next())
	 */
	public Iterator<Element> getElementsThatSatisfyCondition(Condition condition)
	{
		final Condition conditionFinal = condition;
		
		return new Iterator<Element>()
		{
			/**
			 * Variable that represents the collection of all the elements on the board that satisfy the given condition
			 */
			private java.util.Collection<Element> elementsThatSatisfyCondition = this.getElementsThatSatisfyCondition();
			
			/**
			 * Variable that represents the iterator over all the elements on the board that satisfy the given condition
			 */
			private Iterator<Element> iterator = elementsThatSatisfyCondition.iterator();
			
			/**
			 * Returns a collection of all the element on the given board that satisfy the given condition
			 * 
			 * @param	board
			 * 			The board who's arguments may be added to the returned list
			 * @return	All element on the given board that satisfy the given condition
			 * 			| for each element in board.getElements(Element.class):
			 * 			|   result.contains(condition
			 * 			| 	  || ! condition.results(element)
			 */
			public java.util.Collection<Element> getElementsThatSatisfyCondition()
			{
				java.util.Collection<Element> elementsSatisfyCondition = new java.util.ArrayList<Element>();
				
				// iterate over all the elements on the board
				for(Element element: Board.this.getElements(Element.class))
				{
					try
					{
						// if the current element satisfies the given condition, the element is added to the collection
						// if that condition cannot be checked for that sort of element (e.g. a wall and energy-at-least)
						// an IllegalArgumentException is thrown by the condition and that element is not added to the collection
						if(conditionFinal.results(element))
						{
							elementsSatisfyCondition.add(element);
						}
					}
					catch(IllegalArgumentException exc)
					{
					}
				}
				
				return elementsSatisfyCondition;
			}

			/**
			 * Returns true if the iteration has more elements. (In other words, returns true if next would return an element rather than throwing an exception.)
			 */
			@Override
			public boolean hasNext()
			{
				if(iterator.hasNext())
				{
					return true;
				}
				return false;
			}

			/**
			 * Returns the next element in the iteration.
			 */
			@Override
			public Element next() throws java.util.NoSuchElementException
			{
				if(this.hasNext())
				{
					return iterator.next();
				}
				else
				{
					throw new java.util.NoSuchElementException();
				}
			}

			/**
			 * This method is ntt supported by this iterator.
			 */
			@Override
			public void remove() throws UnsupportedOperationException
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	/**
	 * Returns an element placed on this board on the first position one encounters 
	 * while moving forward in a straight line starting from the given position with the given orientation.
	 * When multiple elements are placed on the first encountered position, only one of these elements is returned.
	 * The choice of which element to be returned happens semi-randomly.
	 * When no elements are found when 'you have reached the end of this Board', this method will return 'null'.
	 * 
	 * @param	position
	 * 			The position to start from
	 * @param	orientation
	 * 			The direction where the other position may lie, respectively to the current position (up, down,...).
	 * @return	...
	 * 			| for each i in 1..Long.MAX_VALUE:
	 * 			|	if(! (this.getElements(position.getNeighbourAt(orientation, i)) == null))
	 * 			|     then for one element in this.getElements(position.getNeighbourAt(orientation, i)) | result == element
	 * @return	Returns null when no element is found.
	 * 			| for each i in 1..Long.MAX_VALUE:
	 * 			|	if((this.getElements(position.getNeighbourAt(orientation, i)) == null))
	 * 			|     then result == null
	 * @throws	NullPointerException
	 * 			...
	 * 			| position == null || orientation == null
	 */
	public Element getFirstElementStartingAt(Position position, Orientation orientation) throws NullPointerException
	{
		// local variable representing the element that will be shot
		Element firstElement = null;
		//boolean to stop the while-loop
		boolean stop = false;
		// integer indicating how far away the first position containing elements is from the position on which the shooting robot is placed 
		int i = 1;
		
		while(!stop)
		{
			try
			{
				// Checks if one or more elements are placed at the position neighbouring the position of the shooting robot at a straight relative distance of i
				if(this.getElements(position.getNeighbourAt(orientation, i)) != null)
				{
					// Local variable representing the set containing all elements at the checked position
					java.util.Set<Element> elementsOnPosition = this.getElements(position.getNeighbourAt(orientation, i));
					// The number of elements on the checked position (needed for the random number generator)
					int nbOfElements = elementsOnPosition.size();
					
					// Variable representing a new element of the class random
					Random generator = new Random();
					// Variable containing a random number between 0 and the number of elements on the checked position
					int randomInt = generator.nextInt(nbOfElements+1);
					// Auxiliary integer for picking a random element
					int j = 0;
					// Loop resulting in picking an element on the checked position as randomly as possible
					// The loop goes through all elements on the checked position.
					// If the auxiliary integer equals the random integer between 0 and the number of elements on the checked position,
					// the current in the loop is saved and the loop is forced to break.
					// If the the random integer isn't equal to the auxiliary integer (in which case it has to be bigger),
					// the auxiliary integer is augmented with one and the loop moves forward to the next element.
					for(Element element: elementsOnPosition)
					{
						if (j == randomInt)
						{
							firstElement = element;
							break;
						}
						j++;
					}	
					stop = true;
				}
				else
				{
					i++;
				}
			}
			catch (IllegalPositionException exc)
			{
				stop = true;
			}
		}
	
		return firstElement;
	}

	/**
	 * Removes a given element from this Board if it is placed on this board.
	 * The board of this element will be set null, the position of this element is not changed.
	 * 
	 * @param	element
	 * 			The element that will be removed from this board.
	 * @effect	...
	 * 			| element.setBoard(null)
	 * @post	...
	 * 			| ! this.getElements(Element).contains(element)
	 * @throws	NullpointerException
	 * 			...
	 * 			| element == null
	 * @throws	IllegalStateException
	 * 			...
	 * 			| element.getBoard() != this
	*/
	public void removeElement(Element element) throws NullPointerException,
													  IllegalStateException
	{
		if(this.getElements(Element.class).contains(element))
		{
			// removes the element from the list in board
			Set<Element> newSet = this.getElements(element.getPosition());
			newSet.remove(element);
			elements.put(element.getPosition(), newSet);
			
			element.setBoard(null);
		}
		else
		{
			throw new IllegalStateException();
		}
	}

	/**
	 * Places a given element on this board on a given position if this element can be placed there.
	 * 
	 * @param	position
	 * 			The position where this element has to be placed.
	 * @param	newElement
	 * 			The element that will be placed on this board.
	 * @effect	...
	 * 			| newElement.setPosition(position)
	 * @effect	...
	 * 			| if(canElementBePutAtPosition(position, newElement)) then newElement.setBoard(this)
	 * @post	...
	 * 			| if(canElementBePutAtPosition(position, newElement)) then (new this).getElements(Element).contains(newElement)
	 * @throws	NullPointerException
	 * 			...
	 * 			| newElement == null
	 * @throws	IllegalStateException
	 * 			...
	 * 			| newElement.isTerminated()
	 * @throws	IllegalPositionException
	 * 			...
	 * 			| ! this.canElementBePutAtPosition(position, newElement)
	 * @throws	IllegalBoardException
	 * 			...
	 * 			| ! this.canElementBePutAtPosition(position, newElement)
	 * @throws	IllegalElementCombinationException
	 * 			...
	 * 			| ! this.canElementBePutAtPosition(position, newElement)
	 */
	public void putElement(Position position, Element newElement) throws NullPointerException,
																		 IllegalStateException,
																		 IllegalPositionException,
																		 IllegalBoardException,
																		 IllegalElementCombinationException
	{
		if(newElement.isTerminated())
		{
			throw new IllegalStateException();
		}
		// this element can be placed here (otherwise an exception will be thrown by canElemenBePutAtPosition)
		if(this.canElementBePutAtPosition(position, newElement))
		{	
			// replace the old list of elements at this position with the new list of elements (containing this new element as well)
			elements = addElementToGivenMap(this.getElements(), newElement, position);
			
			// change the board and the position of the element
			newElement.setPosition(position);
			newElement.setBoard(this);			
		}
	}
	
	
	/**
	 * Moves a given element from one place on this board to another place on this board if possible.
	 * 
	 * @param	position
	 * 			The position where this element has to be placed.
	 * @param	element
	 * 			The element that has to be moved
	 * @effect	...
	 * 			| if(canElementBePutAtPosition(position, element)) then this.removeElement(element) && this.putElement(element)
	 * @throws	NullPointerException
	 * 			...
	 * 			| element == null
	 * @throws	IllegalStateException
	 * 			...
	 * 			| element.isTerminated()
	 * @throws	IllegalPositionException
	 * 			...
	 * 			| ! this.canElementBePutAtPosition(position, element)
	 * @throws	IllegalBoardException
	 * 			...
	 * 			| !this.getElements(Element.class).contains(element) || ! this.canElementBePutAtPosition(position, element)
	 * @throws	IllegalElementCombinationException
	 * 			...
	 * 			| ! this.canElementBePutAtPosition(position, element)
	 */
	public void moveElement(Position position, Element element) throws NullPointerException,
																		  IllegalStateException,
																		  IllegalPositionException,
																		  IllegalBoardException,
																		  IllegalElementCombinationException
	{
		if(element.isTerminated())
		{
			throw new IllegalStateException();
		}
		// the given element is not placed on this board
		if(!this.getElements(Element.class).contains(element))
		{
			throw new IllegalBoardException(element, this);
		}
		
		// this element can be placed here (otherwise an exception will be thrown by canElemenBePutAtPosition)
		if(this.canElementBePutAtPosition(position, element))
		{	
			this.removeElement(element);
			element.setPosition(position);
			element.setBoard(this);
		}
	}
	
	/** 
	 * Returns whether the given element can be placed on this board at the given position.
	 * 
	 * @effect	...
	 * 			| this.canElementBePutAtPosition(position, element, this.getElements())
	 */
	public boolean canElementBePutAtPosition(Position position, Element element) throws NullPointerException,
																						IllegalPositionException,
																						IllegalBoardException,
																						IllegalElementCombinationException		
	{
		return this.canElementBePutAtPosition(position, element, this.getElements());
	}
	
	/** 
	 * Returns whether the given element can be placed on this board at the given position with the given set of elements.
	 * 
	 * @param	position
	 * 			The position where the new element may have to be placed.
	 * @param	element
	 * 			The element that may have to be placed on this board.
	 * @param	elements
	 * 			The set of elements that is already placed on this board
	 * @return	...
	 * 			| result == isValidPositionOnBoard(position)
	 * @return	...
	 * 			| result == (element.getBoard() == null)
	 * @return	...
	 * 			| if for each oldElement in elements.get(position): oldElement.canSharePositionWith(element) then result == true
	 * @throws	NullPointerException
	 * 			...
	 * 			| element == null || elements == null
	 * @throws	IllegalPositionException
	 * 			...
	 * 			| ! isValidPositionOnBoard(position)
	 * @throws	IllegalBoardException
	 * 			...
	 * 			| element.getBoard() != null && element.getBoard() != this
	 * @throws	IllegalElementCombinationException
	 * 			...
	 * 			| for some oldElement in this.getElements(position): 
	 * 			|    ! oldElement.canSharePositionWith(element)
	 */
	public boolean canElementBePutAtPosition(Position position, Element element, java.util.Map<Position, java.util.Set<Element>> elements) throws NullPointerException,
																																				  IllegalPositionException,
																																				  IllegalBoardException,
																																				  IllegalElementCombinationException		
	{
		// the given position is not  a valid position.
		if(!isValidPositionOnBoard(position))
		{
			throw new IllegalPositionException(position.getCoordX(), position.getCoordY(), this);
		}
		
		else
		{
			// this element is placed on another board; an element can be located on at most one board.
			if(element.getBoard() != null && element.getBoard() != this)
			{
				throw new IllegalBoardException(element, this);
			}
			// the given position of this board is free; the element can be put there.
			if(elements.get(position) == null)
			{
				return true;
			}
			else
			{
				// checks whether this new element does not conflict with another element at this position
				// e.g. a robot can not be placed at a position on this board that already contains a wall.
				for(Element oldElement: elements.get(position))
				{
					// this element conflicts with another element on this position of this board.
					if(! oldElement.canSharePositionWith(element))
					{
						throw new IllegalElementCombinationException(element,oldElement);
					}
				}
				return true;
			}
		}
	}
	

	/**
	 * Variable representing the set of elements placed on this Board.
	 */
	private java.util.Map<Position, java.util.Set<Element>> elements = new java.util.HashMap<Position, java.util.Set<Element>>();
	
	
	
	/**
	 * Adds the given element to the given list at the given position and returns the altered list.
	 * 
	 * @param	elements
	 * 			The list that has to be updated.
	 * @param	newElement
	 * 			The element to be added.
	 * @param	position
	 * 			The position where the given element had to be put in the given list.
	 * @return	...
	 * 			| result.get(position).contains(newElement)
	 */
	@Model
	private java.util.Map<Position, java.util.Set<Element>> addElementToGivenMap(java.util.Map<Position, java.util.Set<Element>> elements, Element newElement, Position position)
	{
		// create a new List and fill it up with the existing elements at this position
		java.util.Set<Element> elementsAtPosition;
		
		
		if(elements.get(position) == null)
		{
			elementsAtPosition = new java.util.HashSet<Element>();
		}
		else
		{
			elementsAtPosition = new java.util.HashSet<Element>(elements.get(position));
		}
		// add the new element to the new list
		elementsAtPosition.add(newElement);
		// replace the old list of elements at this position with the new list of elements (containing this new element as well)
		elements.put(position, elementsAtPosition);
		
		return elements;
	}

	
	/**
	 * Returns a map of positions and a set of elements. This map represents all the elements of the given board
	 * that can be placed, without conflicts, on this board on the position of the key-value to which they belong to in the map.
	 * An element is put in the set that belongs to its own position if possible.
	 * The elements of the given board that cannot be placed on the board at their own position
	 * or a neighbouring position to their own position are not included in the returned map.
	 * 
	 * @param	other
	 * 			The board whose elements may be moved to this board.
	 * @return	...
	 * 			| for element in other.getElements(Element.class):
	 *			|  if(this.canElementBePutAtPosition(element.getPosition(), element)
	 *          |      && this.canElementBePutAtPosition(element.getPosition(), element, result))
	 *          |   then result.get(element.getPosition()).contains(element)
	 *          |  else for each orientation in Orientation.values():
	 *          |  if(this.canElementBePutAtPosition(element.getPosition().getNeighbour(orientation), element)
	 *          |	   && this.canElementBePutAtPosition(element.getPosition().getNeighbour(orientation), element, result)
	 *          |	then result.get(element.getPosition().getNeighbour(orientation)).contains(element)
	 */
	@Model
	private java.util.Map<Position, java.util.Set<Element>> getMergePositionsOfElements(Board other)
	{
		// create an empty list.
		java.util.Map<Position, java.util.Set<Element>> elementsToMerge = new java.util.HashMap<Position, java.util.Set<Element>>();

		// for each element on the other board
		for(Element otherElement: other.getElements(Element.class))
		{
			try
			{
				// remove the element from the other board. (otherwise the next check will throw an IllegalBoardException)
				other.removeElement(otherElement);
				// check whether this otherElement is not in conflict with an element on the same position on this board,
				// whether its position is a valid position on this board and whether it does not conflict with an element already in elementsToMerge.
				this.canElementBePutAtPosition(otherElement.getPosition(), otherElement);
				this.canElementBePutAtPosition(otherElement.getPosition(), otherElement, elementsToMerge);
				// the board of the element is reset to its old board.
				other.putElement(otherElement.getPosition(), otherElement);

				// the element can be put on its own position on this board; it is added to the list-to-be-returned.
				elementsToMerge = addElementToGivenMap(elementsToMerge, otherElement, otherElement.getPosition());
			}
			// the check has thrown an exception which means it has failed.
			catch(Exception exc)
			{
				// check whether the element can be put at a neighbouring position of its own position on this board.
				for(Orientation orientation: Orientation.values())
				{
					try
					{
						// check whether this otherElement is not in conflict with an element on the neighbouring position on this board,
						// whether its position is a valid position on this board and whether it does not conflict with an element already in elementsToMerge.
						this.canElementBePutAtPosition(otherElement.getPosition().getNeighbour(orientation), otherElement);
						this.canElementBePutAtPosition(otherElement.getPosition().getNeighbour(orientation), otherElement, elementsToMerge);

						// the element can be put on a neighbouring position on this board; it is added to the list-to-be-returned.
						elementsToMerge = addElementToGivenMap(elementsToMerge, otherElement, otherElement.getPosition().getNeighbour(orientation));
						break;
					}
					// the check has thrown an exception which means it has failed.
					catch(Exception exc2)
					{
					}
				}
				// the board of the element is reset to its old board.
				other.putElement(otherElement.getPosition(), otherElement);
			}
		}
		return elementsToMerge;
	}
	
	/**
	 * Merges a given board with this board. This means that all the elements of the given board are moved to this board
	 * if possible, otherwise they are terminated. The other board is also terminated at the end.
	 * 
	 * @param	other
	 * 			The other board that has to be merged with this board.
	 * @effect	...
	 * 			| other.terminate()
	 * @post	...
	 * 			| for each position in this.getMergePositionsOfElements(other).keySet(): 
	 *			|   for each element in this.getMergePositionsOfElements(other).get(position):
	 *			|	  (new this).getElements(position).contains(element)
	 * @throws	IllegalStateExceptions
	 * 			...
	 * 			| this.isTerminated() || other.isTerminated()
	 */
	public void merge(Board other) throws IllegalStateException
	{
		if(this.isTerminated() || other.isTerminated())
		{
			throw new IllegalStateException("A terminated board cannot be merged with another (terminated) board.");
		}
		
		// calculate all the elements on the other board that can be put on this board on their own position or a neighbouring position to their own position.
		for(Position position: this.getMergePositionsOfElements(other).keySet())
		{
			for(Element newElement: this.getMergePositionsOfElements(other).get(position))
			{
				// remove the element from the other board and add it to this board.
				other.removeElement(newElement);
				this.putElement(position, newElement);
			}
		}
		// terminate the other board. all the remaining elements on this board are terminated with it.
		other.terminate();
	}


	/**
	 * Returns a list of orientatedPositions that are approximately reachable by this robot.
	 * This means that he may have enough energy to reach it.
	 * All the orientatedPostions that are not in the list, are either not reachable (the robot certainly has not enough energy to reach it)
	 * 															or the robot cannot be placed there (e.g. there is a wall there)
	 * 
	 * @param	robot
	 * 			The robot for which to calculate the list of vertices.
	 * @return	...
	 * 			| for each orientatedPosition in OrientatedPosition:
	 * 			|   result.contains(orientatedPosition)
	 * 			|	|| orientatedPosition.getPosition().getManhattanDistance(robot.getPosition()) >= robot.getEnergy(EnergyUnit.WATTSECOND)/robot.getTotalCostToMove()
	 * 			|   || this.canElementBePutAtPosition(orientatedPosition.getPosition(),robot)
	 * @throws	IllegalStateException
	 * 			| robot.getBoard() != this || robot.isTerminated()
	 */
	public java.util.List<OrientatedPosition> createListVertices(Robot robot) throws IllegalStateException
	{
		if(robot.getBoard() != this)
		{
			throw new IllegalStateException("The given robot is not placed on this board.");
		}
		if(robot.isTerminated())
		{
			throw new IllegalStateException("The given robot is terminated.");
		}
		
		// make a new empty list of orientatedPositions
		java.util.List<OrientatedPosition> vertices = new java.util.ArrayList<OrientatedPosition>();
		
		// add the robots orientatedPosition to the list
		OrientatedPosition start = new OrientatedPosition(robot.getOrientation(), robot.getPosition());
		vertices.add(start);
		// add the other orientations of the same position to the list
		for(OrientatedPosition orientatedPosition: start.getOtherOrientatedPositionOnPosition())
		{
			vertices.add(orientatedPosition);
		}
		
		// initialize the theoretical maximum distance the given robot can 'walk' in a straight line, given its current energy.
		long maxDistance = (long) (robot.getEnergy(EnergyUnit.WATTSECOND)/robot.getTotalCostToMove());
		// the variable representing the last orientatedPosition from which the neighbours are added to the list.
		int lastChecked = -1;
		
		// add all the other valid orientatedPositions to the list
		while(lastChecked + 2 <= vertices.size())
		{
			for(Orientation orientation: Orientation.values())
			{
				try
				{
					//if(lastChecked <= vertices.size())
					Position newPosition = vertices.get(lastChecked + 1).getPosition().getNeighbour(orientation);
					OrientatedPosition newOrientatedPosition = new OrientatedPosition(orientation, newPosition);
					// the calculated position can store the given robot
					//						   is not yet added to the list of vertices
					// 						   and is not farther away from the robot than the calculated maximum distance
					if((this.canElementBePutAtPosition(newPosition, robot)) && (!vertices.contains(newOrientatedPosition))
							&& (newPosition.getManhattanDistance(robot.getPosition()) <= maxDistance))
					{
						// add the calculated orientatedPosition and the other orientatedPositions
						// with the same position but other possible orientations to the list.
						vertices.add(newOrientatedPosition);
						for(OrientatedPosition orientatedPosition: newOrientatedPosition.getOtherOrientatedPositionOnPosition())
						{
							vertices.add(orientatedPosition);
						}
					}
				}
				catch(IllegalPositionException exc)
				{
				}
				catch(IllegalBoardException exc)
				{
				}
				catch(IllegalElementCombinationException exc)
				{
				}
			}
			lastChecked++;
			
			if(lastChecked == Integer.MAX_VALUE )
			{
				System.out.println("break");
				break;
			}
		}
		return vertices;
	}
	
	
	/**
	 * Generates a list of directedEdges for this robot.
	 * These edges connect the vertices connected in this.createListVertices(robot) with the energy cost to move from one vertex to the other.
	 * 
	 * @param	robot
	 * 			The robot to calculate the edges from.
	 * @return	...
	 * 			| let newOrientatedPosition be new OrientatedPosition(orientatedPosition.getOrientation().turn90Clockwise(), orientatedPosition.getPosition()) in:
	 * 			| 	for each vertexNR in 0..this.createListVertices(robot).size():
	 * 			|  		for one edge in result:
	 * 			|  		  edge.from() == vertexNR
	 * 			|		  && edge.to() == this.createListVertices(robot).indexOf(newOrientatedPosition)
	 * @return	...
	 * 			| let newOrientatedPosition be new OrientatedPosition(orientatedPosition.getOrientation().turn90CounterClockwise(), orientatedPosition.getPosition())
	 * 			|	for each vertexNR in 0..this.createListVertices(robot).size():
	 * 			|  		for one edge in result:
	 * 			|  		  edge.from() == vertexNR
	 * 			|		  && edge.to() == this.createListVertices(robot).indexOf(newOrientatedPosition)
	 * @return	...
	 * 			| let newOrientatedPosition be new OrientatedPosition(orientatedPosition.getOrientation(), orientatedPosition.getPosition().getNeighbour(orientatedPosition.getOrientation()))
	 *			|	for each vertexNR in 0..this.createListVertices(robot).size():
	 * 			|  		for one or no edge in result:
	 * 			|  		  edge.from() == vertexNR
	 * 			|		  && edge.to() == this.createListVertices(robot).indexOf(newOrientatedPosition)
	 * @throws	IllegalStateException
	 * 			| robot.getBoard() != this || robot.isTerminated()
	 */
	@Model
	private java.util.List<DirectedEdge> createEdges(Robot robot) throws IllegalStateException
	{
		java.util.List<OrientatedPosition> vertices = this.createListVertices(robot);
		
		// make a new empty list of edges
		java.util.List<DirectedEdge> edges = new java.util.ArrayList<DirectedEdge>();
		
		// for each this vertex in the list of vertices, create all the edges that start there.
		for(OrientatedPosition orientatedPosition: vertices)
		{
			// the variable indexThis represents the vertex in the EdgeWeighted DiGraph representing this orientatedPosition object
			int indexThis = vertices.indexOf(orientatedPosition);
	
			// create the edge pointing to the vertex with the same position, but an orientation that is turned 90 degrees clockwise.
			int indexTurnClockwise = vertices.indexOf(new OrientatedPosition(orientatedPosition.getOrientation().turn90Clockwise(), orientatedPosition.getPosition()));
			if(indexTurnClockwise != -1)
			{
				DirectedEdge edgeTurnClockwise = new DirectedEdge(indexThis, indexTurnClockwise, Robot.getCostToTurn());
				edges.add(edgeTurnClockwise);
			}
	
			// create the edge pointing to the vertex with the same position, but an orientation that is turned 90 degrees counterclockwise.
			int indexTurnCounterClockwise = vertices.indexOf(new OrientatedPosition(orientatedPosition.getOrientation().turn90CounterClockwise(), orientatedPosition.getPosition()));
			if(indexTurnCounterClockwise != -1)
			{
				DirectedEdge edgeTurnCounterClockwise = new DirectedEdge(indexThis, indexTurnCounterClockwise, Robot.getCostToTurn());
				edges.add(edgeTurnCounterClockwise);
			}
	
			try
			{
				// create the edge pointing to the vertex with the same orientation, but a neighbouring position relative to the position of orientatedPosition.
				int indexNeighbour = vertices.indexOf(new OrientatedPosition(orientatedPosition.getOrientation(), orientatedPosition.getPosition().getNeighbour(orientatedPosition.getOrientation())));
				if(indexNeighbour != -1)
				{
					DirectedEdge edgeNeighbour = new DirectedEdge(indexThis, indexNeighbour, robot.getTotalCostToMove());
					edges.add(edgeNeighbour);
				}
			}
			catch(IllegalPositionException exc)
			{
			}
		}
		
		return edges;
	}


	/**
	 * Creates an EdgeWeightedDigraph for the given robot.
	 * This means that all the vertices from this.createListVertices(robot) and all the valid connections (edges) between them are included.
	 * 
	 * @param	robot
	 * 			The robot to calculate the edgeWeightedDigraph for.
	 * @return	...
	 * 			| result.V() == this.createListVertices(robot).size()
	 * @return	...
	 * 			| result.edges() == this.createEdges(robot)
	 * @return	...
	 * 			| for each vertexNR in 0..this.createListVertices(robot).size():
	 * 			|  for each edge in result.adj(vertexNR):
	 * 			|    edge.from() == vertexNR
	 * @throws	IllegalStateException
	 * 			| robot.getBoard() != this || robot.isTerminated()
	 */
	public EdgeWeightedDigraph createDiGraphForRobot(Robot robot) throws IllegalStateException
	{
		java.util.List<OrientatedPosition> vertices = this.createListVertices(robot);
		java.util.List<DirectedEdge> edges = this.createEdges(robot);
		
		EdgeWeightedDigraph diGraph = new EdgeWeightedDigraph(vertices.size());
		
		// add all edges to the edgeWeightedDigraph
		for(DirectedEdge edge: edges)
		{
			diGraph.addEdge(edge);
		}

		return diGraph;
	}

	/**
	 * Returns a string representation of this board.
	 * 
	 * @return	...
	 * 			| result == "Board: (width, height) = (" + this.getWidth() + "," + this.getHeight() + ")"
	 */
	public String toString()
	{
		return "Board: (width, height) = (" + this.getWidth() + "," + this.getHeight() + ")";
		
	}	
}
