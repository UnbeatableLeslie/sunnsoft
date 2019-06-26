package com.pengheng.core.datasource;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class DataSourceTransactionManager extends org.springframework.jdbc.datasource.DataSourceTransactionManager {
  private static final long serialVersionUID = 6078725651654466985L;
  
  private static final Logger logger = Logger.getLogger(DataSourceTransactionManager.class);
  
  @Override
  protected void doCommit(DefaultTransactionStatus paramDefaultTransactionStatus) {
    super.doCommit(paramDefaultTransactionStatus);
    if (logger.isDebugEnabled()) {
        logger.debug("Commit transaction: completed[" + paramDefaultTransactionStatus.isCompleted() + "]");
    }
  }
  
  @Override
  protected void doRollback(DefaultTransactionStatus paramDefaultTransactionStatus) {
    super.doRollback(paramDefaultTransactionStatus);
    if (logger.isDebugEnabled()) {
        logger.debug("Rollback transaction: completed[" + paramDefaultTransactionStatus.isCompleted() + "]");
    }
  }
  
  @Override
  protected void doBegin(Object paramObject, TransactionDefinition paramTransactionDefinition) {
    super.doBegin(paramObject, paramTransactionDefinition);
    if (logger.isDebugEnabled()) {
        logger.debug("Begin transaction: name[" + paramTransactionDefinition.getName() + "], isolation[" + paramTransactionDefinition.getIsolationLevel() + "]");
    }
  }
}
