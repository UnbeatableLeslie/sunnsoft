package com.sunnsoft;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.sunnsoft.utils.ConfigLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class CodeGenerator {

    private static final String projectPath = System.getProperty("user.dir") + "/" + "sunnsoft-admin";
    private static  String TABLEPREFIX = "hetong_";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(("请输入" + tip + "："));
        return scanner.nextLine();
    }

    public static void main(String[] args) {
        String parent = "com.sunnsoft";
        String parent2 = "com/sunnsoft";
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 包配置
        PackageConfig pc = new PackageConfig();
        String moduleName = scanner("模块名(输入空白字符串不生成Controller)");
        String prefix = scanner("请输入表前缀");
        TABLEPREFIX = prefix.trim().isEmpty() ? TABLEPREFIX : prefix.trim();
        pc.setParent(parent);
        pc.setController("controller." + moduleName);
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity("dao.entity");
        pc.setMapper("dao.mapper");

        Map<String, String> pathMap = new HashMap<>();
        if (moduleName != null && !moduleName.trim().isEmpty()) {
            pathMap.put("controller_path", projectPath + "/src/main/java/" + parent2 + "/controller/" + moduleName);
        }
        String sercvice = scanner("是否生成service(输入空白字符串跳过生成Service)");
        String mapper = scanner("是否生成mapper(输入空白字符串跳过生成Mapper)");
        if (sercvice != null && !sercvice.trim().isEmpty()) {
            pathMap.put("service_path", projectPath + "/src/main/java/" + parent2 + "/service");
            pathMap.put("service_impl_path", projectPath + "/src/main/java/" + parent2 + "/service/impl");
        }
        if (mapper != null && !mapper.trim().isEmpty()) {
            pathMap.put("mapper_path", projectPath + "/src/main/java/" + parent2 + "/dao/mapper");
            pathMap.put("xml_path", projectPath + "/src/main/java/" + parent2 + "/dao/mapper");
        }

        pathMap.put("entity_path", projectPath + "/src/main/java/" + parent2 + "/dao/entity");

        pc.setPathInfo(pathMap);
        mpg.setPackageInfo(pc);
        //设置自定义配置

        mpg.setCfg(getInjectionConfig(pc, moduleName));
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        ConfigBuilder config = new ConfigBuilder(pc, getDataSourceConfig(), getStrategyConfig(pc), getTemplateConfig(), getGlobalConfig());

        mpg.setConfig(config);
        mpg.execute();
    }

    private static InjectionConfig getInjectionConfig(PackageConfig pc, String moduleName) {
        return new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("myModuName", Optional.ofNullable(moduleName).orElse(""));
                this.setMap(map);
            }
        };
    }

    private static StrategyConfig getStrategyConfig(PackageConfig pc) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setRestControllerStyle(true);
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setSuperControllerClass("com.sunnsoft.core.BaseController");
        strategy.setTablePrefix(TABLEPREFIX);
        return strategy;
    }

    private static TemplateConfig getTemplateConfig() {
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        templateConfig.setController("templates/controller2.java");
        templateConfig.setEntity("templates/entity2.java");

        return templateConfig;
    }

    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        ConfigLoader configLoader = ConfigLoader.getInstance();
        dsc.setUrl(configLoader.getValue("spring.datasource.url"));
        // dsc.setSchemaName("public");
        // dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName(configLoader.getValue("spring.datasource.driver-class-name"));
        dsc.setUsername(configLoader.getValue("spring.datasource.username"));
        dsc.setPassword(configLoader.getValue("spring.datasource.password"));
        return dsc;
    }

    private static GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(System.getProperty("user.name"));
        gc.setOpen(false);
        gc.setFileOverride(true);//设置覆盖文件为true
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        return gc;
    }

}
