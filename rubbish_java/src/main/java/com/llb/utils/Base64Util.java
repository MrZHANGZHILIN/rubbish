package com.llb.utils;

/**
 * @Author llb
 * Date on 2020/5/7
 */

import com.alibaba.druid.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

/**
 * 图片编码64或解码
 * @Author llb
 * Date on 2019/12/24
 */
public class Base64Util {

    /**
     * 将文件进行64编码处理
     */
    public static String image2Base64(File file, String imgType) {
        ByteArrayOutputStream outputStream = null;

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, imgType, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    /**
     * 通过本地图片路径，转码base64
     * @param imgPath
     * @return
     */
    public static String image2Base64(String imgPath) {
        File file = new File(imgPath);
        //获取图片类型
        String imgType = imgPath.substring(imgPath.lastIndexOf(".")+1);
        return image2Base64(file, imgType);
    }

    /**
     * 通过本地图片路径，转码base64
     * @param file 图片文件
     * @return
     */
    public static String image2Base64(File file) {
        String path = file.getAbsolutePath();
        return image2Base64(path);
    }

    /**
     * 图片解码
     */
    public static boolean base642Image(String imgStr, String imgFilePath) {
        //图片为空
        if(StringUtils.isEmpty(imgStr)) {
            return false;
        }
        OutputStream out = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < bytes.length; i++) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            out = new FileOutputStream(imgFilePath);
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
