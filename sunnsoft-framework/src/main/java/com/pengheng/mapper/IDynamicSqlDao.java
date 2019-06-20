package com.pengheng.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository("dynamicSqlDao")
//@Mapper
public interface IDynamicSqlDao {

     int dynamicInsertWithSelectKey(Map<Object, Object> paramMap);

     int dynamicInsertWithoutSelectKey(Map<Object, Object> paramMap);

     int dynamicUpdate(Map<Object, Object> paramMap);

     int dynamicDelete(Map<Object, Object> paramMap);

     List<Map<Object,Object>> dynamicSelect(Map<Object, Object> paramMap);

     String dynamicGetSequence(Map<Object, Object> paramMap);

     Map<Object, Object> querySequence(Map<Object, Object> paramMap);

     int createSequence(Map<Object, Object> paramMap);

}
