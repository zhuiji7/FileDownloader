package demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.zhuiji7.filedownloader.R;
import com.zhuiji7.filedownloader.download.DownLoadManager;
import com.zhuiji7.filedownloader.download.DownLoadService;
import com.zhuiji7.filedownloader.download.TaskInfo;

import demo.adapter.ListAdapter;


public class MainActivity extends Activity {
    private Button addbutton;
    private Button userbutton;
    private ListView listview;
    private EditText nameText;
    private EditText urlText;
    private ListAdapter adapter;

    /*使用DownLoadManager时只能通过DownLoadService.getDownLoadManager()的方式来获取下载管理器，不能通过new DownLoadManager()的方式创建下载管理器*/
    private DownLoadManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        //下载管理器需要启动一个Service,在刚启动应用的时候需要等Service启动起来后才能获取下载管理器，所以稍微延时获取下载管理器
        handler.sendEmptyMessageDelayed(1, 50);

        addbutton = (Button)this.findViewById(R.id.button);
        userbutton = (Button) this.findViewById(R.id.userbutton);
        listview = (ListView)this.findViewById(R.id.listView);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View showview = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null);
                nameText = (EditText) showview.findViewById(R.id.file_name);
                urlText = (EditText) showview.findViewById(R.id.file_url);
                new AlertDialog.Builder(MainActivity.this).setView(showview).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ("".equals(nameText.getText().toString()) || "".equals(urlText.getText().toString())) {
                            Toast.makeText(MainActivity.this, "请输入文件名和下载路径", Toast.LENGTH_SHORT).show();
                        } else {
                            TaskInfo info = new TaskInfo();
                            info.setFileName(nameText.getText().toString());
                            /*服务器一般会有个区分不同文件的唯一ID，用以处理文件重名的情况*/
                            info.setTaskID(nameText.getText().toString());
                            info.setOnDownloading(true);
                            /*将任务添加到下载队列，下载器会自动开始下载*/
                            manager.addTask(nameText.getText().toString(), urlText.getText().toString(), nameText.getText().toString());
                            adapter.addItem(info);
                        }
                    }
                }).setNegativeButton("取消", null).show();

            }
        });

        userbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle("切换用户")
                        .setPositiveButton("zhuiji7", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                manager.changeUser("zhuiji7");
                                userbutton.setText("用户: zhuiji7");
                                adapter.setListdata(manager.getAllTask());

                            }
                        }).setNegativeButton("luffy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            manager.changeUser("luffy");
                            userbutton.setText("用户 : luffy");
                            adapter.setListdata(manager.getAllTask());
                        }
                    }).show();
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*获取下载管理器*/
            manager = DownLoadService.getDownLoadManager();
            /*设置用户ID，客户端切换用户时可以显示相应用户的下载任务*/
            manager.changeUser("luffy");
            /*断点续传需要服务器的支持，设置该项时要先确保服务器支持断点续传功能*/
            manager.setSupportBreakpoint(true);
            adapter = new ListAdapter(MainActivity.this,manager);
            listview.setAdapter(adapter);
            userbutton.setText("用户 : " + manager.getUserID());
        }
    };

}
