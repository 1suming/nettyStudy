package TimeServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {
	
	/**
	 * channelActive会在连接被建立并且准备进行通信时被调用
	 */
	@Override
	public void channelActive(final ChannelHandlerContext ctx)
	{
		final ByteBuf time=ctx.alloc().buffer(4); //一个int 4个byte
		time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
		
		final ChannelFuture f=ctx.writeAndFlush(time);
		
		f.addListener(new ChannelFutureListener(){
			public void operationComplete(ChannelFuture future)
			{
				assert f==future;
				ctx.close();
			}
		});
		
		
		
	}
	
	  @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
          cause.printStackTrace();
          ctx.close();
      }

}
