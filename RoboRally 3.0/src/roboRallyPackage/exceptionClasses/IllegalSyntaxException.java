package roboRallyPackage.exceptionClasses;

/**
 * 
 * @version   24 may 2012
 * @author	  Jonas Schouterden (r0260385) & Nele Rober (r0262954)
 * 			  Bachelor Ingenieurswetenschappen, KULeuven
 */
public class IllegalSyntaxException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public IllegalSyntaxException(String toStringText)
	{
		this.toStringText = toStringText;
	}
	
	public IllegalSyntaxException()
	{
		
	}
	
	private String toStringText;
	
	@Override
	public String toString()
	{
		if(this.toStringText != null)
		{
			return this.toStringText;
		}
		return "IllegalSyntaxException in the program file.";
	}
}
