
package roboRallyPackage.commandClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class Command extends Program
{
	public Command()
	{
		
	}
//	public Command(Robot robot)
//	{
//		this.robot = robot;
//	}
	
//	protected Robot getRobot()
//	{
//		return robot;
//	}
	
//	private Robot robot;
	
	public abstract void execute(Robot robot);
	
	public abstract void executeStep(Robot robot);
}
