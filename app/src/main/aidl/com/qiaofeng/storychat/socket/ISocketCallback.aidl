// ISocketCallback.aidl
package com.qiaofeng.storychat.socket;

// Declare any non-default types here with import statements

interface ISocketCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onReceivedJson(String json);
}
