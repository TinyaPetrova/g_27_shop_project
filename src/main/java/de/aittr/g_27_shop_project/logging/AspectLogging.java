package de.aittr.g_27_shop_project.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {

  private Logger logger = LoggerFactory.getLogger(AspectLogging.class);

  @Pointcut("execution(* de.aittr.g_27_shop_project.services.jpa." +
      "JpaProductService.getAllActiveProducts(..))")
  public void getProducts() {}

  @Before("getProducts()")
  public void beforeGetProduct(JoinPoint joinPoint) {
    logger.info("Method getAllActiveProducts is launched");
  }

  @Pointcut("execution(* de.aittr.g_27_shop_project.services.jpa." +
      "JpaProductService.restoreById(..))")
  public void restore() {}

  @After("restore()")
  public void afterRestore(JoinPoint joinPoint) {
    // get args получает аргументы метода массивом обджектов
    Object[] args = joinPoint.getArgs();
    logger.info(String.format("Method restoreById() was launched with param %s"), args[0]);
  }

  @Pointcut("execution(* de.aittr.g_27_shop_project.services.jpa." +
      "JpaProductService.getActiveProductsById(..))")
  public void getProduct() {}

  @AfterReturning(
      pointcut = "getProduct()",
      returning = "result"
  )
  public void afterReturningProduct(Object result) {
    logger.info(String.format("Method getActiveProductsById successfully returned object %s", result));
  }

  @AfterThrowing(
      pointcut = "getProduct()",
      throwing = "e"
  )
  public void afterThrowing(Exception e) {
    logger.info(String.format("Method getActiveProductsyId threw an exception %s", e.getMessage()));
  }
}
