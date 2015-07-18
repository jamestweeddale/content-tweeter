package com.tweeddale.contenttweeter;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by James on 7/17/2015.
 */
public class ContentTweeterScheduler {

    private ContentTweeter contentTweeter;
    private Scheduler scheduler;
    private JobDetail job;
    private Trigger trigger;

    public ContentTweeterScheduler(ContentTweeter contentTweeter, String triggerName, String jobName, String groupName, int interval) {

        try {
            this.contentTweeter = contentTweeter;

            this.scheduler = new StdSchedulerFactory().getScheduler();

            this.job = JobBuilder.newJob(ContentTweeter.class)
                    .withIdentity(jobName, groupName).build();

            this.trigger = TriggerBuilder
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

}
