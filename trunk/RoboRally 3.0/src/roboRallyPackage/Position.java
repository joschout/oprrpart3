package roboRallyPackage;

import roboRallyPackage.exceptionClasses.IllegalPositionException;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing a position.
 * 
 * @invar	A Position must have valid X coordinate
 * 			| this.canHaveAsXCoord(this.getCoordX())
 * @invar	A Position must have a valid Y coordinate
 * 			| this.vanHavaAsYCoord(hits.getCoordY())
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
@Value
public class Position
{
	/**
	 * Initializes this new position with the given x and y coordinates.
	 * 
	 * @param 	coordX
	 * 			The X coordinate for this new position.
	 * @param 	coordY
	 * 			The Y coordinate for this new position.
	 * @effect	The new X coordinate of this new position is equal to the given X coordinate if this is a valid coordinate.
	 * 			| this.setCoordX(coordX)
	 * @effect	The new Y coordinate of this new position is equal to the given Y coordinate if this is a valid coordinate.
	 * 			| this.setCoordY(coordY)
	 * 
	 */
	public Position(long coordX, long coordY)
	{
		// throws exceptions when these x and/or y coordinate are unvalid
		this.setCoordX(coordX);
		this.setCoordY(coordY);
	}
	
	/**
	 * Returns the X coordinate (horizontal axis, starting from the left side of the board) of this position.
	 */
	@Basic
	public long getCoordX()
	{
		return coordX;
	}
	
	/**
	 * Sets the X coordinate of this position to the given value.
	 * @param 	coordX
	 * 			The new X coordinate of this position.
	 * @post	The new X coordinate of this position is equal to the given X coordinate.
	 * 			| (new this).getCoordX() == coordX
	 * @throws	IllegalPositionException
	 * 			This position cannot accept the given X coordinate.
	 * 			| ! canHaveAsXCoordinate(coordX)
	 */
	@Model
	private void setCoordX(long coordX) throws IllegalPositionException
	{
		if  (! canHaveAsXCoordinate(coordX))
		{
			throw new IllegalPositionException(new Position(coordX, this.getCoordY()), null);
		}
		else
		{
			this.coordX = coordX;
		}
	}
	
	/**
	 * Checks whether the given X coordinate is a valid X coordinate.
	 * @param 	coordX
	 * 			The X coordinate to be checked.
	 * @return	True if and only if the given X coordinate is nonnegative
	 * 								and equals or is smaller than the maximal X coordinate.
	 * 			| result == ((coordX >= 0) && (coordX <= Board.getMaxWidth()))
	 * @throws	NullPointerException
	 * 			...
	 * 			| coordX == null
	 */
	public static final boolean canHaveAsXCoordinate(long coordX)
	{
		return ((coordX >= 0) && (coordX <= Board.getMaxWidth()));
	}
	
	/**
	 * Variable representing the X coordinate of this position.
	 */
	private long coordX = 0;
	
	
	
	
	
	
	/**
	 * Returns the Y coordinate (vertical axis, starting from the upper side of the board) of this position.
	 */
	@Basic
	public long getCoordY()
	{
		return coordY;
	}

	/**
	 * Sets the Y coordinate of this position to the given value.
	 * @param 	coordY
	 * 			The new Y coordinate of this position.
	 * @post	The new Y coordinate of this position is equal to the given Y coordinate.
	 * 			| (new this).getCoordY() == coordY
	 * @throws	IllegalPositionException
	 * 			This position cannot accept the given Y coordinate.
	 * 			| ! canHaveAsYCoordinate(coordY)
	 * 
	 */
	@Model
	private void setCoordY(long coordY) throws IllegalPositionException
	{
		if  (! canHaveAsYCoordinate(coordY))
		{
			throw new IllegalPositionException(new Position(this.getCoordX(), coordY), null);
		}
		else
		{
			this.coordY = coordY;
		}
	}
	
	/**
	 * Checks whether the given Y coordinate is a valid Y coordinate.
	 * @param 	coordY
	 * 			The Y coordinate to be checked.
	 * @return	True if and only if the given Y coordinate is nonnegative
	 * 			 and equals or is smaller than the maximal Y coordinate.
	 * 			| result == ((coordY >= 0) && (coordX <= Board.getMaxHeight()))
	 * @throws	NullPointerException
	 * 			...
	 * 			| coordY == null
	 */
	public static final boolean canHaveAsYCoordinate(long coordY)
	{
		return ((coordY >= 0) && (coordY <= Board.getMaxHeight()));
	}
		
	/**
	 * Variable representing the Y coordinate of this position.
	 */
	private long coordY = 0;
	
	
	/**
	 * Checks whether the given position is a valid position.
	 * @param 	position
	 * 			The position to be checked.
	 * @return	True if and only if the X coordinate of the given position is a valid X coordinate
	 * 			and the Y coordinate of the given position is a valid Y coordinate.
	 * 			| result == ((position == null)
	 * 			|  			 || (canHaveAsXCoordinate(position.getCoordX()) && canHaveAsYCoordinate(position.getCoordY())))
	 */
	public static final boolean isValidPosition(Position position)
	{
		try
		{
			return canHaveAsXCoordinate(position.getCoordX()) && canHaveAsYCoordinate(position.getCoordY());
		}
		catch(NullPointerException exc)
		{
			return true;
		}
	}
	

	/**
	 * Returns the position that lies next to this position, given a certain orientation.
	 * 
	 * @param	orientation
	 * 			The direction where the returned position will lie, respectively to the current position (up, down,...)
	 * @return	...
	 * 			| result == this.getNeighbourAt(orientation, 1)
	 * @throws	IllegalPositionException
	 * 			...
	 * 			| ! this.canHaveAsXCoordinate(this.getCoordX() + orientation.getRelativeMoveCoordinates()[0])
	 * 			|    || ! this.canHaveAsYCoordinate(this.getCoordY() + orientation.getRelativeMoveCoordinates()[1])
	 */
	public Position getNeighbour(Orientation orientation) throws IllegalPositionException
	{
		return this.getNeighbourAt(orientation, 1);
	}
	
	/**
	 * Returns the position that lies a given steps from this position, given a certain orientation.
	 * 
	 * @param	orientation
	 * 			The direction where the returned position will lie, respectively to the current position (up, down,...)
	 * @param	steps
	 * 			The number of steps one has to take to reach this new position, starting from this position
	 * @return	...
	 * 			| result == Position(this.getCoordX() + steps * orientation.getRelativeMoveCoordinates()[0],
	 *								 this.getCoordY() + steps * orientation.getRelativeMoveCoordinates()[1]);
	 * @throws	IllegalPositionException
	 * 			...
	 * 			| ! this.canHaveAsXCoordinate(this.getCoordX() + steps * orientation.getRelativeMoveCoordinates()[0])
	 * 			|    || ! this.canHaveAsYCoordinate(this.getCoordY() + steps * orientation.getRelativeMoveCoordinates()[1])
	 */
	public Position getNeighbourAt(Orientation orientation, int steps) throws IllegalPositionException
	{
		// the calculated x coordinate is not a valid x coordinate
		if(! canHaveAsXCoordinate(this.getCoordX() + steps * orientation.getRelativeMoveCoordinates()[0]))
		{
			throw new IllegalPositionException(new Position(this.getCoordX() + steps * orientation.getRelativeMoveCoordinates()[0],
											   				this.getCoordY() + steps * orientation.getRelativeMoveCoordinates()[1]),
											   null);
		}
		// the calculated y coordinate is not a valid y coordinate
		if(! canHaveAsYCoordinate(this.getCoordY() + steps * orientation.getRelativeMoveCoordinates()[1]))
		{
			throw new IllegalPositionException(new Position(this.getCoordX() + steps * orientation.getRelativeMoveCoordinates()[0],
															this.getCoordY() + steps * orientation.getRelativeMoveCoordinates()[1]),
							   				   null);
		}
		// the calculated position, the givens steps away,  is a valid position; this position is returned.
		else
		{
			return new Position(this.getCoordX() + steps * orientation.getRelativeMoveCoordinates()[0],
								this.getCoordY() + steps * orientation.getRelativeMoveCoordinates()[1]);
		}
	}
	
	/**
	 * Returns true if the other position lies on the same line as this position, given a certain orientation (referring to this position).
	 * 
	 * @param	other
	 * 			The other position.
	 * @param	orientation
	 * 			The direction where the other position may lie, respectively to the current position (up, down,...).
	 * @return	...
	 * 			| for each i in 1..this.getManhattanDistance(other)
	 * 			| 	if(getNeighbourAt(orientation, i).equals(other)) then result == true
	 */
	public boolean isPlacedAtOrientationOf(Position other, Orientation orientation)
	{
		for(int i = 1; i <= this.getManhattanDistance(other); i++)
		{
			try
			{
				// the nextPosition is not the other position; a new nextPosition is calculated.
				if(getNeighbourAt(orientation, i).equals(other))
				{
					return true;
				}
			}
			catch (IllegalPositionException exc)
			{
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Returns the ManhattanDistance between this position and another position.
	 * 
	 * @param 	other
	 * 			The other position.
	 * @return	...
	 * 			| result == Math.abs(this.getCoordX() - other.getCoordX()) + Math.abs(this.getCoordY() - other.getCoordY())
	 */
	public long getManhattanDistance(Position other)
	{
		return Math.abs(this.getCoordX() - other.getCoordX()) + Math.abs(this.getCoordY() - other.getCoordY());
	}
	
	/**
	 * Returns a hash code value for this position.
	 */
	@Override
	public int hashCode()
	{
		return ((int) Math.round(this.getCoordX()^7 + this.getCoordY()^11))%3;
	}
	
	/**
	 * Checks whether this position is equal to the given OBJECT;
	 * 
	 * @return	...
	 * 			| result == 
	 * 			|	( (other != null)
	 * 			|	&& (this.getClass() == other.getClass())
	 * 			|	&& (this.getCoordX() == (Position other).getCoordX())
	 * 			|	&& (this.getCoordY() == (Position other).getCoordY())
	 */
	@Override
	public boolean equals(java.lang.Object other)
	{
		if (other == null)
		{
			return false;
		}
		if (this.getClass() != other.getClass())
		{
			return false;
		}
		Position otherPosition = (Position) other;
		return (this.getCoordX() == otherPosition.getCoordX()) && (this.getCoordY() == otherPosition.getCoordY());
		
	}	
	
	
	/**
	 * Returns a string representation of this position.
	 * 
	 * @return	...
	 * 			| result == " x coordinate: " + getCoordX() + ";  y coordinate: " + getCoordY()
	 */
	@Override
	public java.lang.String toString()
	{
		return "Position: (x,y) = (" + this.getCoordX() + "," + this.getCoordY() + ")";
	}
}
