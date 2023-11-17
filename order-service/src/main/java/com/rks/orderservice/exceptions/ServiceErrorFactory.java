package com.rks.orderservice.exceptions;

import com.rks.mcommon.exceptions.BaseException;
import com.rks.mcommon.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ServiceErrorFactory implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(ServiceErrorFactory.class);
    private static ServiceErrorRepository serviceErrorRepository;
    private static Map<String, ServiceError> SERVICE_ERROR_MAP = new HashMap<>();

    private ApplicationContext applicationContext;

    @Autowired
    public ServiceErrorFactory(ServiceErrorRepository serviceErrorRepository, ApplicationContext applicationContext) {
        this.serviceErrorRepository = serviceErrorRepository;
        this.applicationContext = applicationContext;
    }

    public static BaseException getNamedException(String errorCode) {
        ServiceError se = SERVICE_ERROR_MAP.get(errorCode);
        if (se==null) {
            logger.info("in ServiceErrorFactory:getNamedException:: se==null, returning BaseException");
            return new BaseException("failed", 503, "Internal server error");
        }
        BaseException be;
        try {
            Class exceptionClass = Class.forName(se.getException_name());
            logger.info("in ServiceErrorFactory:getNamedException::exceptionClass=" + exceptionClass + " | returning");
            /*be = (BaseException) exceptionClass.getConstructor(String.class, int.class, String.class)
                    .newInstance(se.getStatus(), se.getResponse_code(),
                            se.getError_message());*/
            be = (BaseException) exceptionClass.getConstructor(String.class, int.class, String.class)
                    .newInstance(se.getStatus(), Integer.valueOf(se.getResponse_code()), se.getError_message());
            return be;
        } catch (ClassNotFoundException cnfe) {
            logger.error("[ServiceErrorFactory] No class found for exceptions name {}", CommonUtils.exceptionFormatter(cnfe));
        } catch (NoSuchMethodException ne) {
            logger.error("[ServiceErrorFactory] Check if exceptions constructor is defined properly {}", CommonUtils.exceptionFormatter(ne));
        }catch (IllegalAccessException ie) {
            logger.error("[ServiceErrorFactory] Check if constructor can be accessed {}",CommonUtils.exceptionFormatter(ie));
        } catch (InstantiationException ie) {
            logger.error("[ServiceErrorFactory] Exception cannot be initialized {}",CommonUtils.exceptionFormatter(ie));
        } catch (InvocationTargetException ie) {
            logger.error("[ServiceErrorFactory] Error while initializing exceptions {}",CommonUtils.exceptionFormatter(ie));
        } catch (Exception e) {
            logger.error("[ServiceErrorFactory] Error while initializing exceptions {}", CommonUtils.exceptionFormatter(e));
        }
        return new BaseException("failed", 503, "Internal server error");
    }

    @PostConstruct
    public void init() {
        List<ServiceError> serviceErrors = serviceErrorRepository.findAll();
        SERVICE_ERROR_MAP = serviceErrors.stream().collect(Collectors.toMap(ServiceError::getError_code, Function.identity()));
        logger.info("loading SERVICE_ERROR_MAP: " + SERVICE_ERROR_MAP);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
