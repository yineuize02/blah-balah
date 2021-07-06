package com.fml.blah.common.zookeeper;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ZookeeperUtils")
public class ZookeeperUtils {
  @Autowired CuratorFramework curatorFramework;

  public boolean create(String path) {
    try {
      curatorFramework.create().forPath(path);
      return true;
    } catch (Exception ex) {
      log.error("【创建节点失败】{},{}", path, ex);
      return false;
    }
  }

  public boolean create(String path, byte[] payload) {
    try {
      curatorFramework.create().forPath(path, payload);
      return true;
    } catch (Exception ex) {
      log.error("【创建节点失败】{},{}", path, ex);
      return false;
    }
  }
  /**
   * 创建临时节点 需要注意：虽说临时节点是session失效后立刻删除， 但是并不是client一断开连接，session就立刻会失效
   * 失效是由zk服务端控制的，每次连接时，客户端和服务端会协商一个合理的超时时间 如果超过了超时时间client都一直美哦与发送心跳，才回真的删除这个session创建的临时节点
   */
  public boolean createEphemeral(String path, byte[] payload) {
    try {
      curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(path, payload);
      return true;
    } catch (Exception ex) {
      log.error("【创建节点失败】{},{}", path, ex);
      return false;
    }
  }

  public String createEphemeralSequential(String path, byte[] payload) {
    try {
      return curatorFramework
          .create()
          .withProtection()
          .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
          .forPath(path, payload);
    } catch (Exception ex) {
      log.error("【创建节点存在异常】{},{}", path, ex);
      return null;
    }
  }

  public Stat existsStat(String path) {
    try {
      Stat stat = curatorFramework.checkExists().forPath(path);
      return stat;
    } catch (Exception ex) {
      log.error("【查看指定节点是否存在异常】{},{}", path, ex);
      return null;
    }
  }

  public Boolean isExists(String path) {
    try {
      var result = curatorFramework.checkExists().forPath(path) != null;
      return result;
    } catch (Exception e) {
      log.error("【isExists】{},{}", path, e);
    }
    return null;
  }

  public String getData(String path) {
    try {
      return new String(curatorFramework.getData().forPath(path));
    } catch (Exception ex) {
      log.error("【获取节点数据异常】{},{}", path, ex);
      return null;
    }
  }

  public boolean setData(String path) {
    try {
      curatorFramework.setData().forPath(path);
      return true;
    } catch (Exception ex) {
      log.error("【设置节点值异常】{},{}", path, ex);
      return false;
    }
  }

  public boolean setDataAsync(String path, byte[] payload) {
    try {
      CuratorListener listener =
          new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event)
                throws Exception {
              if (event != null) System.out.println(event.toString());
            }
          };
      curatorFramework.getCuratorListenable().addListener(listener);

      curatorFramework.setData().inBackground().forPath(path, payload);

      return true;
    } catch (Exception ex) {
      log.error("【设置节点值异常】{},{}", path, ex);
      return false;
    }
  }

  public boolean setDataAsyncWithCallback(
      BackgroundCallback callback, String path, byte[] payload) {
    try {
      curatorFramework.setData().inBackground(callback).forPath(path, payload);
      return true;
    } catch (Exception ex) {
      log.error("【设置节点值异常】{},{}", path, ex);
      return false;
    }
  }

  public List<String> getChildren(String path) {
    try {
      return curatorFramework.getChildren().forPath(path);
    } catch (Exception ex) {
      log.error("【查询指定子节点异常】{},{}", path, ex);
      return null;
    }
  }

  public List<String> watchedGetChildren(String path) {
    try {
      return curatorFramework.getChildren().watched().forPath(path);
    } catch (Exception ex) {
      log.error("【查询指定子节点异常】{},{}", path, ex);
      return null;
    }
  }

  public List<String> watchedGetChildren(String path, Watcher watcher) {
    try {
      return curatorFramework.getChildren().usingWatcher(watcher).forPath(path);
    } catch (Exception ex) {
      log.error("【查询指定子节点异常】{},{}", path, ex);
      return null;
    }
  }

  public boolean delete(String path) {
    try {
      curatorFramework.delete().forPath(path);
      return true;
    } catch (Exception ex) {
      log.error("【删除指定节点异常】{},{}", path, ex);
      return false;
    }
  }

  public boolean guaranteedDelete(String path) {
    try {
      curatorFramework.delete().guaranteed().forPath(path);
      return true;
    } catch (Exception ex) {
      log.error("【删除指定节点异常】{},{}", path, ex);
      return false;
    }
  }
}
