package com.pengheng.core;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.util.StringUtils;

public class DateConvertEditor extends PropertyEditorSupport {
  private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  
  private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  
  @Override
  public void setAsText(String paramString) throws IllegalArgumentException {
    if (StringUtils.hasText(paramString)) {
      try {
        if (paramString.indexOf(":") == -1 && paramString.length() == 10) {
          setValue(this.dateFormat.parse(paramString));
        } else if (paramString.indexOf(":") > 0 && paramString.length() == 19) {
          setValue(this.datetimeFormat.parse(paramString));
        } else if (paramString.indexOf(":") > 0 && paramString.length() == 21) {
          paramString = paramString.replace(".0", "");
          setValue(this.datetimeFormat.parse(paramString));
        } else {
          throw new IllegalArgumentException("Could not parse date, date format error");
        } 
      } catch (ParseException parseException) {
        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Could not parse date: " + parseException.getMessage());
        illegalArgumentException.initCause(parseException);
        throw illegalArgumentException;
      } 
    } else {
      setValue(null);
    } 
  }
}
