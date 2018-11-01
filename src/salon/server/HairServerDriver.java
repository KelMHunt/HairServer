package salon.server;

public class HairServerDriver 
{
	public static void main(String[] args)
	{
		MultiThreadedServer server=new MultiThreadedServer();
		System.out.println("Initializing Server");
		server.initServer();
		System.out.println("Starting Server");
		server.waitForRequest();
	}
}
