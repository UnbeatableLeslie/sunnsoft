package ${package.Controller};


import org.springframework.web.bind.annotation.*;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import com.pengheng.model.ResultVo;
import com.pengheng.model.ResultVoSuccess;
import com.pengheng.model.ResultVoFailure;

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
@Api(value = "${table.name}CRUD接口")
@RequestMapping("<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
 <#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
 <#else>
public class ${table.controllerName} {
 </#if>

    @Autowired
    private ${table.serviceName} ${table.name}Service;

    @ApiOperation(value = "获取${table.name}列表",notes="")
    @ApiImplicitParam(name = "${table.name}", value = "${table.name}实体", required = false, dataType = "${table.name}")
    @GetMapping("/")
    public ResultVo list(${table.entityName} ${table.name}) throws Exception {

        Wrapper<${table.entityName}> wrapper = new QueryWrapper<>();
        Collection<${table.entityName}> ${table.name}List = ${table.name}Service.list(wrapper);
        ResultVo resultVo = new ResultVoSuccess();
        resultVo.setData(${table.name}List);
        return resultVo;
    }



    @ApiOperation(value = "添加${table.name}",notes="新增一条${table.name}")
    @ApiImplicitParam(name = "${table.name}", value = "${table.name}实体", required = true, dataType = "${table.name}")
    @PostMapping("/add")
    public ResultVo add(${table.entityName} ${table.name}) throws Exception {

        Boolean flag = ${table.name}Service.save(${table.name});
        return flag?new ResultVoSuccess("添加成功"):new ResultVoFailure("添加失败");
    }

    @ApiOperation(value = "删除${table.name}",notes="根据id删除${table.name}")
    @ApiImplicitParam(name = "id", value = "${table.name}id", required = true, dataType = "<#list table.fields as field><#if field.keyFlag == true>${field.columnType?lower_case?cap_first}</#if></#list> ")
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable <#list table.fields as field><#if field.keyFlag == true>${field.columnType?lower_case?cap_first}</#if></#list> id) throws Exception {

        Boolean flag = ${table.name}Service.removeById(id);
        return flag?new ResultVoSuccess("删除成功"):new ResultVoFailure("删除失败");
    }

    @ApiOperation(value = "修改${table.name}",notes="根据id修改${table.name}")
    @ApiImplicitParam(name = "${table.name}", value = "${table.name}实体", required = true, dataType = "${table.entityName}")
    @PutMapping("/update")
    public ResultVo update(${table.entityName} ${table.name}) throws Exception {

        Boolean flag = ${table.name}Service.updateById(${table.name});
        return flag?new ResultVoSuccess("修改成功"):new ResultVoFailure("修改失败");
    }

    @ApiOperation(value = "查询${table.name}", notes = "查询${table.name}详细信息")
    @ApiImplicitParam(name = "id", value = "${table.name}id", required = true, dataType = "<#list table.fields as field><#if field.keyFlag == true>${field.columnType?lower_case?cap_first}</#if></#list> ")
    @PostMapping("get/{id}")
    public ResultVo get(@PathVariable Integer id) throws Exception {

        ${table.entityName} ${table.name} = ${table.name}Service.getById(id);
        if(${table.name}!=null){
           return new ResultVoSuccess("获取对象成功",${table.name});
        }else{
            return new ResultVoFailure("未找到对应信息");
        }
    }
}
</#if>
