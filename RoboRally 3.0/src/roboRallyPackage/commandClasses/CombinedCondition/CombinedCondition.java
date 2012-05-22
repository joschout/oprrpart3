
package roboRallyPackage.commandClasses.CombinedCondition;

import roboRallyPackage.gameElementClasses.*;
import roboRallyPackage.commandClasses.*;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class CombinedCondition extends Condition
{
	public CombinedCondition(int programLevel)
	{
		this.programLevel = programLevel;
	}
//	public CombinedCondition(Robot robot)
//	{
//		super(robot);
//	}
	
	public abstract boolean results();
	
	public abstract boolean results(Element element) throws IllegalArgumentException;
}
