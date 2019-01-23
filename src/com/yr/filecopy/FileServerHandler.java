package com.yr.filecopy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 服务端处理通道
 * @author zxy-un
 * 
 * 2018年7月9日 下午3:58:24
 */
public class FileServerHandler extends SimpleChannelInboundHandler<Object> {
	private BufferedOutputStream bout;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {
		
		FileEntity fileEntity = (FileEntity)obj;
		byte[] fileBody = fileEntity.getFileBody();
		String fileName = fileEntity.getFileName();
	
		// 获取文件的保存目录
		// File.separator : 系统默认的文件分割符号(linux与windows不一致,使用后会避免这个问题发生)
		String path = "G:\\netty\\"+ File.separator + fileName;
		String oldPath = "F:\\zxy\\数据加密"; 
		String newPath = fileName.replaceAll(oldPath,"");
		File mkdirsFile = new File(path+newPath);
		// 进行文件的保存
		File file = new File(path);
		if(fileEntity.getWhetherFile() == 1){
			if (!mkdirsFile.exists()) { // 如果文件夹不存在就 创建文件夹
				mkdirsFile.mkdir();
			} else {
				System.out.println("该文件夹已存在 !!");
			}
		} else {
			if (!mkdirsFile.exists()) { // 如果文件不存在就 创建文件
				mkdirsFile.createNewFile();
				FileOutputStream out = new FileOutputStream(file, true);
				bout = new BufferedOutputStream(out);
				bout.write(fileBody);
				System.err.println("本次写入字节数：" + fileBody.length +" ****");
				bout.write(fileBody,0,fileBody.length);
				bout.flush();
				
				FileEntity fileEntitys = new FileEntity();
				fileEntitys.setFileName(fileEntity.getFileName());
				fileEntitys.setFileBody(fileEntity.getFileBody());
				fileEntitys.setFileBodyLength(fileEntity.getFileBodyLength());
				fileEntitys.setFileNameLength(fileEntity.getFileNameLength());
				fileEntitys.setWhetherFile(fileEntity.getWhetherFile());
				// 给客户端，响应数据
				ctx.writeAndFlush(fileEntitys);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	/** 
     * 根据byte数组，生成文件 
     */  
    public static void getFile(byte[] bfile, String filePath) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
            file = new File(filePath);
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                } catch (Exception e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (Exception e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }
    }
}
