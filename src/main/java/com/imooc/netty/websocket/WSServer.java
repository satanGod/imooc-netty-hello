package com.imooc.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class WSServer {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();

        try {
            System.out.println("***********服务开始启动***********");
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup,subGroup)
                  .channel(NioServerSocketChannel.class)
                  .childHandler(new WSServerInitialzer());

            ChannelFuture future = server.bind(8088).sync();
            System.out.println("***********服务启动完成***********");
            future.channel().closeFuture().sync();
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
            System.out.println("***********服务结束***********");
        }

    }
}
