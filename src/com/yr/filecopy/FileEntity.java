package com.yr.filecopy;

import java.util.Arrays;

/**
 * 文件拷贝实体类
 * 
 * @author zxy-un
 * 
 * 2018年7月9日 下午3:55:53
 */
public class FileEntity {
	private String fileName;
	private byte[] fileBody;
	private int fileBodyLength;
	private int fileNameLength;
	private int whetherFile;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileBody() {
		return fileBody;
	}

	public void setFileBody(byte[] fileBody) {
		this.fileBody = fileBody;
	}

	public int getFileBodyLength() {
		return fileBodyLength;
	}

	public void setFileBodyLength(int fileBodyLength) {
		this.fileBodyLength = fileBodyLength;
	}

	public int getFileNameLength() {
		return fileNameLength;
	}

	public void setFileNameLength(int fileNameLength) {
		this.fileNameLength = fileNameLength;
	}

	public int getWhetherFile() {
		return whetherFile;
	}

	public void setWhetherFile(int whetherFile) {
		this.whetherFile = whetherFile;
	}

	public FileEntity(String fileName, int fileNameLength, int whetherFile) {
		super();
		this.fileName = fileName;
		this.fileNameLength = fileNameLength;
		this.whetherFile = whetherFile;
	}
	
	public FileEntity(String fileName, byte[] fileBody, int fileBodyLength, int fileNameLength, int whetherFile) {
		super();
		this.fileName = fileName;
		this.fileBody = fileBody;
		this.fileBodyLength = fileBodyLength;
		this.fileNameLength = fileNameLength;
		this.whetherFile = whetherFile;
	}

	@Override
	public String toString() {
		return "FileEntity [fileName=" + fileName + ", fileBody=" + Arrays.toString(fileBody) + ", fileBodyLength="
				+ fileBodyLength + ", fileNameLength=" + fileNameLength + ", whetherFile=" + whetherFile + "]";
	}

	public FileEntity() {
		super();
	}

}
