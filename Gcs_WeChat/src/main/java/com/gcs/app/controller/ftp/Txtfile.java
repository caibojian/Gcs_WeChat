package com.gcs.app.controller.ftp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class Txtfile {

	public static BufferedReader bufread;

	public static void creatTxtFile(File filename) throws IOException {
		if (!filename.exists()) {
			filename.createNewFile();
			System.err.println(filename + "�Ѵ�����");
		}
	}

	public static String readTxtFile(File filename) {
		String read;
		String readStr = "";
		try {
			bufread = new BufferedReader(new InputStreamReader(new FileInputStream(filename),"UTF-8"));
			try {
				while ((read = bufread.readLine()) != null) {
					readStr = readStr + read + "\r\n";
					bufread.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("print:" + "\r\n" + readStr);
		return readStr;
	}

	public static void writeTxtFile(File filename, String readStr, String newStr)
			throws IOException {
		// �ȶ�ȡԭ���ļ����ݣ�Ȼ�����д�����
		String filein = newStr + "\r\n" + readStr + "\r\n";
		RandomAccessFile mm = null;
		try {
			mm = new RandomAccessFile(filename, "rw");
			mm.write(filein.getBytes("UTF-8"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (mm != null) {
				try {
					mm.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static void replaceTxtByStr(String path, String oldStr,
			String replaceStr) {
		String temp = "";
		try {
			File file = new File(path);
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer buf = new StringBuffer();

			// �������ǰ�������
			for (int j = 1; (temp = br.readLine()) != null
					&& !temp.equals(oldStr); j++) {
				buf = buf.append(temp);
				buf = buf.append(System.getProperty("line.separator"));
			}

			// �����ݲ���
			buf = buf.append(replaceStr);

			// ������к��������
			while ((temp = br.readLine()) != null) {
				buf = buf.append(System.getProperty("line.separator"));
				buf = buf.append(temp);
			}

			br.close();
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fos);
			pw.write(buf.toString().toCharArray());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
