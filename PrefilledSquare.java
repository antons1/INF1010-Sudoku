/**
Stores a prefilled square in a SuDoku board.
@author Håkon Antonsen
@version 1.0
*/

public class PrefilledSquare extends Square
{
	/**
	Creates a square with the value v
	@param br The Board this Square belongs to
	@param r The Row this Square belongs to
	@param c The Coloumn this Square belongs to
	@param b The Box this Square belongs to
	@param n The next square in the board (null if this is last square)
	@param v The value of the square
	*/
	public PrefilledSquare(Board br, Row r, Coloumn c, Box b, Square n, int v)
	{
		super(br, r, c, b, n);
		setValue(v);
	} // End constructor

} // End PrefilledSquare