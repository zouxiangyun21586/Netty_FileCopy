package com.yr.filecopy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 解码器
 * @author zxy-un
 * 
 * 2018年7月9日 下午3:58:45
 */
public class FileDecoder extends LengthFieldBasedFrameDecoder{

	//判断传送客户端传送过来的数据是否按照协议传输，头部信息的大小应该是 byte+byte+int = 1+1+4 = 6  
    private static final int HEADER_SIZE = 16;
    private String fileName; // 文件名
    private byte[] fileBody; // 文件内容
    private int fileBodyLength; // 文件内容长度
    private int fileNameLength; // 文件名长度
    private int whetherFile; // 是否是文件
	
    /** 
     * @param maxFrameLength 解码时，处理每个帧数据的最大长度 
     * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置 
     * @param lengthFieldLength 记录该帧数据长度的字段本身的长度 
     * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数 
     * @param initialBytesToStrip 解析的时候需要跳过的字节数 
     * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常 
     */
	public FileDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
			int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
	}
	
	private  int i = 0 ;
	private int count = 0;
	FileEntity fileEntitys = null;
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		if(i == 0){ // 第一次进入
			if (in == null) {  
	            return null;  
	        }  
	        if (in.readableBytes() < HEADER_SIZE) {  
	            throw new Exception("可读信息段比头部信息小");  
	        }
	        
	        fileNameLength = in.readInt(); // 文件名长度
	        int count = 0;

	        if(in.readableBytes() < fileNameLength){ // 如果可读长度小于传过来的文件名长度
	        	System.out.println("可读长度小于传过来的长度,缺值!!");
	        }
        	ByteBuf fileNameIn = in.readBytes(fileNameLength);
        	byte[] FileNameByte = new byte[fileNameLength];
        	fileNameIn.readBytes(FileNameByte);
        	fileName = new String(FileNameByte, "UTF-8");
	        
	        whetherFile = in.readInt(); // 类型
	        if(whetherFile == 0){ // 文件
		    	fileBodyLength = in.readInt(); // 内容长度
		    	if(in.readableBytes() < fileBodyLength){ // 如果可读长度小于传过来的文件名长度
		        	System.out.println("可读长度小于传过来的文件内容长度, 缺少值 ~!!!");
		        }
	  	        fileBody = new byte[fileBodyLength];
	      		
	      		int b = in.readableBytes(); // 可读长度
	      		count = count + b;
	      		in.readBytes(fileBody,0,b);
	      		++i; // 下一次就表示不是第一次进入了
	      		fileEntitys = new FileEntity(fileName, fileBody, fileBodyLength, fileNameLength, whetherFile);
			} else {
	  			int b = in.readableBytes();
	  			in.readBytes(fileBody,count,b);
	  			count = count + b;
	  			if(count == fileBodyLength)
	  			{
	  				System.out.println(count);
	  			}
	  			fileEntitys = new FileEntity(fileName, fileNameLength, whetherFile);
			}
        } else {
        	int b = in.readableBytes();
    		in.readBytes(fileBody,count,b);
    		count = count + b;
    		if(count == fileBodyLength)
    		{
    			System.out.println(count);
    			FileServerHandler.getFile(fileBody, "G:\\netty\\" + fileName);
    		}
        }
//		return fileEntitys;
		return null;
	}
        		
      
//    while(true)
//    {
//    	count = count + in.readableBytes();
//    	if(count >= fileNameLength) // 如果文件名长度等于或者超过了它的总长度,那么就是下一条值了
//    	{
//    		ByteBuf fileNameIn = in.readBytes(fileNameLength);
//        	byte[] FileNameByte = new byte[fileNameLength];  
//        	fileNameIn.readBytes(FileNameByte);  
//        	fileName = new String(FileNameByte, "UTF-8");
//    		break;
//    	}
//    }    
	
//      int bodyCount = 0;
//        while(true)
//        {
//        	bodyCount = bodyCount + in.readableBytes();
//        	if(bodyCount >= fileBodyLength) // 如果文件名长度等于或者超过了它的总长度,那么就是下一条值了
//        	{
//        		ByteBuf fileBodyIn = in.readBytes(fileBodyLength);
//            	byte[] FileBodyByte = new byte[fileBodyLength];  
//            	fileBodyIn.readBytes(FileBodyByte);
//            	fileBody = FileBodyByte;
//        		break;
//        	}
//        }
        
	
//        FileEntity fileEntity = new FileEntity();
//        if(fileEntity.getWhetherFile() == 0){ // 文件
//        	System.out.println("文件 ===");
//        	//注意 :  在编码时什么顺序存入的就按什么顺序取出,否则取值会有问题
//        	fileNameLength = in.readInt(); // int 是有顺序的,先存入哪个值就先取出哪个值
//        	
//        	if (in.readableBytes() < fileNameLength) {  
//        		throw new Exception("fileName 字段长度是 : " + fileNameLength + " , 但实际情况没有这么多.");  
//        	}  
//        	ByteBuf fileNameIn = in.readBytes(fileNameLength);  
//        	byte[] FileNameByte = new byte[fileNameIn.readableBytes()];  
//        	fileNameIn.readBytes(FileNameByte);  
//        	fileName = new String(FileNameByte, "UTF-8");
//        	
//        	fileBodyLength = in.readInt();
//        	
//        	int count =0;
//        	while(true)
//        	{
//        		count = count + in.readableBytes();
//        		if(count == fileBodyLength)
//        		{
//        			System.out.println(11);
//        			break;
//        		}
//				
//			}
//        	
//        	
//        	ByteBuf fileBodyIn = in.readBytes(fileBodyLength);  
//        	byte[] FileBodyByte = new byte[fileBodyIn.readableBytes()];  
//        	fileBodyIn.readBytes(FileBodyByte);  
//        	fileBody = FileBodyByte;
//        	
//        	whetherFile = in.readInt();
//        } else if(fileEntity.getWhetherFile() == 1) { // 文件夹
//        	System.out.println("文件夹 ===");
//        	fileNameLength = in.readInt(); // int 是有顺序的,先存入哪个值就先取出哪个值
//        	
//        	if (in.readableBytes() < fileNameLength) {  
//        		throw new Exception("fileName 字段长度是 : " + fileNameLength + " , 但实际情况没有这么多.");  
//        	}  
//        	ByteBuf fileNameIn = in.readBytes(fileNameLength);  
//        	byte[] FileNameByte = new byte[fileNameIn.readableBytes()];  
//        	fileNameIn.readBytes(FileNameByte);  
//        	fileName = new String(FileNameByte, "UTF-8");
//        	
//        	int bodyLength = in.readInt(); // 内容长度 -- 文件夹中没有内容所以关于内容的都不需要发送
//        	if (in.readableBytes() < bodyLength) {  
//        		throw new Exception("fileBody 字段长度是 : " + bodyLength + " , 但实际情况没有这么多.");  
//        	}  
//        	ByteBuf fileBodyIn = in.readBytes(bodyLength);  
//        	byte[] FileBodyByte = new byte[fileBodyIn.readableBytes()];  
//        	fileBodyIn.readBytes(FileBodyByte);  
//        	
//        	whetherFile = in.readInt();
//        }
//        
//        FileEntity fileEntitys = new FileEntity(fileName,fileBody,fileBodyLength,fileNameLength,whetherFile);
//        return fileEntitys;
}
