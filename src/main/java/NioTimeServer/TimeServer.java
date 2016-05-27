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
		
		new Thread(timeServer,"NIO MultiplexerTimeServer").start(); //main线程退出没关系，jvm会把所有的用户线程执行完后退出.
		
	}
		

}
