package com.pengheng.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
public final class CriterionVo {

    private List<Object> resultList = new ArrayList();
    private List<Object> conditionList = new ArrayList();

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

    public final void addResult(String paramString, Object paramObject, boolean paramBoolean)
    {
        String str = "";
        if (((paramObject instanceof Long)) || ((paramObject instanceof Integer)) || ((paramObject instanceof Float)) || ((paramObject instanceof Double)) || ((paramObject instanceof Byte)))
            str = String.valueOf(paramObject);
        this.resultList.add(new ColumnVo(paramString, str.equals("") ? paramObject : paramBoolean ? "_primarykey_" : str));
    }

    public final void addNormalResult(String paramString, Object paramObject)
    {
        if(paramObject!=null){
            addResult(paramString, paramObject, false);
        }
    }

    public final void addNormalResult(String paramString)
    {
        addResult(paramString, null, false);
    }

    public final void addPrimaryKeyResult(String paramString)
    {
        addResult(paramString, "", true);
    }

    public final void addCondition(String paramString, Object paramObject)
    {
        if(paramObject!=null){
            this.conditionList.add(new ColumnVo(paramString, paramObject));
        }
    }

    public final void addCondition(String paramString1, String paramString2, Object paramObject)
    {
        if(paramObject!=null){
            this.conditionList.add(new ColumnVo(paramString1, paramObject, paramString2));
        }
    }

    public final void addCondition(String paramString1, String paramString2, String paramString3, Object paramObject)
    {
        this.conditionList.add(new ColumnVo(paramString2, paramObject, paramString3, paramString1));
    }

    public final String serializeColunmName()
    {
        ArrayList localArrayList = new ArrayList();
        for (int i = 0; i < this.resultList.size(); i++)
        {
            ColumnVo localColumnVo = (ColumnVo)this.resultList.get(i);
            localArrayList.add(localColumnVo.getColumnName());
        }
        return StringUtils.join(localArrayList.toArray(), ",");
    }
}