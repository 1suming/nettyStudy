package NioTimeServer;

import java.net.ServerSocket;

public class TimeServer {
	
	public static void main(String[] args)
	{
		int port=8888;
		if(args!=null && args.length>0)
		{
				port=Integer.valueOf(args[0]);
		}
		
		MultiplexerTimeServer timeServer=new MultiplexerTimeServer(port);
		
		new Thread(timeServer,"NIO MultiplexerTimeServer").start();
		
	}
		

}
