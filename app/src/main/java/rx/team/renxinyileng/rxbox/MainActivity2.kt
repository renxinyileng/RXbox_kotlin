package rx.team.renxinyileng.rxbox

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aliyun.player.AliPlayerFactory
import com.aliyun.player.IPlayer
import com.aliyun.player.IPlayer.OnLoadingStatusListener
import com.aliyun.player.bean.ErrorCode
import com.aliyun.player.source.UrlSource

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main2)
        val aliPlayer = AliPlayerFactory.createAliPlayer(getApplicationContext())
        aliPlayer.setOnErrorListener { errorInfo ->

            //此回调会在使用播放器的过程中，出现了任何错误，都会回调此接口。
            val errorCode: ErrorCode = errorInfo.code //错误码。
            val errorMsg = errorInfo.msg //错误描述。
            //errorExtra为额外错误信息，形式为json字符串，示例如下，需要注意ModuleCode并不完全等同于errorCode
            //{ "Url": "xxx",
            //	"Module": "NetWork",
            //	"ModuleCode": "-377",
            //  "ModuleMessage": "Redirect to a url that is not a media"}
            val errorExtra = errorInfo.extra
            //出错后需要停止掉播放器。
            aliPlayer.stop()
        }
        aliPlayer.setOnPreparedListener { //一般调用start开始播放视频。
            aliPlayer.start()
        }
        aliPlayer.setOnCompletionListener { //一般调用stop停止播放视频。
            aliPlayer.stop()
        }
        aliPlayer.setOnInfoListener { infoBean ->

            //播放器中的一些信息，包括：当前进度、缓存位置等等。
            val code = infoBean.code //信息码。
            val msg = infoBean.extraMsg //信息内容。
            val value = infoBean.extraValue //信息值。

            //当前进度：InfoCode.CurrentPosition
            //当前缓存位置：InfoCode.BufferedPosition
        }
        aliPlayer.setOnLoadingStatusListener(object : OnLoadingStatusListener {
            //播放器的加载状态, 网络不佳时，用于展示加载画面。
            override fun onLoadingBegin() {
                //开始加载。画面和声音不足以播放。
                //一般在此处显示圆形加载。
            }

            override fun onLoadingProgress(percent: Int, netSpeed: Float) {
                //加载进度。百分比和网速。
            }

            override fun onLoadingEnd() {
                //结束加载。画面和声音可以播放。
                //一般在此处隐藏圆形加载。
                Toast.makeText(this@MainActivity2, "播放完成", Toast.LENGTH_SHORT).show()
            }
        })
        aliPlayer.setAutoPlay(true);
        val urlSource = UrlSource()
        urlSource.uri = "播放地址" // 播放地址，可以是第三方直播地址，或阿里云直播服务中的拉流地址。
        aliPlayer.setDataSource(urlSource)
        aliPlayer.prepare()
        aliPlayer.start()// 开始之后可以调用pause()暂停播放视频。
        // 设置宽高比适应（将按照视频宽高比等比缩小到view内部，不会有画面变形）
        aliPlayer.setScaleMode(IPlayer.ScaleMode.SCALE_ASPECT_FIT)
        aliPlayer.getRotateMode()
    }
}