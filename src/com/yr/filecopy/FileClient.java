package com.yr.filecopy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 * @author zxy-un
 * 
 * 2018年7月9日 下午3:57:57
 */
public class FileClient {
	static final String HOST = System.getProperty("host", "127.0.0.1"); // 本机ip,只能写自己的本机的ip否则连接不上 (可在服务端获取到是谁发来的信息)
    static final int PORT = Integer.parseInt(System.getProperty("port", "8866")); // 使用端口  
    static final int SIZE = Integer.parseInt(System.getProperty("size", "1024"));
    
    public static void main(String[] args) throws Exception {  
    	new FileClient().start();
    }
    
    public void start() throws Exception {
    	EventLoopGroup group = new NioEventLoopGroup(); // 线程池
        try {  
            Bootstrap b = new Bootstrap(); // 通过Bootstrap的connect方法连接服务器端
            b.group(group)  
             .channel(NioSocketChannel.class) // 
             .option(ChannelOption.TCP_NODELAY, true)  
             .handler(new ChannelInitializer<SocketChannel>() {  // 管道
                 @Override  
                 public void initChannel(SocketChannel ch) throws Exception {  
                     ch.pipeline().addLast(new FileEncoder());  // 进行编码
                     ch.pipeline().addLast(new FileClientHandler()); // 客户端Handler
                 }  
             });
  
            ChannelFuture future = b.connect(HOST, PORT).sync();  // 启动客户端
            future.channel().writeAndFlush("Hello Netty Server ,I am a common client");  
            future.channel().closeFuture().sync(); // 判断客户端是否连接成功 
        } finally {  
            group.shutdownGracefully();  
        }
    }
}
