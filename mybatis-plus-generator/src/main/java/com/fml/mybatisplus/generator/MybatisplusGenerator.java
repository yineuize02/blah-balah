package com.fml.mybatisplus.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MybatisplusGenerator {
  /** 读取控制台内容 */
  public static String scanner(String tip) {
    Scanner scanner = new Scanner(System.in);
    StringBuilder help = new StringBuilder();
    help.append("请输入").append(tip).append("：");
    System.out.println(help.toString());
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (StringUtils.isNotBlank(ipt)) {
        return ipt;
      }
    }
    throw new MybatisPlusException("请输入正确的" + tip + "！");
  }

  /** RUN THIS */
  public static void main(String[] args) {
    // 代码生成器
    AutoGenerator mpg = new AutoGenerator();

    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    String projectPath = System.getProperty("user.dir");
    gc.setOutputDir(projectPath + "/mybatis-plus-sample-generator/src/main/java");
    gc.setAuthor("y");
    gc.setOpen(false);
    gc.setBaseResultMap(true);
    gc.setBaseColumnList(true);
    gc.setServiceName("I%sMbpService");
    gc.setServiceImplName("%sMbpServiceImpl");
    mpg.setGlobalConfig(gc);

    String dataBaseName = scanner("Database");
    // String dataBaseName = "blah_user";

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    String url =
        String.format(
            "jdbc:mysql://192.168.230.128:3306/%s?useUnicode=true&serverTimezone=GMT&useSSL=false&characterEncoding=utf8",
            dataBaseName);
    dsc.setUrl(url);
    dsc.setDriverName("com.mysql.cj.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("root");
    mpg.setDataSource(dsc);

    // 包配置
    PackageConfig pc = new PackageConfig();
    // pc.setModuleName("blah-user");
    pc.setModuleName(scanner("模块名"));
    pc.setParent("com.fml.blah");
    mpg.setPackageInfo(pc);

    // 自定义配置
    InjectionConfig cfg =
        new InjectionConfig() {
          @Override
          public void initMap() {
            // to do nothing
          }
        };
    List<FileOutConfig> focList = new ArrayList<>();
    focList.add(
        new FileOutConfig("/templates/mapper.xml.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            // 自定义输入文件名称
            return new String(
                projectPath
                    + "/mybatis-plus-sample-generator/src/main/resources/mapper/"
                    + pc.getModuleName()
                    + "/"
                    + tableInfo.getEntityName()
                    + "Mapper"
                    + StringPool.DOT_XML);
          }
        });

    focList.add(
        new FileOutConfig("/templates/fieldNames.java.ftl") {
          @Override
          public String outputFile(TableInfo tableInfo) {
            return new String(
                projectPath
                    + "/mybatis-plus-sample-generator/src/main/java/"
                    + pc.getParent().replace('.', '/')
                    + "/"
                    + "entity_table_field_name"
                    + "/"
                    + tableInfo.getEntityName()
                    + "FieldNames"
                    + StringPool.DOT_JAVA);
          }
        });
    cfg.setFileOutConfigList(focList);
    mpg.setCfg(cfg);
    mpg.setTemplate(new TemplateConfig().setXml(null));

    // 策略配置
    StrategyConfig strategy = new StrategyConfig();
    strategy.setNaming(NamingStrategy.underline_to_camel);
    strategy.setColumnNaming(NamingStrategy.underline_to_camel);
    //
    // strategy.setSuperEntityClass("com.baomidou.mybatisplus.samples.generator.common.BaseEntity");
    strategy.setEntityLombokModel(true);
    //    strategy.setSuperControllerClass(
    //        "com.baomidou.mybatisplus.samples.generator.common.BaseController");
    // strategy.setInclude(scanner("表名"));
    // strategy.setInclude("users");
    //  strategy.setSuperEntityColumns("id");
    strategy.setControllerMappingHyphenStyle(true);
    strategy.setTablePrefix(pc.getModuleName() + "_");
    mpg.setStrategy(strategy);
    // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
    mpg.setTemplateEngine(new FreemarkerTemplateEngine());
    mpg.execute();
  }
}
