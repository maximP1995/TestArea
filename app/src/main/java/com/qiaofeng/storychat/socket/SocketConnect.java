package com.qiaofeng.storychat.socket;

import android.location.Address;
import android.text.TextUtils;
import android.util.Log;

import com.qiaofeng.storychat.socket.entity.SocketEntity;

import org.greenrobot.eventbus.EventBus;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.handler.timeout.ReadTimeoutException;
import org.jboss.netty.util.CharsetUtil;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timeout;
import org.jboss.netty.util.Timer;
import org.jboss.netty.util.TimerTask;

import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhengmj on 18-12-6.
 */

public class SocketConnect {
    private static SocketConnect instance;
    private ClientBootstrap bootstrap;
    public static final int READ_TIMEOUT = 240;
    public static final int WRITE_TIMEOUT = 120;
    public static final int RECONNECT_DELAY = 5;
    private volatile boolean isRunConnect;
    private volatile Timer timer;
    private Channel mChannel;
    private String Address;
    private String Port;

    private ConnectListen listen;

    public static SocketConnect getInstance() {
        if (instance == null) {
            synchronized (SocketConnect.class) {
                if (instance == null) instance = new SocketConnect();
            }
        }
        return instance;
    }
    public synchronized void initSocket(ConnectListen listen){
        timer = new HashedWheelTimer();
        this.listen = listen;
        bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            // 心跳包
            final ChannelHandler idleStateHandler = new IdleStateHandler(timer, READ_TIMEOUT, WRITE_TIMEOUT, 0);
            final ChannelHandler heartbeatHandler = new HeartbeatHandler();
            // private final ChannelHandler timeoutHandler = new
            // ReadTimeoutHandler(timer, READ_TIMEOUT);
            private final ChannelHandler uptimeHandler = new UptimeClientHandler(bootstrap, timer);

            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(idleStateHandler, heartbeatHandler, new DelimiterBasedFrameDecoder(8192 * 8192, Delimiters.lineDelimiter()), new StringDecoder(CharsetUtil.UTF_8), new StringEncoder(CharsetUtil.UTF_8), uptimeHandler);
            }
        });
        bootstrap.setOption("tcpNoDelay", true);
        bootstrap.setOption("keepAlive", true);
        bootstrap.setOption("reuseAddress", true);
        bootstrap.setOption("remoteAddress", new InetSocketAddress(Address, TextUtils.isEmpty(Port)?80:Integer.parseInt(Port)));
        mChannel = bootstrap.connect().awaitUninterruptibly().getChannel();
        listen.channel(mChannel);
        isRunConnect = true;
    }
    public synchronized void shutBootstrap() {
        try {
            isRunConnect = false;
            if (timer != null) timer.stop();
            if (mChannel != null) mChannel.close().awaitUninterruptibly();
            if (bootstrap != null) bootstrap.releaseExternalResources();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    class UptimeClientHandler extends SimpleChannelUpstreamHandler {

        final ClientBootstrap bootstrap;
        private final Timer timer;
        private long startTime = -1;

        public UptimeClientHandler(ClientBootstrap bootstrap, Timer timer) {
            this.bootstrap = bootstrap;
            this.timer = timer;
        }

        InetSocketAddress getRemoteAddress() {
            return (InetSocketAddress) bootstrap.getOption("remoteAddress");
        }

        @Override
        public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            listen.messageReceived("CONNECTION_ERROR");
        }

        @Override
        public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
            timer.newTimeout(new TimerTask() {
                public void run(Timeout timeout) {
                    mChannel = bootstrap.connect().awaitUninterruptibly().getChannel();
                    listen.channel(mChannel);
                }
            }, RECONNECT_DELAY, TimeUnit.SECONDS);
        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
            if (startTime < 0) {
                startTime = System.currentTimeMillis();
            }
            println("Connected to: " + getRemoteAddress());
        }

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
            println("收到:"+e.getMessage());
            listen.messageReceived((String) e.getMessage());

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
            Throwable cause = e.getCause();
            if (cause instanceof ConnectException) {
                startTime = -1;
            }
            if (cause instanceof ReadTimeoutException) {
            } else {
                cause.printStackTrace();
            }
            listen.messageReceived("CONNECTION_ERROR");
            ctx.getChannel().close();
        }

        private void println(String msg) {
            if (startTime < 0) {
                Log.d("ReceivedManager", String.format("[SERVER IS DOWN] %s%n", msg));
            } else {
                Log.d("ReceivedManager", String.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis() - startTime) / 1000, msg));
            }
        }
    }
}
