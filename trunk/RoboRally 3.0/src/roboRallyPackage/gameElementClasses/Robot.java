
package roboRallyPackage.gameElementClasses;

import roboRallyPackage.*;
import roboRallyPackage.exceptionClasses.IllegalBoardException;
import roboRallyPackage.exceptionClasses.IllegalElementCombinationException;
import roboRallyPackage.exceptionClasses.IllegalPositionException;
import roboRallyPackage.pathFindingClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * @invar	Each robot must have either no position at all or exactly one valid position.
 *			(the fact that position == null is ok is checked in board.isValidPositionOnBoard() and Position.isValidPosition())
 * 			| this.getBoard() != null && this.getBoard().isValidPositionOnBoard(this.getPosition())
 * 			|  || this.getBoard() == null && this.getPosition().isValidPosition(Position)()
 * @invar	Each robot must have either no board at all or exactly one valid board.
 * 			| this.getBoard() == null
 * 			|  || ! this.getBoard().isTerminated()
 * @invar	The orientation of each robot must be a valid orientation.
 * 			| this.canHaveAsOrientation(this.getOrientation())
 * @invar	The current energy level of each robot must be a valid current energy level.
 * 			| this.canHaveAsEnergy(this.getEnergy())
 * @invar	The maximum energy level of each robot must be a valid maximum energy level.
 * 			| this.canHaveAsMaxEnergy(this.getMaxEnergy())
 * @invar	The total weight of the items a robot can carry must not exceed the maximum weight it can carry.
 * 			| this.getTotalWeightToCarry() <= getMaxWeightToCarry()
 * @invar	An item that this robot is carrying cannot have a Board or a Position
 * 			| for each item in this.getPossessions(): item.getBoard() == null && item.getPosition() == null
 * @invar	A terminated Robot cannot carry any items or alter its state.
 * 			| if this.isTerminated() then this.getPossessions().size() == 0
 * @invar	A robot cannot carry elements that are not of its generic type T.
 * 			| for each element in this.getPossessions(): element instanceof T
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 *
 */
public class Robot extends Element implements IEnergyHolder
{

	/**
	 * Initialize this new robot with given position, board, orientation, currentEnergy and maxEngery.
	 * 
	 * @param 	position
	 * 			The position for this new robot.
	 * @param	board
	 * 			The board on which this new robot is placed.
	 * @param	orientation
	 * 			The orientation for this new robot.
	 * @param	currentEnergy
	 * 			The current energy level for this new robot.
	 * @param	maxEnergy
	 * 			The maximum amount of energy this new robot can ever have.
	 * @effect	This new robot is initialized as an Element with the given position and the given board.
	 * 			| super(position, board)
	 * @effect	The new orientation of this new robot is equal to the given orientation.
	 * 			| this.setOrientation(orientation)
	 * @effect	The new current energy of this new robot is equal to the given current energy if this is a valid value for current energy.
	 * 			Otherwise, the currentEnergy is set to 0.
	 * 			| if(canHaveAsEnergy(currentEnergy))
	 * 			| 	then this.setEnergy(currentEnergy)
	 * 			| else this.setEnergy(0)
	 * @post	If the value of maxEnergy is a valid value, the new maximum energy level of this new robot is set to this value (in Ws).
	 * 			Otherwise, the maxEnergy is set to 20 000 Ws.
	 * 			| if(canHaveAsMaxEnergy(maxEnergy))
	 * 			|	then (new this).getMaxEnergy == maxEnergy
	 *			| else (new this).getMaxEnergy == 20000
	 */
	public Robot(Position position, Board board, Orientation orientation, double currentEnergy, double maxEnergy)
	{	
		super(position, board);

		this.setOrientation(orientation);
		this.setEnergy(currentEnergy);
		
		if(this.canHaveAsMaxEnergy(maxEnergy))
		{
			this.setMaxEnergy(maxEnergy);
		}
		else
		{
			this.setMaxEnergy(20000);
		}
	}

	/**
	 * Terminates this robot. All its possessions are also terminated and removed from the robots list.
	 * 
	 * @post	This robot is terminated
	 * 			| (new this).isTerminated()
	 * @post	All the elements of this are terminated
	 * 			| for each item in this.getPossessions(): (new this).isTerminated
	 * @post	All the elements this robot possesses are removed from the list that keeps these items
	 * 			| (new this).getPossessions().size() == 0
	 */
	@Override
	public void terminate()
	{
		for(Item item: this.getPossessions())
		{
			this.getPossessions().remove(item);
			item.terminate();
		}
		super.terminate();
	}

	// getter and setter of Position and Board are defined in the superclass Element
	
	/**
	 * Returns the orientation of this robot.
	 */
	@Basic
	public Orientation getOrientation()
	{
		return orientation;
	}
	
	/**
	 * Set the orientation of this robot to the given orientation.
	 * 
	 * @param 	orientation
	 * 			The new orientation of this robot.
	 * @pre		The given orientation must be a valid orientation for a robot
	 * 			| canHaveAsOrientation(orientation)
	 * @post	The old orientation is changed into the given orientation.
	 * 			| (new this).getOrientation() == orientation
	 * @throws	IllegalStateException
	 * 			This robot is terminated
	 * 			| this.isTerminated()
	 */
	@Model
	private void setOrientation(Orientation orientation) throws IllegalStateException
	{
		assert canHaveAsOrientation(orientation): "This is not a valid orientation for this robot.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		this.orientation = orientation;
	}
	
	/**
	 * Checks whether the given orientation is a valid orientation for this robot.
	 * 
	 * @param	orientation
	 * 			The orientation to be checked.
	 * @return	True if and only if the given orientation is one of the four following objects from the enumeration Orientation: UP, RIGHT, DOWN or LEFT.
	 * 			| result == (orientation == Orientation.UP
	 * 			|  			 || orientation == Orientation.RIGHT
	 * 			|			 || orientation == Orientation.DOWN
	 * 			|			 || orientation == Orientation.LEFT)
	 */
	public static final boolean canHaveAsOrientation(Orientation orientation)
	{
		return orientation == Orientation.UP || orientation == Orientation.RIGHT || orientation == Orientation.DOWN || orientation == Orientation.LEFT;
	}
	
	/**
	 * Variable representing the orientation of this robot.
	 */
	private Orientation orientation = Orientation.RIGHT;
	
	
	
	
	/**
	 * Returns the current energy level of this robot.
	 */
	@Basic @Override
	public double getEnergy()
	{
		return currentEnergy;
	}
	
	/**
	 * Sets the amount of energy of this robot to the given amount of energy, expressed in watt-seconds (Ws).
	 * 
	 * @param 	energyAmount
	 * 			The new energy amount for this robot in watt-seconds (Ws).
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#setEnergy(double)
	 */
	public void setEnergy(double currentEnergy) throws IllegalStateException
	{
		assert (canHaveAsEnergy(currentEnergy)): "Preconditions for setEnergy(double) must be satisfied.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		
		this.currentEnergy = currentEnergy;
	}
	
	/**
	 * Checks whether the given currentEnergy is a valid currentEnergy.
	 * @param 	currentEnergy
	 * 			The amount of energy to be checked.
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#canHaveAsEnergy(double)
	 */
	public boolean canHaveAsEnergy(double currentEnergy)
	{
		return ((currentEnergy >= 0) && (currentEnergy <= this.getMaxEnergy()));
	}
	
	/**
	 * Variable representing the current amount of energy of this robot.
	 */
	private double currentEnergy = 0;
	
	
	
	
	
	
	/**
	 * Returns the maximum energy level of this robot.
	 */
	@Basic @Override
	public final double getMaxEnergy()
	{
		return maxEnergy;
	}
	
	/**
	 * Sets the maximum energy level of this Robot to the given maximum energy level, expressed in watt-seconds (Ws).
	 * 
 	 * @param 	maxEnergyAmount
	 * 			The new maximum energy level for this Robot in watt-seconds (Ws).
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#setMaxEnergy(double)
	 */
	public void setMaxEnergy(double maxEnergyAmount) throws IllegalStateException,
															UnsupportedOperationException
	{
		assert this.canHaveAsMaxEnergy(maxEnergyAmount): "The given maximum energy level is not a valid maximum energy level for this robot.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		
		this.maxEnergy = maxEnergyAmount;
	}
	
	/**
	 * Check whether the given maximum energy is a valid maximum energy for this robot.
	 * 
	 * @param 	maxEnergy
	 * 			The maximum energy level to be checked.
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#canHaveAsMaxEnergy(double)
	 * @return	True if the given maximum energy level equals or is greater than the most expensive cost for an action
	 * 			(otherwise, this robot will f.e. never be able to shoot)
	 * 			| result == (maxEnergy >= java.util.Collections.max(getListCosts()))
	 * @return	False if the given energy is greater than 20000 Ws.
	 * 			| result != (energy > Double.MAX_VALUE)
	 */
	public boolean canHaveAsMaxEnergy(double maxEnergy)
	{
		return (maxEnergy >= 0 && maxEnergy >= java.util.Collections.max(getListCosts()) && maxEnergy <= 20000);
	}
	
	/**
	 * Variable representing the maximum amount of energy this robot can have.
	 */
	private double maxEnergy;
	

	/**
	 * Returns a percentage of the current energy level relative to the maximum amount of energy this robot can have.
	 * 
	 * @return	A percentage of the current energy level relative to the maximum amount of energy this robot can have.
	 * 			| result == (this.getEnergy() / this.getMaxEnergy()) * 100
	 */
	public double getEnergyFraction()
	{
		return (this.getEnergy() / this.getMaxEnergy()) * 100;
	}
	
	/**
	 * Recharges the current energy level with the given amount of energy.
	 * 
	 * @param	amount
	 * 			The amount of energy added to the current energy of this robot.
	 * @pre		This robot can accept the given amount of energy.
	 * 			| canAcceptForRecharge(amount)
	 * @effect	The given amount of energy is added to the current amount of energy this robot has.
	 * 			| this.setEnergy(this.getEnergy() + amount)
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 */
	@Override
	public void recharge(double amount) throws IllegalStateException
	{
		assert canAcceptForRecharge(amount): "Precondition for recharge(double) must be satisfied.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		this.setEnergy(this.getEnergy() + amount);
	}
	
	/**
	 * Returns a boolean reflecting whether this amount of energy can be added to the energy stack of this robot.
	 *
	 * @param 	amount
	 * 			The amount of energy added to the current energy of this robot.	
	 * @return 	True if the given amount is strictly positive and.
	 *  		| result == (amount > 0)
	 * @return	True if the sum of the given amount and the current energy level equals or is smaller than the maximum energy level this robot can have.
	 * 			| result == (this.getEnergy() + amount <= getMaxEnergy())
	 */
	public boolean canAcceptForRecharge(double amount)
	{
		return (this.getEnergy() + amount <= getMaxEnergy() && amount > 0);
	}
	
	
	/**
	 * Returns the minimum amount of energy this robot needs to move to the given position.
	 * Returns -1 if this robot cannot reach the given position for sure.
	 * 
	 * @param	position
	 * 			The position where to this robot may be moved to
	 * @return	-1 if this robot is not placed on a board
	 * 			| if this.getBoard() == null
	 * 			| 	then result == -1
	 * @return	The minimum amount of energy this robot needs to move to the given position.
	 *  		| energyForPositionWithDifferingOrientation = { DijkstraSP(this.getBoard().createDiGraphForRobot(this), 0).distTo(thisVertices.indexOf(new OrientatedPosition(position, orientation)) ) 
	 *  		|																								for all orientation in orientation.values()}
	 *			| result == {there exists an energy in energyForPositionWithDifferingOrientation | energy <= otherEnergy
	 * 			|																			for all otherEnergy in energyForPositionWithDifferingOrientation}
	 * @return	-2 if this robot robot hasn't got enough energy left to reach the given position or if the given position already contains an element 
	 * 			| for all orientatedPosition in {OrientatedPosition | orientatedPosition.getPosition() == position && orientatedPosition.getOrientation() !=null}
	 * 			|	if (! (this.getBoard().createListVertices(this).contains(orientatedPosition)))
	 * 			|		then result == -2
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 */
	
	public double getEnergyRequiredToReach(Position position) throws IllegalStateException
	{
		try
		{
			DijkstraSP thisShortestPaths = new DijkstraSP(this.getBoard().createDiGraphForRobot(this), 0);
			java.util.List<OrientatedPosition> thisVertices = this.getBoard().createListVertices(this);

			double energy = Double.POSITIVE_INFINITY;

			for(Orientation orientation: Orientation.values())
			{
				try
				{
					OrientatedPosition orientatedPosition = new OrientatedPosition(orientation, position);
					if(thisShortestPaths.distTo(thisVertices.indexOf(orientatedPosition)) < energy)
					{
						energy = thisShortestPaths.distTo(thisVertices.indexOf(orientatedPosition));
					}
				}
				catch(ArrayIndexOutOfBoundsException exc)
				{
					return -2;
				}
			}

			return energy;
		}
		catch(NullPointerException exc)
		{
			return -1;
		}
	}
	
	
	
	
	/**
	 * Returns the energy cost of turning 90 degrees.
	 */
	@Basic @Immutable
	public static final double getCostToTurn()
	{
		return costToTurn;
	}
	
	/**
	 * Variable representing the loss of energy for a robot when turning 90 degrees.
	 */
	private static final double costToTurn = 100;
	
	/**
	 * Returns the energy cost of shoot.
	 */
	@Basic @Immutable
	public static final double getCostToShoot()
	{
		return costToShoot;
	}

	/**
	 * Variable representing the loss of energy for a robot when shooting one time.
	 */
	private static final double costToShoot = 1000;

	/**
	 * Returns the energy cost of moving one step.
	 * This method does not take into account whether a robot is carrying items or not.
	 */
	@Basic @Immutable
	public static final double getCostToMove()
	{
		return costToMove;
	}
	
	/**
	 * Variable representing the loss of energy for a robot when moving one step.
	 */
	private static final double costToMove = 500;
	
	/**
	 * Returns the the additional loss of energy for each kg this robot is carrying when moving one stop.
	 */
	@Basic @Immutable
	public static final double getAdditionalCostToMove()
	{
		return additionalCostToMove;
	}
	
	/**
	 * Variable representing the additional loss of energy for each kg a robot is carrying when moving one step.
	 */
	private static final double additionalCostToMove = 50;
	
	/**
	 * Returns the total energy cost of moving this robot one step.
	 * This method takes into account the normal cost and the additional cost per kg of items this robot is carrying.
	 * 
	 * @return	The cost to move increased with the additional cost to move times the total weight of all the possessions this robot is carrying.
	 * 			| result == getCostToMove() + getAdditionalCostToMove() * this.getTotalWeightPossessions()
	 */
	public double getTotalCostToMove()
	{
		return getCostToMove() + Math.round((getAdditionalCostToMove() * this.getTotalWeightPossessions())/1000);
	}

	/**
	 * Returns a list of the costs for all the possible actions of this robot.
	 * The moving costs is the cost to move without carrying any items.
	 * 
	 * @return	A list of the costs for all the possible actions of this robot.
	 * 			| result == new List(getCostToMove(), getCostToTurn(), getCostToShoot())
	 */
	@Model
	private java.util.List<Double> getListCosts()
	{
		java.util.List<Double> listCosts = new java.util.ArrayList<Double>();
		listCosts.add(getCostToMove());
		listCosts.add(getCostToTurn());
		listCosts.add(getCostToShoot());
		
		return listCosts;
	}

	
	/**
	 * Returns a list of all the possessions this robot is carrying.
	 */
	@Basic
	public java.util.List<Item> getPossessions()
	{
		return possessions;
	}
		
	/**
	 * Returns the total weight of all the elements this robot is carrying.
	 * 
	 * @return	The total weight of all the items this robot is carrying.
	 * 			| for each item in this.getPossessions(): result == result + item.getWeight()
	 */
	public int getTotalWeightPossessions()
	{
		int totalWeight = 0;
		// iterate over all the items this robot is carrying.
		for(Item item:this.getPossessions())
		{
			// add the weight of this item to the total weight.
			totalWeight = totalWeight + item.getWeight();
		}
		return totalWeight;
	}
	
	/**
	 * Returns the iths heaviest possession of this robot.
	 * 
	 * @param	index
	 * 			The index that indicates the ith heaviest possession.
	 * @return	The ith heaviest possession
	 * 			| result == this.getPossessions().get(index)
	 */
	public Item getIthHeaviestPossession(int index)
	{
		return this.getPossessions().get(index);
	}
	
	/**
	 * Returns a string representation of all the items this robot is carrying.
	 * 
	 * @return	"/" if this robot has no possessions.
	 * 			| if(this.getPossessions() == null) then result == "/"
	 * @return	A string-list of all the items this robot possesses.
	 * 			| for each item in this.getPossessions(): result == result + item.toString()
	 */
	public String getPossessionsString()
	{
		String possessionString = "";
		
		// this robot has no possessions
		if(this.getPossessions() == null)
		{
			possessionString = "/";
		}
		else
		{
			// make a string that contains all the possessions of this robot
			for(Item item: this.getPossessions())
			{
				possessionString = possessionString + item.toString() + ", ";
			}
		}
		return possessionString;
	}


	/**
	 * Picks up the given item. The item is added to the list of possessions of this robot.
	 * The item is inserted in the list of possessions based on its weight. 
	 * The list of possessions of a robot is sorted on the weight of the possessions, starting with the heaviest item (index 0).
	 * The board and position of the given item are set to null.
	 * 
	 * @param	item
	 * 			The item to be picked up.
	 * @pre		The given item must be a valid item to be picked up.
	 * 			| canPickUp(item)
	 * @post	The given item is added to the list of possessions of this robot.
	 * 			| (new this).getPossessions().contains(item)
	 * @post	The items in the list of possessions of this robot are sorted based on their weight, starting with the heaviest item (index 0).
	 * 			|	for i in 0..(new this).getPossessions().size-2:
	 * 			|			(new this).getPossessions().get(i).getWeight() <= (new this).getPossessions().get(i+1).getWeight()
	 * @post	The new size of the list of possessions of this robot is incremented with 1.
	 *			| (new this).getPossessions.size() ==  this.getPossessions.size() + 1
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 */
	public void pickUp(Item item) throws IllegalStateException
	{
		assert canPickUp(item):"The given item cannot be picked up by this robot.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		for(int i= 0; i <= getPossessions().size(); i++)
		{
			try
			{
				// if the item at position i is lighter than the given item, than put the given item before it.
				if (getPossessions().get(i).getWeight() <= item.getWeight())
				{
					getPossessions().add(i, item);
					item.setBoard(null);
					item.setPosition(null);
				}
			}
			// add the item to the end of the list; the given item is lighter than all the other items this robot is carrying.
			catch(IndexOutOfBoundsException exc)
			{
				item.setBoard(null);
				item.setPosition(null);
				getPossessions().add(item);
				break;
			}
		}

	}

	/**
	 * Checks whether this robot can pick up the given item.
	 * 
	 * @param	item
	 *			The item to be checked.
	 * @return	The given item cannot be null and it cannot be terminated for the result to be true.
	 * 			| result == (item != null) 
	 * 			|			 && (! item.isTerminated())
	 * @return	The position of the given item cannot be null
	 * 			and the position of the given item must be equal to the position of this robot for the result to be true.
	 *			| result == (item.getPosition() != null) 
	 *			|		     && (item.getPosition().equals(this.getPosition())) 
	 * @return	The board on which the given item is placed cannot be null
	 * 			and the board on which the given item is placed must be equal to the board on which this robot is placed for this result to be true.
	 *			| result == (item.getBoard() != null) 
	 *			|			 && (item.getBoard() == this.getBoard())
	 * @return	The sum of the resulting weight of all the items this robot was already carrying and the weight of the given item
	 * 			must be smaller than or equal to the maximal weight this robot can carry for the result to be true.
	 * 			| result == (this.getTotalWeightPossessions() + item.getWeight() <= getMaxWeightToCarry())
	 * @return	This robot cannot already carry the given item for the result to be true.
	 * 			| result == (! this.getPossessions().contains(item))
	 */
	public boolean canPickUp(Item item)
	{
		return (item != null) && (! item.isTerminated())
				&& (item.getPosition() != null) && (item.getPosition().equals(this.getPosition())) 
				&& (item.getBoard() != null) && (item.getBoard() == this.getBoard())
				&& (this.getTotalWeightPossessions() + item.getWeight() <= getMaxWeightToCarry())
				&& (! this.getPossessions().contains(item));
	}
	
	/**
	 * Returns the maximum weight a robot can carry.
	 */
	@Basic @Immutable
	public static final int getMaxWeightToCarry()
	{
		return maxWeightToCarry;
	}
	
	/**
	 * Variable representing the maximum weight a robot can carry.
	 */
	private static final int maxWeightToCarry = Integer.MAX_VALUE;

	/**
	 * Drops the given item. The item is removed from the list of possessions of this robot.
	 * The position of the given item is set to the position of this robot.
	 * The board on which the given item is placed is the board on which this robot is placed.
	 * 
	 * @param	item
	 * 			The item to be dropped.
	 * @pre		The given item must be a valid item to be dropped.
	 * 			| canDrop(item)
	 * @post	The new list of possessions of this robot does not contain the given item.
	 * 			|   !((new this).getPossessions().contains(item))
	 * @post	The new position of the given item is the position of this robot.
	 * 			| (new item).getPosition() == this.getPostion()
	 * @post	The new board on which the given item is placed is the board on which this robot is placed.
	 * 			| (new item).getBoard() == this.getBoard()
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 */
	public void drop(Item item) throws IllegalStateException
	{
		assert canDrop(item): "This robot cannot drop the given item.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		this.getPossessions().remove(item);
		item.setPosition(this.getPosition());
		item.setBoard(this.getBoard());
	}

	/**
	 * Checks whether this robot can drop the given item.
	 * 
	 * @param	item
	 * 			The item to be dropped.
	 * @return	The given item cannot be null.
	 * 			| result == (item != null)
	 * @result	This robot must carry the given item.
	 * 			| result == this.getPossessions().contains(item)
	 * @result 	The position and the board of the this robot cannot be null.
	 * 			| result == ((this.getBoard() != null) && (this.getPosition() != null))
	 */
	public boolean canDrop(Item item)
	{
		return (item != null)
				&& (this.getPossessions().contains(item)) && (this.getBoard() != null) && (this.getPosition() != null);
	}


	/**
	 * Use the given item.
	 * 
	 * @param	item
	 * 			The item to use.
	 * @pre		The robot must be able to use the given item
	 * 			| canUse(item)
	 * @effect	The robot uses this item
	 * 			| item.use(this)
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 */
	public void use(Item item) throws IllegalStateException
	{
		assert canUse(item): "The given item cannot be use by this robot.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		item.use(this);
	}
	
	/**
	 * Checks whether the given item can be used by this robot.
	 * 
	 * @param	item
	 * 			The item to be checked
	 * @return	The given item cannot be null or terminated
	 * 			| result == (item != null) && (!item.isTerminated())
	 * @return	The given item must be possessed by this robot
	 * 			| result == this.getPossessions().contains(item))
	 * @return	The given 
	 */
	public boolean canUse(Item item)
	{
		return ((item != null) && this.getPossessions().contains(item) && !item.isTerminated());
	}

	/**
	 * Variable representing all the possessions this robot is carrying.
	 */
	private java.util.List<Item> possessions = new java.util.ArrayList<Item>(0);




	/**
	 * Lets this robot shoot its laserbeam to destroy another element.
	 * One of the elements that is standing on the first position one encounters when 'walking' in a straight line
	 * from the position of the robot with its orientation, is terminated and removed from the board.
	 * If no element is standing within the shooting range of this robot, it will shoot anyway, though nothing is hit.
	 * 
	 * @pre		The robot must be able to use its laser.
	 * 			| canShoot()
	 * @effect	The energy level of this robot is decreased with the cost of shooting one time
	 * 			| this.setEnergy(this.getEnergy() - getCostToShoot())
	 * @throws	IllegalBoardException
	 * 			The robot must be placed on a board
	 * 			| this.getBoard() == null
	 * @throws	IllegatStateException
	 * 			When this robot has either no position, no orientation or no board or when it is terminated
	 * 			| this.getPostition() == null || this.getOrientation() == null || this.getBoard() == null || this.isTerminated()
	 */
	public void shoot() throws IllegalBoardException
	{
		assert canShoot(): "The preconditions of shoot() must be satisfied.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not shoot.");
		}
		// the energy of this robot is decreased with the energy it costs to shoot
		this.setEnergy(this.getEnergy() - getCostToShoot());
		
		try
		{
			// the element that is hit is terminated
			Element shotElement = this.getBoard().getFirstElementStartingAt(this.getPosition(), this.getOrientation());
			try
			{
				shotElement.terminate();
			}
			// no element is found within the shooting range of this robot.
			catch(NullPointerException exc)
			{
			}
		}
		catch(NullPointerException exc)
		{
			throw new IllegalStateException();
		}
	}
	

	
	/**
	 * Checks whether this robot can use its laser.
	 * 
	 * @return	True if this robot is standing on a board.
	 * 			| result == (this.getBoard() != null)
	 * @return	True if and only if the current amount of energy of this robot is greater than
	 * 			or equal to the amount of energy it costs for this robot to shoot its laser.
	 *			| result == (this.getEnergy() <= getCostToShoot())
	 */
	public boolean canShoot()
	{
		return (this.getEnergy() >= getCostToShoot()) && (this.getBoard() != null) && (this.getPosition() != null);
	}
	

	/**
	 * Turns this robot 90 degrees in clockwise direction if the robot has sufficient energy.
	 * Does not modify the state of the robot if it has insufficient energy.
	 * 
	 * @pre		The robot must have enough energy to turn
	 * 			| this.getEnergy() >= getCostToTurn()
	 * @effect	The current orientation of this robot is changed by turning 90 degrees clockwise
	 * 			if this robot has sufficient energy.
	 * 			| if(this.getEnergy() >= getCostToTurn())
	 * 			| then this.setOrientation(this.getOrientation().turn90Clockwise())
	 * 	     	|	    && this.setEnergy(this.getEnergy()-getCostToTurn())
	 * @throws	IllegalStateException
	 * 			When this robot is terminated.
	 * 			| this.isTerminated()
	 */
	public void turnClockwise() throws IllegalStateException
	{
		assert this.getEnergy() >= getCostToTurn(): "This robot has not enough enery to turn.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		this.setOrientation(this.getOrientation().turn90Clockwise());
		this.setEnergy(this.getEnergy() - getCostToTurn());
	}

	/**
	 * Turns this robot 90 degrees in counter-clockwise direction if the robot has sufficient energy.
	 * Does not modify the state of the robot if it has insufficient energy.
	 * 
	 * @pre		This robot must have enough energy to turn
	 * 			| this.getEnergy() >= getCostToTurn()
	 * @effect	The current orientation of this robot is changed by turning 90 degrees counter-clockwise
	 * 			if this robot has sufficient energy.
	 * 			| if(this.getEnergy() >= getCostToTurn())
	 * 			| then this.setOrientation(this.getOrientation().turn90CounterClockwise())
	 * 			|		&& this.setEnergy(this.getEnergy()-getCostToTurn())
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated
	 */
	public void turnCounterClockwise() throws IllegalStateException
	{
		assert this.getEnergy() >= getCostToTurn(): "This robot has not enough energy to turn.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}

		this.setOrientation(this.getOrientation().turn90CounterClockwise());
		this.setEnergy(this.getEnergy() - getCostToTurn());
	}
	
	/**
	 * Moves this robot one step in its current direction if the robot has sufficient energy.
	 * Does not modify the state of the robot if it has insufficient energy or 
	 * 
	 * @pre		This robot has enough energy to move
	 * 			| this.getEnergy() >= this.getTotalCostToMove()
	 * @effect	The current position is changed according to the current orientation of this robot if this robot had sufficient energy.
	 * 			For example, if this robot is facing to the right, he will move one step to the right.
	 *			| this.setPosition(this.getPosition().getNeighbour(this.getOrientation()))
	 * @effect	The energy level of this robot is decreased with the cost to move for this robot.
	 *			| this.setEnergy(this.getEnergy() - this.getTotalCostToMove())
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 */
	public void moveOneStep() throws IllegalStateException
	{
		assert (this.getEnergy() >= this.getTotalCostToMove()): "This robot has not enough energy to move.";

		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		try
		{
			// check whether this robot can be placed at the next position.
			this.getBoard().canElementBePutAtPosition(this.getPosition().getNeighbour(this.getOrientation()), this);
			// these setters are not executes when the previous checker throws an exception
			this.setPosition(this.getPosition().getNeighbour(this.getOrientation()));
			this.setEnergy(this.getEnergy() - this.getTotalCostToMove());
		}
		catch(IllegalPositionException exc)
		{
			System.err.println(exc.toString());
		}
		catch(IllegalBoardException exc)
		{
			System.err.println(exc.toString());
		}
		catch(IllegalElementCombinationException exc)
		{
			System.err.println(exc.toString());
		}
	}
	

	/**
	 * Checks whether this robot can share a position with another element.
	 * 
	 * @param	other
	 * 			The element where to this robot may be in conflict.
	 * @return	True if the other element is not an element
	 * 			| result == (other == null)
	 * @return	False if the other element is a.
	 * 			| result != (other instanceof Wall)
	 * @return	False if the other element is a robot.
	 * 			| result != (other instanceof Robot)
	 */
	@Override
	public boolean canSharePositionWith(Element other)
	{
		return ((other == null) && !(other instanceof Wall) && !(other instanceof Robot));
	}

	/**
	 * Energy is transferred from this robot to another IEnergyHolder.
	 * 
	 * @param	other
	 * 			The IEnergyHolder to be given energy.
	 * @param	amount
	 * 			The amount of energy to be transferred.
	 * @see		Interface IEnergyHolder
	 * 			roboRallyPackage.gameElementClasses.IEnergyHolder#transferEnergy(double)
	 * @throws	UnsupportedOperationException
	 * 			Always throw this exception when invoked on a robot.
	 * 			| this instance of Robot
	 */
	public void transferEnergy(IEnergyHolder other, double amount) throws IllegalStateException, UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * This robots maximum energy level is decreased with 4000 Ws if possible, otherwise it is terminated.
	 * 
	 * @effect	The robots maximum energy level is decreased with 4000 Ws if possible, otherwise it is terminated.
	 * 			| if(this.canHaveAsMaxEnergy(this.getMaxEnergy()-4000))
	 * 			|  then this.setMaxEnergy(this.getMaxEnergy()-4000)
	 * 			| else this.terminate()
	 * @throws	IllegalStateException
	 * 			When this robot is terminated.
	 * 			| this.isTerminated()
	 */
	@Override
	public void takeHit() throws IllegalStateException
	{
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot be hit by a laser.");
		}
		
		// this robots maximum energy level can be decreased with 4000 Ws.
		if(this.canHaveAsMaxEnergy(this.getMaxEnergy()-4000))
		{
			this.setMaxEnergy(this.getMaxEnergy()-4000);
		}
		// the robots maximum energy reaches 0 when decreasing it.
		else
		{
			this.terminate();
		}
	}
	
	/**
	 * Returns a string representation of this robot.
	 * 
	 * @return	A string of this robot.
	 * 			| result == "Robot with:" + "\n"
	 *			| 			+ super.toString() + "\n" 
	 *			| 			+ " energy level: " + this.getEnergy() + "(" + getEnergyFraction() + "%)"
	 */
	@Override
	public java.lang.String toString()
	{
		return "Robot with:" + "\n"
				+ super.toString() + ";  " + "\n"
				+ " Energy level: " + this.getEnergy() + " (" + getEnergyFraction() + "%) " + ";  " + "\n"
				+ " Possessions: " + this.getPossessionsString();
	}

	
	
	/**
	 * Moves this robot and the given robot as close as possible to each other
	 * consuming as little energy as possible and taking in account the current energy they have.
	 * No inefficient moves or turns are included.
	 * 
	 * @param	other
	 * 			The other robot where to this robot has to end up as close as possible (both can move!)
	 * @post	Given two positions that are reachable by this robot and the given other robot (they have enough energy to reach it).
	 * 			Either the distance between them is greater than the distance between the new positions of this robot and the given other robot
	 * 			or they are not valid positions for these robots and their boards (e.g. there may be an element that conflicts with a robot)
	 * 			or they are equal to the new positions of this robot and the given other robot
	 * 			| for each {position1 | position1 in Position && this.getEnergyRequiredToReach(position1) is in (0..this.getEnergy())} and
	 * 			|		   {position2 | position2 in Position && other.getEnergyRequiredToReach(position2) is in (0..other.getEnergy())}:
	 * 			|  ((new this).getPosition().getManhattanDistance((new other).getPosition()) <= position1.getManhattanDistance(position2)
	 * 			|	  || ! this.getBoard().canElementBePutAtPosition(position1, this)
	 * 			|	  || ! other.getBoard().canElementBePutAtPosition(position2, other))
     *			|	  || ((new this).getPosition() == position1 && (new other).getPosition() == position2)
     * @effect	The energy this robot is decreased with the energy it needs to reach the position where it ends up.
     * 			| this.setEnergy(this.getEnergy() - this.getEnergyRequiredToReach((new this).getPosititon()))
     * @effect	The energy the given other robot is decreased with the energy it needs to reach the position where it ends up.
     * 			| other.setEnergy(other.getEnergy() - other.getEnergyRequiredToReach((new other).getPosititon()))
	 * @throws	IllegalBoardException
	 * 			When this robot and the given other robot are not placed on the same board.
	 * 			| other.getBoard() != this.getBoard()
	 * @throws	IllegalStateException
	 * 			When this robot or the other robot are terminated or when both robots are not standing on a board
	 * 			| this.isTerminated() || other.isTerminated() || this.getBoard() == null || other.getBoard() == null
	 */
	public void moveNextTo(Robot other) throws IllegalBoardException,
											   IllegalStateException
	{
		if(other.getBoard() != this.getBoard())
		{
			throw new IllegalBoardException(other, this.getBoard());
		}
		if(this.isTerminated() || other.isTerminated() || this.getBoard() == null || other.getBoard() == null)
		{
			throw new IllegalStateException();
		}
		
		DijkstraSP thisShortestPaths = new DijkstraSP(this.getBoard().createDiGraphForRobot(this), 0);
		DijkstraSP otherShortestPaths = new DijkstraSP(this.getBoard().createDiGraphForRobot(other), 0);

		java.util.List<OrientatedPosition> thisVertices = this.getBoard().createListVertices(this);
		java.util.List<OrientatedPosition> otherVertices = other.getBoard().createListVertices(other);

		// initialize the search-values with a maximum value
		long manhattanDistance = Long.MAX_VALUE;
		double bestTotalEnergy = Double.POSITIVE_INFINITY;

		OrientatedPosition thisBestOrientatedPosition = new OrientatedPosition(this.getOrientation(),this.getPosition());
		OrientatedPosition otherBestOrientatedPosition = new OrientatedPosition(other.getOrientation(),other.getPosition());

		for(OrientatedPosition thisOrientatedPosition: thisVertices)
		{
			// this robot has enough energy to reach thisOrientatedPosition
			if(thisShortestPaths.distTo(thisVertices.indexOf(thisOrientatedPosition)) <= this.getEnergy())
			{
				for(OrientatedPosition otherOrientatedPosition: otherVertices)
				{
					// the other robot has enough energy to reach the otherOrientatedPosition
					if(otherShortestPaths.distTo(otherVertices.indexOf(otherOrientatedPosition)) <= other.getEnergy())
					{
						// calculate the manhattan distance between thisOrientatedPosition and otherOrientatedPosition
						// and the total energy for this robot to reach thisOrientatedPosition and for the other robot to reach otherOrientatedPosition
						long possibleBetterManhattanDistance = thisOrientatedPosition.getPosition().getManhattanDistance(otherOrientatedPosition.getPosition());
						double possibleBetterTotalEnergy = thisShortestPaths.distTo(thisVertices.indexOf(thisOrientatedPosition)) + otherShortestPaths.distTo(otherVertices.indexOf(otherOrientatedPosition));
						
						// the robots are not allowed to end up at the same position
						if(possibleBetterManhattanDistance > 0)
						{
							// if(possibleBetterManhattanDistance == manhattanDistance && (possibleBetterTotalEnergy >= bestTotalEnergy)) continue
							// if(possibleBetterManhattanDistance > manhattanDistance) continue
							
							// either the manhattan distance of these positions is less than the current best positions;
							//        the manhattan distance of these positions is equal to the current best positions, but the total energy cost is less than the current best energy cost;
							//		  the current total energy cost is still on its initilized-value (maximum): this is the first calculated combination of orientatedPositions.
							if((possibleBetterManhattanDistance < manhattanDistance)
								|| (possibleBetterManhattanDistance == manhattanDistance && possibleBetterTotalEnergy < bestTotalEnergy)
								|| (bestTotalEnergy == Double.POSITIVE_INFINITY))
							{
								// all the current best-values are updated.
								bestTotalEnergy = possibleBetterTotalEnergy;
								thisBestOrientatedPosition = thisOrientatedPosition;
								otherBestOrientatedPosition = otherOrientatedPosition;
								manhattanDistance = possibleBetterManhattanDistance;
							}
						}
					}
				}
			}
		}
		
		// move this robot to the found best orientatedPosition and reduce its energy
		this.setPosition(thisBestOrientatedPosition.getPosition());
		this.setOrientation(thisBestOrientatedPosition.getOrientation());
		this.setEnergy(this.getEnergy() - thisShortestPaths.distTo(thisVertices.indexOf(thisBestOrientatedPosition)));
		
		// move the other robot to the found best orientatedPosition and reduce its energy
		other.setPosition(otherBestOrientatedPosition.getPosition());
		other.setOrientation(otherBestOrientatedPosition.getOrientation());
		other.setEnergy(other.getEnergy() - otherShortestPaths.distTo(otherVertices.indexOf(otherBestOrientatedPosition)));		
	}	
}

