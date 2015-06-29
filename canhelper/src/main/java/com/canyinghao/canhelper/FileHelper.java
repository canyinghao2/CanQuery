package com.canyinghao.canhelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.os.Environment;

/**
 * 文件工具类
 * @author canyinghao
 *
 */
public class FileHelper {

	private static FileHelper util;

	synchronized public static FileHelper getInstance() {

		if (util == null) {
			util = new FileHelper();

		}
		return util;

	}

	private FileHelper() {
		super();
	}

	/**
	 * 是否有外存卡
	 * 
	 * @return
	 */
	public boolean isExistExternalStore() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到sd卡路径
	 * 
	 * @return
	 */
	public String getExternalStorePath() {
		if (isExistExternalStore()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	/**
	 * 将字符串写入文件
	 * 
	 * @param text
	 * @param fileStr
	 * @param isAppend
	 */
	public void writeFile(String text, String fileStr, boolean isAppend) {

		try {
			File file = new File(fileStr);

			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream f = new FileOutputStream(fileStr, isAppend);
			f.write(text.getBytes());
			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 移动文件 1 需要一个write权限 2 目录要同级别,这个很关键,你交换的两个文件夹要有相同的层数.
	 * 
	 * @param srcFileName
	 *            源文件完整路径，需要文件名
	 * @param destDirName
	 *            目的目录路径,不需要文件名
	 * @return 文件移动成功返回true，否则返回false
	 */
	public boolean moveFile(String srcFileName, String destDirName) {

		File srcFile = new File(srcFileName);
		if (!srcFile.exists() || !srcFile.isFile()) {
			return false;
		}

		File destDir = new File(destDirName);
		if (!destDir.exists()) {
			destDir.mkdirs();
		}

		return srcFile.renameTo(new File(destDirName + File.separator
				+ srcFile.getName()));
	}

	/**
	 * 重命名
	 * 
	 * @param oldFilePath
	 *            旧文件的完整路径
	 * @param newName
	 *            新的文件名
	 * @return
	 */
	public boolean renameFile(String oldFilePath, String newName) {
		File srcFile = new File(oldFilePath);
		if (!srcFile.exists() || !srcFile.isFile()) {
			return false;
		}

		File destDir = new File(srcFile.getParentFile(), newName);

		return srcFile.renameTo(destDir);

	}

	/**
	 * 拷贝文件
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 * @throws java.io.IOException
	 */
	public boolean copyFileTo(File srcFile, File destFile) {

		try {

			if (!srcFile.exists() || srcFile.isDirectory()) {
				return false;
			}

			if (destFile.isDirectory()) {
				return false;
			}
			FileInputStream fis = new FileInputStream(srcFile);
			FileOutputStream fos = new FileOutputStream(destFile);
			int readLen = 0;
			byte[] buf = new byte[1024];
			while ((readLen = fis.read(buf)) != -1) {
				fos.write(buf, 0, readLen);
			}
			fos.flush();
			fos.close();
			fis.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean copyFileTo(String srcStr, String destStr) {
		File srcFile = new File(srcStr);
		File destFile = new File(destStr);

		return copyFileTo(srcFile, destFile);
	}

	/**
	 * 拷贝文件夹
	 * 
	 * @param srcDir
	 * @param destDir
	 * @return
	 */
	public boolean copyFilesTo(File srcDir, File destDir) {

		if (!destDir.exists()) {
			destDir.mkdirs();
		}
		if (!srcDir.isDirectory() || !destDir.isDirectory()) {
			return false;
		}
		File[] srcFiles = srcDir.listFiles();
		for (int i = 0; i < srcFiles.length; i++) {
			if (srcFiles[i].isFile()) {

				File destFile = new File(destDir.getPath() + File.separator
						+ srcFiles[i].getName());
				copyFileTo(srcFiles[i], destFile);
			} else if (srcFiles[i].isDirectory()) {
				File theDestDir = new File(destDir.getPath() + File.separator
						+ srcFiles[i].getName());
				copyFilesTo(srcFiles[i], theDestDir);
			}
		}
		return true;
	}

	public boolean copyFilesTo(String srcStr, String destStr) {
		File srcFile = new File(srcStr);
		File destFile = new File(destStr);

		return copyFilesTo(srcFile, destFile);
	}

	/**
	 * 移动文件
	 * 
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	public boolean moveFileTo(File srcFile, File destFile) {
		boolean iscopy = copyFileTo(srcFile, destFile);
		if (!iscopy)
			return false;
		delFile(srcFile.getAbsolutePath());
		return true;
	}

	public boolean moveFileTo(String srcStr, String destStr) {
		File srcFile = new File(srcStr);
		File destFile = new File(destStr);

		return moveFileTo(srcFile, destFile);
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean delFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return false;
		}

		return file.delete();
	}

	/**
	 * 删除文件夹里的所有文件
	 * 
	 * @param path
	 * @return
	 */
	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);
				delFolder(path + File.separator + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
