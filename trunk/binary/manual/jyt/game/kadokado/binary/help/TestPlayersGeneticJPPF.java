/*
 * Created on 28 mars 2010
 * @author jtoumit
 */
package jyt.game.kadokado.binary.help;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JFrame;

import org.jppf.client.JPPFClient;
import org.jppf.client.concurrent.JPPFExecutorService;



public class TestPlayersGeneticJPPF extends JFrame
{
	private static final Random mRandom = new Random(System.currentTimeMillis());
	private Double[][] mAllResults;
	private int mCurrentRun;

	public TestPlayersGeneticJPPF()
	{
		super("Genetic testing for test players");
		setBounds(0, 0, 600, 350);
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent pE)
			{
				System.exit(0);
			}
		});
	}

	private void run() throws InterruptedException, ExecutionException
	{
		OneTestJPPF bestTest = null;
		int nbRun = 20;
		List<OneTestJPPF> individuals = new ArrayList<OneTestJPPF>();
		JPPFClient client = new JPPFClient();
		ExecutorService poolExecutor = new JPPFExecutorService(client);
		for (int i = 0; i < 20; i++)
			individuals.add(new OneTestJPPF(createRandomWeights(), nbRun));
		long start = System.currentTimeMillis();
		for (mCurrentRun = 0; mCurrentRun < 100; mCurrentRun++)
		{
			List<Future<OneTestJPPF>> futures = new ArrayList<Future<OneTestJPPF>>();
			System.out.println("Run " + mCurrentRun);
			futures = poolExecutor.invokeAll(individuals);
/*			for (OneTestJPPF oneTest : individuals)
				futures.add(poolExecutor.submit(oneTest, oneTest));*/
			int currentIndividual = 0;
			for (Future<OneTestJPPF> future : futures)
			{
				OneTestJPPF test = future.get();
				if ((bestTest == null) || (test.getResult() > bestTest.getResult()))
					bestTest = test;
				mAllResults[mCurrentRun][currentIndividual++] = new Double(test.getResult());
				repaint();
				System.out.print("*");
			}
			System.out.println();
			printCurrentResult(bestTest, mCurrentRun, 100, start);

			// Now seed the next generation
			// Sort in decreasing order and kill the worst while keeping the best
			Collections.sort(individuals, new Comparator<OneTestJPPF>()
			{
				public int compare(OneTestJPPF t1, OneTestJPPF t2)
				{
					return -new Integer(t1.getResult()).compareTo(new Integer(t2.getResult()));
				};
			});
			for (int iInd = 0; iInd < individuals.size(); iInd++)
			{
				if (iInd > individuals.size() * 0.9)
				// kill the 10% worst and feed them with random stuff
					individuals.set(iInd, new OneTestJPPF(createRandomWeights(), nbRun));
				else if (iInd > individuals.size() * 0.6)
				// kill the 40% worst and feed them with the 10% best
					individuals.set(iInd, new OneTestJPPF(shakeShake(individuals.get(mRandom.nextInt(10) * individuals.size() / 100).getWeights(), individuals, 50, 20, 20), nbRun));
				else if (iInd < individuals.size() / 50)
				// keep the 2% best
				{
					
				}
				else if (iInd < individuals.size() / 10)
				// shake the others a little until 10%
				{
					individuals.set(iInd, new OneTestJPPF(shakeShake(individuals.get(iInd).getWeights(), individuals, 80, 5, 5), nbRun));
				}
				else
				// Shake the middle a little
					individuals.set(iInd, new OneTestJPPF(shakeShake(individuals.get(iInd).getWeights(), individuals, 70, 10, 10), nbRun));
			}
		}
		poolExecutor.shutdown();
		System.out.println("Done");
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		for (int i = 0; i < Math.min(mCurrentRun + 1, mAllResults.length); i++)
		{
			for (int j = 0; j < mAllResults[i].length; j++)
			{
				if (mAllResults[i][j] != null)
				{
					int c = 200 - (200 * i / (mCurrentRun + 1));
					g.setColor(new Color(c, c, c));
					g.drawRect(j * 2, (int)(mAllResults[i][j].doubleValue() * 350 / 100000), 1, 1);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException
	{
		TestPlayersGeneticJPPF frame = new TestPlayersGeneticJPPF();
		frame.mAllResults = new Double[100][];
		for (int i = 0; i < frame.mAllResults.length; i++)
			frame.mAllResults[i] = new Double[500];
		frame.setVisible(true);
		frame.run();
	}

	private double[] createRandomWeights()
	{
		double[] weights = new double[22];
		for (int j = 0; j < weights.length; j++)
			weights[j] = mRandom.nextDouble() * 100;
		return weights;
	}

	private double[] shakeShake(double[] pDouble, List<OneTestJPPF> pIndividuals, int pPercentKeep, int pPercentCopy, int pPercentRandom)
	{
		double[] res = new double[pDouble.length];
		for (int i = 0; i < res.length; i++)
		{
			int rand = mRandom.nextInt(100);
			if (rand < pPercentKeep)
				res[i] = pDouble[i];
			else if (rand > 100 - pPercentCopy)
			{
				int chooseOne = mRandom.nextInt(30) * pIndividuals.size() / 100;
				res[i] = pIndividuals.get(chooseOne).getWeights()[i];
			}
			else if (rand < pPercentKeep + pPercentRandom)
				res[i] = mRandom.nextDouble() * 100;
			else
				res[i] *= Math.min(100, Math.max(0, mRandom.nextDouble() * 2));
		}
		return res;
	}

	private void printCurrentResult(OneTestJPPF pBestTest, int pCurrent, int pTotal, long pStart)
	{
		System.out.println(((System.currentTimeMillis() - pStart) / 1000) + "s, " + pCurrent + " / " + pTotal + ", currentBestHit: " + pBestTest.getResult() + " (" + pBestTest.getMin() + "-" + pBestTest.getMax() + "), " + pBestTest.getStringRepresentation());
	}

}
