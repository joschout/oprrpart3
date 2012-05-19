
package roboRallyPackage.gameElementClasses;
import roboRallyPackage.Board;
import roboRallyPackage.Position;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of items.
 * 
 * @invar	The weight of this item, expressed in grams [g], must be a valid weight.
 * 			| this.canHaveAsWeight(this.getWeight())
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class Item extends Element 
{
	/**
	 * Initializes this new item with the given position, board and weight [g].
	 * 
	 * @param 	position
	 * 			The position for this new item.
	 * @param	board
	 * 			The board on which this new item will be placed.
	 * @param 	weight
	 * 			The weight for this new item, expressed in grams [g].
	 * @effect	This new item is initialized as an Element with the given position and the given board.
	 * 			| super(position, board)
	 * @post	If the given weight is a valid weight for this Item, the weight of this item is set to the given weight.
	 * 			| if(canHaveAsWeigh(weight)) then (new this).getWeight == weight
	 */
	public Item(Position position, Board board, int weight)
	{
		super(position, board);
		if(canHaveAsWeigh(weight))
		{
			this.weight = weight;
		}
		else
		{
			this.weight = 0;
		}
	}
	
	/**
	 * Initializes this new item with the given weight, expressed in grams [g].
	 * 
	 * @param 	weight
	 * 			The weight for this new item, expressed in grams [g].
	 * @effect	...
	 * 			| this(null, null, weight)
	 */
	public Item(int weight)
	{
		this(null, null, weight);
	}
	
	/**
	 * Checks whether this item can share a position with another element.
	 */
	@Override
	public boolean canSharePositionWith(Element other)
	{
		return !(other instanceof Wall);
	}
	
	/**
	 * Returns the variable representing the weight of this item, expressed in grams [g].
	 */
	@Basic
	public int getWeight()
	{
		return weight;
	}
	
	/**
	 * Checks whether the given weight, expressed in grams [g], is a valid weight for this item.
	 * 
	 * @param	weight
	 * 			The weight to be checked, expressed in grams [g].
	 * @return	...
	 * 			| result != (weight < 0)
	 * @return	...
	 * 			| result != (weight > this.getMaxWeight())
	 */
	public boolean canHaveAsWeigh(int weight)
	{
		return ((weight >= 0) && (weight <= this.getMaxWeight()));
	}
	
	/**
	 * Variable representing the weight of this item, expressed in grams [g].
	 */
	private final int weight;

 
	/**
	 * Returns the variable representing the maximal weight an item can have, expressed in grams [g].
	 */
	@Basic
	public int getMaxWeight()
	{
		return maxWeight;
	}

	/**
	 * Variable representing the maximal weight an item can have, expressed in grams [g].
	 */
	private final int maxWeight = Integer.MAX_VALUE;

	/**
	 * The given robot uses this item.
	 * 
	 * @param	robot
	 * 			The robot that uses this item.
	 * @pre		...
	 * 			| robot.getPossessions().contains(this)
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated() || robot.isTerminates()
	 */
	public abstract void use(Robot robot) throws IllegalStateException;
	
	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * Which properties and to what extend, depends on the elements.
	 * 
	 * @throws	IllegalStateException
	 * 			When this item is terminated.
	 * 			| this.isTerminated()
	 */
	@Override
	public abstract void takeHit() throws IllegalStateException;
	
	/**
	 * Returns a string representation of this item.
	 * 
	 * @return	...
	 * 			| result == super.toString() + "\n" 
	 *			| 			+ " weight: " + this.getWeight()
	 */
	@Override
	public String toString()
	{
		return super.toString() + ";  " + "\n"
				+ " Weight: " + this.getWeight() + " [g]";
	}
}
