
package roboRallyPackage;

/**
 * An enumerations representing the directions where to a robot can turn.
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public enum Direction
{
	CLOCKWISE {
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "clockwise";
		}
	}, COUNTER_CLOCKWISE {
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "counterclockwise";
		}
	};
	
	public abstract String toString();
}
