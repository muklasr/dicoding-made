package com.muklas.mybackgroundthread

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity(), MyAsyncCallback {

    companion object{
        private const val INPUT_STRING = "Halo Ini Demo AsyncTask!!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val demoAsync = DemoAsync(this)
        demoAsync.execute(INPUT_STRING)
    }

    override fun onPreExecute() {
        tv_status.setText(R.string.status_pre)
        tv_desc.text = INPUT_STRING
    }

    override fun onPostExecute(result: String) {
        tv_status.setText(R.string.status_post)
        tv_desc.text = result
    }

    private class DemoAsync(val myListener: MyAsyncCallback) : AsyncTask<String, Void, String>() {
        companion object {

            private val LOG_ASYNC = "DemoAsync"
        }

        private val myListener2: WeakReference<MyAsyncCallback>

        init {
            this.myListener2 = WeakReference(myListener)
        }

        override fun onPreExecute() {
            super.onPreExecute()
            Log.d(LOG_ASYNC, "status: onPreExecute")
            val myListener = myListener2.get()
            myListener?.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String {
            Log.d(LOG_ASYNC, "status: doInBackground")

            var output: String? = null

            try {
                val input = params[0]
                output = "$input Selamat Belajar!!"
                Thread.sleep(2000)
            } catch (e: Exception){
                Log.d(LOG_ASYNC, e.message)
            }

            return output.toString()
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            Log.d(LOG_ASYNC, "status: onPostExecute")

            val myListener = myListener2.get()
            myListener?.onPostExecute(result)
        }
    }

}

internal interface MyAsyncCallback {
    fun onPreExecute()
    fun onPostExecute(text: String)
}