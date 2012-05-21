
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class PickupAndUse extends BasicCommand
{
	public PickupAndUse(Robot robot)
	{
		super(robot);
	}
	
	public void execute()
	{
		for(Element element: this.getRobot().getBoard().getElements(this.getRobot().getPosition()))
		{
			if(element instanceof Item)
			{
				this.getRobot().pickUp((Item) element);
				this.getRobot().use((Item) element);
				break;
			}
		}
	}
	@Override
	public String toString(){
		return "(pick-up-and-use)";
	}
}
