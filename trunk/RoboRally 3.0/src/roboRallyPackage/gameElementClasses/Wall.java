
package roboRallyPackage.gameElementClasses;
import roboRallyPackage.*;

/**
 * A class of walls. Extends the abstract class Element.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Wall extends Element
{
	/**
	 * Initialize this new wall with the given position and board.
	 * 
	 * @param 	position
	 * 			The position for this new wall.
	 * @param	board
	 * 			The board on which this new wall will be placed.
	 * @effect	This new wall is initialized as an Element with the given position and board.
	 * 			| super(position, board)
	 */
	public Wall(Position position, Board board)
	{
		super(position, board);
	}

	/**
	 * Initialize this new wall without a position or a board.
	 * 
	 * @effect	...
	 * 			| this(null, null)
	 */
	public Wall()
	{
		this(null, null);
	}
	
	/**
	 * Checks whether this wall can share a position with another element.
	 */
	@Override
	public boolean canSharePositionWith(Element other)
	{
		return (other == null);
	}
	
	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * A wall does not change it state when hit.
	 */
	@Override
	public void takeHit() throws IllegalStateException
	{
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated wall cannot be hit by a laser.");
		}
	}
}
