package com.esd.common.disability;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class AutoDiscern {
	private int[] num = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private int black = -14000000;
	private static final int[] s0 = new int[] { 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0,
			0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, 0, 0 };
	private static final int[] s1 = new int[] { 0, 0, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0,
			0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, 0 };
	private static final int[] s2 = new int[] { 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, -1, -1, -1, 0, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0,
			-1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0 };
	private static final int[] s3 = new int[] { 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, -1, -1, -1, 0, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1,
			-1, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, -1, -1, 0, 0, -1, -1, -1, -1, -1, 0, 0, 0 };
	private static final int[] s4 = new int[] { 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, -1, 0, -1, -1, 0, 0, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, 0, 0,
			-1, -1, 0, 0, -1, 0, 0, 0, -1, -1, 0, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0 };
	private static final int[] s5 = new int[] { 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0,
			-1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0 };
	private static final int[] s6 = new int[] { 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 0, -1, -1, -1, 0, 0, -1, -1, -1, 0, 0,
			-1, -1, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, -1, -1, 0, 0 };
	private static final int[] s7 = new int[] { 0, 0, -1, -1, -1, -1, -1, -1, 0, 0, -1, -1, -1, -1, -1, -1, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0,
			0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0 };
	private static final int[] s8 = new int[] { 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, -1, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, -1, 0, -1, -1, 0, 0, 0, -1,
			-1, -1, 0, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, -1, -1, 0, 0 };
	private static final int[] s9 = new int[] { 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, -1, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0,
			0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, 0, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0 };
	private static final ArrayList<int[]> list = new ArrayList<int[]>(9) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6732234070993079758L;

		{
			add(s0);
			add(s1);
			add(s2);
			add(s3);
			add(s4);
			add(s5);
			add(s6);
			add(s7);
			add(s8);
			add(s9);
		}
	};

	public AutoDiscern() {

	}

	private int[] proc(int[] s) {
		for (int m = 0; m < s.length; m++) {
			if (s[m] > black) {
				s[m] = 0;
			} else {
				s[m] = -1;
			}
			// System.out.print("[" + m + "]:" + s[m]);
		}
		return s;
	}

	public String discernPic(BufferedImage bi) {
		BiImage biImage = new BiImage();
		try {
			biImage.initialize(bi);
			BufferedImage tmpBi = biImage.monochrome();
			int s1 = cut(tmpBi, 6, 4, 8, 12);// 测试OK
			int s2 = cut(tmpBi, 19, 4, 8, 12);// 测试OK
			int s3 = cut(tmpBi, 32, 4, 8, 12);// 测试OK
			int s4 = cut(tmpBi, 45, 4, 8, 12);// 测试OK
			StringBuffer sb = new StringBuffer();
			sb.append(s1).append(s2).append(s3).append(s4);
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int discern(BufferedImage bufferedImage) {
		int value = -1;
		int resemble = 0;
		int[] pixels = new int[96];
		int[] src = getRGB(bufferedImage, 0, 0, 8, 12, pixels);
		src = proc(src);
		for (int l = 0; l < list.size(); l++) {
			int tmp = 0;
			int[] it = (int[]) list.get(l);
			for (int i = 0; i < src.length; i++) {
				if (src[i] == it[i]) {
					tmp++;
				}
			}
			if (tmp == 96) {
				value = l;
				return num[value];
			}
			if (tmp > resemble) {
				resemble = tmp;
				value = l;
			}

		}
		// System.out.println("resemble:[" + resemble + "%]");
		return num[value];

	}

	public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}

	public int cut(BufferedImage bi, int x, int y, int width, int height) {
		int rand = -1;
		BufferedImage read = null;
		// 读取源图像;
		int srcHeight = bi.getHeight(); // 源图宽度
		int srcWidth = bi.getWidth(); // 源图高度
		if (srcWidth > 0 && srcHeight > 0) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
			Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.createGraphics();
			g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
			g.dispose();

			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(tag, "JPEG", os);
				// read = ImageIO.read(result);
				ByteArrayInputStream bin = new ByteArrayInputStream(os.toByteArray());
				read = ImageIO.read(bin);
			} catch (IOException e) {
				e.printStackTrace();
			}
			rand = discern(read);
		}
		return rand;
	}

}
