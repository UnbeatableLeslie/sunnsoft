package com.pengheng.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
public final class CriterionVo {

    private List<Object> resultList = new ArrayList<Object>();
    private List<Object> conditionList = new ArrayList<Object>();

    public final void clear()
    {
        this.resultList.clear();
        this.conditionList.clear();
    }

    public List<Object> getResultList()
    {
        return this.resultList;
    }

    public List<Object> getConditionList()
    {
        return this.conditionList;
    }

    public final void addResult(String columnName, Object columnValue, boolean paramBoolean)
    {
        String str = "";
        if (((columnValue instanceof Long)) || ((columnValue instanceof Integer)) || ((columnValue instanceof Float)) || ((columnValue instanceof Double)) || ((columnValue instanceof Byte)))
            str = String.valueOf(columnValue);
        this.resultList.add(new ColumnVo(columnName, str.equals("") ? columnValue : paramBoolean ? "_primarykey_" : str));
    }

    public final void addNormalResult(String columnName, Object columnValue)
    {
        if(columnValue!=null){
            addResult(columnName, columnValue, false);
        }
    }

    public final void addNormalResult(String columnName)
    {
        addResult(columnName, null, false);
    }

    public final void addPrimaryKeyResult(String columnName)
    {
        addResult(columnName, "", true);
    }

    public final void addCondition(String columnName, Object columnValue)
    {
        if(columnValue!=null){
            this.conditionList.add(new ColumnVo(columnName, columnValue));
        }
    }

    public final void addCondition(String columnName, String operator, Object columnValue)
    {
        if(columnValue!=null){
            this.conditionList.add(new ColumnVo(columnName, columnValue, operator));
        }
    }
    public final void addCondition(String linker, String columnName, String operator, Object columnValue)
    {
        this.conditionList.add(new ColumnVo(columnName, columnValue, operator, linker));
    }

    public final String serializeColunmName()
    {
        ArrayList<Object> localArrayList = new ArrayList<Object>();
        for (int i = 0; i < this.resultList.size(); i++)
        {
            ColumnVo localColumnVo = (ColumnVo)this.resultList.get(i);
            localArrayList.add(localColumnVo.getColumnName());
        }
        return StringUtils.join(localArrayList.toArray(), ",");
    }
}