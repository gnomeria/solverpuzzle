/*
 * Created on 20 janv. 2010
 * @author jtoumit
 */
package jyt.game.puzzle.solving;

@SuppressWarnings("unchecked")
public class Puzzle<T>
{
	private T[][] mGrid;

	public Puzzle(int pWidth, int pHeight)
	{
		super();
		mGrid = (T[][])new Object[pWidth][pHeight];
		for (int i = 0; i < mGrid.length; i++)
			for (int j = 0; j < mGrid[i].length; j++)
				mGrid[i][j] = null;
	}

	public Puzzle(final Puzzle<? extends T> pPuzzle)
	{
		super();
		for (int i = 0; i < pPuzzle.mGrid.length; i++)
			for (int j = 0; j < pPuzzle.mGrid[i].length; j++)
				mGrid[i][j] = pPuzzle.mGrid[i][j];
	}

	public T get(int x, int y)
	{
		return mGrid[x][y];
	}

	public void set(int x, int y, T pElement)
	{
		mGrid[x][y] = pElement;
	}

	public int getWidth()
	{
		return mGrid.length;
	}
	
	public int getHeight()
	{
		return mGrid[0].length;
	}
}
