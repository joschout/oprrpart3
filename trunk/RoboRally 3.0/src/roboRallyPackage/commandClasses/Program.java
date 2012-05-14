/**
 * 
 */
package roboRallyPackage.commandClasses;

/**
 * @author Nele
 *
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
}
