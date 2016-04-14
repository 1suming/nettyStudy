package TimeServer;

 

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
	
	private int port;
	
	public TimeServer(int port)
	{
		this.port=port;
	}
	
 public static void main(String[] args) throws Exception {
	    int port;
	    if (args.length > 0) {
	        port = Integer.parseInt(args[0]);
	    } else {
	        port = 8888;
	    }
	    
	    new TimeServer(port).run();
 	}
	        
	public void run()
	{
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workerGroup=new NioEventLoopGroup();
		
		try{
		ServerBootstrap b=new ServerBootstrap();
		b.group(bossGroup,workerGroup)
		  .channel(NioServerSocketChannel.class )
				.childHandler(new ChannelInitializer<SocketChannel>()
						{
					//ChannelInitializer 是一个特殊的处理类，他的目的是帮助使用者配置一个新的channel
							public void initChannel(SocketChannel ch)
							{
								ch.pipeline().addLast(new TimeServerHandler());
							}
						})
				.option(ChannelOption.SO_BACKLOG	, 128)
				.childOption(ChannelOption.SO_KEEPALIVE,true);
		
		//绑定端口，开始接受进来的连接
		ChannelFuture f=b.bind(port).sync();
		
 
		// 等待服务器  socket 关闭 。
        // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
        f.channel().closeFuture().sync();
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			
		}
		
		 
	}
	

}
