
package roboRallyPackage.gameElementClasses;

import roboRallyPackage.*;
import roboRallyPackage.commandClasses.Program;
import roboRallyPackage.exceptionClasses.IllegalBoardException;
import roboRallyPackage.exceptionClasses.IllegalElementCombinationException;
import roboRallyPackage.exceptionClasses.IllegalPositionException;
import roboRallyPackage.exceptionClasses.IllegalSyntaxException;
import roboRallyPackage.pathFindingClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 *A class representing robots.
 * 
 * @invar	The orientation of each robot must be a valid orientation.
 * 			| this.canHaveAsOrientation(this.getOrientation())
 * @invar	The total weight of the items a robot can carry must not exceed the maximum weight it can carry.
 * 			| this.getTotalWeightToCarry() <= getMaxWeightToCarry()
 * @invar	An item that this robot is carrying cannot have a Board or a Position
 * 			| for each item in this.getPossessions(): item.getBoard() == null && item.getPosition() == null
 * @invar	A robot cannot carry a terminated item
 * 			| for each item in this.getPossessions().size(): ! item.isTerminated()
 * @invar	A terminated Robot cannot carry any items or alter its state.
 * 			| if this.isTerminated() then this.getPossessions().size() == 0
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Robot extends Element implements IEnergyHolder
{
	/**
	 * Initialize this new robot with given position, board, orientation, currentEnergy [Ws] and maxEngery [Ws].
	 * 
	 * @param 	position
	 * 			The position for this new robot.
	 * @param	board
	 * 			The board on which this new robot is placed.
	 * @param	orientation
	 * 			The orientation for this new robot.
	 * @param	currentEnergy
	 * 			The current energy level for this new robot, expressed in watt-second [Ws].
	 * @param	maxEnergy
	 * 			The maximum amount of energy this new robot can ever have, expressed in watt-second [Ws].
	 * @effect	This new robot is initialized as an Element with the given position and board.
	 * 			| super(position, board)
	 * @effect	The new orientation of this new robot is equal to the given orientation.
	 * 			| this.setOrientation(orientation)
	 * @effect	The new current energy of this new robot is equal to the given current energy, expressed in watt-second [Ws], if this is a valid value for current energy.
	 * 			| if(canHaveAsEnergy(currentEnergy)	then this.setEnergy(currentEnergy)
	 * @post	If the value of maxEnergy, expressed in watt-second [Ws], is a valid value, the new maximum energy level of this new robot is set to this value (in Ws).
	 * 			| if(canHaveAsMaxEnergy(maxEnergy)) then (new this).getMaxEnergy == maxEnergy
	 */
	public Robot(Position position, Board board, Orientation orientation, double currentEnergy, double maxEnergy)
	{	
		super(position, board);

		this.setOrientation(orientation);
		this.setEnergy(currentEnergy);
		this.setMaxEnergy(maxEnergy);
	}
	
	/**
	 * Create a new robot with standard values.
	 * 
	 * @effect	Initialize the robot with standard values
	 * 			| this(null,null,Orientation.RIGHT,0,20000)
	 */
	public Robot()
	{	
		this(null,null,Orientation.RIGHT,0,20000);
	}

	/**
	 * Terminates this robot. All its possessions are also terminated and removed from the robots list.
	 * 
	 * @post	This robot is terminated
	 * 			| (new this).isTerminated()
	 * @post	All the elements of this robot are terminated
	 * 			| for each item in this.getPossessions(): (new this).isTerminated
	 * @post	All the elements this robot possesses are removed from the list that keeps these items
	 * 			| (new this).getPossessions().size() == 0
	 */
	@Override
	public void terminate()
	{
		for(Item item: this.getPossessions())
		{
			this.removeItem(item);
			item.terminate();
		}
		super.terminate();
	}
	
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
	public double getEnergy(EnergyUnit unit)
	{
		return currentEnergy.toEnergyUnit(unit).getAmount();
	}
	
	/**
	 * Sets the amount of energy of this robot to the given amount of energy, expressed in watt-seconds [Ws].
	 */
	@Override
	public void setEnergy(double currentEnergy) throws IllegalStateException
	{
		assert (canHaveAsEnergy(currentEnergy)): "Preconditions for setEnergy(double) must be satisfied.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}
		
		this.currentEnergy = new EnergyAmount(currentEnergy, EnergyUnit.WATTSECOND);
	}
	
	/**
	 * Checks whether the given amount of energy, expressed in Watt-second [Ws], is a valid amount of energy for this robot.
	 */
	@Override
	public boolean canHaveAsEnergy(double energy)
	{
		return ((energy <= this.getMaxEnergy()) && (EnergyAmount.isValidEnergyAmount(energy)));
	}
	
	/**
	 * Variable representing the current amount of energy of this robot.
	 */
	private EnergyAmount currentEnergy = EnergyAmount.WATTSECOND_0;
	
	
	
	
	
	
	/**
	 * Returns the variable representing the maximum amount of energy this robot can have, expressed in watt-seconds [Ws].
	 */
	@Basic @Override
	public final double getMaxEnergy()
	{
		return maxEnergy.getAmountInWattSecond();
	}
	
	/**
	 * Sets the maximum energy level of this Robot to the given maximum energy level, expressed in watt-seconds [Ws].
	 */
	@Override
	public void setMaxEnergy(double maxEnergyAmount) throws IllegalStateException,
															UnsupportedOperationException
	{
		assert this.canHaveAsMaxEnergy(maxEnergyAmount): "The given maximum energy level is not a valid maximum energy level for this robot.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		
		this.maxEnergy = new EnergyAmount(maxEnergyAmount, EnergyUnit.WATTSECOND);;
	}
	
	/**
	 * Check whether the given maximum energy, expressed in Watt-second [Ws], is a valid maximum energy for this robot.
	 * 
	 * @param 	maxEnergy
	 * 			The maximum energy level to be checked.
	 * @return	False if the given maximum energy is less than the most expensive cost for an action (otherwise this robot will never be able to do that action)
	 * 			| result != (maxEnergy < java.util.Collections.max(getListCosts()))
	 * @return	False if the given energy is greater than 20000 Ws.
	 * 			| result != (energy > 20000)
	 */
	@Override
	public boolean canHaveAsMaxEnergy(double maxEnergy)
	{
		return (maxEnergy <= 20000 && maxEnergy >= java.util.Collections.max(getListCosts()) && EnergyAmount.isValidEnergyAmount(maxEnergy));
	}
	
	/**
	 * Variable representing the maximum amount of energy this robot can have.
	 */
	private EnergyAmount maxEnergy = new EnergyAmount((double) 20000,EnergyUnit.WATTSECOND);
	

	/**
	 * Returns a percentage of the current energy level relative to the maximum amount of energy this robot can have.
	 * 
	 * @return	A percentage of the current energy level relative to the maximum amount of energy this robot can have.
	 * 			| result == (this.getEnergy(EnergyUnit.WATTSECOND) / this.getMaxEnergy()) * 100
	 */
	@Override
	public double getEnergyFraction()
	{
		return (this.getEnergy(EnergyUnit.WATTSECOND) / this.getMaxEnergy()) * 100;
	}
	
	/**
	 * Recharges this robot with the given amount of energy, expressed in watt-seconds [Ws].
	 */
	@Override
	public void recharge(double amount) throws IllegalStateException
	{
		assert canAcceptForRecharge(amount): "This robot cannot accept the given amoutn of energy for reacharge.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot recharge.");
		}
		this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) + amount);
	}
	
	/**
	 * Checks whether the given amount of energy, expressed in watt-seconds [Ws], can be added to the current energy level of this robot.
	 */
	@Override
	public boolean canAcceptForRecharge(double amount)
	{
		return (this.getEnergy(EnergyUnit.WATTSECOND) + amount <= getMaxEnergy() 
				&& EnergyAmount.isValidEnergyAmount(amount));
	}
	
	
	/**
	 * Transfers the given amount of energy, expressed in watt-seconds [Ws], from this robot to another IEnergyHolder.
	 */
	@Override
	public void transferEnergy(IEnergyHolder other, double amount) throws IllegalStateException, UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the minimum amount of energy, expressed in watt-second [Ws], this robot needs to move to the given position.
	 * Returns -1 if this robot cannot reach the given position.
	 * This would mean that either the robot has insufficient energy to reach the given position or the position is not reachable because of obstacles.
	 * 
	 * @param	position
	 * 			The position where to this robot may be moved to.
	 * @return	The minimum amount of energy this robot needs to move to the given position.
	 *  		| energyForPositionWithDifferingOrientation
	 *  		|    == {DijkstraSP(this.getBoard().createDiGraphForRobot(this), 0).distTo(thisVertices.indexOf(new OrientatedPosition(position, orientation)))
	 *  		|          for all orientation in orientation.values()}
	 *			| result
	 *			|    == {there exists an energy in energyForPositionWithDifferingOrientation | energy <= otherEnergy
	 * 			|			for all otherEnergy in energyForPositionWithDifferingOrientation}
	 * @return	-1 if this robot robot hasn't got enough energy left to reach the given position or if the given position already contains an element 
	 * 			| for all orientatedPosition in {OrientatedPosition | orientatedPosition.getPosition() == position && orientatedPosition.getOrientation() !=n ull}
	 * 			|	if (! (this.getBoard().createListVertices(this).contains(orientatedPosition)))
	 * 			|		then result == -1
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 * @throws	IllegalBoardException
	 * 			When this robot is not placed on a board.
	 * 			| this.getBoard() == null
	 */	
	public double getEnergyRequiredToReach(Position position) throws IllegalStateException,
																	 IllegalBoardException
	{
		if(this.getBoard() == null)
		{
			throw new IllegalBoardException(this, null);
		}

		// create a Dijkstra that can calculate the shortest path to the given position
		DijkstraSP thisShortestPaths = new DijkstraSP(this.getBoard().createDiGraphForRobot(this), 0);
		java.util.List<OrientatedPosition> thisVertices = this.getBoard().createListVertices(this);

		// for all orientated positions of the given position, the required energy is calculated, using the shortest path to get there.
		// the least of these energies are selected and returned in the end of this method.
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
			// the orientated position is not part of the vertex, so this robot cannot reach that orientated position
			catch(ArrayIndexOutOfBoundsException exc)
			{
				return -1;
			}
		}
		// this robot had not enough energy to reach the given position
		if(energy < this.getEnergy(EnergyUnit.WATTSECOND))
		{
			return -1;
		}
		else
		{
			return energy;
		}
	}
	
	
	
	
	/**
	 * Returns the energy cost of turning 90 degree, expressed in Watt-second [Ws].
	 */
	@Basic @Immutable
	public static final double getCostToTurn()
	{
		return costToTurn.getAmountInWattSecond();
	}
	
	/**
	 * Variable representing the loss of energy for a robot when turning 90 degrees, expressed in Watt-second [Ws].
	 */
	private static final EnergyAmount costToTurn = new EnergyAmount((double)100, EnergyUnit.WATTSECOND);
	
	/**
	 * Returns the energy cost of shooting, expressed in Watt-second [Ws].
	 */
	@Basic @Immutable
	public static final double getCostToShoot()
	{
		return costToShoot.getAmountInWattSecond();
	}

	/**
	 * Variable representing the loss of energy for a robot when shooting one time, expressed in Watt-second [Ws].
	 */
	private static final EnergyAmount costToShoot = new EnergyAmount((double)1000, EnergyUnit.WATTSECOND);

	/**
	 * Returns the energy cost of moving one step, expressed in Watt-second [Ws].
	 * This method does not take into account whether a robot is carrying items or not.
	 */
	@Basic @Immutable
	public static final double getCostToMove()
	{
		return costToMove.getAmountInWattSecond();
	}
	
	/**
	 * Variable representing the loss of energy for a robot when moving one step, expressed in Watt-second [Ws].
	 */
	private static final EnergyAmount costToMove = new EnergyAmount((double) 500, EnergyUnit.WATTSECOND);
	
	/**
	 * Returns the the additional loss of energy for each kilogram of items this robot is carrying when moving one step, expressed in Watt-second [Ws].
	 */
	@Basic @Immutable
	public static final double getAdditionalCostToMove()
	{
		return additionalCostToMove.getAmountInWattSecond();
	}
	
	/**
	 * Variable representing the additional loss of energy for each kg a robot is carrying when moving one step, expressed in Watt-second [Ws].
	 */
	private static final EnergyAmount additionalCostToMove = new EnergyAmount((double) 50, EnergyUnit.WATTSECOND);
	
	/**
	 * Returns the total energy cost of moving this robot one step, expressed in Watt-second [Ws].
	 * This method takes into account the normal cost and the additional cost per kilogram of items this robot is carrying.
	 * 
	 * @return	The cost to move increased with the additional cost to move times the total weight of all the possessions this robot is carrying.
	 * 			| result == getCostToMove() + Math.round(getAdditionalCostToMove() * (this.getTotalWeightPossessions()/1000))
	 */
	public double getTotalCostToMove()
	{
		return getCostToMove() + Math.round(getAdditionalCostToMove() * (this.getTotalWeightPossessions()/1000));
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
	 * Returns the total weight of all the elements this robot is carrying, expressed in grams [g].
	 * 
	 * @return	The total weight of all the items this robot is carrying, expressed in kilograms [g].
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
	 * 			| result == this.getPossessions().get(index - 1)
	 * @throws	IndexOutOfBoundsException 
	 * 			When the given index is not a valid index
	 * 			| index <= 0 || index > this.getPossessions().size()
	 */
	public Item getIthHeaviestPossession(int index) throws IndexOutOfBoundsException 
	{
		return this.getPossessions().get(index - 1);
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
				possessionString = possessionString + item.getClass().getName() + ", ";
			}
		}
		return possessionString;
	}


	/**
	 * Adds an item to the list of items of this robot. The item is inserted on the right position into the list.
	 * The items in the list are ordered by weight: starting with the heaviest item.
	 * 
	 * @param	item
	 * 			The item to be added to the list of items
	 * @pre		The item can be carried by this robot
	 * 			| this.canCarry(item)
	 * @post	The given item is added to the list of possessions of this robot.
	 * 			| (new this).getPossessions().contains(item)
	 * @post	The new size of the list of possessions of this robot is incremented with 1.
	 *			| (new this).getPossessions.size() ==  this.getPossessions.size() + 1
	 * @post	The items in the list of possessions of this robot are sorted based on their weight, starting with the heaviest item (index 0).
	 * 			| for i in 0..(new this).getPossessions().size-2:
	 * 			|	(new this).getPossessions().get(i).getWeight() >= (new this).getPossessions().get(i+1).getWeight()
	 */
	@Model
	private void addItem(Item item)
	{
		assert(this.canCarry(item)):"This robot cannot carry this item.";
		
		for(int i= 0; i <= getPossessions().size(); i++)
		{
			try
			{
				// if the item at position i is lighter than the given item, than put the given item before it.
				if (getPossessions().get(i).getWeight() >= item.getWeight())
				{
					getPossessions().add(i, item);
				}
			}
			// add the item to the end of the list; the given item is lighter than all the other items this robot is carrying.
			catch(IndexOutOfBoundsException exc)
			{
				this.possessions.add(item);
				break;
			}
		}
	}
	
	/**
	 * Checks whether this robot carry the given item.
	 * 
	 * @param	item
	 *			The item to be checked.
	 * @return	False if the given item is null.
	 * 			| result != (item == null)
	 * @return	False if the given item is terminated.
	 * 			| result != item.isTerminated()
	 * @return	False if the sum of the resulting weight of all the items this robot was already carrying and the weight of the given item
	 * 			is greater than the maximal weight this robot can carry.
	 * 			| result != (this.getTotalWeightPossessions() + item.getWeight() > getMaxWeightToCarry())
	 * @return	False when this robot is already carrying the given item.
	 * 			| result ! this.getPossessions().contains(item)
	 */
	public boolean canCarry(Item item)
	{
		return (item != null) && (! item.isTerminated())
				&& (this.getTotalWeightPossessions() + item.getWeight() <= getMaxWeightToCarry())
				&& (! this.getPossessions().contains(item));
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
	 * 			| this.canPickUp(item)
	 * @effect	The given item is added to the list of items of this robot.
	 * 			| this.addItem(item)
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated() || item.isTerminated()
	 */
	public void pickUp(Item item) throws IllegalStateException
	{
		assert this.canPickUp(item):"The given item cannot be picked up by this robot.";
		if(this.isTerminated() || item.isTerminated())
		{
			throw new IllegalStateException("Either this robot or this item is terminated. The robot cannot pick up the item.");
		}
		addItem(item);
		item.getBoard().removeElement(item);
		item.setPosition(null);
	}

	/**
	 * Checks whether this robot can pick up the given item.
	 * 
	 * @param	item
	 *			The item to be checked.
	 * @return	False if the item cannot be added to the list of items.
	 * 			| result == this.canCarry(item)
	 * @return	False if the given item does not have a position
	 * 			| result != (item.getPosition() == null)
	 * @return	False if the position of the given item is not equal to the position of this robot.
	 *			| result != !(item.getPosition().equals(this.getPosition()))
	 * @return  False if the given item is not standing on a board
	 * 			| result != (item.getBoard() == null) 
	 * @return	False if the board on which the given item is placed is not equal to the board on which this robot is placed.
	 *			| result != !(item.getBoard() == this.getBoard())
	 */
	public boolean canPickUp(Item item)
	{
		return (this.canCarry(item))
				&& (item.getPosition() != null) && (item.getPosition().equals(this.getPosition())) 
				&& (item.getBoard() != null) && (item.getBoard() == this.getBoard());
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
	 * Removes the given item from the list of items.
	 * If this robot is not carrying the given item, nothing changes.
	 * 
	 * @param	item
	 * 			The item to be removed
	 * @post	The robot is no longer carrying the given item
	 * 			| ! this.getPossessions().contains(item)
	 */
	@Model
	private void removeItem(Item item)
	{
		this.possessions.remove(item);
	}

	/**
	 * Drops the given item. The item is removed from the list of possessions of this robot.
	 * The position of the given item is set to the position of this robot.
	 * The board on which the given item is placed is the board on which this robot is placed.
	 * 
	 * @param	item
	 * 			The item to be dropped.
	 * @pre		The given item must be a valid item to be dropped.
	 * 			| this.canDrop(item)
	 * @post	The new list of possessions of this robot does not contain the given item.
	 * 			| !((new this).getPossessions().contains(item))
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
		assert this.canDrop(item): "This robot cannot drop the given item.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot pick up, carry, use or drop items.");
		}
		this.removeItem(item);
		item.setPosition(this.getPosition());
		this.getBoard().putElement(this.getPosition(), item);
	}

	/**
	 * Checks whether this robot can drop the given item.
	 * 
	 * @param	item
	 * 			The item to be dropped.
	 * @return	False if the given item is null.
	 * 			| result != (item == null)
	 * @result 	False if this robot is not standing on a board (this implies that the robot has a position as well)
	 * 			| result != (this.getBoard() == null)
	 * @result	False if this robot is not carrying the given item.
	 * 			| result != !this.getPossessions().contains(item)
	 */
	public boolean canDrop(Item item)
	{
		return (item != null)
				&& (this.getBoard() != null)
				&& (this.getPossessions().contains(item)) ;
	}


	/**
	 * Use the given item.
	 * 
	 * @param	item
	 * 			The item to use.
	 * @pre		The robot must be able to use the given item
	 * 			| this.canUse(item)
	 * @effect	The robot uses this item
	 * 			| item.use(this)
	 * @post	If the item is terminated after is had been used, it is removed from the list of possession of this robot.
	 * 			| if((new item).isTerminated()) then !(new this).getPossessions().contains(item)
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 */
	public void use(Item item) throws IllegalStateException
	{
		assert this.canUse(item): "The given item cannot be use by this robot.";
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot pick up, carry, use or drop items.");
		}
		
		item.use(this);
		
		if(item.isTerminated())
		{
			this.removeItem(item);
		}
	}
	
	/**
	 * Checks whether the given item can be used by this robot.
	 * 
	 * @param	item
	 * 			The item to be checked
	 * @return	False if the given item is null or terminated
	 * 			| result == (item != null)
	 * @return	The given item must be carried by this robot (this implies that it is not terminated)
	 * 			| result == this.getPossessions().contains(item))
	 */
	public boolean canUse(Item item)
	{
		return ((item != null) && this.getPossessions().contains(item));
	}
	
	/**
	 * Transfers all the items of this robot to the given other robot if possible.
	 * If not all items can be transferred, as much items as possible are transferred.
	 * 
	 * @param	other
	 * 			The other robot that will be given the items.
	 * @throws	When both robots are not placed next to each other
	 * 			| !this.getPosition().getAllNeighbours().contains(other.getPosition())
	 * @throws	IllegalBoardException
	 * 			When both robots are not placed on the same board.
	 * 			| this.getBoard() != other.getBoard()
	 * @throws	IllegalStateException
	 * 			When either this robot or the given robot is terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 */
	public void transferItems(Robot other) throws IllegalPositionException, IllegalBoardException, IllegalStateException
	{
		if(!java.util.Arrays.asList(this.getPosition().getAllNeighbours()).contains(other.getPosition()))
		{
			throw new IllegalPositionException("Both robots are not placed next to each other.");
		}
		if(this.getBoard() != other.getBoard())
		{
			throw new IllegalBoardException("Both robots are not placed on the same board.");
		}
		if(this.isTerminated() || other.isTerminated())
		{
			throw new IllegalStateException("Either this robot or the given robot is terminated. The items cannot be transferred.");
		}
		
		for(Item item: this.getPossessions())
		{
			// this other robot can carry the item.
			// the item is removed from the list of items of this robot and added to the list of the other robot.
			if(other.canCarry(item))
			{
				this.getPossessions().remove(item);
				other.addItem(item);
			}
		}
	}

	/**
	 * Variable representing all the possessions this robot is carrying.
	 */
	private java.util.List<Item> possessions = new java.util.ArrayList<Item>(0);




	/**
	 * Turns this robot 90 degrees in clockwise direction if the robot has sufficient energy.
	 * Does not modify the state of the robot if it has insufficient energy.
	 * 
	 * @pre		The robot must have enough energy to turn
	 * 			| this.getEnergy(EnergyUnit.WATTSECOND) >= getCostToTurn()
	 * @effect	The current orientation of this robot is changed by turning 90 degrees clockwise
	 * 			| this.setOrientation(this.getOrientation().turn90Clockwise())
	 * @effect	The energy level of this robot is decreased with the cost of one turn
	 * 	     	| this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - getCostToTurn())
	 * @throws	IllegalStateException
	 * 			When this robot is terminated.
	 * 			| this.isTerminated()
	 */
	public void turnClockwise() throws IllegalStateException
	{
		assert this.getEnergy(EnergyUnit.WATTSECOND) >= getCostToTurn(): "This robot has not enough enery to turn.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot turn.");
		}
		this.setOrientation(this.getOrientation().turn90Clockwise());
		this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - getCostToTurn());
	}

	/**
	 * Turns this robot 90 degrees in counter-clockwise direction if the robot has sufficient energy.
	 * Does not modify the state of the robot if it has insufficient energy.
	 * 
	 * @pre		The robot must have enough energy to turn
	 * 			| this.getEnergy(EnergyUnit.WATTSECOND) >= getCostToTurn()
	 * @effect	The current orientation of this robot is changed by turning 90 degrees counter clockwise
	 * 			| this.setOrientation(this.getOrientation().turn90CounterClockwise())
	 * @effect	The energy level of this robot is decreased with the cost of one turn
	 * 	     	| this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - getCostToTurn())
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated
	 */
	public void turnCounterClockwise() throws IllegalStateException
	{
		assert this.getEnergy(EnergyUnit.WATTSECOND) >= getCostToTurn(): "This robot has not enough energy to turn.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot turn.");
		}

		this.setOrientation(this.getOrientation().turn90CounterClockwise());
		this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - getCostToTurn());
	}
	
	/**
	 * Moves this robot one step in its current direction if the robot has sufficient energy.
	 * Does not modify the state of the robot if it has insufficient energy or if it has been terminated.
	 * 
	 * @pre		This robot has enough energy to move
	 * 			| this.getEnergy(EnergyUnit.WATTSECOND) >= this.getTotalCostToMove()
	 * @effect	The current position is changed according to the current orientation of this robot.
	 * 			For example, if this robot is facing to the right, he will move one step to the right.
	 *			| this.setPosition(this.getPosition().getNeighbour(this.getOrientation()))
	 * @effect	The energy level of this robot is decreased with the cost to move for this robot.
	 *			| this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - this.getTotalCostToMove())
	 * @throws	IllegalStateException
	 * 			When this robot is terminated
	 * 			| this.isTerminated()
	 */
	public void moveOneStep() throws IllegalStateException
	{
		assert (this.getEnergy(EnergyUnit.WATTSECOND) >= this.getTotalCostToMove()): "This robot has not enough energy to move.";

		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot move.");
		}
		try
		{
			// check whether this robot can be placed at the next position.
			this.getBoard().canElementBePutAtPosition(this.getPosition().getNeighbour(this.getOrientation()), this);
			// these setters are not executes when the previous checker throws an exception
			this.getBoard().moveElement((this.getPosition().getNeighbour(this.getOrientation())),this);
			this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - this.getTotalCostToMove());
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
	 * 			| for each {position1 | position1 in Position && this.getEnergyRequiredToReach(position1) is in (0..this.getEnergy(EnergyUnit.WATTSECOND))} and
	 * 			|		   {position2 | position2 in Position && other.getEnergyRequiredToReach(position2) is in (0..other.getEnergy(EnergyUnit.WATTSECOND))}:
	 * 			|  ((new this).getPosition().getManhattanDistance((new other).getPosition()) <= position1.getManhattanDistance(position2)
	 * 			|	  || ! this.getBoard().canElementBePutAtPosition(position1, this)
	 * 			|	  || ! other.getBoard().canElementBePutAtPosition(position2, other))
	 *			|	  || ((new this).getPosition() == position1 && (new other).getPosition() == position2)
	 * @effect	The energy this robot is decreased with the energy it needs to reach the position where it ends up.
	 * 			| this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - this.getEnergyRequiredToReach((new this).getPosititon()))
	 * @effect	The energy the given other robot is decreased with the energy it needs to reach the position where it ends up.
	 * 			| other.setEnergy(other.getEnergy(EnergyUnit.WATTSECOND) - other.getEnergyRequiredToReach((new other).getPosititon()))
	 * @throws	IllegalBoardException
	 * 			When this robot and the given other robot are not placed on the same board or they are not standing on any board.
	 * 			| other.getBoard() != this.getBoard() || this.getBoard() == null || other.getBoard() == null
	 * @throws	IllegalStateException
	 * 			When this robot or the other robot are terminated
	 * 			| this.isTerminated() || other.isTerminated()
	 */
	public void moveNextTo(Robot other) throws IllegalBoardException,
											   IllegalStateException
	{
		if(other.getBoard() != this.getBoard() || this.getBoard() == null || other.getBoard() == null)
		{
			throw new IllegalBoardException("Both robots are not standing on the same board or on no board at all.");
		}
		if(this.isTerminated() || other.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot move.");
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
			if(thisShortestPaths.distTo(thisVertices.indexOf(thisOrientatedPosition)) <= this.getEnergy(EnergyUnit.WATTSECOND))
			{
				for(OrientatedPosition otherOrientatedPosition: otherVertices)
				{
					// the other robot has enough energy to reach the otherOrientatedPosition
					if(otherShortestPaths.distTo(otherVertices.indexOf(otherOrientatedPosition)) <= other.getEnergy(EnergyUnit.WATTSECOND))
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
		this.getBoard().moveElement(thisBestOrientatedPosition.getPosition(), this);
		this.setOrientation(thisBestOrientatedPosition.getOrientation());
		this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - thisShortestPaths.distTo(thisVertices.indexOf(thisBestOrientatedPosition)));
		
		// move the other robot to the found best orientatedPosition and reduce its energy
		other.getBoard().moveElement(otherBestOrientatedPosition.getPosition(), other);
		other.setOrientation(otherBestOrientatedPosition.getOrientation());
		other.setEnergy(other.getEnergy(EnergyUnit.WATTSECOND) - otherShortestPaths.distTo(otherVertices.indexOf(otherBestOrientatedPosition)));		
	}

	/**
	 * Checks whether this robot can share a position with another element.
	 */
	@Override
	public boolean canSharePositionWith(Element other)
	{
		return ( !(other instanceof Wall) && !(other instanceof Robot));
	}

	/**
	 * Lets this robot shoot its laserbeam to destroy another element.
	 * One of the elements that is standing on the first position one encounters when 'walking' in a straight line
	 * from the position of the robot with its orientation, is terminated and removed from the board.
	 * If no element is standing within the shooting range of this robot, it will shoot anyway, though nothing is hit.
	 * 
	 * @pre		The robot must be able to use its laser.
	 * 			| this.canShoot()
	 * @effect	The energy level of this robot is decreased with the cost of shooting one time
	 * 			| this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - getCostToShoot())
	 * @throws	IllegatStateException
	 * 			When this is terminated
	 * 			| this.isTerminated()
	 */
	public void shoot() throws IllegalStateException
	{
		assert this.canShoot(): "This robot cannot shoot.";
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated robot cannot shoot.");
		}
		
		// the energy of this robot is decreased with the energy it costs to shoot
		this.setEnergy(this.getEnergy(EnergyUnit.WATTSECOND) - getCostToShoot());
		
		try
	    {
	            // the element that is hit is terminated
	            Element shotElement = this.getBoard().getFirstElementStartingAt(this.getPosition(), this.getOrientation());
	            try
	            {
	                    shotElement.takeHit();
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
	 * @return	False if this robot is not standing on a board.
	 * 			| result != (this.getBoard() == null)
	 * @return	False if this robot does not have a position.
	 * 			| result != (this.getPosition() == null)
	 * @return	False the current amount of energy of this robot is less than the amount of energy it costs for this robot to shoot its laser.
	 *			| result != (this.getEnergy(EnergyUnit.WATTSECOND) < getCostToShoot())
	 */
	public boolean canShoot()
	{
		return ((this.getBoard() != null) && (this.getPosition() != null) && (this.getEnergy(EnergyUnit.WATTSECOND) >= getCostToShoot()));
	}

	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * This robots maximum energy level is decreased with 4000 Ws if possible, otherwise it is terminated.
	 * 
	 * @effect	The robots maximum energy level is decreased with 4000 Ws if possible, otherwise it is terminated.
	 * 			| if(this.canHaveAsMaxEnergy(this.getMaxEnergy() - 4000))
	 * 			|  then this.setMaxEnergy(this.getMaxEnergy() - 4000)
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

			if(this.getMaxEnergy()<this.getEnergy(EnergyUnit.WATTSECOND))
				this.setEnergy(this.getMaxEnergy());
		}
		// the robots maximum energy reaches 0 when decreasing it.
		else
		{
			this.terminate();
		}
	}
	/**
	 * Variable representing a program this robot has saved in its memory.
	 */
	private Program program = null;

	/**
	 * Returns the program of this robot.
	 */
	@Basic
	public Program getProgram()
	{
		return program;
	}

	/**
	 * Sets the program of this robot, given a program input.
	 * 
	 * @param	program
	 * 			The new program of this robot
	 * @post	The program of this robot is set to the given program
	 * 			| (new this).getProgram() == program
	 */
	public void setProgram(Program program) 
	{
		this.program = program;
	}
	
	/**
	 * Sets the program of this robot, given a string input.
	 * 
	 * @param	inputProgram
	 * 			A string representation of a program. The program to be set.
	 * @effect	The program of this robot is set to the given program
	 * 			| this.setProgram(Parser.parse(0,this,inputProgram))
	 * @throws	IllgalSyntaxException
	 * 			When the given string is not a valid string representation of a program
	 * 			| !Parser.isValidStringProgram()
	 */
	public void setProgram(String inputProgram) throws IllegalSyntaxException
	{
		Program program = Parser.parse(0, this, inputProgram);
		this.setProgram(program);
	}
	
	/**
	 * Runs n steps of this robots program.
	 * One step of the program means that it will run the program until a basic command (e.g. 'move')
	 * 
	 * @param	n
	 * 			The number of steps to be executed
	 * @effect	The robot will execute n steps of its program
	 * 			| for i = 1..n: this.getProgram.executeStep(this)
	 */
	public void runProgramStep(int n)
	{
		for(int i = 1; i <= n; i++)
		{
			this.getProgram().executeStep(this);
		}
	}

	/**
	 * Returns a string representation of this robot.
	 * 
	 * @return	A string of this robot.
	 * 			| result == "Robot with:" + "\n"
	 *			| 			+ super.toString() + "\n" 
	 *			| 			+ " Energy level: " + this.getEnergy(EnergyUnit.WATTSECOND) + "(" + this.getEnergyFraction() + "%)" + ";  " + "\n"
	 *			+ " Possessions: " + this.getPossessionsString();
	 */
	@Override
	public java.lang.String toString()
	{
		return "Robot with:" + "\n"
				+ super.toString() + ";  " + "\n"
				+ " Energy level: " + this.getEnergy(EnergyUnit.WATTSECOND) + " (" + this.getEnergyFraction() + "%) " + ";  " + "\n"
				+ " Possessions: " + this.getPossessionsString();
	}
	
	
}

