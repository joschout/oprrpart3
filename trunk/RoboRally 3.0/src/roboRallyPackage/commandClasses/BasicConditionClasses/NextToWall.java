
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class NextToWall extends BasicCondition
{
	public NextToWall(int programLevel,Robot robot)
	{
		super(programLevel);
		this.robot = robot;
	}
	
	private Robot robot;
	
	public Robot getRobot(){
		return this.robot;
	}
	
//	public boolean results()
//	{
//		for(Position position: this.getRobot().getPosition().getAllNeighbours())
//		{
//			for(Element element: this.getRobot().getBoard().getElements(position))
//			{
//				if(element instanceof Wall)
//				{
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	@Override
	public String toString()
	{
		return "(wall)";
	}

	@Override
	public boolean results() {
		return this.results(this.getRobot());
	}

	@Override
	public boolean results(Element element) throws IllegalArgumentException {
		if(!(element instanceof Robot)){
		throw new IllegalArgumentException();
		}
		
		Position rightPosition = element.getPosition().getNeighbour(Orientation.RIGHT);
		for(Element el: element.getBoard().getElements(rightPosition))
			{
				if(el instanceof Wall)
				{
					return true;
				}
			}
		return false;
	}
}

