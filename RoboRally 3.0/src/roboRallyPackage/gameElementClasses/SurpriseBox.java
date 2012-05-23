
package roboRallyPackage.gameElementClasses;

import java.util.Random;

import roboRallyPackage.Board;
import roboRallyPackage.Position;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class of SurpriseBoxes. Extends Item.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 *
 */
public class SurpriseBox extends Item
{
	/**
	 * Initialize this new SurpriseBox with the given position, board and weight [g].
	 * 
	 * @param 	position
	 * 			The position for this new element.
	 * @param	board
	 * 			The board on which this new element will be placed.
	 * @param	weight
	 * 			The weight for this new SurpriseBox, expressed in grams [g].
	 * @effect	This new surprise box is initialized as an Item with the given position, board and weight.
	 * 			| super(position, board, weight)
	 */
	public SurpriseBox(Position position, Board board, int weight)
	{
		super(position, board, weight);
	}
	
	/**
	 * Initialize this new SurpriseBox with the given weight [g].
	 * 
	 * @param	weight
	 * 			The weight for this new SurpriseBox, expressed in grams [g].
	 * @effect	...
	 * 			| this(null, null, weight)
	 */
	public SurpriseBox(int weight)
	{
		this(null, null, weight);
	}
	
	/**
	 * Returns the variable representing a list of all the items this surprise box can contain.
	 */
	@Basic
	private Item[] getSurpriseItems()
	{
		return surpriseItems;
	}

	/**
	 * Initialize the list with all the items that need to be added to the list of items this surprise box can contain.
	 * These are: a battery, a repair kit and another surprise box.
	 * 
	 * Only items are allowed to be added to the list.
	 * 
	 * @post	...
	 * 			| (new this).getSurprisItems().contains(new Battery(2500, 5))
	 * @post	...
	 * 			| (new this).getSurprisItems().contains(new RepairKit(500,5))
	 * @post	...
	 * 			| (new this).getSurprisItems().contains(new SurpriseBox(5))
	 * @post	...
	 * 			| (new this).getSurprisItems().length == 3
	 */
	private void addAllSurpriseItems()
	{
		Item[] newSurpriseItems = new Item[3];
		newSurpriseItems[0] = new Battery(2500, 5);
		newSurpriseItems[1] = new RepairKit(500,5);
		newSurpriseItems[2] = new SurpriseBox(5);
		surpriseItems = newSurpriseItems;
	}
	
	/**
	 * Variable representing all the items that this surprise box can contain.
	 */
	private Item[] surpriseItems = new Item[0];

	/**
	 * Returns the variable representing a list of all the methods this surprise can invoke when using a surprise box.
	 */
	@Basic
	public java.lang.reflect.Method[] getSurpriseMethods()
	{
		return surpriseMethods;
	}
	
	/**
	 * Add a method the the list of methods than can be invoked when using a surprise box.
	 * 
	 * @param	method
	 * 			The method to be added.
	 * @pre		...
	 * 			| this.isValidSurpriseMethod(method)
	 * @post	...
	 * 			| this.getSurpriseMethods().length + 1 = (new this).getSurpriseMethods().length
	 * @post	...
	 * 			| (new this).getSurpriseMethods().contains(method)
	 */
	@Model
	private void addSurpriseMethod(java.lang.reflect.Method method)
	{
		assert this.isValidSurpriseMethod(method): "The given method is not a valid method to add to the list of surprise methods.";
		
		// a new list, one size greater that the current list of methods is created.
		java.lang.reflect.Method newSurprises[] = new java.lang.reflect.Method[surpriseMethods.length + 1];
		for(int i = 0; i < surpriseMethods.length; i++)
		{
			newSurprises[i] = surpriseMethods[i];
		}
		// the new method is added to the list and the old list is replaced with the new list.
		newSurprises[newSurprises.length - 1] = method;
		surpriseMethods = newSurprises;
	}

	/**
	 * Initialize the list of methods with all the methods that can be invoked when using a surprise box.
	 * These are: takeHit(), teleport() and otherItem()
	 * 
	 * @post	...
	 * 			| (new this).getSurpriseMethods().contains(this.getClass().getMethod("takeHit", new Class[0]))
	 * @post	...
	 * 			| (new this).getSurpriseMethods().contains(this.getClass().getMethod("teleport", Robot.class))
	 * @post	...
	 * 			| (new this).getSurpriseMethods().contains(this.getClass().getMethod("otherItem", Robot.class))
	 * @post	...
	 * 			| (new this).getSurpriseMethods().length == 3
	 */
	@Model
	private void addAllSurpriseMethods()
	{
		// note: only public methods are found using Class.getMethod()
	
		try
		{
			java.lang.reflect.Method explodeMethod;
			explodeMethod = this.getClass().getMethod("takeHit", new Class[0]);
			this.addSurpriseMethod(explodeMethod);
		}
		catch (NoSuchMethodException e)
		{
		}
		catch (SecurityException e)
		{
		}
		
		try
		{
			java.lang.reflect.Method teleportMethod;
			teleportMethod = this.getClass().getMethod("teleport", Robot.class);
			this.addSurpriseMethod(teleportMethod);
		}
		catch (NoSuchMethodException e) 
		{
		}
		catch (SecurityException e) 
		{
		}
		
		try
		{
			java.lang.reflect.Method otherItemMethod;
			otherItemMethod = this.getClass().getMethod("otherItem", Robot.class);
			this.addSurpriseMethod(otherItemMethod);
		} 
		catch (NoSuchMethodException e)
		{
		}
		catch (SecurityException e) 
		{
		}
		
	}

	/**
	 * Checks whether the given method is a valid method to be added to the list of surprise methods.
	 * These checks concern the number and the type of parameters the given method requires.
	 * For, in use() one of these methods may be invoked and only those with a valid number and type of parameters can properly be invoked.
	 * 
	 * @param	method
	 * 			The method to be checked.
	 * @return	...
	 * 			| method.getParameterTypes().length == 0
	 * @return	...
	 * 			| method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == Robot.class
	 */
	private boolean isValidSurpriseMethod(java.lang.reflect.Method method)
	{
		return (method.getParameterTypes().length == 0)
				|| (method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == Robot.class);
	}

	/**
	 * Variable representing all the methods that can be invoked when using a surprise box.
	 */
	private java.lang.reflect.Method[] surpriseMethods = new java.lang.reflect.Method[0];

	
	/**
	 * Teleports the given robot to a random position on the board.
	 * If the robot is not standing on a board, it is not moved.
	 * If the calculated random position is not a valid position for the given robot,
	 * a new random position is calculated until a valid position is found or more that 25 positions are checked.
	 * If no valid position is found after 25 checks, the robot is not moved.
	 * 
	 * @param	robot
	 * 			The robot to be moved
	 * @post	...
	 * 			| if(robot.getBoard() != null)
	 * 			|  then for some random i in 0..robot.getBoard().getWidth() and some random j in 0..robot.getBoard().getHeigth():
	 * 			|   if(robot.getBoard().canElementBePutAtPosition(new Position(i,j),robot)
	 * 			|    then robot.setPostion(new Position(i,j)
	 * 			|	else calculate new random position until more than 25 positions are checked
	 */
	public void teleport(Robot robot)
	{
		// the robot is not standing on a board, nothing happens.
		if(robot.getBoard() == null)
		{		
		}
		else
		{
			boolean stop = false;
			int i = 0;

			while(!stop || i >= 25)
			{
				// create a random number between 0 and the width of the board of the given robot
				// and create a random number between 0 and the height of the board of the given robot.
				Random generator = new Random();
				long randomX = (long) generator.nextInt((int) robot.getBoard().getWidth());
				long randomY = (long) generator.nextInt((int) robot.getBoard().getHeight());

				// this is valid random position for the robot; move the robot to the position.
				if(robot.getBoard().canElementBePutAtPosition(new Position(randomX, randomY), robot))
				{
					robot.getBoard().putElement(new Position(randomX, randomY), robot);
					stop = true;
				}
				// the while-loop will be re-invoked till either a valid random position is found or more than 25 random position are checked.
				else
				{
					i++;
				}
			}
			// when no valid position is found after 25 invocations of the while-statement, the robot will not be moved.
		}
	}
	
	/**
	 * A surprise box can contain another item; this item is added to the list of possessions of the given robot.
	 * The added item must be an item stored in the list of items this surprise box holds (this.getSurpriseItems())
	 * This method can be invoked when using a surprise box.
	 * 
	 * @param	robot
	 * 			The robot to be given the surprise item.
	 * @effect	...
	 * 			| for a random i in 0..this.getSurpriseItems().length - 1: robot.addItem(this.getSurpriseItems()[i])
	 * @post	...
	 * 			| robot.getPossession().length + 1 = (new robot).getPossessions().length
	 */
	public void otherItem(Robot robot)
	{
		// create a random number between 0 and the number of surprise items (-1)
		Random generator = new Random();
		int randomInt = generator.nextInt(getSurpriseItems().length - 1);

		// add the new, random selected, item to the possessions of the robot, if possible.
		this.getSurpriseItems()[randomInt].setPosition(robot.getPosition());
		this.getSurpriseItems()[randomInt].setBoard(robot.getBoard());
		if(robot.canPickUp(this.getSurpriseItems()[randomInt]))
		{
			robot.pickUp(this.getSurpriseItems()[randomInt]);
			System.out.println("A " + this.getSurpriseItems()[randomInt].toString() + " is added to the possessions of the robot.");
		}
		else
		{
			this.getSurpriseItems()[randomInt].terminate();
			System.out.println("The surprise box tunred out to be an " + this.getSurpriseItems()[randomInt].toString() + " but the robot was not able to pick it up, so it was terminated.");
		}
	}
	

	/**
	 * The given robot uses this surprise box. One of the methods in getSurpriseMethods() will be invoked.
	 * In the end this surprise box will be terminated and thus removed from the list of items of the robot.
	 * 
	 * @effect	...
	 * 			| for one random integer i in 0..this.getSurpriseMethods().length - 1:
	 * 			|	this.getSurpriseMethods()[i].invoke(robot)
	 * @effect	...
	 * 			| this.terminate()
	*/
	@Override
	public void use(Robot robot) throws IllegalStateException
	{
		assert robot.getPossessions().contains(this): "The given robot does is not carrying this item.";

		this.addAllSurpriseMethods();
		this.addAllSurpriseItems();
		
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated battery can not be altered.");
		}
		if(robot.isTerminated())
		{
			throw new IllegalStateException("A terminated robot can not be altered.");
		}

		// create a random number
		Random generator = new Random();
		int randomInt = generator.nextInt(this.getSurpriseMethods().length - 1);

		// a random method is selected and invoked
		try
		{
			System.out.println("The surprise box had the following effect: " + this.getSurpriseMethods()[randomInt].getName());
			
			// the method does not require any parameter
			if(this.getSurpriseMethods()[randomInt].getParameterTypes().length == 0)
			{
				this.getSurpriseMethods()[randomInt].invoke(this, new Object[0]);
			}
			// the method requires the given robot as parameter.
			if(this.getSurpriseMethods()[randomInt].getParameterTypes().length == 1 && this.getSurpriseMethods()[randomInt].getParameterTypes()[0] == Robot.class)
			{
				this.getSurpriseMethods()[randomInt].invoke(this, robot);
			}
		}
		catch(Exception exc)
		{

		}
		
		// this surprise box is terminated
		this.terminate();
	}


	
	/**
	 * When an element is hit (e.g. it is shot by a robot) some of its properties are altered.
	 * A surprise box explodes and hits all elements on the same position as the box itself
	 * and all the elements on the neighbouring positions of the position of the surprise box.
	 * In the end the surprise box is terminated.
	 * 
	 * @effect	All the elements on the position of this surprise box are hit.
	 * 			| for each element in this.getBoard().getElements(this.getPosition()): element.takeHit()
	 * @effect	All the elements that are standing on a position next to the position of this surprise box are hit.
	 * 			| for each position in this.getPosition().getAllNeighbours():
	 * 			|   for each element in this.getBoard().getElements(position): element.takeHit()
	 * @effect	The surprise box is terminated in the end.
	 * 			| this.terminate()
	 */
	@Override
	public void takeHit() throws IllegalStateException
	{
		if(this.isTerminated())
		{
			throw new IllegalStateException("A terminated surprise box cannot be hit.");
		}
		
		// all the elements on the position of this surprise box are hit.
		for(Element element : this.getBoard().getElements(this.getPosition()))
		{
			element.takeHit();
		}
		
		// all the elements on the neighouring position of the position of this surprise box are hit.
		for(Position position: this.getPosition().getAllNeighbours())
		{
			for(Element element: this.getBoard().getElements(position))
			{
				element.takeHit();
			}
		}
		
		// this surprise box is hit.
		this.terminate();
	}

	/**
	 * Returns a string representation of this surprise box.
	 * 
	 * @return	...
	 * 			| result == "Surprise box with:" + "\n"
	 *			| 			+ super.toString() + "\n"
	 */
	@Override
	public java.lang.String toString()
	{
		return "Surprise box { " + super.toString() + "}";
	}
}

