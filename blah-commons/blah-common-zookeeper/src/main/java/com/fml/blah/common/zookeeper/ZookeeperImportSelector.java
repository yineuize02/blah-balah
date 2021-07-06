package com.fml.blah.common.zookeeper;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ZookeeperImportSelector implements ImportSelector {

  @Override
  public String[] selectImports(AnnotationMetadata importingClassMetadata) {
    return new String[] {"com.fml.blah.common.zookeeper.ZookeeperConfig"};
  }
}
