
package roboRallyPackage.gameElementClasses;
import roboRallyPackage.*;

/**
 * A class of walls. Extends the abstract class Element.
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Wall extends Element
{
	/**
	 * Initialize this new wall with the given position and the given board.
	 * 
	 * @param 	position
	 * 			The position for this new wall to be placed on.
	 * @param 	board
	 * 			The board for this new wall to be placed on.
	 * @post	...
	 * 			| this.getBoard == board
	 * @post	...
	 * 			| this.getPosition() == position
	 */
	public Wall(Position position, Board board)
	{
		super(position, board);
	}

	/**
	 * Initialize this new wall on no position and no board.
	 * @post	...
	 * 			| this.getBoard == null
	 * @post	...
	 * 			| this.getPosition() == null
	 * 
	 */
	public Wall()
	{
		this(null, null);
	}
	
	/**
	 * Checks whether this wall can share a position with another element.
	 * 
	 * @param	other
	 * 			The element where to this wall may be in conflict.
	 * @return	True if and only if this wall can be placed at the same position (and board) as the other element.
	 * 			| result == (other == null)
	 */
	@Override
	public boolean canSharePositionWith(Element other)
	{
		return (other == null);
	}
	
	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * A wall does not change it state when hit.
	 * 
	 * @throws	IllegalStateException
	 * 			When this Wall is terminated.
	 * 			| this.isTerminated()
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
