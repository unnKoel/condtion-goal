package com.wind.goal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/**
 * 文件缓存抽象类
 * 常用于文件修改后重新加载
 * 
 * @author zhouyanjun
 * @version 1.0 2015-1-15
 */
public abstract class FileCache {
	private static final Logger logger = Logger.getLogger(FileCache.class);
	public final static int RELOAD_INTERVAL_TIME = 20;
	public final static String DEFAULT_ENCODING = "utf-8";
	private String filePath; // 文件路径
	private long lastCheckTime = 0l; // 最后检查时间
	private long lastModified = 0l; // 最后修改时间

	public FileCache(String filePath) {
		this(filePath, false);
	}

	/**
	 * @param filePath
	 *            文件路径
	 * @param initNeedExist
	 *            初始化时是否必须存在此文件
	 */
	public FileCache(String filePath, boolean initNeedExist) {
		this.filePath = filePath;
		if (filePath == null) {
			throw new NullPointerException("filePath is null");
		}
		File file = new File(filePath);
		if (!file.exists()) {
			if (initNeedExist) {
				throw new NullPointerException(filePath + " is not exists");
			} else {
				logger.error(filePath + " is not exists");
			}
		} else {
			loadFile();
		}
	}

	/**
	 * 加载文件，若文件修改则调用重新加载数据抽象方法
	 * 1、判断文件最后检查时间
	 * 2、判断文件是否修改
	 */
	protected void loadFile() {
		if (filePath != null && System.currentTimeMillis() - lastCheckTime > RELOAD_INTERVAL_TIME) {
			File file = new File(filePath);
			if (!file.exists()) {// 文件不存在则2秒检查一次
				lastCheckTime = System.currentTimeMillis() + 2000 - RELOAD_INTERVAL_TIME;
				logger.error(filePath + " is not exists");
				return;
			}
			lastCheckTime = System.currentTimeMillis();
			if (lastModified != file.lastModified()) {
				String msg = "Load File Cache:" + file.getAbsolutePath()
						+ " beforeModified:" + lastModified + " lastModified:" + file.lastModified();
				if (logger.isEnabledFor(Priority.WARN)) {
					logger.warn(msg);
				} else {
					System.out.println(this.getClass().getSimpleName() + ">> " + msg + " date:" + new Date());
				}

				lastModified = file.lastModified();
				this.loadData();
			}
		}
	}

	protected String readFile() throws Exception
	{
		if (this.filePath == null || this.filePath == "")
			throw new FileNotFoundException();
		StringBuilder sb = new StringBuilder();
		String line = null;
		BufferedReader br = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filePath));
			br = new BufferedReader(new InputStreamReader(fis, DEFAULT_ENCODING));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (br != null) br.close();
			if (fis != null) fis.close();
		}
		char c = sb.charAt(0);
		while (c != '[' && c != '{') {
			sb.deleteCharAt(0);
			c = sb.charAt(0);
		}
		return sb.toString();
	}

	/**
	 * 加载数据
	 */
	abstract protected void loadData();

	protected File getFile() {
		return new File(filePath);
	}

	public long getLastModified() {
		return lastModified;
	}
}