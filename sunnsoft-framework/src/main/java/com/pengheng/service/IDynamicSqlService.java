package com.pengheng.service;

import java.util.List;
import java.util.Map;

import com.pengheng.model.CriterionVo;
public interface IDynamicSqlService {

    String dynamicInsert(String paramString1, String paramString2, CriterionVo criterionVo);

    int dynamicInsertWithEffect(String paramString1, String paramString2, CriterionVo criterionVo);

    String dynamicInsert(String paramString, CriterionVo criterionVo);

    int dynamicInsertWithEffect(String paramString, CriterionVo criterionVo);

    int dynamicUpdate(String paramString, CriterionVo criterionVo);

    int dynamicDelete(String paramString, CriterionVo criterionVo);

    List<Map<Object,Object>> dynamicSelect(String paramString);
    
    List<Map<Object,Object>> dynamicSelect(String paramString, CriterionVo criterionVo);
    
    Map<Object,Object> dynamicSelectUnique(String paramString);

    Map<Object,Object> dynamicSelectUnique(String paramString, CriterionVo criterionVo);

    String dynamicGetSequence(String paramString);

}
