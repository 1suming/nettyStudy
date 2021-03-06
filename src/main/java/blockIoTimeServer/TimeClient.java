package blockIoTimeServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {
	
	  public static void main(String[] args)
	  {
			int port = 8888;
			if (args != null && args.length > 0) {
			    try {
				port = Integer.valueOf(args[0]);
			    } catch (NumberFormatException e) {
				// 采用默认值
			    }
			}
			
			Socket socket=null;
			BufferedReader in=null;
			PrintWriter out=null;
			try{
				socket=new Socket("127.0.0.1",port);
				in=new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				
				out=new PrintWriter(socket.getOutputStream(),true);//atuo flush
				out.println("QUERY TIME ORDER");
				System.out.println("Send order 2 server succeed.");
				
				String resp=in.readLine();
				System.out.println("Now is :"+resp);
				
			}catch(Exception e)
			{
				
			}finally{
				if(out!=null)
				{
					out.close();
					
				}
				if(socket!=null)
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
	  }

}
