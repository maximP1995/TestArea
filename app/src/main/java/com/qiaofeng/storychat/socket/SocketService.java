package com.qiaofeng.storychat.socket;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zhengmj on 18-12-6.
 */

public class SocketService extends Service implements ConnectListen{
    private String address;
    private String port;
    private static Object lock = new Object();
    private volatile Channel mChannel;
    private final RemoteCallbackList<ISocketCallback> mCallbacks = new RemoteCallbackList<ISocketCallback>();
    private volatile boolean isBinder;
    private volatile boolean isSuccess;
    private final BlockingQueue<String> msgQueue = new ArrayBlockingQueue<String>(2000);
    private volatile int reqdoOkCount;//由于还没有绑定service，获取不到监听，所以先记录一下，等绑定好了之后就判断这个
    private final ISocketInterface.Stub mBinder = new ISocketInterface.Stub() {

        @Override
        public void onSend(String msg) throws RemoteException {
            sendText(msg);
        }

        @Override
        public void registerReceivedCallback(ISocketCallback cb) throws RemoteException {
            synchronized (lock) {
                mCallbacks.register(cb);
                isBinder = true;
                if (reqdoOkCount > 0) {
                    notifyReceivedJson("RECEIVE_OK");
                }
                reqdoOkCount = 0;
            }
        }

        @Override
        public void unregisterReceivedCallback(ISocketCallback cb) throws RemoteException {
            mCallbacks.unregister(cb);
        }

    };
    public boolean isConnected() {
        return mChannel != null && mChannel.isBound() && mChannel.isOpen() && mChannel.isConnected();
    }
    private void sendText(String reqUrl) {
        if (!isSuccess) return;
        if (isConnected()) {
            ChannelFuture channelFuture = mChannel.write(reqUrl).awaitUninterruptibly();
            if (!channelFuture.isSuccess()) {
                channelFuture.getChannel().close();
            }
        } else {
            if (mChannel != null) {
                mChannel.close();

            }
        }

    }
    @Override
    public void onCreate() {
        super.onCreate();
        ThreadManager.submit(new Runnable() {
            @Override
            public void run() {
                SocketConnect.getInstance().initSocket(SocketService.this);
            }
        });
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null&&intent.getExtras()!=null) {
            Bundle bundle = intent.getExtras();
            address = bundle.getString("address");
            port = bundle.getString("port");
        }
        return Service.START_STICKY;
    }
    /**
     * 回调数据
     *
     * @param json
     */
    private void notifyReceivedJson(String json) {
        msgQueue.add(json);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        isBinder = false;
        super.unbindService(conn);
    }

    @Override
    public void channel(Channel channel) {
        mChannel = channel;
    }

    @Override
    public void messageReceived(String json) {

    }
}
