package rx.team.renxinyileng.rxbox

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import okhttp3.*
import okio.IOException
import java.io.FileInputStream
import java.util.Properties


private data class Data(val status: Int, val message: String="null", val qq: String="null", val phone: String="null", val phonediqu: String="null")

class MainActivity : AppCompatActivity() {
    private var text: TextView? = null
    private var button: Button? = null
    private var editText: EditText? = null
    private val client = OkHttpClient()
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 创建启动服务的意图
        val startServiceIntent = Intent(this, RXboxService::class.java)
        // 启动服务
        startService(startServiceIntent)
        text = findViewById(R.id.text)
        button = findViewById(R.id.mButton)
        editText = findViewById(R.id.editText)
        button!!.setOnClickListener {
            val textValue = editText!!.text.toString()
            if (textValue != null && textValue != "") {
                if (isChineseMobileNumber(textValue)) {
                    val querycontent: String = "qqphone?phone="
                    val querycontenttitle: String = "手机号"
                    okhttp_url_inquire_Social_accounts_bound(textValue, querycontent, querycontenttitle)
                } else if (isQQNumber(textValue)) {
                    val querycontent: String = "qqapi?qq="
                    val querycontenttitle: String = "QQ"
                    okhttp_url_inquire_Social_accounts_bound(textValue, querycontent, querycontenttitle)
                } else {
                    Toast.makeText(this, "输入的内容不符合规范", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isChineseMobileNumber(number: String): Boolean {
        val pattern = Regex("1[3-9]\\d{9}")
        return pattern.matches(number)
    }

    private fun isQQNumber(qq: String): Boolean {
        val pattern = Regex("\\d{5,12}")
        return pattern.matches(qq)
    }

    private fun okhttp_url_inquire_Social_accounts_bound(textValue: String, Query_content: String, Query_content_title: String) {
        val url_qq = BuildConfig.URL_QQ
        val url = url_qq + Query_content + textValue
        text = findViewById(R.id.text)
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理失败情况
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "无法访问网站", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                // 处理响应
                val responseBody = response.body?.string()
                if (responseBody != null) {

                    val gson = Gson()
                    val person = try {
                        gson.fromJson(responseBody, Data::class.java)
                    } catch (e: Exception) {
                        // 处理解析异常
                        println("解析响应体时出错: ${e.message}")
                        return
                    }
                    mainScope.launch {
                        // 这里更新 UI
                        text?.text = "你查询的${Query_content_title}号:${person.qq}\n查询状态:${person.message}\n手机号:${person.phone}\n手机号归属地:${person.phonediqu}"
                    }
                } else {
                    println("响应成功但内容获取为空")
                }
            }
        })
    }
    fun getLocalProperty(key: String?): String? {
        val properties = Properties()
        try {
            FileInputStream("local.properties").use { fis ->
                properties.load(fis)
                return properties.getProperty(key)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}