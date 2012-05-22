
package roboRallyPackage.commandClasses;

import roboRallyPackage.gameElementClasses.Element;

/**
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public abstract class Condition extends Program
{
	public abstract boolean results();

	public abstract boolean results(Element element) throws IllegalArgumentException;
}
