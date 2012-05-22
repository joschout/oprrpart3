package roboRallyPackage.exceptionClasses;
import roboRallyPackage.Board;
import roboRallyPackage.gameElementClasses.Element;
import be.kuleuven.cs.som.annotate.*;

public class IllegalBoardException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalBoardException(Element element, Board board)
	{
		this.element = element;
		this.board = board;
	}
	
	public IllegalBoardException(String toStringText)
	{
		this.toStringText = toStringText;
	}
	
	String toStringText;
	
	@Basic
	public Element getelement()
	{
		return element;
	}
	
	private Element element = null;
	
	@Basic
	public Board getBoard()
	{
		return board;
	}
	
	private Board board = null;
	
	@Override
	public String toString()
	{
		if(this.toStringText != null)
		{
			return this.toStringText;
		}
		return "This element cannot be placed on this board.";
	}
}
