package TimeServer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient {
	 public static void main(String[] args) throws Exception {

         String host = "127.0.0.1" ;//args[0];
         int port =8888;// Integer.parseInt(args[1]);
         
         EventLoopGroup workerGroup=new NioEventLoopGroup();
         try{
        	 
        	 Bootstrap b=new Bootstrap();
        	 b.group(workerGroup);
        	 b.channel(NioSocketChannel.class);
        	 b.option(ChannelOption.SO_KEEPALIVE	,true);
        	 
        	 b.handler(new ChannelInitializer<SocketChannel>(){
        		 
        		 @Override
        		 public void initChannel(SocketChannel ch)
        		 {
        			// ch.pipeline().addLast(new TimeClientHandler2());
        			 ch.pipeline().addLast(new TimeDecoder(),new TimeClientHandler());
        			 
        		 }
        	 });
        	 
        	 //等待客户端
        	 ChannelFuture f=b.connect(host,port).sync();
        	 
        	 //等待连接关闭
        	 f.channel().closeFuture().sync();
        	 
        	 
        	 
         }finally{
        	 workerGroup.shutdownGracefully();
        	 
         }
         
         
         
         
	 }

}
