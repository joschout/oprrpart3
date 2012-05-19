/**
 * 
 */
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
/**
 * @author Nele
 *
 */
public class AtItem extends BasicCondition
{
	public AtItem(Robot robot)
	{
		super(robot);
	}
	
	public boolean results()
	{
		for(Element element: this.getRobot().getBoard().getElements(this.getRobot().getPosition()))
		{
			if(element instanceof Item)
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString(){
		return "(at-item)";
		
	}
}
