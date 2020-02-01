package com.muklas.mycoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object{
        private const val INPUT_STRING= "Halo Ini Demo Coroutines!!"
        private const val LOG_COROUTINES= "DemoCoroutines"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_status.setText(R.string.status_pre)
        tv_desc.setText(R.string.desc)

        GlobalScope.launch(Dispatchers.Main){
            //background thread
            val input = INPUT_STRING

            var output: String? = null

            Log.d(LOG_COROUTINES, "ststus: doInBackground")
            try {
                output = "$input Selamat Belajar!!"

                delay(2000)

                //pindah ke MainThread untuk Update UI
                withContext(Dispatchers.Main){
                    tv_status.setText(R.string.status_post)
                    tv_desc.text = output
                }
            } catch (e: Exception){
                Log.d(LOG_COROUTINES, e.message.toString())
            }
        }

    }
}
