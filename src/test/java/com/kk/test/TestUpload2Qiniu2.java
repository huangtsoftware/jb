package com.kk.test;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

public class TestUpload2Qiniu2 {
	// 设置好账号的ACCESS_KEY和SECRET_KEY
	String ACCESS_KEY = "TagrdOqL_pPwji0ltqUJdQJ0zeAC6QdEfPWC4_xX";
	String SECRET_KEY = "oQBC6tWN92gWt4noLna71pdbh4Ek8YaaFwZk2BA9";
	// 要上传的空间
	String bucketname = "zimuxh";
	// 上传到七牛后保存的文件名
	String key = "my-java.png";
	// 上传文件的路径
	String FilePath = "E:\\user.JPG";

	// 密钥配置
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	// 创建上传对象
	UploadManager uploadManager = new UploadManager();

	// 设置callbackUrl以及callbackBody,七牛将文件名和文件大小回调给业务服务器
	public String getUpToken() {
		return auth.uploadToken(bucketname, null, 3600,
				new StringMap().put("callbackUrl", "http://your.domain.com/callback").put("callbackBody",
						"filename=$(fname)&filesize=$(fsize)"));
	}

	public void upload() throws IOException {
		try {
			// 调用put方法上传
			Response res = uploadManager.put(FilePath, null, getUpToken());
			// 打印返回的信息
			System.out.println(res.bodyString());
		} catch (QiniuException e) {
			Response r = e.response;
			// 请求失败时打印的异常的信息
			System.out.println(r.toString());
			try {
				// 响应的文本信息
				System.out.println(r.bodyString());
			} catch (QiniuException e1) {
				// ignore
			}
		}
	}

	public static void main(String args[]) throws IOException {
		new TestUpload2Qiniu2().upload();
	}
}