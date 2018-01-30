package net.shenru.qqgroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.orhanobut.logger.Logger;

import net.shenru.qqgroup.service.TaskManager;

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

}
