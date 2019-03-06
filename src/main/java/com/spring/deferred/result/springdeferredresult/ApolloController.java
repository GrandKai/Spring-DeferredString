package com.spring.deferred.result.springdeferredresult;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @Author: zyn
 * @Description:
 * @Date: Created in 2019-03-06 16:16
 * @Modified By:
 */
@RestController
@Slf4j
public class ApolloController {

  private Multimap<String, DeferredResult<String>> watchRequests =
      Multimaps.synchronizedSetMultimap(HashMultimap.create());


  //模拟长轮询
  @RequestMapping(value = "/watch/{namespace}", method = RequestMethod.GET, produces = "text/html")
  public DeferredResult<String> watch(@PathVariable("namespace") String namespace) {
    log.info("收到请求：{}", namespace);
    DeferredResult<String> result = new DeferredResult<String>();

    result.onCompletion(new Runnable() {
      @Override
      public void run() {
        log.info("移除 key: {}", namespace);
        watchRequests.remove(namespace, result);

      }
    });

    watchRequests.put(namespace, result);
    log.info("controller 线程释放");
    return result;
  }


  //模拟发布namespace配置
  @RequestMapping(value = "/publish/{namespace}", method = RequestMethod.GET, produces = "text/html")
  public Object publishConfig(@PathVariable("namespace") String namespace) {
    if (watchRequests.containsKey(namespace)) {
      Collection<DeferredResult<String>> deferredResults = watchRequests.get(namespace);
      Long time = System.currentTimeMillis();
      //通知所有watch这个namespace变更的长轮训配置变更结果
      for (DeferredResult<String> deferredResult : deferredResults) {
        deferredResult.setResult(namespace + " changed:" + time);
      }
    }
    return "success";
  }
}
