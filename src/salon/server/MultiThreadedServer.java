package salon.server;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.EOFException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

public class MultiThreadedServer 
{
	private ServerSocket servSocket;
	
	public void initServer()
	{
		try
		{
			servSocket= new ServerSocket(8888, 8);
		}
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
	}
	
	public void waitForRequest()
	{
		if(servSocket==null)
		{
			System.out.println("Server not initialized");
			return;
		}
		try
		{
			while(true)
			{
				Socket socket=servSocket.accept();
				HandleClientRequestsThreadJob job= 
						new HandleClientRequestsThreadJob(socket);
				job.start();
			}			
		}
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
	}
	
	
}
