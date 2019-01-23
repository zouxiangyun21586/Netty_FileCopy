package com.yr.file;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class CustomDecoder extends LengthFieldBasedFrameDecoder {

	private static final int HEADER_SIZE = 16;

	private int fileType;

	private String fileName;

	private int fileNameLength;

	private int fileContentLength;

	private byte[] fileContent;
	
	private int filePathLength;
	
	private String filePath;
	
	private  int i = 0 ;
	
	private int count = 0;

	/**
	 * 
	 * @param maxFrameLength
	 *            解码时，处理每个帧数据的最大长度
	 * @param lengthFieldOffset
	 *            该帧数据中，存放该帧数据的长度的数据的起始位置
	 * @param lengthFieldLength
	 *            记录该帧数据长度的字段本身的长度
	 * @param lengthAdjustment
	 *            修改帧数据长度字段中定义的值，可以为负数
	 * @param initialBytesToStrip
	 *            解析的时候需要跳过的字节数
	 * @param failFast
	 *            为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，
	 *            为false，读取完整个帧再报异常
	 */
	public CustomDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
			int initialBytesToStrip, boolean failFast) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		if(i == 0)
		{
			Entity entity = null;
			if (in == null) {
				return null;
			}
			if (in.readableBytes() < HEADER_SIZE) {
				throw new Exception("可读信息段比头部信息都小，你在逗我？");
			}
			fileType = in.readInt();
			
			fileNameLength = in.readInt();
			
    		byte a[] = new byte[fileNameLength];
    		ByteBuf buf1 = in.readBytes(fileNameLength);
    		buf1.readBytes(a);
    		String name = new String(a);
    		System.out.println(name);
    		
    		fileContentLength = in.readInt();
    		
    		fileContent = new byte[fileContentLength];
    		
    		int b = in.readableBytes();
    		count = count + b;
    		in.readBytes(fileContent,0,b);
    		++i;
		}
		else
		{
			int b = in.readableBytes();
    		in.readBytes(fileContent,count,b);
    		count = count + b;
    		if(count == fileContentLength)
    		{
    			System.out.println(count);
    			CustomServerHandler.getFile(fileContent, "d://a.exe");
    			//System.out.println(new String(fileContent));
    			
    		}
		}
		
	        
	        
	      
	        
//		if(fileType == 0) {
//			fileContentLength = in.readInt();
//			int begin = 0;
//			int end = 0;
//			
//			byte conbyte[] = new byte[100000];
//			int fileContentCount = 0;
//	        while(true)
//	        {
//	        	begin = fileContentCount;
//	        	end = in.readableBytes();
//	        	fileContentCount = fileContentCount + end;
//	        	
//	        	ByteBuf buf1 = in.readBytes(end);
//        		buf1.readBytes(conbyte,begin,end);
//	        	if(fileContentCount >= fileContentLength)
//	        	{
//	        		
//	        		break;
//	        	}
//	        }
//	        
//			
//		}
//		filePathLength = in.readInt();
//		int filePathCount = 0;
//        while(true)
//        {
//        	filePathCount = filePathCount + in.readableBytes();
//        	if(filePathCount >= filePathLength)
//        	{
//        		ByteBuf buf2 = in.readBytes(filePathLength);
//        		byte[] req2 = new byte[filePathLength];
//        		buf2.readBytes(req2);
//        		filePath = new String(req2);
//        		break;
//        	}
//        }
//        
//        
//		if (fileType == 0){
//			entity = new Entity(fileType, fileName, fileNameLength, fileContentLength, fileContent,filePathLength,filePath);
//		}else {
//			entity = new Entity(fileType, fileName, fileNameLength,filePathLength,filePath);
//		}
		return null;
	}
}