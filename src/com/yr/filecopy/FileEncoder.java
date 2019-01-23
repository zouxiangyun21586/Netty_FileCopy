package com.yr.filecopy;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * @author zxy-un
 * 
 * 2018年7月9日 下午3:58:38
 */
public class FileEncoder extends MessageToByteEncoder<FileEntity>{

	@Override
	protected void encode(ChannelHandlerContext ctx, FileEntity fileEntity, ByteBuf out) throws Exception {
		if(null == fileEntity){  
            throw new Exception("文件实体类中 为 null值");  
        }  
        String fileName = fileEntity.getFileName();  
        // 将String转byte[],因为 中文 的长度不一致,所以需转换成 byte数组
        byte[] fileNameByte = fileName.getBytes(Charset.forName("utf-8"));
        int whetherFile = fileEntity.getWhetherFile();
        if(whetherFile == 0){ // 文件
        	out.writeInt(fileNameByte.length); // 文件名字长度
        	out.writeBytes(fileNameByte);  // 文件名
        	
        	out.writeInt(whetherFile); // 是否是文件
        	
        	out.writeInt(fileEntity.getFileBody().length); // 文件内容长度
        	out.writeBytes(fileEntity.getFileBody()); // 文件内容
        } else { // 文件夹
        	out.writeInt(fileNameByte.length); // 文件名字长度
        	out.writeBytes(fileNameByte);  // 文件名
        	
        	out.writeInt(whetherFile); // 是否是文件
        }
        
	}

}
