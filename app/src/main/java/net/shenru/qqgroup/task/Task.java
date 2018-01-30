package net.shenru.qqgroup.task;

/**
 * Created by xtdhwl on 28/01/2018.
 */

public class Task {


    private Task preTask;
    private Task nextTask;


    private String event;

    public Task getPreTask() {
        return preTask;
    }

    public void setPreTask(Task preTask) {
        this.preTask = preTask;
    }

    public Task getNextTask() {
        return nextTask;
    }

    public void setNextTask(Task nextTask) {
        this.nextTask = nextTask;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }


    @Override
    public String toString() {
        return "Task{" +
                "preTask=" + preTask +
                ", nextTask=" + nextTask +
                ", event='" + event + '\'' +
                '}';
    }
}
