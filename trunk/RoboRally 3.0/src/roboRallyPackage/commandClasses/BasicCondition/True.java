/**
 * 
 */
package roboRallyPackage.commandClasses.BasicCondition;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
/**
 * @author Nele
 *
 */
public class True extends BasicCondition
{
	public True(Robot robot)
	{
		super(robot);
	}
	
	public boolean results()
	{
		return true;
	}
}
