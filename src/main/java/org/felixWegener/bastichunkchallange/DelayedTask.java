package org.felixWegener.bastichunkchallange;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.LinkedList;
import java.util.Queue;

public class DelayedTask {
    private static final Queue<DelayedTaskEntry> taskQueue = new LinkedList<>();

    public static void scheduleDelayedTask(int delayTicks, Runnable task) {
        taskQueue.add(new DelayedTaskEntry(delayTicks, task));
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (!taskQueue.isEmpty()) {
                DelayedTaskEntry currentTask = taskQueue.peek();
                currentTask.delayTicks--;

                if (currentTask.delayTicks <= 0) {
                    currentTask.task.run();
                    taskQueue.poll();
                }
            }
        });
    }

    private static class DelayedTaskEntry {
        int delayTicks;
        Runnable task;

        DelayedTaskEntry(int delayTicks, Runnable task) {
            this.delayTicks = delayTicks;
            this.task = task;
        }
    }
}
