package com.db.extrato.scheduler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.ArrayUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class DerivativesQuartzScheduler {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private QuartzProperties quartzProperties;

    private DataSource dataSource;
    
    @Value("${com.db.extrato.scheduler.morning_cron}")
    private String morningCron;
    
    @Value("${com.db.extrato.scheduler.afternoon_cron}")
    private String afternoonCron;
    
    @Value("${com.db.extrato.scheduler.evening_cron}")
    private String eveningCron;

	@Autowired
	public void SchedulerConfig(@Qualifier("springDataSource") DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Bean
	public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer() {
		return bean -> bean.setDataSource(dataSource);
	}

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Trigger... triggers) {
        SchedulerJobFactory jobFactory = new SchedulerJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setAutoStartup(true);
        factory.setWaitForJobsToCompleteOnShutdown(true);
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setQuartzProperties(properties);
        factory.setJobFactory(jobFactory);
        if(ArrayUtils.isNotEmpty(triggers)) {
        	factory.setTriggers(triggers);
        }
        return factory;
    }
    
    @Bean
    public CronTriggerFactoryBean morningDerivativesReportsTrigger(JobDetail morningDerivativesReportsJobDetail) {
        Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName("MorningDerivativesReportsTrigger");
        factoryBean.setCronExpression(morningCron);
        factoryBean.setStartTime(calendar.getTime());
        factoryBean.setStartDelay(0L);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        factoryBean.setJobDetail(morningDerivativesReportsJobDetail);
        return factoryBean;
    }
    
    @Bean
    public JobDetailFactoryBean morningDerivativesReportsJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(DerivativesQuartzJob.class);
        jobDetailFactory.setName("MorningDerivativesReportsJobDetail");
        jobDetailFactory.setDurability(true);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("periodo", "MANHA");
        jobDetailFactory.setJobDataAsMap(dataMap);
        return jobDetailFactory;
    }
    
    @Bean
    public CronTriggerFactoryBean afternoonDerivativesReportsTrigger(JobDetail afternoonDerivativesReportsJobDetail) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName("AfternoonDerivativesReportsTrigger");
        factoryBean.setCronExpression(afternoonCron);
        factoryBean.setStartTime(calendar.getTime());
        factoryBean.setStartDelay(0L);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        factoryBean.setJobDetail(afternoonDerivativesReportsJobDetail);
        return factoryBean;
    }
    
    @Bean
    public JobDetailFactoryBean afternoonDerivativesReportsJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(DerivativesQuartzJob.class);
        jobDetailFactory.setName("AfternoonDerivativesReportsJobDetail");
        jobDetailFactory.setDurability(true);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("periodo", "TARDE");
        jobDetailFactory.setJobDataAsMap(dataMap);
        return jobDetailFactory;
    }
    
    @Bean
    public CronTriggerFactoryBean eveningNightDerivativesReportsTrigger(JobDetail eveningDerivativesReportsJobDetail) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName("EveningNightDerivativesReportsTrigger");
        factoryBean.setCronExpression(eveningCron);
        factoryBean.setStartTime(calendar.getTime());
        factoryBean.setStartDelay(0L);
        factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        factoryBean.setJobDetail(eveningDerivativesReportsJobDetail);
        return factoryBean;
    }
    
    @Bean
    public JobDetailFactoryBean eveningDerivativesReportsJobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(DerivativesQuartzJob.class);
        jobDetailFactory.setName("EveningDerivativesReportsJobDetail");
        jobDetailFactory.setDurability(true);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("periodo", "NOITE");
        jobDetailFactory.setJobDataAsMap(dataMap);
        return jobDetailFactory;
    }
    
}
