package demo;

import android.app.Application;
import android.content.Intent;

import com.zhuiji7.filedownloader.download.DownLoadService;

public class DLApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        this.startService(new Intent(this, DownLoadService.class));
    }

}
