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
		this.energyCondition = new EnergyAmount(energy, EnergyUnit.WATTSECOND);
		
	}
	
	private EnergyAmount energyCondition;
	
	public double getEnergyConditionWattSecond(){
		return energyCondition.getAmountInWattSecond();
	}

	public boolean results() 
	{
		return (this.getRobot().getEnergy(EnergyUnit.WATTSECOND) >= energyCondition.getAmountInWattSecond());
	}
	
	@Override
	public String toString(){
		return"(energy-at-least " + this.getEnergyConditionWattSecond() +")";
		
	}
}
