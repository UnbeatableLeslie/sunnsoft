package com.pengheng.service;

import java.util.List;
import java.util.Map;

import com.pengheng.model.CriterionVo;
public interface IDynamicSqlService {

    String dynamicInsert(String paramString1, String paramString2, CriterionVo criterionVo);

    int dynamicInsertWithEffect(String paramString1, String paramString2, CriterionVo criterionVo);

    void dynamicInsert(String paramString, CriterionVo criterionVo);

    int dynamicInsertWithEffect(String paramString, CriterionVo criterionVo);

    int dynamicUpdate(String paramString, CriterionVo criterionVo);

    int dynamicDelete(String paramString, CriterionVo criterionVo);

    List<Object> dynamicSelect(String paramString);
    
    List<Object> dynamicSelect(String paramString, CriterionVo criterionVo);

    Map<Object,Object> dynamicSelectUnique(String paramString, CriterionVo criterionVo);

    String dynamicGetSequence(String paramString);

}
