
package roboRallyPackage.commandClasses.BasicConditionClasses;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class BasicCondition extends Condition
{
	public BasicCondition(int programLevel)
	{
		super(programLevel);
	}
	
	public abstract boolean results();
	
	public abstract boolean results(Element element);
}
