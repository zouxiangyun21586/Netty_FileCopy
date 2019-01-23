package com.yr.filecopy;

public class FileMain {
	
	public static void main(String[] args) throws Exception {
        new FileServer().start();
        new FileClient().start();
	}
}
