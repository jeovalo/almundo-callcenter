package com.jeovalo.almundo.callcenter;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAsync
@SpringBootApplication
@Configuration
@EnableAutoConfiguration // Configuracion automatica de Sprint Boot
@PropertySource(value = { "classpath:application.properties"})
@EnableSwagger2
@ComponentScan(basePackages = { "com.jeovalo.almundo.callcenter" })
public class CallCenterApplication extends SpringBootServletInitializer implements AsyncConfigurer { 
  private static final Class<CallCenterApplication> applicationClass = CallCenterApplication.class;
  
  private static final Logger LOG = LoggerFactory.getLogger(applicationClass);

    // Maximo numero de hilos en el Pool que atenderan la Cola con Prioridades
    @Value("${callcenter.numThreads}")
    private Integer numeroMaximoHilos;
    
    public static void main(String[] args) throws Exception {
        new SpringApplication(CallCenterApplication.class).run(args);
    }



    /**
     * Configure SpringApplicationBuilder
     */
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
     return application.sources(applicationClass);
    }
    
    /**
     * 
     * @return ThreadPoolTaskExecutor context Bean
     */
    @Bean
       public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
           ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
           threadPoolTaskExecutor.setCorePoolSize(numeroMaximoHilos);;
           threadPoolTaskExecutor.setMaxPoolSize(numeroMaximoHilos);
           threadPoolTaskExecutor.initialize();
           return threadPoolTaskExecutor;
       }
    
    /**
     * @return un Executor por defecto en el contexto de Spring
     */
    @Bean("callcenterExecutor")
    @Override
    public Executor getAsyncExecutor() {
     return new ConcurrentTaskExecutor(threadPoolTaskExecutor());
    }

    /**
     * 
     * @return ExceptionHandler for AsyncConfigurer
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
     return (throwable, method, objects) -> LOG.error("-- exception handler -- " + throwable);
    }
        
    
    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
