/**
 * 
 */
package roboRallyPackage.commandClasses.ConditionCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.ConditionCommand.*;
/**
 * @author Nele
 *
 */
public class EnergyAtLeast extends ConditionCommand
{
	public EnergyAtLeast(Robot robot, double energy)
	{
		super(robot);
		this.energy = energy;
	}
	
	private double energy;

	public boolean results() 
	{
		return (this.getRobot().getEnergy() >= energy);
	}
}
