
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
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
	@Override
	public String toString(){
		return "(true)";
	}
}
