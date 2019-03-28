// ISocketInterface.aidl
package com.qiaofeng.storychat.socket;
import com.qiaofeng.storychat.socket.ISocketCallback;
// Declare any non-default types here with import statements

interface ISocketInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onSend(String msg);
    void registerReceivedCallback(ISocketCallback cb);
    void unregisterReceivedCallback(ISocketCallback cb);
}
