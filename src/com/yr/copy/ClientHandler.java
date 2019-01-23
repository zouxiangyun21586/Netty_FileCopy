package com.yr.copy;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

//用于读取客户端发来的信息
public class ClientHandler extends ChannelHandlerAdapter {
	private static String path = "F:\\zxy\\数据加密";

	private static List<String> fileList = new ArrayList<String>();

	// 客户端与服务端，连接成功的售后
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 进行图片的传输
		isFile(new File(path));
		int i = 0;
		for (String filepath : fileList) {
			File file = new File(filepath);
			Request req = new Request();
			req.setName(filepath);
			req.setId(String.valueOf(++i));
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			byte[] byt = new byte[1024];
			while(in.read(byt) != -1){
				req.setAttachment(GzipUtils.gzip(byt));
				ctx.channel().writeAndFlush(req);
			}
			in.close();
			System.out.println(i+"-------cilent发送文件成功");
		}

	}

	// 只是读数据，没有写数据的话
	// 需要自己手动的释放的消息
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			Response response = (Response) msg;
			System.out.println(response);

		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private static void isFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				isFile(file2);
			}
		} else {
			fileList.add(file.getPath());
		}
	}

}
