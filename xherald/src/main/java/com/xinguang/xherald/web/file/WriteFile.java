package com.xinguang.xherald.web.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
	public static void write2File(String path,String content){
		try {
			File file=new File(path);
			//System.out.println(file.getAbsolutePath());
			File dir=file.getParentFile();
	        if (!dir.exists()) {
	            dir.mkdirs();
	            file.createNewFile();
	        }else if (!file.exists()) {
	        	file.createNewFile();
			}
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(new File(path),true));
			bWriter.write(content);
			bWriter.newLine();
			bWriter.flush();
			bWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
