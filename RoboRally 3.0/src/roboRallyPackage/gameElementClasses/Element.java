
package roboRallyPackage.gameElementClasses;
import roboRallyPackage.*;
import roboRallyPackage.exceptionClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * @invar	Each element must have either no position at all or exactly one valid position.
 *			(the fact that position == null is okay, it is checked in this.getBoard().isValidPositionOnBoard() and Position.isValidPosition())
 * 			| this.getBoard() != null && this.getBoard().isValidPositionOnBoard(this.getPosition())
 * 			|  || this.getBoard() == null && Position.isValidPosition(this.getPosition())
 * @invar	Each element must have either no board at all or exactly one valid board.
 * 			| this.getBoard() == null
 * 			|  || ! this.getBoard().isTerminated()
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 *
 */
public abstract class Element extends Terminatable
{
	/**
	 * Initialize this new element with given position and board.
	 * 
	 * @param 	position
	 * 			The position for this new element.
	 * @param	board
	 * 			The board on which this new element is placed.
	 * @effect	The new position of this new element is equal to the given element.
	 * 			| this.setPosition(position)
	 * @effect	The new board of this new element is equal to the given element.
	 * 			| this.setBoard(board)
	 * 
	 */
	public Element(Position position, Board board)
	{
		try
		{
			this.setPosition(position);
			this.setBoard(board);
		}
		catch(NullPointerException exc)
		{
		}
		catch(IllegalPositionException exc)
		{
			System.err.println(exc.toString());
		}
		catch(IllegalElementCombinationException exc)
		{
			System.err.println(exc.toString());
		}
	}
	
	/**
	 * Terminates this element and removes all the connections to other objects.
	 * 
	 * @post	...
	 * 			| if(this.getBoard() != null) then this.getBoard().removeElement(this)
	 * @effect	...
	 * 			| this.setBoard(null)
	 * @effect	...
	 * 			| this.setPosition(null)
	 */
	@Override
	public void terminate()
	{
		// disconnect all the connections with other objects.
		if(this.getBoard() != null)
		{
			this.getBoard().removeElement(this);
		}
		this.setPosition(null);
		// set the boolean isTerminated() to true.
		super.terminate();
	}

	/**
	 * Returns the position of this element.
	 */
	@Basic
	public Position getPosition()
	{
		return position;
	}

	/**
	 * Sets the position of this element to the given position if this is a valid position.
	 * 
	 * @param 	position
	 * 			The new position of this element.
	 * @post	...
	 * 			| (new this).getPosition() == position
	 * @throws	IllegalPositionException
	 * 			The given position is not a valid position
	 * 			| ! (this.canHavePosition(position))
	 * @throws	IllegalStateException
	 * 			| robot.getBoard() != this || robot.isTerminated()
	 */
	@Model
	public void setPosition(Position position) throws IllegalPositionException,
													  IllegalStateException
	{
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated Element is not allowed to have a position.");
		}
		else if(!this.canHavePosition(position))
		{
			throw new IllegalPositionException(position, this.getBoard());
		}
		else
		{
			this.position = position;
		}
	}

	/**
	 * Checks whether the given position is a valid position for this element.
	 * 
	 * @param	position
	 * 			The position to be checked.
	 * @return	...
	 * 			| if(this.getBoard() != null && this.getBoard().isValidPositionOnBoard(position)) then result == true
	 * @return	...
	 * 			| if(this.getBoard() == null && Position.isValidPosition(position)) then result == true
	 * @return	...
	 * 			| result == false
	 */
	public boolean canHavePosition(Position position)
	{
		// the element has no board, the position must satisfy the demands a positions asks.
		if(this.getBoard() != null && this.getBoard().isValidPositionOnBoard(position))
		{
			return true;
		}
		// the element does have a board, the position must satisfy the demands a board asks concerning a position.
		if(this.getBoard() == null && Position.isValidPosition(position))
		{
			return true;
		}
		return false;
	}

	/**
	 * Variable representing the position of this element.
	 */
	private Position position = null;

	
	/**
	 * Checks whether this element can share a position with another element.
	 * 
	 * @param	other
	 * 			The element where to this element may be in conflict.
	 * @return	False if both this element is a robot and the other element is a robot.
	 * 			| if((this instanceof Robot) && (other instanceof Robot)) then result == false
	 * @return	False if either this element is a wall or the other element is a wall (or both).
	 * 			| if((! (this instanceof Wall) || ! (other instanceof Wall)) the result == false
	 */
	public abstract boolean canSharePositionWith(Element other);
	
	/**
	 * Returns the board of this element.
	 */
	@Basic
	public Board getBoard()
	{
		return board;
	}
	
	/**
	 * Sets the board of this element to the given board.
	 * This element is not allowed to be therminated. In that case nothing changes.
	 * 
	 * @param 	newBoard
	 * 			The new board of this element.
	 * @post	...
	 * 			| ! this.getBoard().getBoard().getElements().contains(this)
	 * @post	...
	 * 			| (new this).getBoard() == board
	 * @post	...
	 * 			| (new this).getBoard().getElements().contains(this)
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated()
	 */
	@Model
	public void setBoard(Board newBoard) throws IllegalStateException
	{
		Board oldBoard = this.getBoard();
		
		// this element already has a board that is different from the new board.
		// this element is removed from that board and the board of this element is set to 'null'.
		if(this.getBoard() != null && this.getBoard() != newBoard)
		{
			if(this.getBoard().getElements(this.getPosition()).contains(this))
			{
				this.getBoard().removeElement(this);
			}
		}
		
		// you are trying to set a new, real board, wile this element did not yet have a board.
		// this element is added to that board and the board of this element is set to that new board if this is allowed. Otherwise the element is terminated.
		if((this.getBoard() == null) && (oldBoard == null) && (newBoard != null))
		{
			try
			{
				this.board = newBoard;
				newBoard.putElement(this.getPosition(), this);
			}
			catch(IllegalPositionException exc)
			{
				this.terminate();
				System.err.println("This element cannot be placed on this position on this board. It is not a valid position. It is terminated.");
			}
			catch(IllegalElementCombinationException exc)
			{
				this.terminate();
				System.err.println("This element is in conflict with another element on this position on this board. It is terminated.");
			}
		}
		
		// you are trying to change the board of this element. this means that this element was standing on a board before you called this method.
		// the current board of this element (this.getBoard()) is already set to 'null' by a previous if-test of this method.
		// this element is added to that board and the board of this element is set to that new board if this is allowed. Otherwise the elements board is not changed.
		else if((this.getBoard() == null) && (oldBoard != null) && (newBoard != null))
		{
			try
			{
				this.board = newBoard;
				newBoard.putElement(this.getPosition(), this);
			}
			catch(IllegalPositionException exc)
			{
				oldBoard.putElement(this.getPosition(), this);
				System.err.println("This element cannot be placed on this position on this board. It is not a valid position. It is still standing on its old board.");
			}
			catch(IllegalElementCombinationException exc)
			{
				oldBoard.putElement(this.getPosition(), this);
				System.err.println("This element is in conflict with another element on this position on this board. It is still standing on its old board.");
			}
		}
		
		// set the board of this element to 'null'.
		else if(newBoard == null)
		{
			this.board = newBoard;
		}
		
		// if(newBoard == this.getBoard()) nothing happens
	}

	/**
	 * Variable representing the board of this element.
	 */
	private Board board = null;
	
	/**
	 * Returns a string representation of this element.
	 * 
	 * @return	...
	 * 			| result == " " + this.getPosition().toString() + ";  " + "\n"
			   	|			+ " " + this.getBoard().toString();
	 */
	@Override
	public String toString()
	{
		String resultString = "";
		
		if(this.getPosition() != null){
		resultString += " " + this.getPosition().toString() + ";  " + "\n";
		}
		if(this.getBoard() != null){
				
			resultString +=  " " + this.getBoard().toString();
		}
		return resultString;
	}
}
