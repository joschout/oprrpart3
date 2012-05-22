
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class CanHitRobot extends BasicCondition
{
	public CanHitRobot(int programLevel, Robot robot)
	{
		super(programLevel);
		this.robot = robot;
	}
	
	private Robot robot;
	
	public Robot getRobot()
	{
		return this.robot;
	}
	
	
	public boolean results()
	{
		return this.results(this.getRobot());
	}
	
	@Override
	public boolean results(Element element) throws IllegalArgumentException {
		if(!(element instanceof Robot)){
			throw new IllegalArgumentException();
		}
		// get the first position in the line of the given robot that contains an element
		Element testElement = element.getBoard().getFirstElementStartingAt(element.getPosition(), ((Robot)element).getOrientation());
	
		// if some element is found it may be possible that more elements are placed on that position
		// if one of them is a robot, this method should return true
		if(testElement != null)
		{
			for(Element el: element.getBoard().getElements(testElement.getPosition()))
			{
				if(el instanceof Robot)
				{
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public String toString()
	{
		return "(can-hit-robot)";
	}
	
	@Override
	public String getNotationExample()
	{
		return "(can-hit-robot)";
	}
}
