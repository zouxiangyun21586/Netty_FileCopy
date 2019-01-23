package com.yr.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CustomServerHandler extends SimpleChannelInboundHandler<Object> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (msg instanceof Entity) {
			
			Entity entity = (Entity) msg;
			
			
//			System.out.println(entity.getFileContent());
//			System.out.println(entity);
			System.out.println(entity.getFileName());
			String path = "G:\\netty";
			String oldPath = "F:\\zxy\\数据加密";
			String newPath = entity.getFilePath().replaceAll(oldPath,"");
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdir();
			} 
			File mdirtFile = new File(path+newPath);
			if (entity.getFileType() == 1) {
				if (!mdirtFile.exists()) {
					mdirtFile.mkdir();
				}else{
					System.out.println("该文件夹已存在");
				}
			} else {
				if (!mdirtFile.exists()) {
					try {
//						mdirtFile.createNewFile();
						getFile(entity.getFileContent(), mdirtFile.getPath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
	
	
	/** 
     * 根据byte数组，生成文件 
     */  
    public static void getFile(byte[] bfile, String filePath) {  
        BufferedOutputStream bos = null;  
        FileOutputStream fos = null;  
        File file = null;  
        try {  
//            File dir = new File(filePath);  
//            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
//                dir.mkdirs();  
//            }  
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
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }  


}