package com.yr.file;

import java.io.FileInputStream;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class CustomEncoder extends MessageToByteEncoder<Entity> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Entity entity, ByteBuf out) throws Exception {
		if (null == entity) {
			throw new Exception("entity is null");
		}
		int fileType = entity.getFileType();
		String name = entity.getFileName();
		byte[] fileName = name.getBytes(Charset.forName("utf-8"));
		byte[] fileContent = entity.getFileContent();
		String path = entity.getFilePath();
		byte[] filePath = path.getBytes(Charset.forName("utf-8"));
		if (fileType == 0) {
			out.writeInt(fileType);
			out.writeInt(fileName.length);
			out.writeBytes(fileName);
			FileInputStream in = new FileInputStream(path);
			out.writeInt(in.available());
			out.writeBytes(in, 0);
//			
//			byte[] temp = new byte[1024];
//			int size = 0;
//			while ((size = in.read(temp)) != -1) {
//				System.out.println("client == " + size);
//				out.writeBytes(temp, 0, size);
//			}
//			in.close();
		} else {
			out.writeInt(fileType);
			out.writeInt(fileName.length);
			out.writeBytes(fileName);
		}
	}

}