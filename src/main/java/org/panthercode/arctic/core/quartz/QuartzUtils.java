package org.panthercode.arctic.core.quartz;

import org.panthercode.arctic.core.arguments.ArgumentUtils;
import org.panthercode.arctic.core.helper.identity.Identity;
import org.quartz.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by architect on 01.03.17.
 */
public class QuartzUtils {

    /**
     *
     */
    private QuartzUtils() {
    }

    /**
     *
     * @param duration
     * @return
     */
    public static SimpleScheduleBuilder scheduleWithIntervalInMiliseconds(int duration) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");

        return SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMilliseconds(duration);
    }

    /**
     *
     * @param duration
     * @param count
     * @return
     */
    public static SimpleScheduleBuilder scheduleWithIntervalInMilliseconds(int duration, int count) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");
        ArgumentUtils.checkGreaterOrEqualsZero(count, "count");

        return SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(duration).withRepeatCount(count);
    }

    /**
     *
     * @param duration
     * @return
     */
    public static SimpleScheduleBuilder scheduleWithIntervalInSeconds(int duration) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");

        return SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(duration);
    }

    /**
     *
     * @param duration
     * @param count
     * @return
     */
    public static SimpleScheduleBuilder scheduleWithIntervalInSeconds(int duration, int count) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");
        ArgumentUtils.checkGreaterOrEqualsZero(count, "count");

        return SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(duration).withRepeatCount(count);
    }

    /**
     *
     * @param duration
     * @return
     */
    public static SimpleScheduleBuilder scheduleWithIntervalInMinutes(int duration) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");

        return SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMinutes(duration);
    }

    /**
     *
     * @param duration
     * @param count
     * @return
     */
    public static SimpleScheduleBuilder scheduleWithIntervalInMinutes(int duration, int count) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");
        ArgumentUtils.checkGreaterOrEqualsZero(count, "count");

        return SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(duration).withRepeatCount(count);
    }

    /**
     *
     * @param duration
     * @return
     */
    public static SimpleScheduleBuilder scheduleWithIntervalInHours(int duration) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");

        return SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInHours(duration);
    }

    /**
     *
     * @param duration
     * @param count
     * @return
     */
    public static SimpleScheduleBuilder scheduleWithIntervalInHours(int duration, int count) {
        ArgumentUtils.checkGreaterOrEqualsZero(duration, "duration");
        ArgumentUtils.checkGreaterOrEqualsZero(count, "count");

        return SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(duration).withRepeatCount(count);
    }

    /**
     *
     * @param identity
     * @return
     */
    public static TriggerBuilder triggerBuilder(Identity identity) {
        ArgumentUtils.checkNotNull(identity, "identity");

        return QuartzUtils.triggerBuilder(identity.getName(), identity.getGroup());
    }

    /**
     *
     * @param name
     * @param group
     * @return
     */
    public static TriggerBuilder triggerBuilder(String name, String group) {
        ArgumentUtils.checkNotNull(name, "name");

        return TriggerBuilder.newTrigger().withIdentity(name, group);
    }

    /**
     *
     * @param identity
     * @return
     */
    public static TriggerBuilder triggerBuilderStartNow(Identity identity) {
        return QuartzUtils.triggerBuilderStartNow(identity.getName(), identity.getGroup(), null);
    }

    /**
     *
     * @param identity
     * @param schedule
     * @return
     */
    public static TriggerBuilder triggerBuilderStartNow(Identity identity, ScheduleBuilder<? extends Trigger> schedule) {
        ArgumentUtils.checkNotNull(identity, "identity");

        return QuartzUtils.triggerBuilderStartNow(identity.getName(), identity.getGroup(), schedule);
    }

    /**
     *
     * @param name
     * @param group
     * @return
     */
    public static TriggerBuilder triggerBuilderStartNow(String name, String group) {
        return QuartzUtils.triggerBuilderStartNow(name, group, null);
    }

    /**
     *
     * @param name
     * @param group
     * @param schedule
     * @return
     */
    public static TriggerBuilder triggerBuilderStartNow(String name, String group, ScheduleBuilder<? extends Trigger> schedule) {
        ArgumentUtils.checkNotNull(name, "name");

        TriggerBuilder triggerBuilder = QuartzUtils.triggerBuilder(name, group).startNow();

        if (schedule != null) {
            triggerBuilder.withSchedule(schedule);
        }

        return triggerBuilder;
    }

    /**
     *
     * @param identity
     * @param startAt
     * @return
     */
    public static TriggerBuilder triggerBuilderStartAt(Identity identity, LocalDateTime startAt) {
        return QuartzUtils.triggerBuilderStartAt(identity.getName(), identity.getGroup(), startAt, null);
    }

    /**
     *
     * @param identity
     * @param startAt
     * @param schedule
     * @return
     */
    public static TriggerBuilder triggerBuilderStartAt(Identity identity, LocalDateTime startAt, ScheduleBuilder<? extends Trigger> schedule) {
        ArgumentUtils.checkNotNull(identity, "identity");

        return QuartzUtils.triggerBuilderStartAt(identity.getName(), identity.getGroup(), startAt, schedule);
    }

    /**
     *
     * @param name
     * @param group
     * @param startAt
     * @return
     */
    public static TriggerBuilder triggerBuilderStartAt(String name, String group, LocalDateTime startAt) {
        return QuartzUtils.triggerBuilderStartAt(name, group, startAt, null);
    }

    /**
     *
     * @param name
     * @param group
     * @param startAt
     * @param schedule
     * @return
     */
    public static TriggerBuilder triggerBuilderStartAt(String name, String group, LocalDateTime startAt, ScheduleBuilder<? extends Trigger> schedule) {
        ArgumentUtils.checkNotNull(name, "name");
        ArgumentUtils.checkNotNull(startAt, "start at");

        TriggerBuilder triggerBuilder = QuartzUtils.triggerBuilder(name, group)
                .startAt(Date.from(startAt.atZone(ZoneId.systemDefault()).toInstant()));

        if (schedule != null) {
            triggerBuilder.withSchedule(schedule);
        }

        return triggerBuilder;
    }

    /**
     *
     * @param identity
     * @param startAt
     * @param endAt
     * @return
     */
    public static TriggerBuilder triggerBuilderTimeSpan(Identity identity, LocalDateTime startAt, LocalDateTime endAt) {
        return QuartzUtils.triggerBuilderTimeSpan(identity, startAt, endAt, null);
    }

    /**
     *
     * @param identity
     * @param startAt
     * @param endAt
     * @param schedule
     * @return
     */
    public static TriggerBuilder triggerBuilderTimeSpan(Identity identity, LocalDateTime startAt, LocalDateTime endAt, ScheduleBuilder<? extends Trigger> schedule) {
        ArgumentUtils.checkNotNull(identity, "identity");

        return QuartzUtils.triggerBuilderTimeSpan(identity.getName(), identity.getGroup(), startAt, endAt, schedule);
    }

    /**
     *
     * @param name
     * @param group
     * @param startAt
     * @param endAt
     * @return
     */
    public static TriggerBuilder triggerBuilderTimeSpan(String name, String group, LocalDateTime startAt, LocalDateTime endAt) {
        return QuartzUtils.triggerBuilderTimeSpan(name, group, startAt, endAt, null);
    }

    /**
     *
     * @param name
     * @param group
     * @param startAt
     * @param endAt
     * @param schedule
     * @return
     */
    public static TriggerBuilder triggerBuilderTimeSpan(String name, String group, LocalDateTime startAt, LocalDateTime endAt, ScheduleBuilder<? extends Trigger> schedule) {
        return QuartzUtils.triggerBuilderStartAt(name, group, startAt, schedule)
                .endAt(Date.from(endAt.atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     *
     * @param clazz
     * @param identity
     * @return
     */
    public static JobBuilder jobBuilder(Class<? extends Job> clazz, Identity identity) {
        ArgumentUtils.checkNotNull(identity, "identity");

        return QuartzUtils.jobBuilder(clazz, identity.getName(), identity.getGroup());
    }

    /**
     *
     * @param clazz
     * @param name
     * @param group
     * @return
     */
    public static JobBuilder jobBuilder(Class<? extends Job> clazz, String name, String group) {
        ArgumentUtils.checkNotNull(clazz, "class");
        ArgumentUtils.checkNotNull(name, "name");

        return JobBuilder.newJob(clazz).withIdentity(name, group);
    }
}
