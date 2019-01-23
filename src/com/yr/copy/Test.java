package com.yr.copy;

import java.io.File;
import java.io.FileNotFoundException;

public class Test {
	private static String path = "F:\\zxy\\数据加密";

	private static void isFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				isFile(file2);
			}

		} else {
			System.out.println(file.getPath());
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File(path);
		isFile(file);
	}
}
