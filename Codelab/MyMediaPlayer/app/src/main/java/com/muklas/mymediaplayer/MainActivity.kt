package com.muklas.mymediaplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = MainActivity::class.java.simpleName
    private var mService: Messenger? = null
    private lateinit var mBoundServiceIntent: Intent
    private var mServiceBound = false

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null
            mServiceBound = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = Messenger(service)
            mServiceBound = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_play.setOnClickListener(this)
        btn_stop.setOnClickListener(this)

        mBoundServiceIntent = Intent(this, MediaService::class.java)
        mBoundServiceIntent.action = MediaService.ACTION_CREATE

        startService(mBoundServiceIntent)
        bindService(mBoundServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_play -> {
                if (!mServiceBound) return
                try {
                    mService?.send(Message.obtain(null, MediaService.PLAY, 0, 0))
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
            R.id.btn_stop -> {
                if (!mServiceBound) return
                try {
                    mService?.send(Message.obtain(null, MediaService.STOP, 0, 0))
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
            else -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(mServiceConnection)
        mBoundServiceIntent.action = MediaService.ACTION_DESTROY

        startService(mBoundServiceIntent)
    }
}
