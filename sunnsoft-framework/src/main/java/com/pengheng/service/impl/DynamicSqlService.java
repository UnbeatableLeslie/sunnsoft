package com.pengheng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengheng.mapper.IDynamicSqlDao;
import com.pengheng.model.CriterionVo;
import com.pengheng.service.IDynamicSqlService;
import com.pengheng.util.Toolkits;

@Service("dynamicSqlService")
public class DynamicSqlService implements IDynamicSqlService {

    @Autowired
    private IDynamicSqlDao dynamicSqlDao = null;

	@Override
    public int dynamicInsertWithEffect(String tableName, String sequenceName, CriterionVo paramCriterionVo)
    {
        int i = 0;
		HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", tableName);
        localHashMap.put("resultList", paramCriterionVo.getResultList());
        if (!"".equals(Toolkits.defaultString(sequenceName)))
        {
            localHashMap.put("sequenceName", sequenceName);
            i = this.dynamicSqlDao.dynamicInsertWithSelectKey(localHashMap);
        }
        else
        {
            i = this.dynamicSqlDao.dynamicInsertWithoutSelectKey(localHashMap);
        }
        return i;
    }

    @Override
    public int dynamicInsertWithEffect(String tableName, CriterionVo paramCriterionVo)
    {
        return dynamicInsertWithEffect(tableName, null, paramCriterionVo);
    }

    @Override
    public String dynamicInsert(String tableName, String sequenceName, CriterionVo paramCriterionVo)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", tableName);
        localHashMap.put("resultList", paramCriterionVo.getResultList());
        if (!"".equals(Toolkits.defaultString(sequenceName)))
        {
            localHashMap.put("sequenceName", sequenceName);
            this.dynamicSqlDao.dynamicInsertWithSelectKey(localHashMap);
        }
        else
        {
            this.dynamicSqlDao.dynamicInsertWithoutSelectKey(localHashMap);
        }
        return Toolkits.defaultString(localHashMap.get("id"));
    }

    @Override
    public String dynamicInsert(String tableName, CriterionVo paramCriterionVo)
    {
    	HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", tableName);
        localHashMap.put("resultList", paramCriterionVo.getResultList());
        this.dynamicSqlDao.dynamicInsertWithoutSelectKey(localHashMap);
        return Toolkits.defaultString(localHashMap.get("id"));
    }

    @Override
    public int dynamicUpdate(String tableName, CriterionVo paramCriterionVo)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", tableName);
        localHashMap.put("resultList", paramCriterionVo.getResultList());
        localHashMap.put("conditionList", paramCriterionVo.getConditionList());
        return this.dynamicSqlDao.dynamicUpdate(localHashMap);
    }

    @Override
    public int dynamicDelete(String tableName, CriterionVo paramCriterionVo)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", tableName);
        localHashMap.put("conditionList", paramCriterionVo.getConditionList());
        return this.dynamicSqlDao.dynamicDelete(localHashMap);
    }
    
    @Override
    public List<Map<Object,Object>> dynamicSelect(String paramString)
    {
        return dynamicSelect(paramString,new CriterionVo());
    }

    @Override
    public List<Map<Object,Object>> dynamicSelect(String paramString, CriterionVo paramCriterionVo)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", paramString);
        String str = "*";
        if ((paramCriterionVo.getResultList() != null) && (!paramCriterionVo.getResultList().isEmpty())) {
            str = paramCriterionVo.serializeColunmName();
        }
        localHashMap.put("resultList", str);
        localHashMap.put("conditionList", paramCriterionVo.getConditionList());
        return this.dynamicSqlDao.dynamicSelect(localHashMap);
    }
    @Override
    public Map<Object,Object> dynamicSelectUnique(String paramString)
    {
    	return dynamicSelectUnique(paramString, new CriterionVo());
    }

    @Override
    public Map<Object,Object> dynamicSelectUnique(String tableName, CriterionVo paramCriterionVo)
    {
    	HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", tableName);
        String str = "*";
        if ((paramCriterionVo.getResultList() != null) && (!paramCriterionVo.getResultList().isEmpty())) {
            str = paramCriterionVo.serializeColunmName();
        }
        localHashMap.put("resultList", str);
        localHashMap.put("conditionList", paramCriterionVo.getConditionList());
        List<Map<Object,Object>> list = this.dynamicSqlDao.dynamicSelect(localHashMap);
        if(CollectionUtils.isEmpty(list)){
        	return null;
        }else {
        	return (Map<Object, Object>) list.get(0);
        }
    }

    @Override
    public String dynamicGetSequence(String sequenceName)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("sequenceName", sequenceName);
        return this.dynamicSqlDao.dynamicGetSequence(localHashMap);
    }

}