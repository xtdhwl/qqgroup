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
        Task main = new Task();
        main.setEvent(QQConstant.ACTIVITY_SPLASH);

        Task group = new Task();
        main.setEvent(QQConstant.ACTIVITY_TROOP);


        main.setNextTask(group);

        return main;
    }

    public Task getTask() {
        return mTask;
    }

    public void setTask(Task task) {
        mTask = task;
    }
}
