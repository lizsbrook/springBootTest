package com.yuning.gao.util.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: gaoming01
 * Date: 20:06 2019/3/8
 * Description：${description}
 */
public class NettyServer {
    public static void main(String[] args) {
//        ServerBootstrap serverBootstrap = new ServerBootstrap();
//
//        NioEventLoopGroup boos = new NioEventLoopGroup();
//        NioEventLoopGroup worker = new NioEventLoopGroup();
//        serverBootstrap
//                .group(boos, worker)
//                .channel(NioServerSocketChannel.class)
//                .childHandler(new ChannelInitializer<NioSocketChannel>() {
//                    protected void initChannel(NioSocketChannel ch) {
//                        ch.pipeline().addLast(new StringDecoder());
//                        ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
//                            @Override
//                            protected void channelRead0(ChannelHandlerContext ctx, String msg) {
//                                System.out.println(msg);
//                            }
//                        });
//                    }
//                })
//                .bind(9000);
    }
}
