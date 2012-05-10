/**
 * 
 */
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.CombinedCondition.*;
/**
 * @author Nele
 *
 */
public class EnergyAtLeast extends CombinedCondition
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
