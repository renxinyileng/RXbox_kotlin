package rx.team.renxinyileng.rxbox

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import okhttp3.*
import okio.IOException

class MainActivity : AppCompatActivity() {
    private var text: TextView? = null
    private var button: Button? = null
    private var editText: EditText? =null
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.text)
        button = findViewById(R.id.mButton)
        editText =findViewById(R.id.editText)
        button!!.setOnClickListener {
            val request = Request.Builder()
                .url("https://api.example.com/data")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // 处理失败情况
                    println("Request failed: ${e.message}")
                }

                override fun onResponse(call: Call, response: Response) {
                    // 处理响应
                    val responseBody = response.body?.string()
                    println("Response received: $responseBody")
                    val textValue = editText!!.text.toString()
                    if (isChineseMobileNumber(textValue)){
                        text!!.text = "你查询的手机号"
                    }else if (isQQNumber(textValue)){
                        text!!.text = "你查询的QQ号"
                    }
                }
            })
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
}