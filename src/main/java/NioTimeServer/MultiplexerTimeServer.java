package NioTimeServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiplexerTimeServer  implements Runnable{

	private volatile boolean stop;
	
	private Selector selector;
	private ServerSocketChannel serverChannel;
	
	
	public MultiplexerTimeServer(int port) 
	{
		 try {
			selector=Selector.open();
		
			 serverChannel=ServerSocketChannel.open();
			 serverChannel.configureBlocking(false);
			 serverChannel.socket().bind(new InetSocketAddress(port),1024);
			 serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			 System.out.println("The time server is start in port:"+port);
		 
		 
		 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
	}
	
	public void stop()
	{
		this.stop=true;
	}
	
	 
	public void run()
	{
		while(!stop)
		{
			try {
				selector.select(1000);
				System.out.println("selector.select one time.");
				Set<SelectionKey> selectedKeys= selector.selectedKeys();
				Iterator<SelectionKey> it=selectedKeys.iterator();
				SelectionKey key=null;
				while(it.hasNext()){
					key=it.next();
					it.remove();
					
					try{
						handleInput(key);
					}catch(Exception e)
					{
						if(key!=null)
						{
							key.cancel();
							if(key.channel()!=null)
								key.channel().close();
						}
					}
				}
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException
	{
		if(key.isValid())
		{
			//处理新接入的请求消息
			if(key.isAcceptable())
			{
				//Accept then new conn 
				ServerSocketChannel ssc=(ServerSocketChannel)key.channel();
				SocketChannel sc=ssc.accept();
				sc.configureBlocking(false);
				//add The new Conn to the selector
				sc.register(selector, SelectionKey.OP_READ);
				
				
			}
			if(key.isReadable())
			{
				//Read teh data
				SocketChannel sc=(SocketChannel)key.channel();
				ByteBuffer readBuffer=ByteBuffer.allocate(1024);
				int readBytes=sc.read(readBuffer);
				if(readBytes>0)
				{
					readBuffer.flip();
					byte[] bytes=new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					
					String body=new String(bytes,"UTF-8");
					System.out.println("the time server receive order:"+body);
					
					
					String currentTime="QUERY TIME ORDER".equalsIgnoreCase(body)?
							 new java.util.Date(System.currentTimeMillis()).toLocaleString()
							 :"BAD ORDER";
							 
					doWrite(sc,currentTime);
							 
					
				}
				else if(readBytes<0)
				{
					//对端链路关闭
					key.cancel();
					sc.close();
				}
				else 
				{
					//读到0字节，忽略
				}
			}
			
			
		}
	}
	private void doWrite(SocketChannel channel,String response) throws IOException
	{
		if(response!=null && response.trim().length()>0)
		{
			byte[] bytes=response.getBytes();
			ByteBuffer writeBuffer=ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
			
		}
	}
	
	 

}
