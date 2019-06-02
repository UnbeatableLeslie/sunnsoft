package com.pengheng.model;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.pengheng.util.Toolkits;

public final class ColumnVo implements Serializable {
    private static final long serialVersionUID = 1469310718520300181L;
    private String columnName;
    private Object columnValue;
    private String operator = "=";
    private String linker = "and";

    public ColumnVo(String paramString, Object paramObject)
    {
        setColumnName(paramString);
        setColumnValue(paramObject);
    }

    public ColumnVo(String paramString1, Object paramObject, String paramString2)
    {
        setColumnName(paramString1);
        setColumnValue(paramObject);
        setOperator(paramString2);
    }

    public ColumnVo(String paramString1, Object paramObject, String paramString2, String paramString3)
    {
        setColumnName(paramString1);
        setColumnValue(paramObject);
        setOperator(paramString2);
        setLinker(paramString3);
    }

    public String getLinker()
    {
        return this.linker;
    }

    public void setLinker(String paramString)
    {
        this.linker = paramString;
    }

    public String getOperator()
    {
        return this.operator;
    }

    public void setOperator(String paramString)
    {
        if ("in".equals(paramString))
        {
            String[] arrayOfString = StringUtils.split(Toolkits.defaultString(getColumnValue()), ',');
            setColumnValue("'" + StringUtils.join(arrayOfString, "','") + "'");
        }
        this.operator = paramString;
    }

    public String getColumnName()
    {
        return this.columnName;
    }

    public void setColumnName(String paramString)
    {
        this.columnName = paramString;
    }

    public Object getColumnValue()
    {
        return this.columnValue;
    }

    public void setColumnValue(Object paramObject)
    {
        this.columnValue = paramObject;
    }
}