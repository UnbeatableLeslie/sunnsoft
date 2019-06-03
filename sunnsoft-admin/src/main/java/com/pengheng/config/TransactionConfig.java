package com.pengheng.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 通过aop切面配置全局事物
 * @author 彭恒
 *
 */
//@Aspect
@Configuration
public class TransactionConfig {
     private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.pengheng.controller.**.*(..))";
 
        @Autowired
        private PlatformTransactionManager transactionManager;
 
        @Bean
        public TransactionInterceptor txAdvice() {
 
            DefaultTransactionAttribute required_attribute = new DefaultTransactionAttribute();
            required_attribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            required_attribute.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
            
 
            DefaultTransactionAttribute required_readonly_attribute = new DefaultTransactionAttribute();
            required_readonly_attribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            required_readonly_attribute.setReadOnly(true);
            required_readonly_attribute.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
            
            NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
            source.addTransactionalMethod("add*", required_attribute);
            source.addTransactionalMethod("save*", required_attribute);
            source.addTransactionalMethod("delete*", required_attribute);
            source.addTransactionalMethod("update*", required_attribute);
            source.addTransactionalMethod("exec*", required_attribute);
            source.addTransactionalMethod("set*", required_attribute);
            source.addTransactionalMethod("get*", required_readonly_attribute);
            source.addTransactionalMethod("query*", required_readonly_attribute);
            source.addTransactionalMethod("find*", required_readonly_attribute);
            source.addTransactionalMethod("list*", required_readonly_attribute);
            source.addTransactionalMethod("count*", required_readonly_attribute);
            source.addTransactionalMethod("is*", required_readonly_attribute);
            return new TransactionInterceptor(transactionManager, source);
        }
 
        @Bean
        public Advisor txAdviceAdvisor() {
            AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
            pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
            return new DefaultPointcutAdvisor(pointcut, txAdvice());
        }
}
