
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
		super(programLevel);
	}

	@Override
	public abstract boolean results();
	
	@Override
	public abstract boolean results(Element element) throws IllegalArgumentException;
	
	@Override
	public abstract String getNotationExample();
}
