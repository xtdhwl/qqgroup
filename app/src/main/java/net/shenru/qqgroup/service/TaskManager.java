package net.shenru.qqgroup.service;

import net.shenru.qqgroup.QQConstant;
import net.shenru.qqgroup.task.Task;

/**
 * Created by xtdhwl on 28/01/2018.
 */

public class TaskManager {


    private static TaskManager mInstance = new TaskManager();

    private Task mTask;

    public static TaskManager getInstance() {
        return mInstance;
    }

    private TaskManager() {
        mTask = createTask();
    }

    private Task createTask() {
        Task task1 = new Task();
        task1.setEvent(QQConstant.ACTIVITY_SPLASH);

        Task task2 = new Task();
        task2.setEvent(QQConstant.ACTIVITY_TROOP);

        Task task3 = new Task();
        task3.setEvent(QQConstant.ACTIVITY_CHAT);

        task1.setNextTask(task2);
        task2.setNextTask(task3);

        return task1;
    }

    public synchronized Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        mTask = task;
    }

    public synchronized boolean nextTask() {
        Task nextTask = mTask.getNextTask();
        if (nextTask != null) {
            setTask(nextTask);
            return false;
        }
        return false;
    }

    public synchronized void initTask() {
        setTask(createTask());
    }
}
