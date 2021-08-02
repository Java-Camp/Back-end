package com.jcf.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Slf4j
@Aspect
public class CheckAspect {
    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private TransactionStatus status = null;

    @Pointcut("execution(* com.jcf.service.*.*(..))") // expression
    private void businessService() {}  // signature

    @Before("businessService()")
    public void doBeforeTask(){
        log.info("Transaction was started");
        DefaultTransactionDefinition paramTransactionDefinition = new DefaultTransactionDefinition();
        status = platformTransactionManager.getTransaction(paramTransactionDefinition);
    }

    @After("businessService()")
    public void doAfterTask(){
        status = null;
    }

    @AfterReturning("businessService()")
    public void doAfterReturnningTask(){
        log.info("Transaction was done successful.");
        platformTransactionManager.commit(status);
    }

    @AfterThrowing(value = "businessService()", throwing = "ex")
    public void doAfterThrowingTask(Exception ex){
        log.info("Transaction was canceled. Exception was thrown:\n" + ex);
        platformTransactionManager.rollback(status);
    }
}
// todo threadPool
