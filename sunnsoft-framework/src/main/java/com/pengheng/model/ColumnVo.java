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

    public ColumnVo(String columnName, Object columnValue)
    {
        setColumnName(columnName);
        setColumnValue(columnValue);
    }

    public ColumnVo(String columnName, Object columnValue, String operator)
    {
        setColumnName(columnName);
        setColumnValue(columnValue);
        setOperator(operator);
    }

    public ColumnVo(String columnName, Object columnValue, String operator, String linker)
    {
        setColumnName(columnName);
        setColumnValue(columnValue);
        setOperator(operator);
        setLinker(linker);
    }

    public String getLinker()
    {
        return this.linker;
    }

    public void setLinker(String linker)
    {
        this.linker = linker;
    }

    public String getOperator()
    {
        return this.operator;
    }

    public void setOperator(String operator)
    {
        if ("in".equals(operator))
        {
            String[] arrayOfString = StringUtils.split(Toolkits.defaultString(getColumnValue()), ',');
            setColumnValue("'" + StringUtils.join(arrayOfString, "','") + "'");
        }
        this.operator = operator;
    }

    public String getColumnName()
    {
        return this.columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public Object getColumnValue()
    {
        return this.columnValue;
    }

    public void setColumnValue(Object columnValue)
    {
        this.columnValue = columnValue;
    }
}