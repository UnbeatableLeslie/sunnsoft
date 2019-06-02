package com.pengheng.core.datasource;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.pengheng.core.DefaultContainer;
import com.pengheng.util.Toolkits;

public class ManualTransaction {
  public static final int PROPAGATION_REQUIRED = 0;
  
  public static final int PROPAGATION_SUPPORTS = 1;
  
  public static final int PROPAGATION_MANDATORY = 2;
  
  public static final int PROPAGATION_REQUIRES_NEW = 3;
  
  public static final int PROPAGATION_NOT_SUPPORTED = 4;
  
  public static final int PROPAGATION_NEVER = 5;
  
  public static final int PROPAGATION_NESTED = 6;
  
  public static final int ISOLATION_DEFAULT = -1;
  
  public static final int ISOLATION_READ_UNCOMMITTED = 1;
  
  public static final int ISOLATION_READ_COMMITTED = 2;
  
  public static final int ISOLATION_REPEATABLE_READ = 4;
  
  public static final int ISOLATION_SERIALIZABLE = 8;
  
  private TransactionStatus transactionStatus = null;
  
  private DataSourceTransactionManager dataSourceTransactionManager = null;
  
  private static final Logger logger = Logger.getLogger(ManualTransaction.class);
  
  public ManualTransaction() { this(3, 2); }
  
  public ManualTransaction(int paramInt1, int paramInt2) { this(null, paramInt1, paramInt2); }
  
  public ManualTransaction(String paramString, int paramInt1, int paramInt2) {
    if (DefaultContainer.getSpringContext() != null) {
      if (Toolkits.defaultString(paramString).equals(""))
        paramString = "dataSourceTransactionManager.default"; 
      if (this.dataSourceTransactionManager == null)
        this.dataSourceTransactionManager = (DataSourceTransactionManager)DefaultContainer.getSpringContext().getBean(paramString); 
      DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
      defaultTransactionDefinition.setPropagationBehavior(paramInt1);
      defaultTransactionDefinition.setIsolationLevel(paramInt2);
      this.transactionStatus = this.dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);
      if (logger.isDebugEnabled())
        logger.debug("Create transaction with[" + paramString + "], propagation behavior[" + paramInt1 + "], isolation level[" + paramInt2 + "]"); 
    } else {
      throw new IllegalStateException("Transaction init failure, spring context is invalid");
    } 
  }
  
  public void commit() {
    if (this.dataSourceTransactionManager != null && this.transactionStatus != null) {
      this.dataSourceTransactionManager.commit(this.transactionStatus);
      if (logger.isDebugEnabled())
        logger.debug("Commit transaction."); 
    } 
  }
  
  public void rollback() {
    if (this.dataSourceTransactionManager != null && this.transactionStatus != null) {
      this.dataSourceTransactionManager.rollback(this.transactionStatus);
      if (logger.isDebugEnabled())
        logger.debug("Rollback."); 
    } 
  }
  
  public Object createSavepoint() {
    Object object = null;
    if (this.transactionStatus != null) {
      object = this.transactionStatus.createSavepoint();
      if (logger.isDebugEnabled())
        logger.debug("Create savepoint: " + object.getClass().getName()); 
    } 
    return object;
  }
  
  public void rollbackToSavepoint(Object paramObject) {
    if (this.transactionStatus != null) {
      this.transactionStatus.rollbackToSavepoint(paramObject);
      if (logger.isDebugEnabled())
        logger.debug("Rollback to savepoint: " + paramObject.getClass().getName()); 
    } 
  }
  
  public void releaseSavepoint(Object paramObject) {
    if (paramObject != null) {
      this.transactionStatus.releaseSavepoint(paramObject);
      if (logger.isDebugEnabled())
        logger.debug("Release savepoint: " + paramObject.getClass().getName()); 
    } 
  }
  
  public void setRollbackOnly() {
    if (this.transactionStatus != null) {
      this.transactionStatus.setRollbackOnly();
      if (logger.isDebugEnabled())
        logger.debug("Set rollback only."); 
    } 
  }
  
  public void transaction(Runnable paramRunnable) {
    try {
      paramRunnable.run();
      commit();
      if (logger.isDebugEnabled())
        logger.debug("Transaction commit"); 
    } catch (Exception exception) {
      rollback();
      if (logger.isDebugEnabled())
        logger.debug("Transaction rollback"); 
    } 
  }
}
