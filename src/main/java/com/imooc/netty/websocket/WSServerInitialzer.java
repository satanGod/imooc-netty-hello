package com.imooc.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //websockt基于http协议，所以要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        //对httpMassage进行聚合，聚合成FullHttpRequest或FullHttpResponse

        //几乎在netty编程中，都会使用到此handler
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        //***************以上是用于支持HTTP协议***************************

        /**
         * websocket 服务器的协议，用于指定客户端访问的路由：/ws
         * 本handler会帮你处理一些繁重的复杂的事，会帮你处理
         * 握手动作：handshaking（close、ping、pong）ping+pong = 心跳
         * 对于websocket来讲，都是用frames来传输的，不同的数据类型对应不同的frames。
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义handler
        pipeline.addLast(new ChatHandler());

    }
}
