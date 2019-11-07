package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import redis.clients.jedis.Jedis;
@Configuration
@Aspect
public class RedisCacheAopHash {
    @Autowired
    private Jedis jedis;
    @Around("execution(* com.baizhi.service.*.selectAll(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取类的对象
        Object target = proceedingJoinPoint.getTarget();
        //获取方法
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
      //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(RedisCache.class);
        if(b){
//            目标方法上存在该注解
            String className = target.getClass().getName();
            StringBuilder sb = new StringBuilder();
            String methodName = method.getName();
            sb.append(methodName).append("(");
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if(i == args.length-1){
                    break;
                }
                sb.append(",");
            }
            sb.append(")");
            if(jedis.hexists(className,sb.toString())){
//                判断redis中是否存在对应的key
                String result = jedis.hget(className, sb.toString());
                return JSONObject.parse(result);
            }else{
                Object result = proceedingJoinPoint.proceed();
                jedis.hset(className,sb.toString(),JSONObject.toJSONString(result));
                return result;
            }
        }else{
//            不存在该注解
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }
    @After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.selectAll(..))")
    public void after(JoinPoint joinPoint){
        //获取类的对象
        Object target = joinPoint.getTarget();
        //获取方法
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取类的名称
        String className = target.getClass().getName();
        //判断此方法上是否存在存在某种注解
        if(method.isAnnotationPresent(ClearRedisCache.class)){
            //如果存在则删除
            jedis.del(className);
        }
    }
}
