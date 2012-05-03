/**
 * 
 */
package roboRallyPackage.gameElementClasses;

/**
 * @author Nele
 *
 */
public class RepairKit extends Item implements IEnergyHolder
{
	// volledig nominaal!
	
	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.Item#use(roboRallyPackage.gameElementClasses.Robot)
	 */
	@Override
	public void use(Robot robot) {
		// TODO Auto-generated method stub
		// increase the maxEnergy of the robot met de helft van de energie van deze kit; tot de max maxEnenergy van de robot
		// de energy van deze kit gaat (niet met de helft!) naar beneden; termineer als helemaal op
	}

	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.IEnergyHolder#getEnergy()
	 */
	@Override
	public double getEnergy() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.IEnergyHolder#setEnergy(double)
	 */
	@Override
	public void setEnergy(double energyAmount) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.IEnergyHolder#canHaveAsEnergy(double)
	 */
	@Override
	public boolean canHaveAsEnergy(double energy) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.IEnergyHolder#getMaxEnergy()
	 */
	@Override
	public double getMaxEnergy()
	{
		// TODO Auto-generated method stub
		return maxEngery;
	}
	
	private static final double maxEngery = Integer.MAX_VALUE;

	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.IEnergyHolder#getEnergyFraction()
	 */
	@Override
	public double getEnergyFraction() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.IEnergyHolder#recharge(double)
	 */
	@Override
	public void recharge(double amount) throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.IEnergyHolder#canAcceptForRecharge(double)
	 */
	@Override
	public boolean canAcceptForRecharge(double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see roboRallyPackage.gameElementClasses.IEnergyHolder#transferEnergy(roboRallyPackage.gameElementClasses.IEnergyHolder, double)
	 */
	@Override
	public void transferEnergy(IEnergyHolder other, double amount)
			throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

}
