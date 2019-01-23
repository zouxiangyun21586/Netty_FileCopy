package com.yr.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class CustomClientHandler extends ChannelInboundHandlerAdapter {
	private static List<Entity> list = new ArrayList<>();

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String path = "F:\\zxy\\数据加密";
		storage(path);
		for (Entity entity : list) {
			Entity entity1 = null;
			if (entity.getFileType() == 1) {
				entity1 = new Entity(entity.getFileType(), entity.getFileName(), entity.getFileNameLength(),
						entity.getFilePathLength(), entity.getFilePath());
			} else {
				entity1 = new Entity(entity.getFileType(), entity.getFileName(), entity.getFileNameLength(),
						entity.getFileContentLength(), entity.getFileContent(), entity.getFilePathLength(),
						entity.getFilePath());
			}
			ctx.write(entity1);
//			System.out.println(entity1);
			
		}
	}

	public static void storage(String path) throws Exception {
		File file = new File(path);
		String[] files = file.list();
		if (null == files || "".equals(files)) {
			System.out.println("你要上传的文件路径下没有任何东西");
		}
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i]; // 文件名
			files[i] = file.getPath() + file.separator + files[i]; // 获取文件路劲
//			System.out.println(file.getPath()+"-----------"+files[i]);
			boolean isFile = new File(files[i]).isDirectory(); // 判断文件是否是文件夹
			if (isFile) { // 如果是文件夹
				Entity fileEntity = new Entity();
				fileEntity.setFileType(1);
				fileEntity.setFileName(fileName);
				fileEntity.setFileNameLength(fileName.length());
				fileEntity.setFilePathLength(files[i].length());
				fileEntity.setFilePath(files[i]);
				list.add(fileEntity);
				storage(files[i]);
			} else { // 如果是文件
				Entity folderEntity = new Entity();
				String fileUrl = files[i]; // 文件路径
				//byte[] fileContent = fileRead(fileUrl);
				folderEntity.setFileType(0);
				folderEntity.setFileName(fileName);
				folderEntity.setFileNameLength(fileName.length());
				//folderEntity.setFileContent(fileContent);
				//folderEntity.setFileContentLength(fileContent.length);
				folderEntity.setFilePathLength(files[i].length());
				folderEntity.setFilePath(files[i]);
				list.add(folderEntity);
			}
		}
		
	}

	public static byte[] fileRead(String path) throws Exception {
		FileInputStream in = new FileInputStream(path);
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		in.close();
		byte[] bytes = out.toByteArray();
		return bytes;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}