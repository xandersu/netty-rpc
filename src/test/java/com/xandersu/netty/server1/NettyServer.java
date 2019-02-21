package com.xandersu.netty.server1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: suxun
 * @Date: 2019/2/21 21:31
 * @Description:
 */
public class NettyServer {

    public static void main(String[] args) {
        //默认1个线程
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        //默认CPU核心数两倍的线程·
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
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
                            //ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()));
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new SimpleServerHandler());
                            ch.pipeline().addLast(new StringEncoder());
                        }
                    });
            ChannelFuture f = bootstrap.bind(8080).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
