package com.qiaofeng.storychat.socket;

import org.jboss.netty.channel.Channel;

/**
 * Created by zhengmj on 18-12-6.
 */

public interface ConnectListen {
    void channel(Channel channel);
    void messageReceived(String json);
}
