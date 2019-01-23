package com.yr.copy;

import java.io.Serializable;

public class Request implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2813211330451521507L;
	private String id;
	private String name;
	private String requestMessage;
	private byte[] attachment;
	private boolean isCreateFile;
	
	public boolean getIsCreateFile() {
		return isCreateFile;
	}

	public void setCreateFile(boolean isCreateFile) {
		this.isCreateFile = isCreateFile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public byte[] getAttachment() {
		return attachment;
	}

	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}

}
