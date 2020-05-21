package com.llb.utils;


import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;

/**
 * 上传文件到七牛云
 * @Author llb
 * Date on 2020/1/3
 */
public class Upload2QiNiu {

    //    七牛云的accessKey
    private static final String ACCESSKEY = PropertiesUtil.getProperty("qiNiuAccessKey");
    //    七牛云的secretKey
    private static final String SECRETKEY = PropertiesUtil.getProperty("qiNiuSecretKey");
    //    存储空间名称
    private static final String BUCKET = PropertiesUtil.getProperty("bucket");

    public static Auth auth = null;
    static {
        if(auth == null) {
            auth = Auth.create(ACCESSKEY, SECRETKEY);
        }
    }



    //上传的目标对象（我这里地区是华东）
    Configuration cfg = new Configuration(Region.huadong());
    UploadManager uploadManager = new UploadManager(cfg);

    /**
     * 文件上传到七牛云
     * @param fileName 文件名
     * @return
     */
    public static String getUpToken(String fileName) {
        System.out.println("fileName::"+fileName);
        return auth.uploadToken(BUCKET, fileName);
    }

    /**
     * 给到文件，上传到七牛云
     * @TODO 获取文件下载链接
     * @param file
     * @param appid
     * @return
     */
    public String uploadFile(File file, String appid) throws QiniuException {
        //给项目文件分空间，全部保存在rubbish目录下
        String filename = "rubbish/"+appid+ "=" + System.currentTimeMillis() +file.getName();
        Response response = null;
        try {
            response = uploadManager.put(file, filename , getUpToken(filename));
        } catch (QiniuException e) {
            e.printStackTrace();
            System.out.println("上传失败！！！");
        }
        return response.bodyString();
    }

    /**
     * 给到文件，上传到七牛云
     * @param path 图片路径
     * @param appid
     * @return
     * @throws QiniuException
     */
    public String uploadFile(String path, String appid) throws QiniuException {
        File file = new File(path);
        return uploadFile(file, appid);
    }

    public static void main(String[] args) throws QiniuException {
        Upload2QiNiu upload2Qiniu = new Upload2QiNiu();
        String url = "F:\\mysys\\毕业设计-垃圾分类(智能图片识别垃圾分类）\\logo\\logo.png";
        File file = new File(url);
        String base64 = new Base64Util().image2Base64("http://img.liulebin.cn//icon/%E5%86%99%E4%BF%A1.png");
        String str = upload2Qiniu.uploadFile(file, "liulebin");
        System.out.println(str);
    }
}
