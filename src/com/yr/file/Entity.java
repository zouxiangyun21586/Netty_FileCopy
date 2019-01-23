package com.yr.file;

import java.util.Arrays;

public class Entity {
	
	private int fileType;
	
	private String fileName;
	
	private int fileNameLength;
	
	private int fileContentLength;
	
	private byte[] fileContent;
	
	private int filePathLength;
	
	private String filePath;
	
	public Entity(){
		
	}
	public Entity(int fileType,String fileName,int fileNameLength,int filePathLength,String filePath){
		this.fileType = fileType;
		this.fileName = fileName;
		this.fileNameLength = fileNameLength;
		this.filePath = filePath;
		this.filePathLength = filePathLength;
	}
	public Entity(int fileType,String fileName,int fileNameLength,int fileContentLength,byte[] fileContent,int filePathLength,String filePath){
		this.fileType = fileType;
		this.fileName = fileName;
		this.fileNameLength = fileNameLength;
		this.fileContentLength = fileContentLength;
		this.fileContent = fileContent;
		this.filePath = filePath;
		this.filePathLength = filePathLength;
	}
	
	public int getFilePathLength() {
		return filePathLength;
	}
	public void setFilePathLength(int filePathLength) {
		this.filePathLength = filePathLength;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileNameLength() {
		return fileNameLength;
	}

	public void setFileNameLength(int fileNameLength) {
		this.fileNameLength = fileNameLength;
	}

	public int getFileContentLength() {
		return fileContentLength;
	}

	public void setFileContentLength(int fileContentLength) {
		this.fileContentLength = fileContentLength;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	@Override
	public String toString() {
		return "Entity [fileType=" + fileType + ", fileName=" + fileName + ", fileNameLength=" + fileNameLength
				+ ", fileContentLength=" + fileContentLength + ", fileContent=" + Arrays.toString(fileContent)
				+ ", filePathLength=" + filePathLength + ", filePath=" + filePath + "]";
	}

	
}
