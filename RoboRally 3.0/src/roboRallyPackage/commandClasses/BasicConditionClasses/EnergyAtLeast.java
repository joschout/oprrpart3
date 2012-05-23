
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;
import be.kuleuven.cs.som.annotate.*;

/**
 * Class representing an energy-at-least condition.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class EnergyAtLeast extends BasicCondition
{
	/**
	 * Initializes this energy-at-least condition with the given program level, IEnergyHolder and energy.
	 * 
	 * @param	programLevel
	 * 			The program level of this new energy-at-least condition.
	 * @param	holder
	 * 			The IEnergyHolder of this new energy-at-least condition.
	 * @param	energy
	 * 			The energy of this new energy-at-least condition.
	 * @effect	...
	 * 			| super(programLevel)
	 * @post	...
	 * 			| (new this).getIEnergyHolder() == holder
	 * @post	...
	 * 			| (new this).getEnergyWattSecond() == energy
	 */
	public EnergyAtLeast(int programLevel, IEnergyHolder holder, double energy)
	{
		super(programLevel);
		this.energy = new EnergyAmount(energy, EnergyUnit.WATTSECOND);
		this.holder = holder;
	}
	
	/**
	 * Returns the IEnergyHolder of this energy-at-least condition.
	 */
	@Basic
	public IEnergyHolder getIEnergyHolder()
	{
		return this.holder;
	}
	
	/**
	 * Variable representing the IEnergyHolder of this energy-at-least condition. 
	 */
	private IEnergyHolder holder;

	/**
	 * Returns the energy of this energy-at-least condition in watt-seconds [Ws].
	 */
	@Basic
	public double getEnergyWattSecond()
	{
		return energy.getAmountInWattSecond();
	}

	/**
	 * Variable representing the energy of this energy-at-least condition.
	 */
	private EnergyAmount energy;


	/**
	 * Returns the result of this energy-at-least condition.
	 * This will be true if the energy of the robot of this condition is greater that the energy level of this energy-at-least condition.
	 * 
	 * @return	...
	 * 			| result == (this.getIEnergyHolder().getEnergy(EnergyUnit.WATTSECOND) >= this.getEnergyWattSecond())
	 */
	public boolean results() 
	{
		return (this.getIEnergyHolder().getEnergy(EnergyUnit.WATTSECOND) >= this.getEnergyWattSecond());
	}
	
	/**
	 * Returns the result of this energy-at-least condition, given the given element.
	 * This will be true if the energy of the given element is greater that the energy level of this energy-at-least condition.
	 * 
	 * @param	element
	 * 			The element on who the result will depend
	 * @return	...
	 * 			| result == (((IEnergyHolder) element).getEnergy(EnergyUnit.WATTSECOND) >= this.getEnergyWattSecond())
	 * @throws	IllegalArgumentException
	 * 			...
	 * 			| !(element instanceof IEnergyHolder)
	 */
	public boolean results(Element element) throws IllegalArgumentException
	{
		if(!(element instanceof IEnergyHolder))
		{
			throw new IllegalArgumentException("This element is not an IEnergyholder. It is not possible to ckeck its energy.");
		}
		return (((IEnergyHolder) element).getEnergy(EnergyUnit.WATTSECOND) >= this.getEnergyWattSecond());
	}
	
	/**
	 * String representation of this energy-at-least condition.
	 */
	@Override
	public String toString()
	{
		return this.getIndentation() + "(energy-at-least " + this.getEnergyWattSecond() + ")";
	}
	
	/**
	 * String representation of this energy-at-least condition, in the syntax used by the Parser.
	 */
	@Override
	public String getNotationExample()
	{
		return "(energy-at-least 'energy-number')";
	}
}
