package com.myproject.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;

public class IconUtil {
	
	/**
	 * 讲颜色值转换成color对象，如讲#FFFFFF转换成color对象
	 * @param str
	 * @return
	 */
	public static Color string2Color(String colorStr) {  
        int i = Integer.parseInt(colorStr.substring(1), 16);  
        return new Color(i);  
    }  

	/**
	 * 修改png图片中透明图大于0的的像素颜色
	 * @param iconPath
	 * @param colorValue 像这样 #ffffff
	 * @param targetIconPath
	 */
	public static void updateIconColor(String iconPath, Color color, String targetIconPath){
		try {
			File file = new File(iconPath);
			BufferedImage bi = ImageIO.read(file);
			int width = bi.getWidth();
			 int minx = bi.getMinX();
			 int height = bi.getHeight();
		        int miny = bi.getMinY();
			//循环每个像素点，更改透明度大于0的像素点的值
			for(int i = minx; i< width; i++){
				for(int j = miny; j< height; j++){
					//argb 分别代表 透明图、红绿蓝三原色,通过如下位运算就可以获取a，r,g,b 的值
					int argb = bi.getRGB(i, j);
					//下面是获取这个4个值的代码，通过位运算效率很高
					int a = (argb&0xff000000)>>>24;  //透明度是0到100,0 表示完全透明
			
/*					//下面的3原色的值范围分别是0到255
					int r = (argb&0xff0000)>>>16;
					int g = (argb&0xff00)>>>8;
					int b = (argb&0xff);*/
					if(a > 0){
						bi.setRGB(i,j,color.getRGB());
					}
				}
			}
			//生成新的图片
			File targetFile = new File(targetIconPath);
/*			if(!targetFile.exists()){
				targetFile.createNewFile();
			}*/
			 ImageIO.write(bi, "png", targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
	}
	
	
	/**
	 * 替换图片中的特定颜色
	 * @param iconPath
	 * @param targetPah
	 * @param sourceColor
	 * @param targetColor
	 * @param iconType
	 */
	public static void replaceIconColor(String iconPath, String targetPah, Color sourceColor, Color targetColor, String iconType){
		try {
			File file = new File(iconPath);
			BufferedImage bi = ImageIO.read(file);
			int width = bi.getWidth();
			 int minx = bi.getMinX();
			 int height = bi.getHeight();
		        int miny = bi.getMinY();
			//循环每个像素点
			for(int i = minx; i< width; i++){
				for(int j = miny; j< height; j++){
					//argb 分别代表 透明图、红绿蓝三原色,通过如下位运算就可以获取a，r,g,b 的值
					int argb = bi.getRGB(i, j);
					//下面是获取这个4个值的代码，通过位运算效率很高
//					int a = (argb&0xff000000)>>>24;  //透明度是0到100,0 表示完全透明
			
/*					//下面的3原色的值范围分别是0到255
					int r = (argb&0xff0000)>>>16;
					int g = (argb&0xff00)>>>8;
					int b = (argb&0xff);*/
					if(argb == sourceColor.getRGB()){
						bi.setRGB(i,j,targetColor.getRGB());
					}
				}
			}
			//生成新的图片
			 ImageIO.write(bi, iconType, new File(targetPah));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
		}
	}
	
	public static void main(String[] args) {
		//updateIconColor("E:\\test\\test.png", string2Color("#660066"), "E:\\test\\test01"+UUID.randomUUID()+".png");
		replaceIconColor("E:\\test\\test.jpg", "E:\\test\\test01"+UUID.randomUUID()+".jpg", string2Color("#fddebf"), string2Color("#0783bb") , "jpg");
	}
}
