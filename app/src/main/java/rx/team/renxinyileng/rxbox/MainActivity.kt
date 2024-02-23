package rx.team.renxinyileng.rxbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.serialization.Serializable
import okhttp3.*
import okio.IOException
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Serializable
private data class Data(val status: Int, val message: String,val qq: String,val phone: String,val phonediqu: String)

class MainActivity : AppCompatActivity() {
    private var text: TextView? = null
    private var button: Button? = null
    private var editText: EditText? = null
    private val client = OkHttpClient()
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        text = findViewById(R.id.text)
        button = findViewById(R.id.mButton)
        editText = findViewById(R.id.editText)
        button!!.setOnClickListener {
            val textValue = editText!!.text.toString()
            if (textValue!=null && textValue!="") {
                if (isChineseMobileNumber(textValue)) {
                    val request = Request.Builder()
                        .url("https://zy.xywlapi.cc/qqphone?phone=$textValue")
                        .build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            // 处理失败情况
                            Toast.makeText(this@MainActivity, "无法访问网站", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            // 处理响应
                            val responseBody = response.body?.string()
                            if (responseBody != null) {
//                                val person = Json.decodeFromString<Data>(responseBody)
//                                println(person)
//                                text!!.text = "你查询的QQ号:$person.qq\n查询状态:$person.message\n手机号:$person.phone\n手机号归属地:$person.phonediqu"
                                mainScope.launch {
                                    // 这里更新 UI
                                    text!!.text = responseBody
                                }
                            } else {
                                println("响应成功但内容获取为空")
                            }
                        }
                    })
                } else if (isQQNumber(textValue)) {
                    val request = Request.Builder()
                        .url("https://zy.xywlapi.cc/qqapi?qq=$textValue")
                        .build()

                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            // 处理失败情况
                            Toast.makeText(this@MainActivity, "无法访问网站", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call, response: Response) {
                            // 处理响应
                            val responseBody = response.body?.string()
                            if (responseBody != null) {
//                                val person = Json.decodeFromString<Data>(responseBody)
//                                println(person)
//                                text!!.text = "你查询的QQ号:$person.qq\n查询状态:$person.message\n手机号:$person.phone\n手机号归属地:$person.phonediqu"
                                mainScope.launch {
                                    // 这里更新 UI
                                    text!!.text = responseBody
                                }
                            } else {
                                println("响应成功但内容获取为空")
                            }
                        }
                    })
                }else{
                    Toast.makeText(this,"输入的内容不符合规范",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"请输入内容", Toast.LENGTH_SHORT).show()
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
}