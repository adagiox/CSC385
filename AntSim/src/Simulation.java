public class Simulation
{

	public final static int STEP_TYPE = 0;
	public final static int CONT_TYPE = 1;

	int simType;
	int turn;
	int daysElapsed;
	boolean running;
	AntSimGUI antSimGUI;
	Environment env;
	EnvironmentManager envManager;

	public Simulation()
	{
		this.simType = 1;
		this.turn = 1;
		this.daysElapsed = 0;
		this.running = true;
		this.antSimGUI = new AntSimGUI();
		this.env = new Environment();
		this.envManager = new EnvironmentManager(this.env);
	}

	public boolean nextDay()
	{
		// reset ants
		return true;
	}

	public boolean nextTurn()
	{
		envManager.getEvents(turn);
		if (envManager.processEvents() == false)
			return false;
		return true;
	}

	public boolean runStep()
	{
		//env.environmentGrid[14][14].printNodeContent();
		if (this.nextTurn() == false)
			return false;
		//env.environmentGrid[14][14].printNodeContent();
		this.turn++;
		return true;
	}

	public boolean runCont()
	{
		while (true)
		{
			//System.out.println("----------------------------\nTURN: " + turn + "\n");
			if (this.nextTurn() == false)
				break ;
			turn++;
			//env.environmentGrid[14][14].printNodeContent();
//			env.printEnv();
			//System.out.println("Days elapsed: " + daysElapsed);
			if (turn > 10)
			{
				daysElapsed++;
				turn = 1;
			}

		}
		return false;
	}

	public boolean runSimulation()
	{
		if (this.simType == Simulation.STEP_TYPE)
			this.runStep();
		else
			this.runCont();
		env.printEnv();
		System.out.println(envManager.numDead);
		return true;
	}

	public boolean endSimulation()
	{
		// cleanup
		return true;
	}
}
