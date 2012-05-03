/**
 * 
 */
package roboRallyPackage.commandClasses.BooleanCommand;

import roboRallyPackage.*;
import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;
import roboRallyPackage.commandClasses.ConditionCommand.*;
/**
 * @author Nele
 *
 */
public abstract class BooleanCommand extends Command implements Condition
{
	public abstract boolean results();
}
