import java.util.*;
import java.io.*;

/**
A collection used to store solutions to SuDoku boards
@author H&aring;kon Antonsen
@version 1.0
*/
public class SudokuCollection extends LinkedList<int[][]>
{
	private final int MAX_SOLUTIONS = 500;	// The max number of solutions to store
	private int solutions;					// The number of solutions found
	private int lastGivenSolution;			// The solution that was last given

	/**
	Creates an empty container
	*/
	public SudokuCollection()
	{
		solutions = 0;
		lastGivenSolution = -1;

	} // End construct

	/**
	Puts a solution in the container
	@param sq The board to be inserted
	*/
	public void insert(Square[][] sq)
	{
		// Increase the number of solutions
		solutions++;

		// Store the solution if the max limit is not hit
		if(size() <= MAX_SOLUTIONS)
		{
			// Convert to int[][]
			int[][] tmp = new int[sq.length][sq[0].length];
			for(int i = 0; i < sq.length; i++)
			{
				for(int j = 0; j < sq[0].length; j++)
				{
					tmp[i][j] = sq[i][j].getValue();

				} // ENDFOR

			} // ENDFOR

			// Store
			add(tmp);

		} // ENDIF

	} // End insert

	/**
	Returns the last solution in the container and removes it
	@return the last solution in the container
	*/
	public int[][] get()
	{
		return remove();

	} // End get

	/**
	Returns the number of solutions found
	@return The number of solutions
	*/
	public int getSolutionCount()
	{
		return solutions;

	} // End getSolutionCount

	/**
	Returns the next solution in the container, or null if there are no more
	@return The next solution, or null if there are no more
	*/
	public int[][] getNextSolution()
	{
		if(lastGivenSolution+1 < size())
		{
			lastGivenSolution++;
			return get(lastGivenSolution);
		} // ENDIF

		return null;

	} // End getNextSolution

	/**
	Sets last given solution to none, so when getNextSoluton() is called again, it gives the first solution.
	*/
	public void resetGetNext()
	{
		lastGivenSolution = -1;
	} // End resetGetNext

	/**
	Sets the last given solution back one.
	*/
	public void setBackGetNext()
	{
		if(lastGivenSolution > -1)
		{
			lastGivenSolution--;
		} // ENDIF
	} // End setBackGetNext

	/**
	Gets the number of stores solutions
	@retun The number of stores solutions
	*/
	public int getStoredSolutionCount()
	{
		if(solutions > MAX_SOLUTIONS)
		{
			return MAX_SOLUTIONS;

		}
		else 
		{
			return solutions;
		} // ENDIF
	} // End getStoredSolutionCount

	/**
	Returns the position of getNextSolution()
	@return The position of getNextSolution()
	*/
	public int getLastGivenSolution()
	{
		return lastGivenSolution;
	} // End getLastGivenSolution

	/**
	Stores all solutions to the file f.
	@param f The file to save to
	@throws FileNotFoundException if file is not found
	@throws IOException if there is an error writing to the file.
	*/
	public void saveToFile(String f) throws FileNotFoundException, IOException
	{
		FileWriter fw = new FileWriter(f);
		BufferedWriter w = new BufferedWriter(fw);

		int i = 0;
		Iterator<int[][]> it = iterator();
		int[][] s;

		while(it.hasNext())
		{
			i++;
			s = it.next();
			w.write(i + ": ");

			for(int[] ss : s)
			{
				for(int sss : ss)
				{
					w.write(Integer.toString(sss, 16).toUpperCase());

				} // ENDFOR

				w.write("// ");

			} // ENDFOR

			w.newLine();

		} // ENDWHILE

		w.close();
		fw.close();

	} // End saveToFile

} // End SudokuCollection