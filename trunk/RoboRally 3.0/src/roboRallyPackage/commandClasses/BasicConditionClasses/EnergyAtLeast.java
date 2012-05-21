
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
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
	public String toString()
	{
		return"(energy-at-least " + this.getEnergyConditionWattSecond() +")";
	}
}
