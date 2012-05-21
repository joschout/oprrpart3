
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class EnergyAtLeast extends BasicCondition
{
	public EnergyAtLeast(int programLevel,IEnergyHolder holder, double energy)//Robot robot, double energy)
	{
		super(programLevel);
		this.energyCondition = new EnergyAmount(energy, EnergyUnit.WATTSECOND);
		
	}
	public IEnergyHolder holder;
	
	
	public IEnergyHolder getIEnergyHolder(){
		return this.holder;
	}
	
	private EnergyAmount energyCondition;
	
	public double getEnergyConditionWattSecond(){
		return energyCondition.getAmountInWattSecond();
	}

	public boolean results() 
	{
		return (this.getIEnergyHolder().getEnergy(EnergyUnit.WATTSECOND) >= energyCondition.getAmountInWattSecond());
	}
	
	@Override
	public String toString()
	{
		return"(energy-at-least " + this.getEnergyConditionWattSecond() +")";
	}
}
