package TimeServer;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeClientHandler extends ChannelInboundHandlerAdapter{
	
	public void channelRead(ChannelHandlerContext ctx,Object msg)
	{
		 
		ByteBuf m=(ByteBuf)msg;
		try{
			long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L; //有可能有问题，没读到4个字节
			System.out.println(new Date(currentTimeMillis));
			ctx.close();
			
		}finally{
			m.release();
			
		}
		 
		 
	}
	
	  @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
          cause.printStackTrace();
          ctx.close();
      }

}
