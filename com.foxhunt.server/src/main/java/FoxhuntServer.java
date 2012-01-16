import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Nu-hin
 * Date: 16.01.12
 * Time: 21:06
 * To change this template use File | Settings | File Templates.
 */
public class FoxhuntServer
{
	final static Logger log = LoggerFactory.getLogger(FoxhuntServer.class);
	public static void main(String[] arguments)
			throws InterruptedException
	{
		log.info("Server launched");
		World world = new World();
		Thread worldThread = new Thread(world.getFixLoop());
		worldThread.setName("World");
		worldThread.start();
		world.EnqueueFix(new Fix());
		Thread.sleep(3000);
		world.EnqueueFix(new Fix());
		Thread.sleep(1000);
		world.Stop();
		log.info("Server stopped");
	}
}
