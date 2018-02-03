package net.shenru.qqgroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.settings.Settings;
import com.orhanobut.logger.Logger;

import net.shenru.qqgroup.service.TaskManager;
import net.shenru.qqgroup.task.Task;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //moveTaskToBack(true);
    }

    public void startClick(View view) {
        Log.i("MainActivity:%s", "hello");
        Logger.i("MainActivity:%s", "hello");
        TaskManager.getInstance().initTask();

    }

    public void accessibilityClick(View view) {
        TaskManager.getInstance().setTask(createAccessibilityTask());
        Intent it = new Intent();
        it.setAction("android.settings.ACCESSIBILITY_SETTINGS");
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(it);
    }


    private Task createAccessibilityTask() {
        Task task1 = new Task();
        task1.setEvent(SettingsConstant.ACTIVITY_SETTINGS);

        Task task2 = new Task();
        task2.setEvent(SettingsConstant.ACTIVITY_TOGGLE);

        Task task3 = new Task();
        task3.setEvent(QQConstant.ACTION_SELECT);

        task1.setNextTask(task2);
        task2.setNextTask(task3);

        return task1;
    }


}
