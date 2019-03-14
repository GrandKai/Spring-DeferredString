package com.spring.deferred.result.springdeferredresult;

import com.netease.cloud.ClientConfiguration;
import com.netease.cloud.auth.BasicCredentials;
import com.netease.cloud.auth.Credentials;
import com.netease.cloud.services.nos.NosClient;
import com.netease.cloud.services.nos.model.Bucket;
import com.netease.cloud.services.nos.transfer.TransferManager;
import java.io.File;
import java.util.List;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2019-03-07 16:16
 * @Modified By:
 */
public class TTT {

  public static void main(String[] args) {
    String accessKey = "fb79521202814d929c69d0ac7800178d";
    String secretKey = "f070937a7d354123a955fff047f3e02f";
    String endPoint = "nos-eastchina1.126.net";

    Credentials credentials = new BasicCredentials(accessKey, secretKey);

    ClientConfiguration conf = new ClientConfiguration();
    // 设置 NosClient 使用的最大连接数
    conf.setMaxConnections(200);
    // 设置 socket 超时时间
    conf.setSocketTimeout(10000);
    // 设置失败请求重试次数
    conf.setMaxErrorRetry(2);
    // 如果要用 https 协议，请加上下面语句
//    conf.setProtocol(Protocol.HTTPS);

    NosClient nosClient = new NosClient(credentials, conf);
    nosClient.setEndpoint(endPoint);

    List<Bucket> list = nosClient.listBuckets();

/*    for (Bucket bucket : list) {

      String bucketName =  bucket.getName();
      System.out.println("桶名称：" + bucketName);

//      nosClient.deleteBucket(bucketName);

      boolean exists = nosClient.doesBucketExist(bucketName);
      CannedAccessControlList acl = nosClient.getBucketAcl(bucketName);
      // bucket权限
      System.out.println("acl权限：" + acl.toString());

      nosClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }*/

    String bucketName = list.get(0).getName();

/*    //要上传文件的路径
    String filePath = "C:\\Users\\zyn\\Pictures\\timg.jpg";

    File file = new File(filePath);

    // 没有目录的话会自动创建目录
    String serverPath = "用户名称目录/" + file.getName();

    try {
      nosClient.putObject(bucketName, serverPath, file);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }*/

    File file1 = new  File("F:\\Chromium-73.0.3683.27.zip");
    TransferManager transferManager = new TransferManager(nosClient);

    transferManager.upload(bucketName, file1.getName(), file1);

    /*Download download = transferManager.download(bucketName, "timg.jpg", file1);
    try {
      download.waitForCompletion();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }*/
  }


}
