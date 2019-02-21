package com.xandersu.netty.server1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: suxun
 * @Date: 2019/2/21 21:43
 * @Description:
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ctx.channel().writeAndFlush("is ok /r/n");
        ctx.channel().close();
    }
}
