package com.asiainfo.operlog;

import com.alibaba.fastjson.JSONObject;
import com.asiainfo.entity.UserOperaterLogs;
import com.asiainfo.service.UserOperaterLogsService;
import com.asiainfo.service.UserService;
import com.asiainfo.utils.IpUtils;
import com.asiainfo.utils.operlog.OperStringUtils;
import com.asiainfo.utils.operlog.ServletUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 操作日志记录处理
 * 
 * @author ruoyi
 */
@Aspect
@Component
@EnableAsync
public class LogAspect
{
   static Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Autowired
    private UserOperaterLogsService userOperaterLogsService;
    @Autowired
    private UserService userService;

    // 配置织入点
    @Pointcut("@annotation(com.asiainfo.operlog.Log)")
    public void logPointCut() {
    }

    /**
     * 前置通知 用于拦截操作
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     * 
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfter(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e);
    }

    @Async
    protected void handleLog(final JoinPoint joinPoint, final Exception e){
        try{
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null){
                return;
            }
            // 获取当前的用户

            // *========数据库日志=========*//
            UserOperaterLogs operLog = new UserOperaterLogs();
            operLog.setStatus(Integer.parseInt(BusinessStatus.SUCCESS));
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            operLog.setLoginUserId("admin");
            if (e != null){
                operLog.setStatus(Integer.parseInt(BusinessStatus.FAIL));
                operLog.setErrorMsg(OperStringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setOperMethod(className + "." + methodName + "()");
            // 处理设置注解上的参数
            getControllerMethodDescription(controllerLog, operLog);
            // 保存数据库
            userOperaterLogsService.addUserOperaterLogs(operLog);
        }
        catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     * @param log
     * @param operLog
     * @throws Exception
     */
    public void getControllerMethodDescription(Log log, UserOperaterLogs operLog) throws Exception {
        // 设置action动作
        operLog.setOperAction(log.action());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置channel
        operLog.setChannel(log.channel());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     * @param operLog
     */
    private void setRequestValue(UserOperaterLogs operLog) {
        Map<String, String[]> map = ServletUtils.getRequest().getParameterMap();
        String params = JSONObject.toJSONString(map);
        operLog.setOperParam(OperStringUtils.substring(params, 0, 255));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }
}
