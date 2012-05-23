package roboRallyPackage;

/**
 * A class of terminatable objects.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class Terminatable
{
	/**
	 * Checks whether the primary object is terminated.
	 */
	public boolean isTerminated()
	{
		return isTerminated;
	}
	
	/**
	 * Variable registering whether this 'Terminatable' is terminated. 
	 */
	private  boolean isTerminated = false;

	/**
	 * Terminates the primary object of this method.
	 */
	public void terminate()
	{
		this.isTerminated = true;
	}
}
