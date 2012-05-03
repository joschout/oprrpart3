
package roboRallyPackage.gameElementClasses;

/**
 * An interface which classes, whose objects represent gamepieces with an internal energy level, can implement.
 * The methods in this interface concern the energy of these gamepieces in Ws.
 * 
 * @version 26 april 2012
 * @author Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			 Bachelor Ingenieurswetenschappen, KULeuven
 *
 */
public interface IEnergyHolder
{
	public abstract boolean isTerminated();

	/**
	 * Returns the variable representing the current amount if energy of this IEnergyHolder in watt-seconds (Ws).
	 */
	public abstract double getEnergy();

	/**
	 * Sets the amount of energy of this IEnergyHolder to the given amount of energy, expressed in watt-seconds (Ws).
	 * 
	 * @param 	energyAmount
	 * 			The new energy amount for this IEnergyHolder in watt-seconds (Ws).
	 * @pre		The given amount of energy (in watt-seconds (Ws)) is a valid amount of energy for this IEnergyHolder.
	 * 			| this.canHaveAsEnergy(energyAmount)
	 * @post	The new amount of energy is equal to the given amount of energy.
	 * 			| (new this).getEnergy() == energyAmount
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated()
	 */
	public abstract void setEnergy(double energyAmount) throws IllegalStateException;

	/**
	 * Checks whether the given amount of energy is a valid amount of energy for this IEnergyHolder.
	 * 
	 * @param	energy
	 * 			The amount of energy to be checked.
	 * @return 	True if and only if the given energy is nonnegative and not greater than the maximum amount of energy of this IEnergyHolder.
	 * 			| result == (energy >= 0) && (energy <= this.getMaxEnergy())
	 */
	public abstract boolean canHaveAsEnergy(double energy);

	/**
	 * Returns the variable representing the maximum amount of energy this IEnergyHolder can have.
	 */
	public abstract double getMaxEnergy();

	/**
	 * Returns a percentage of the current energy level relative to the maximum amount of energy this IEnergyHolder can have.
	 * 
	 * @return	A percentage of the current energy level relative to the maximum amount of energy this IEnergyHolder can have.
	 * 			| result == (this.getEnergy() / this.getMaxEnergy()) * 100
	 */
	public abstract double getEnergyFraction();

	/**
	 * Recharges this IEnergyHolder with the given amount of energy, expressed in watt-seconds (Ws).
	 * 
	 * @param	amount
	 * 			The amount of energy to be added to this IEnergyHolder, expressed in watt-seconds (Ws).
	 * @pre		This IEnergyHolder can be recharged with the given amount of energy.
	 * 			| this.canAcceptForRecharge(amount)
	 * @effect	The given amount of energy is added to the current amount of energy this IEnergyHolder has.
	 * 			| this.setEnergy(this.getEnergy() + amount)
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated()
	 */
	public abstract void recharge(double amount) throws IllegalStateException;

	/**
	 * Checks whether the given amount of energy (expressed in watt-seconds (Ws)) can be added to the current energy level of this IEnergyHolder.
	 * 
	 * @param 	amount
	 * 			The amount to be checked, expressed in watt-seconds (Ws).
	 * @return	True if and only if the given amount of energy is greater than zero 
	 *			 			 and if this IEnergyHolder can have a total energy level equal to its the given amount of energy added to its current energy level.
	 *			| result == ((amount > 0) && this.canHaveAsEnergy(this.getEnergy() + amount))
	 */
	public abstract boolean canAcceptForRecharge(double amount);

	/**
	 * Energy is transferred from this IEnergyHolder to another IEnergyHolder.
	 * 
	 * @param	other
	 * 			The IEnergyHolder to be given energy.
	 * @param	amount
	 * 			The amount of energy to be transferred, expressed in watt-seconds (Ws).
	 * @pre		The other IEnergyHolder must be able to accept the amount of energy to be transferred.
	 * 			| other.canAcceptForRecharge(amount)
	 * @pre		The amount to be transferred can not be greater than the amount of energy this IEnergyHolder has.
	 * 			| amount <= this.getEnergy()
	 * @effect	The other IEnergyHolder is recharged with amount of energy to be transferred.
	 * 			| other.recharge(amount)
	 * @effect	The transferred amount of energy is subtracted from the amount of energy of this IEnergyHolder.
	 * 			| this.setEnergy(this.getEnergy() - amount);
	 * @throws	IllegalStateException
	 * 			...
	 * 			| this.isTerminated()
	 */
	public abstract void transferEnergy(IEnergyHolder other, double amount) throws IllegalStateException;
}
