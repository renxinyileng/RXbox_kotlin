package rx.team.renxinyileng.rxbox
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class RXboxService : Service() {
    private val TAG = "RXboxService"
    // 当服务被创建时调用
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Service created")
    }
    // 当服务被启动时调用
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "Service started")
        // 在这里执行您希望长时间运行的任务
        // 例如，您可以在这里启动一个线程来执行工作
        // 如果服务被杀死，系统会重新启动服务，但是除非有挂起的意图，
        // 否则不会重新传递最后的意图。这适用于不执行命令的媒体播放器（或类似服务）。

        return START_NOT_STICKY
    }
    // 当服务被绑定时调用
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    // 当服务被销毁时调用
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Service destroyed")
    }
}