package com.pengheng.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pengheng.mapper.IDynamicSqlDao;
import com.pengheng.model.CriterionVo;
import com.pengheng.service.IDynamicSqlService;
import com.pengheng.util.Toolkits;

@Service("dynamicSqlService")
public class DynamicSqlService implements IDynamicSqlService {

    private static final String JDBC_DEFAULT_USERNAME = Toolkits.getSystemPropertyValue("jdbc_username");

    @Autowired
    private IDynamicSqlDao dynamicSqlDao = null;

	public int dynamicInsertWithEffect(String paramString1, String paramString2, CriterionVo paramCriterionVo)
    {
        int i = 0;
		HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", paramString1);
        localHashMap.put("resultList", paramCriterionVo.getResultList());
        if (!Toolkits.defaultString(paramString2).equals(""))
        {
            localHashMap.put("sequenceName", paramString2);
            i = this.dynamicSqlDao.dynamicInsertWithSelectKey(localHashMap);
        }
        else
        {
            i = this.dynamicSqlDao.dynamicInsertWithoutSelectKey(localHashMap);
        }
        return i;
    }

    public int dynamicInsertWithEffect(String paramString, CriterionVo paramCriterionVo)
    {
        return dynamicInsertWithEffect(paramString, null, paramCriterionVo);
    }

    public String dynamicInsert(String paramString1, String paramString2, CriterionVo paramCriterionVo)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", paramString1);
        localHashMap.put("resultList", paramCriterionVo.getResultList());
        if (!Toolkits.defaultString(paramString2).equals(""))
        {
            localHashMap.put("sequenceName", paramString2);
            this.dynamicSqlDao.dynamicInsertWithSelectKey(localHashMap);
        }
        else
        {
            this.dynamicSqlDao.dynamicInsertWithoutSelectKey(localHashMap);
        }
        return Toolkits.defaultString(localHashMap.get("id"));
    }

    public String dynamicInsert(String paramString, CriterionVo paramCriterionVo)
    {
    	HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", paramString);
        localHashMap.put("resultList", paramCriterionVo.getResultList());
        this.dynamicSqlDao.dynamicInsertWithoutSelectKey(localHashMap);
        return Toolkits.defaultString(localHashMap.get("id"));
    }

    public int dynamicUpdate(String paramString, CriterionVo paramCriterionVo)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", paramString);
        localHashMap.put("resultList", paramCriterionVo.getResultList());
        localHashMap.put("conditionList", paramCriterionVo.getConditionList());
        return this.dynamicSqlDao.dynamicUpdate(localHashMap);
    }

    public int dynamicDelete(String paramString, CriterionVo paramCriterionVo)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", paramString);
        localHashMap.put("conditionList", paramCriterionVo.getConditionList());
        return this.dynamicSqlDao.dynamicDelete(localHashMap);
    }
    
    public List<Map<Object,Object>> dynamicSelect(String paramString)
    {
        return dynamicSelect(paramString,new CriterionVo());
    }

    public List<Map<Object,Object>> dynamicSelect(String paramString, CriterionVo paramCriterionVo)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", paramString);
        String str = "*";
        if ((paramCriterionVo.getResultList() != null) && (!paramCriterionVo.getResultList().isEmpty()))
            str = paramCriterionVo.serializeColunmName();
        localHashMap.put("resultList", str);
        localHashMap.put("conditionList", paramCriterionVo.getConditionList());
        return this.dynamicSqlDao.dynamicSelect(localHashMap);
    }
    public Map<Object,Object> dynamicSelectUnique(String paramString)
    {
    	return dynamicSelectUnique(paramString, new CriterionVo());
    }

    public Map<Object,Object> dynamicSelectUnique(String paramString, CriterionVo paramCriterionVo)
    {
    	HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("tableName", paramString);
        String str = "*";
        if ((paramCriterionVo.getResultList() != null) && (!paramCriterionVo.getResultList().isEmpty()))
            str = paramCriterionVo.serializeColunmName();
        localHashMap.put("resultList", str);
        localHashMap.put("conditionList", paramCriterionVo.getConditionList());
        List<Map<Object,Object>> list = this.dynamicSqlDao.dynamicSelect(localHashMap);
        if(CollectionUtils.isEmpty(list)){
        	return null;
        }else {
        	return (Map<Object, Object>) list.get(0);
        }
        
        
    }

    public String dynamicGetSequence(String paramString)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("sequenceName", paramString);
        return this.dynamicSqlDao.dynamicGetSequence(localHashMap);
    }

    private boolean checkSequenceExists(String paramString)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        localHashMap.put("sequence_name", paramString.toUpperCase());
        localHashMap.put("sequence_owner", JDBC_DEFAULT_USERNAME.toUpperCase());
        Map localMap = this.dynamicSqlDao.querySequence(localHashMap);
        return MapUtils.isNotEmpty(localMap);
    }

    private void createSequence(String paramString)
    {
        HashMap<Object,Object> localHashMap = new HashMap<Object,Object>();
        String str = "create sequence " + paramString + " minvalue 1 maxvalue 9999999999999999999999999999 start with 1000001 increment by 1";
        localHashMap.put("sql", str);
        this.dynamicSqlDao.createSequence(localHashMap);
    }
}