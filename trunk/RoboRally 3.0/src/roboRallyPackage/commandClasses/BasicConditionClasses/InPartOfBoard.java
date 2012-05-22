/**
 * 
 */
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.Element;
import roboRallyPackage.gameElementClasses.Robot;
import roboRallyPackage.Position;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class InPartOfBoard extends BasicCondition
{
	/**
	 * 
	 * @param programLevel
	 * @param robot
	 * @param	coordinates
	 * 			the couple (coordinates[0], coordinates[1]) should represent the (x,y) coordinates of the first position
	 * 			the couple (coordinates[2], coordinates[3]) should represent the (x,y) coordinates of the second position
	 */
	public InPartOfBoard(int programLevel, Robot robot, long[] coordinates)
	{
		super(programLevel);
		this.robot = robot;
		this.position1 = new Position(coordinates[0], coordinates[1]);
		this.position2 = new Position(coordinates[2], coordinates[3]);
	}
	
	public InPartOfBoard(int programLevel, Robot robot, Position position1, Position position2)
	{
		super(programLevel);
		this.robot = robot;
		this.position1 = position1;
		this.position2 = position2;
	}

	private Robot robot;
	
	public Robot getRobot()
	{
		return this.robot;
	}
	
	private Position position1;
	
	public Position getPosition1() 
	{
		return this.position1;
	}

	public Position getPosition2() 
	{
		return this.position2;
	}

	private Position position2;

	@Override
	public boolean results()
	{
		return this.results(this.getRobot());
	}

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

	@Override
	public String toString()
	{
		return "(in-subrange " + this.getPosition1().getCoordX() + ", " + this.getPosition1().getCoordY() + ", "
							   + this.getPosition2().getCoordX() + ", " + this.getPosition2().getCoordY() + ")";
	}
	
	@Override
	public String getNotationExample()
	{
		return "(in-subrange 'X1-number', 'Y1-number', 'X2-number', 'Y2-number')";
	}
}
