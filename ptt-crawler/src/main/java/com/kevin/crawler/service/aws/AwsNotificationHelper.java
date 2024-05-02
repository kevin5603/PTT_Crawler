package com.kevin.crawler.service.aws;


import com.kevin.crawler.model.Article;
import com.kevin.crawler.model.Crawl;
import com.kevin.crawler.model.Subscriber;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.identity.spi.IdentityProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.DeleteTopicRequest;
import software.amazon.awssdk.services.sns.model.DeleteTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

@Deprecated
public class AwsNotificationHelper {

  private static final Logger log = LoggerFactory.getLogger(AwsNotificationHelper.class);
  private final AwsCredentialHelper awsCredentialHelper = new AwsCredentialHelper();
  private final SnsClient snsClient;
  private String topic = "ptt";

  public AwsNotificationHelper() {
    IdentityProvider provider = awsCredentialHelper.getProvider();
    snsClient = SnsClient.builder()
      .credentialsProvider(provider)
      .region(Region.US_WEST_2)
      .build();
  }

  public String createSNSTopic(String topicName) {
    CreateTopicResponse result;
    try {
      CreateTopicRequest request = CreateTopicRequest.builder()
        .name(topicName)
        .build();

      result = snsClient.createTopic(request);
      return result.topicArn();

    } catch (SnsException e) {
      e.printStackTrace();
      System.err.println(e.awsErrorDetails().errorMessage());
      System.exit(1);
    }
    return "";
  }

//  public void listSNSTopics() {
//    try {
//      ListTopicsRequest request = ListTopicsRequest.builder()
//        .build();
//
//      ListTopicsResponse response = snsClient.listTopics(request);
//      log.info(
//        "Status was " + response.sdkHttpResponse().statusCode() + "\n\nTopics\n\n"
//          + response.topics());
//
//      List<Topic> test = response.topics().stream()
//        .filter(topic -> topic.topicArn().endsWith("ptt")).toList();
//    } catch (SnsException e) {
//      e.printStackTrace();
//      System.err.println(e.awsErrorDetails().errorMessage());
//      System.exit(1);
//    }
//  }

  public void registerSubscriber(String topicArn, String protocol, String endpoint) {
    try {
      SubscribeRequest subscribeRequest = SubscribeRequest.builder()
        .protocol(protocol)
        .endpoint(endpoint)
        .returnSubscriptionArn(true)
        .topicArn(topicArn)
        .build();
      SubscribeResponse response = snsClient.subscribe(subscribeRequest);
      log.info("Subscription ARN: {}\n\n Status is {}", response.subscriptionArn(),
        response.sdkHttpResponse().statusCode());
    } catch (SnsException e) {
      e.printStackTrace();
      log.error(e.awsErrorDetails().errorMessage());
    }
  }

  public void pubTopic(String topicArn, String message) {
    try {
      PublishRequest publishRequest = PublishRequest.builder()
        .message(message)
        .topicArn(topicArn)
        .build();
      PublishResponse response = snsClient.publish(publishRequest);
      log.info("{} Message sent. Status is {}", response.messageId(),
        response.sdkHttpResponse().statusCode());
    } catch (SnsException e) {
      e.printStackTrace();
      log.error(e.awsErrorDetails().errorMessage());
    }
  }

  private void deleteSNSTopic(String topicArn) {
    try {
      DeleteTopicRequest request = DeleteTopicRequest.builder()
        .topicArn(topicArn)
        .build();
      DeleteTopicResponse response = snsClient.deleteTopic(request);
      log.info("{} Message sent. Status is {}", response.responseMetadata().requestId(),
        response.sdkHttpResponse().statusCode());
    } catch (SnsException e) {
      log.error(e.awsErrorDetails().errorMessage());
    }
  }

  public void sendSns(Article article, Set<Crawl> crawlSet) {
    String topicPrefix = "PTT_";
    for (Crawl crawl : crawlSet) {
      Subscriber subscriber = crawl.getSubscriber();
      String topicArn = createSNSTopic(topicPrefix + subscriber.getName());
//      String protocol = switch (subscriber.getNotifyType()) {
//        case 1 -> "email";
//        case 2 -> "sms";
//        default -> null;
//      };
//      registerSubscriber(topicArn, protocol, subscriber.getEndpoint());
      String message = """
        link: %s
        title: %s
        content: %s
        """.formatted(article.getLink(), article.getTitle(), article.getContent());
      pubTopic(topicArn, message);
    }
  }


}
