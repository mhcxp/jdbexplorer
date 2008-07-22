package cn.com.qimingx.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import cn.com.qimingx.utils.ImageUtils;

public class TestImageProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> paths = new ArrayList<String>();
		paths.add("c:/temp/images/notImg.gif");
		paths.add("c:/temp/images/noImg.bmp");
		paths.add("c:/temp/images/noImg.jpg");

		paths.add("c:/temp/images/china_good.gif");
		paths.add("c:/temp/images/caput.GIF");
		paths.add("c:/temp/images/bmp.jpg");
		paths.add("c:/temp/images/bmp.bmp");
		paths.add("c:/temp/images/bmp2.bmp");
		paths.add("c:/temp/images/aPNG.png");

		for (String path : paths) {
			File file = new File(path);
			if (file.exists()) {
				InputStream in = null;
				try {
					in = new FileInputStream(file);
					String name = file.getName();
					if (ImageUtils.isImage(in)) {
						System.out.println(name + " is Image File@@");
					} else {
						System.out.println(name + " not Image File**");
					}
				} catch (FileNotFoundException e) {
					System.out.println("ERROR,读取图片文件出错~");
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(in);
				}
			} else {
				System.out.println("Error,文件不存在：" + file.getAbsolutePath());
			}
		}
	}

}
