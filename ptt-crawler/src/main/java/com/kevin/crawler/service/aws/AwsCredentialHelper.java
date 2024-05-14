package com.kevin.crawler.service.aws;

import com.kevin.crawler.config.InitializeConfiguration;
import java.util.Properties;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.identity.spi.IdentityProvider;

public class AwsCredentialHelper {

  private static IdentityProvider provider;

  public AwsCredentialHelper() {
    init();
  }

  public static void init() {
    if (provider == null) {
//      Properties p = InitializeConfiguration.initializeConfig();
//      String accessKey = p.getProperty("AWS_ACCESS_KEY_ID");
//      String secretKey = p.getProperty("AWS_SECRET_KEY_ID");
//      AwsBasicCredentials credentials = AwsBasicCredentials.builder().accessKeyId(accessKey)
//        .secretAccessKey(secretKey).build();
//      provider = StaticCredentialsProvider.create(credentials);
    }
  }

  public IdentityProvider getProvider() {
    return provider;
  }

}
