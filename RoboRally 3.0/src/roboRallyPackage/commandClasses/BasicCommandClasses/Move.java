
package roboRallyPackage.commandClasses.BasicCommandClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Move extends BasicCommand
{
	public Move() //Robot robot)
	{
//		super(robot);
	}
	
	public void execute(Robot robot)
	{
		robot.moveOneStep();
	}
	@Override
	public String toString(){
		return "(move)";
	}
}
