package ${package.Controller};


import org.springframework.web.bind.annotation.*;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
<#if swagger2>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
</#if>
import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;
import java.util.Collection;
import com.sunnsoft.model.ResultVo;
import com.sunnsoft.model.ResultVoSuccess;
import com.sunnsoft.model.ResultVoFailure;
import com.sunnsoft.model.ResultVoNotFound;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import ${package.Service}.${table.serviceName};
import ${package.Entity}.${table.entityName};
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
    * ${table.comment!} 前端控制器
    * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@Slf4j
<#if swagger2>
@Api(value = "${table.entityName}CRUD接口")
</#if>
@RequestMapping("${cfg.myModuName}/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
 <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
 <#else>
public class ${table.controllerName} {
 </#if>

    @Autowired
    private ${table.serviceName} ${table.entityPath}Service;

    <#if swagger2>
    @ApiOperation(value = "获取${table.entityPath}列表",notes="")
    </#if>
    @GetMapping("/")
    public ResultVo list(${table.entityName} ${table.entityPath}) throws Exception {
        QueryWrapper<${table.entityName}> wrapper = new QueryWrapper<>();
        Collection<${table.entityName}> ${table.entityPath}List = ${table.entityPath}Service.list(wrapper);
        ResultVo resultVo = new ResultVoSuccess();
        resultVo.setData(${table.entityPath}List);
        return resultVo;
    }

    <#if swagger2>
    @ApiOperation(value = "添加${table.entityPath}",notes="新增一条${table.entityPath}")
    </#if>
    @PostMapping("/add")
    public ResultVo add(${table.entityName} ${table.entityPath}) throws Exception {
        Boolean flag = ${table.entityPath}Service.save(${table.entityPath});
        return flag?new ResultVoSuccess("添加成功"):new ResultVoFailure("添加失败");
    }

    <#if swagger2>
    @ApiOperation(value = "删除${table.entityPath}",notes="根据id删除${table.entityPath}")
    </#if>
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable <#list table.fields as field><#if field.keyFlag == true>${field.columnType?lower_case?cap_first}</#if></#list> id) throws Exception {
        Boolean flag = ${table.entityPath}Service.removeById(id);
        return flag?new ResultVoSuccess("删除成功"):new ResultVoFailure("删除失败");
    }

    <#if swagger2>
    @ApiOperation(value = "修改${table.entityPath}",notes="根据id修改${table.entityPath}")
    </#if>
    @PutMapping("/update")
    public ResultVo update(${table.entityName} ${table.entityPath}) throws Exception {
        Boolean flag = ${table.entityPath}Service.updateById(${table.entityPath});
        return flag?new ResultVoSuccess("修改成功"):new ResultVoFailure("修改失败");
    }

    <#if swagger2>
    @ApiOperation(value = "查询${table.entityPath}", notes = "查询${table.entityPath}详细信息")
    </#if>
    @PostMapping("get/{id}")
    public ResultVo get(@PathVariable <#list table.fields as field><#if field.keyFlag == true>${field.columnType?lower_case?cap_first}</#if></#list> id) throws Exception {
        ${table.entityName} ${table.entityPath} = ${table.entityPath}Service.getById(id);
        if(${table.entityPath}!=null){
           return new ResultVoSuccess("获取对象成功",${table.entityPath});
        }else{
            return new ResultVoNotFound("未找到对应信息");
        }
    }
}
</#if>
