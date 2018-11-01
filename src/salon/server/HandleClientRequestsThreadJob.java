package salon.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import salon.models.Shampoo;
import salon.communication.Request;
import salon.communication.Response;

public class HandleClientRequestsThreadJob extends Thread
{
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	public HandleClientRequestsThreadJob(Socket socket)
	{
		this.socket=socket;
	}
	public void getStreams()
	{
		try
		{
			oos= new ObjectOutputStream(socket.getOutputStream());
			ois= new ObjectInputStream(socket.getInputStream());
		}
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
	}
	
	public void closeConnection()
	{
		try
		{
			ois.close();
			oos.close();
			socket.close();
		}
		catch(IOException ie)
		{
			ie.printStackTrace();
		}
	}
	
	public void run()
	{
		try{
			getStreams();
			Request request=null;
			Response response= new Response();
			try{
				do{
					try
					{
						request=(Request)ois.readObject();
						if (request.getAction().equals("add_shampoo")) 
						{
							Shampoo data = (Shampoo) request.getObj();
							// add to database
							response.setSuccess(true);
							oos.writeObject(response);
						}
					}catch(ClassCastException | ClassNotFoundException e)
					{
						e.printStackTrace();
					}
				}while(!request.getAction().equals("exit"));
			}catch(EOFException e)
			{
				//unable to communicate with client
			}
			closeConnection();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
