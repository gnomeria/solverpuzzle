/*
 * Created on 31 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.xianxiang.help;

import jyt.game.puzzle.solving.ActionManager;
import jyt.game.puzzle.solving.Puzzle;
import jyt.game.puzzle.solving.impl.CollapserLazy;

public class PlayerTester
{
	public static void main(String[] args)
	{
		Puzzle<Element> puzzle = new PuzzleBuilderRandom().buildPuzzle();
		ActionManager<Element> actionManager = new ActionManager<Element>(puzzle, new CollapserLazy<Element>());
		ScoreComputer scoreComputer = new ScoreComputer();
		IPlayer player = new PlayerWithRater(new CombinationRaterBestCurrentMove(scoreComputer), actionManager, scoreComputer);
		player.play(puzzle);
		System.out.println(scoreComputer.getCurrentScore());
	}
}
