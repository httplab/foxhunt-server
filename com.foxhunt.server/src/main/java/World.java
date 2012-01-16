import com.mysql.jdbc.MySQLConnection;

import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 16.01.12
 * Time: 23:14
 * To change this template use File | Settings | File Templates.
 */
public class World
{
	private Queue<Fix> fixQueue;
	private boolean stop = true;

	public void EnqueueFix(Fix fix)
	{
		fixQueue.add(fix);
	}

	public void FixProcessingLoop()
	{
		FoxhuntServer.log.info("Entered world loop");
		stop=false;
		while (!stop)
		{
			while (fixQueue.isEmpty() && !stop)
			{
				try
				{
					Thread.sleep(10);
				} catch (InterruptedException e)
				{
					FoxhuntServer.log.error(e.getMessage());
					return;
				}
			};
			if(stop)
			{
				return;
			}
			ProcessFix(fixQueue.poll());
		}
	}
	
	private void ProcessFix(Fix fix)
	{
		FoxhuntServer.log.info(fix.toString());
	}

	public void Stop()
	{
	 	 if(!stop)
		 {
			 stop = true;
		 }
	}

	public Runnable getFixLoop()
	{
		return new Runnable()
		{
			@Override public void run()
			{
				FixProcessingLoop();
				FoxhuntServer.log.info("Left world loop");
			}
		};
	}

	public World()
	{
		fixQueue = new LinkedList<Fix>();
	}
}
