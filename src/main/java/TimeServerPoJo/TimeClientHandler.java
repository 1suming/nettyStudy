package TimeServerPoJo;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	
	public void channelRead(ChannelHandlerContext ctx,Object msg)
	{
		 UnixTime m=(UnixTime )msg;
		 System.out.println(m);
		 
		 ctx.close();
		 
		 
		 
	}
	
	  @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
          cause.printStackTrace();
          ctx.close();
      }

}
