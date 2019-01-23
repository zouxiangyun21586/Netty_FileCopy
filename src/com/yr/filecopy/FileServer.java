package com.yr.filecopy;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务端
 * @author zxy-un
 * 
 * 2018年7月9日 下午3:58:05
 */
public class FileServer {
	
	private int port = 8866;  // 端口
	
    public FileServer(int port) {  
        this.port = port;  
    } 
    
    public FileServer() {  
    }
    
    public void start(){  
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);  
        EventLoopGroup workerGroup = new NioEventLoopGroup();  
        try {  
            ServerBootstrap sbs = new ServerBootstrap()
            		.group(bossGroup,workerGroup)
            		.channel(NioServerSocketChannel.class)
            		.localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {  
                          
                        protected void initChannel(SocketChannel ch) throws Exception {  
                        	/**
                        	 * 参数解释:
                        	 * 	1) 处理每个帧数据的最大长度  (MAX_FRAME_LENGTH)
                        	 * 	2) 记录该帧数据长度的字段本身的长度  (LENGTH_FIELD_LENGTH)
                        	 * 	3) 该帧数据中，存放该帧数据的长度的数据的起始位置  (LENGTH_FIELD_OFFSET)
                        	 * 	4) 修改帧数据长度字段中定义的值，可以为负数 (LENGTH_ADJUSTMENT)
                        	 * 	5) 解析的时候需要跳过的字节数  (INITIAL_BYTES_TO_STRIP)
                        	 * 	6) 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，
                    	 	 *     为false，读取完整个帧再报异常 (failFast)
                        	 */
                        	ch.pipeline().addLast(new FileDecoder(1024*1024, 0, 0, 0, 0, true)); // 进行解码
                            ch.pipeline().addLast(new FileServerHandler());  
                        };  
                          
                    }).option(ChannelOption.SO_BACKLOG, 128)     
                    .childOption(ChannelOption.SO_KEEPALIVE, true);  
             // 绑定端口，开始接收进来的连接  
             ChannelFuture future = sbs.bind(port).sync();    
               
             System.out.println("服务开始监听：   " + port + " 端口" );  
             future.channel().closeFuture().sync();  
        } catch (Exception e) {  
            bossGroup.shutdownGracefully();  
            workerGroup.shutdownGracefully();  
        }  
    }  
      
    public static void main(String[] args) throws Exception {  
        int port;  
        if (args.length > 0) {  
            port = Integer.parseInt(args[0]);  
        } else {  
            port = 8866;  
        }  
        new FileServer(port).start();  
    }
}
