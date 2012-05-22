
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
		this.energy = new EnergyAmount(energy, EnergyUnit.WATTSECOND);
		this.holder = holder;
	}
	public IEnergyHolder holder;
	
	
	public IEnergyHolder getIEnergyHolder()
	{
		return this.holder;
	}
	
	private EnergyAmount energy;
	
	public double getEnergyWattSecond()
	{
		return energy.getAmountInWattSecond();
	}

	public boolean results() 
	{
		return (this.getIEnergyHolder().getEnergy(EnergyUnit.WATTSECOND) >= this.getEnergyWattSecond());
	}
	
	public boolean results(Element element) throws IllegalArgumentException
	{
		if(!(element instanceof IEnergyHolder))
		{
			throw new IllegalArgumentException("This element is not an IEnergyholder. It is not possible to ckeck its energy.");
		}
		return (((IEnergyHolder) element).getEnergy(EnergyUnit.WATTSECOND) >= this.getEnergyWattSecond());
	}
	
	@Override
	public String toString()
	{
		return"(energy-at-least " + this.getEnergyWattSecond() + ")";
	}
	
	@Override
	public String getNotationExample()
	{
		return "(energy-at-least 'energy-number')";
	}
}
