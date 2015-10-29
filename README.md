# FileDownloader
Android下载框架,支持多用户、多任务、断点续传

使用只需要几行配置代码

1、初始化，在Application的 onCreate() 函数里启动DownLoadService <br>
    this.startService(new Intent(this, DownLoadService.class));
    
2、在用户登录后设置用户信息以及服务器是否支持断点续传<br>
   /*获取下载管理器，DownLoadManager 对象只能通过DownLoadService.getDownLoadManager()获取，不能通过new的方式 */<br>
   DownLoadManager manager = DownLoadService.getDownLoadManager();<br>
   /*设置用户ID，客户端切换用户时可以显示相应用户的下载任务*/<br>
   manager.changeUser("luffy");<br>
   /*断点续传需要服务器的支持，设置该项时要先确保服务器支持断点续传功能*/ <br> 
   manager.setSupportBreakpoint(true);<br>
   
3、在需要下载的地方添加下载任务<br>
  manager.addTask(“id_001”, "http://sqdd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk", "qq.apk");<br>
   /*监听下载任务*/<br>
  manager.setSingleTaskListener("id_001", new DownLoadListener(){<br>
      @Override<br>
      public void onStart(SQLDownLoadInfo sqlDownLoadInfo) {<br>
        //开始下载<br>
     } <br>
      @Override<br>
     public void onProgress(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {<br>
        //更新进度<br>
     } <br>
      @Override<br>
     public void onStop(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {<br>
         //停止下载<br>
     } <br>
     @Override<br>
     public void onError(SQLDownLoadInfo sqlDownLoadInfo) {<br>
         //下载出错<br>
     } <br>
     @Override<br>
     public void onSuccess(SQLDownLoadInfo sqlDownLoadInfo) {<br>
         //下载成功<br>
     } <br>
  }); <br>

  
