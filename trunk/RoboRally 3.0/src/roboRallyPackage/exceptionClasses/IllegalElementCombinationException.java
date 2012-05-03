package roboRallyPackage.exceptionClasses;
import roboRallyPackage.gameElementClasses.Element;
import be.kuleuven.cs.som.annotate.*;

public class IllegalElementCombinationException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalElementCombinationException(Element newElement, Element oldElement)
	{
		this.newElement = newElement;
		this.oldElement = oldElement;
	}
	
	@Basic
	public Element getNewElement()
	{
		return newElement;
	}
	
	private Element newElement = null;
	
	@Basic
	public Element getOldElement()
	{
		return oldElement;
	}
	
	private Element oldElement = null;
	
	@Override
	public String toString()
	{
		return "The element you are trying to place on this position is in conflict with another element that is already standing on this position.";
	}
}
