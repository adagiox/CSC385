public class Simulation implements SimulationEventListener
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
		this.initSimulation(0);
		this.antSimGUI = new AntSimGUI();
		antSimGUI.initGUI(env.cv);
		antSimGUI.setTime(new String(daysElapsed + ", " + turn));
		antSimGUI.addSimulationEventListener(this);
	}

	public void initSimulation(int type)
	{
		this.simType = type;
		this.turn = 1;
		this.daysElapsed = 0;
		this.running = true;
		this.env = new Environment();
		this.envManager = new EnvironmentManager(this.env);
	}

	public boolean nextTurn()
	{
		System.out.println("Turn: " + turn);
		envManager.getEvents(turn);
		if (envManager.processEvents() == false)
			return false;
		return true;
	}

	public boolean runStep()
	{
		antSimGUI.setTime(new String(daysElapsed + ", " + turn));
		if (this.nextTurn() == false)
			return false;
		this.turn++;
		if (turn > 10)
		{
			daysElapsed++;
			turn = 1;
		}
		return true;
	}

	public boolean runCont()
	{
		while (true)
		{
			antSimGUI.setTime(new String(daysElapsed + ", " + turn));
			if (this.nextTurn() == false)
				break ;
			turn++;
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
		return true;
	}

	public boolean endSimulation()
	{
		// cleanup
		return true;
	}

	@Override
	public void simulationEventOccurred(SimulationEvent simEvent)
	{
		if (simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT)
		{
			this.initSimulation(0);
		}
		else if (simEvent.getEventType() == SimulationEvent.STEP_EVENT)
		{
			if (this.simType == 0)
				this.runStep();
			else
			{
				this.initSimulation(0);
			}
		}
		else if (simEvent.getEventType() == SimulationEvent.RUN_EVENT)
		{
			if (this.simType == 1)
				this.runCont();
			else
			{
				this.initSimulation(1);
			}
		}
		else
			return ;
	}
}
