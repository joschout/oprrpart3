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
public class EnergyAtLeast extends BasicCondition
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
