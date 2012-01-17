import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	final static Logger log = LoggerFactory.getLogger(FoxhuntServer.class);


	public void EnqueueFix(Fix fix)
	{
		fixQueue.add(fix);
	}

	public void FixProcessingLoop()
	{
		log.info("Entered world loop");
		stop=false;
		while (true)
		{
			if(!fixQueue.isEmpty())
			{
				ProcessFix(fixQueue.poll());
			}

			if(stop)
			{
				return;
			}
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
				log.info("Left world loop");
			}
		};
	}

	public World()
	{
		fixQueue = new LinkedList<Fix>();
	}
}
