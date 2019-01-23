package com.yr.filecopy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * 客户端发送通道
 * @author zxy-un
 * 
 * 2018年7月9日 下午3:58:11
 */
public class FileClientHandler extends ChannelInboundHandlerAdapter {
	private static String filePath = "F:\\zxy\\数据加密"; 
	String fileName = "";
	static byte[] fileBody = null;
	int whetherFile = 0; // 如果为 0 是文件,1 是文件夹
	static List<FileEntity> listFileEntity = new ArrayList<FileEntity>();
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		isFile(new File(filePath)); // 判断
		for (FileEntity fileEntity : listFileEntity) {
			FileEntity filesEntity = null;
			fileName = fileEntity.getFileName();
			fileBody = fileEntity.getFileBody();
			whetherFile = fileEntity.getWhetherFile();
			if(fileEntity.getWhetherFile() == 1){ // 文件夹
				filesEntity = new FileEntity(fileName, fileName.getBytes().length,whetherFile); // 构造方法
			} else { // 文件
				filesEntity = new FileEntity(fileName, fileBody, fileBody.length, fileName.getBytes().length,whetherFile); // 构造方法 
			}
			ctx.writeAndFlush(filesEntity);  
		}
	}
	
	// 只是读数据，没有写数据的话
	// 需要自己手动的释放的消息
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try {
			FileEntity fileEntity = (FileEntity) msg;
			System.out.println(fileEntity);

		} finally {
			ReferenceCountUtil.release(msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	public void isFile(File file){
		if(file.isDirectory()){ // 判断是否是文件夹?
			FileEntity fileEntity = new FileEntity();
			fileEntity.setFileName(file.getName());
			fileEntity.setFileNameLength(file.getName().getBytes().length);
			fileEntity.setWhetherFile(1);
			File[] files = file.listFiles(); // 得到里面所有的文件与文件夹
			for (File file2 : files) { // 如果是文件夹就将其中的所有文件和文件夹都循环出来
				isFile(file2); // 递归: 自己调用自己
			}
			listFileEntity.add(fileEntity);
		} else if(file.isFile()){ // 判断是否是文件?
			try {
				FileEntity fileEntity = new FileEntity();
				fileEntity.setFileName(file.getName());
				fileEntity.setFileNameLength(file.getName().getBytes().length);

				FileInputStream in = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
				byte[] temp = new byte[1024];
				int size = 0;
				while ((size = in.read(temp)) != -1) {
					out.write(temp, 0, size);
				}
				in.close();
				byte[] bytes = out.toByteArray();
				
	            fileEntity.setFileBody(bytes);
	            fileEntity.setFileBodyLength(bytes.length);
	            fileEntity.setWhetherFile(0);
	            listFileEntity.add(fileEntity);
	            out.close();
	            in.close();
			} catch (Exception e) {
				System.out.println("读取文件内容出错");
				e.printStackTrace();
			}
		}
	}
}
