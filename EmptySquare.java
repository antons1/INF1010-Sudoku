/**
Stores an empty square in a SuDoku board
@author Håkon Antonsen
@version 1.0
*/

public class EmptySquare extends Square
{
	/**
	Creates an empty Square
	@param br The Board this Square belongs to
	@param r The Row this Square belongs to
	@param c The Coloumn this Square belongs to
	@param b The Box this Square belongs to
	@param n The next Square in the Board (null if this is tha last square)
	*/
	public EmptySquare(Board br, Row r, Coloumn c, Box b, Square n)
	{
		super(br, r, c, b, n);

	} // End constructor
	
} // End EmptySquare