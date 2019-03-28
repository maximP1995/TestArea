package com.qiaofeng.storychat.socket;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 * Created by zhengmj on 18-12-6.
 */

public class HeartbeatHandler extends IdleStateAwareChannelHandler {
    public static final String PING = "ping";
    public static final String R_N = "\r\n";
    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)throws Exception {
        if (e.getState() == IdleState.READER_IDLE) {
            ctx.getChannel().close();
        }else if (e.getState() == IdleState.WRITER_IDLE) {
            ctx.getChannel().write(PING + R_N).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        future.getChannel().close();
                    }
                }
            });
        }
    }
}
