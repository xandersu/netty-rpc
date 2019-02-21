package com.xandersu.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: suxun
 * @Date: 2019/2/21 21:31
 * @Description:
 */
public class NettyServer {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        //默认1个线程
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        //默认CPU核心数两倍的线程·
        EventLoopGroup childGroup = new NioEventLoopGroup();
        //指定两个线程组,accept时间，channel read write时间
        bootstrap.group(parentGroup, childGroup);
        //指定128个通道排队
        bootstrap.option(ChannelOption.SO_BACKLOG, 128)
                //心跳包 flase 自己写
                .childOption(ChannelOption.SO_KEEPALIVE, false)
                //绑定，监听这个通道
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new SimpleServerHandler());
                    }
                })
        ;
    }
}
