
package roboRallyPackage.commandClasses;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class Program
{
	public void run(String program)
	{
		this.getCommand(program).execute();
	}
	
	public Command getCommand(String program)
	{
		return null;
	}
	
	public void executeStep()
	{
	}
}
