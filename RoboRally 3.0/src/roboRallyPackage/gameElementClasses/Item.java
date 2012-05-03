
package roboRallyPackage.gameElementClasses;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of items.
 * 
 * @invar	The weight of this item must be a valid weight.
 * 			| this.canHaveAsWeight(this.getWeight())
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class Item extends Element 
{
	/**
	 * Initializes this new item with the given weight.
	 * 
	 * @param 	weight
	 * 			The weight for this new item.
	 * @post	If the given weight is not negative and less than or equal to the maximal allowed weight for this Item, 
	 * 			the initial weight of this item is equal to the given weight. Otherwise, its initial weight is set to 0.
	 * 			| if(canHaveAsWeigh(weight)) then (new this).getWeight == weight
	 * 			| else (new this).getWeight == 0
	 */
	public Item(int weight)
	{
		super(null, null);
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
	 * Checks whether this item can share a position with another element.
	 * 
	 * @param	other
	 * 			The element where to this element may be in conflict.
	 * @return	...
	 * 			| result == (other == null)
	 * @return	...
	 * 			| result == !(other instanceof Wall)
	 */
	@Override
	public boolean canSharePositionWith(Element other)
	{
		return (other == null || !(other instanceof Wall));
	}
	
	/**
	 * Returns the variable representing the weight of this item.
	 */
	@Basic
	public int getWeight()
	{
		return weight;
	}
	
	/**
	 * Checks whether the given weight is a valid weight for this item.
	 * 
	 * @param	weight
	 * 			The weight to be checked.
	 * @return	...
	 * 			| result == (weight >= 0)
	 * @return	...
	 * 			| result == (weight <= this.getMaxWeight())
	 */
	public boolean canHaveAsWeigh(int weight)
	{
		return ((weight >= 0) && (weight <= this.getMaxWeight()));
	}
	
	/**
	 * Variable representing the weight of this item.
	 */
	private final int weight;

 
	/**
	 * Returns the variable representing the maximal weight an item can have.
	 */
	@Basic
	public int getMaxWeight()
	{
		return maxWeight;
	}

	/**
	 * Variable representing the maximal weight an item can have.
	 */
	private final int maxWeight = Integer.MAX_VALUE;

	/**
	 * A robot uses this item.
	 */
	public abstract void use(Robot robot);
	
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
				+ " Weight: " + this.getWeight();
	}
}
