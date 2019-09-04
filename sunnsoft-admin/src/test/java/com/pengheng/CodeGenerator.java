package com.pengheng;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CodeGenerator {

    private static final String projectPath = System.getProperty("user.dir") + "/" + "sunnsoft-admin";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        String parent = "com.pengheng";
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 包配置
        PackageConfig pc = new PackageConfig();
        String moduleName = scanner("模块名");
        pc.setParent(parent);
        pc.setController("controller." + moduleName);
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setEntity("dao.entity");
        pc.setMapper("dao.mapper");

        Map<String, String> pathMap = new HashMap<>();
        pathMap.put("controller_path", projectPath + "/src/main/java/" + parent + "/controller/" + moduleName);
        pathMap.put("service_path", projectPath + "/src/main/java/" + parent + "/service");
        pathMap.put("service_impl_path", projectPath + "/src/main/java/" + parent + "/service/impl");
        pathMap.put("entity_path", projectPath + "/src/main/java/" + parent + "/dao/entity");
        pathMap.put("mapper_path", projectPath + "/src/main/java/" + parent + "/dao/mapper");
        pathMap.put("xml_path", projectPath + "/src/main/java/" + parent + "/dao/mapper");
        pc.setPathInfo(pathMap);
        mpg.setPackageInfo(pc);
        //设置自定义配置
//        mpg.setCfg(getInjectionConfig(pc));
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        ConfigBuilder config = new ConfigBuilder(pc, getDataSourceConfig(), getStrategyConfig(pc), getTemplateConfig(), getGlobalConfig());

        mpg.setConfig(config);
        mpg.execute();
    }

    private static StrategyConfig getStrategyConfig(PackageConfig pc) {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
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
        dsc.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
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