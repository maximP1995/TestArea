package com.qiaofeng.storychat.socket;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.qiaofeng.storychat.socket.entity.SocketEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.Observable;

/**
 * Created by zhengmj on 18-12-6.
 */

public class SocketUtil {
    private static SocketUtil instance;
    private Context context;
    private String address;
    private String port;
    private volatile ISocketInterface iRemoteService;

    private SocketUtil(Context context){
        this.context = context;
    }

    public static SocketUtil getInstance(Context context, String address, @Nullable String port){
        if (instance == null) {
            synchronized (SocketUtil.class) {
                if (instance == null) {
                    instance = new SocketUtil(context);
                }
            }
        }
        return instance;
    }
    private ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            iRemoteService = ISocketInterface.Stub.asInterface(service);
            try {
                iRemoteService.registerReceivedCallback(mCallback);
            } catch (RemoteException e) {
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("ReceivedManager", "onServiceDisconnected.......user_name:" + name);
        }
    };
    private ISocketCallback mCallback = new ISocketCallback.Stub() {

        @Override
        public void onReceivedJson(String json) throws RemoteException {
            Log.d("ReceivedManager", "mCallback IReceivedCallback.......回调数据:" + json);
            doReceivedJson(json);
        }
    };

    private void doReceivedJson(String json) {
        if (json.equals("SEND_MESSAGE")){
            try{
                if (iRemoteService!=null)iRemoteService.onSend("I_SEND");
            }catch (RemoteException e){
                Toast.makeText(context,"发送消息失败",Toast.LENGTH_SHORT).show();
            }
        }else {
            SocketEntity entity = new SocketEntity();
            entity.msg = json;
            EventBus.getDefault().post(entity);
        }
    }

    public void bindService(){
        Intent intent = new Intent(context, SocketService.class);
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
        bundle.putString("port",port);
        intent.putExtras(bundle);
        context.bindService(intent,mServiceConnection,Context.BIND_AUTO_CREATE);
    }
}
