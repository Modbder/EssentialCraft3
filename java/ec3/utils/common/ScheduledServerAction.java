package ec3.utils.common;

public abstract class ScheduledServerAction 
{
	public int actionTime;
	
	public ScheduledServerAction(int time)
	{
		actionTime = time;
	}
	
	public abstract void execute();
}
