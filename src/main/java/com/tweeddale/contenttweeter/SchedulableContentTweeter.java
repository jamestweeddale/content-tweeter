package com.tweeddale.contenttweeter;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by James on 7/17/2015.
 */
public class SchedulableContentTweeter extends ContentTweeter implements Job {

    private Scheduler scheduler;
    private JobDetail job;
    private Trigger trigger;

    public SchedulableContentTweeter(String triggerName, String jobName, String groupName, int interval) {
        super();

        try {
            scheduler = new StdSchedulerFactory().getScheduler();

            job = JobBuilder.newJob(SchedulableContentTweeter.class)
                    .withIdentity(jobName, groupName).build();

            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity(triggerName, groupName)
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(interval).repeatForever())
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Failed to setup quartz scheduler. Program terminating");
        }
    }

    public void start() {

        try {
            scheduler.start();
            scheduler.scheduleJob(job, trigger);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Failed to start quartz scheduler. Program terminating");
        }

    }

    /**
     * Required by Quartz Job interface. Executed when quartz job fires on objects of this class
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        tweet();
    }
}
