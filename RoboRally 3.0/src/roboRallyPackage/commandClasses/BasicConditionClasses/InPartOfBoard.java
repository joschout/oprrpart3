
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.Element;
import roboRallyPackage.gameElementClasses.Robot;
import roboRallyPackage.Position;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing an in-subrange condition
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class InPartOfBoard extends BasicCondition
{
	/**
	 * Initializes this in-subrange condition with the given program level, robot and positions.
	 * 
	 * @param	programLevel
	 * 			The program level of this new in-subrange condition.
	 * @param	robot
	 * 			The robot of this new in-subrange condition.
	 * @param	position1
	 * 			The first position of this new in-subrange condition.
	 * @param	position2
	 * 			The second position of this new in-subrange condition.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| (new this).getRobot() == robot
	 * @post	...
	 * 			| (new this).getPosition1() == position1
	 * @post	...
	 * 			| (new this).getPosition2() == position2
	 */
	public InPartOfBoard(int programLevel, Robot robot, Position position1, Position position2)
	{
		super(programLevel);
		this.robot = robot;
		this.position1 = position1;
		this.position2 = position2;
	}

	/**
	 * Initializes this in-subrange condition with the given program level, robot and coordinates.
	 * The given list of coordinates represent two positions with coordinates (x,y)
	 * These positions will be (coordinates[0], coordinates[1]) and (coordinates[2], coordinates[3]).
	 * 
	 * @param	programLevel
	 * 			The program level of this new in-subrange condition.
	 * @param	robot
	 * 			The robot of this new in-subrange condition.
	 * @param	coorinates
	 * 			The couple (coordinates[0], coordinates[1]) should represent the (x,y) coordinates of the first position
	 * 			The couple (coordinates[2], coordinates[3]) should represent the (x,y) coordinates of the second position
	 * @effect	...
	 * 			| this(programLevel, robot, new Position(coordinates[0], coordinates[1]), new Position(coordinates[2], coordinates[3]))
	 */
	public InPartOfBoard(int programLevel, Robot robot, long[] coordinates)
	{
		this(programLevel, robot, new Position(coordinates[0], coordinates[1]), new Position(coordinates[2], coordinates[3]));
	}
	
	/**
	 * Returns the robot of this in-subrange condition.
	 */
	@Basic
	public Robot getRobot()
	{
		return this.robot;
	}
	
	/**
	 * Variable representing the robot of this in-subrange condition.
	 */
	private Robot robot;
	
	/**
	 * Returns the first position of this in-subrange condition.
	 */
	@Basic
	public Position getPosition1() 
	{
		return this.position1;
	}

	/**
	 * Variable representing the first position of this in-subrange condition.
	 */
	private Position position1;

	/**
	 * Returns the second position of this in-subrange condition.
	 */
	@Basic
	public Position getPosition2() 
	{
		return this.position2;
	}

	/**
	 * Variable representing the second position of this in-subrange condition.
	 */
	private Position position2;

	/**
	 * Returns the result of this in-subrange condition.
	 * This will be true if the robot of this condition is placed between the rectangle formed by the two positions of the condition.
	 * 
	 * @effect	...
	 * 			| result == this.results(this.getRobot())
	 */
	@Override
	public boolean results()
	{
		return this.results(this.getRobot());
	}

	/**
	 * Returns the result of this true condition, given the given element.
	 * This will be true if the given element is placed between the rectangle formed by the two positions of the condition.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	...
	 * 			| result == ((element.getPosition().getCoordX() >= this.getPosition1().getCoordX()) && (element.getPosition().getCoordX() <= this.getPosition2().getCoordX()))
	 *			|			 || ((element.getPosition().getCoordX() <= this.getPosition1().getCoordX()) && (element.getPosition().getCoordX() >= this.getPosition2().getCoordX()))
	 * @return	...
	 * 			| result == ((element.getPosition().getCoordY() >= this.getPosition1().getCoordY()) && (element.getPosition().getCoordY() <= this.getPosition2().getCoordY()))
	 *			|		     || ((element.getPosition().getCoordY() <= this.getPosition1().getCoordY()) && (element.getPosition().getCoordY() >= this.getPosition2().getCoordY()))
	 */
	@Override
	public boolean results(Element element)
	{
		// checks whether the x coordinate of the given element lies between the x coordinates of the two positions of this inPartOfBoard-condition
		if(((element.getPosition().getCoordX() >= this.getPosition1().getCoordX()) && (element.getPosition().getCoordX() <= this.getPosition2().getCoordX()))
				|| ((element.getPosition().getCoordX() <= this.getPosition1().getCoordX()) && (element.getPosition().getCoordX() >= this.getPosition2().getCoordX())))
		{
			// checks whether the y coordinate of the given element lies between the y coordinates of the two positions of this inPartOfBoard-condition
			if(((element.getPosition().getCoordY() >= this.getPosition1().getCoordY()) && (element.getPosition().getCoordY() <= this.getPosition2().getCoordY()))
					|| ((element.getPosition().getCoordY() <= this.getPosition1().getCoordY()) && (element.getPosition().getCoordY() >= this.getPosition2().getCoordY())))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * String representation of this in-subrange condition.
	 * 
	 * @return	...
	 * 			| result == "(in-subrange " + this.getPosition1().getCoordX() + ", " + this.getPosition1().getCoordY() + ", "
							   + this.getPosition2().getCoordX() + ", " + this.getPosition2().getCoordY() + ")"
	 */
	@Override
	public String toString()
	{
		return "(in-subrange " + this.getPosition1().getCoordX() + ", " + this.getPosition1().getCoordY() + ", "
							   + this.getPosition2().getCoordX() + ", " + this.getPosition2().getCoordY() + ")";
	}
	
	/**
	 * String representation of this in-subrange condition, in the syntax used by the Parser.
	 * 
	 * @return	...
	 * 			| result == "(in-subrange 'X1-number', 'Y1-number', 'X2-number', 'Y2-number')"
	 */
	@Override
	public String getNotationExample()
	{
		return "(in-subrange 'X1-number', 'Y1-number', 'X2-number', 'Y2-number')";
	}
}
