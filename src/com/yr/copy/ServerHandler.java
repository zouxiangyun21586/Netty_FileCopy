package com.yr.copy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {
	// 用于获取客户端发送的信息
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		Request req = (Request) msg;
		System.out.println("Server : " + req.getId() + ", " + req.getName());
		// 进行文件资源的还原
		byte[] attachment = GzipUtils.ungzip(req.getAttachment());

		// 获取文件的保存目录
		String path = "G:\\netty"+ File.separator + req.getName();
		// 进行文件的保存
		File file = new File(path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		try {
			@SuppressWarnings("resource")
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file, true));
			System.err.println("本次写入字节数：" + attachment.length);
			out.write(attachment, 0, attachment.length);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 给客户端，响应数据
		Response resp = new Response();
		resp.setId(req.getId());
		resp.setName("resp" + req.getId());
		resp.setResponseMessage("响应内容" + req.getId());
		ctx.writeAndFlush(resp);// .addListener(ChannelFutureListener.CLOSE);

	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}