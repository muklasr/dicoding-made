package com.muklas.mysound

import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sp: SoundPool
    private var soundId: Int = 0
    private var spLoaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = SoundPool.Builder()
            .setMaxStreams(10)
            .build()

        sp.setOnLoadCompleteListener { soundPool, sampleId, status ->
            if(status == 0){
                spLoaded = true
            } else {
                Toast.makeText(this, "Gagal load", Toast.LENGTH_SHORT).show()
            }
        }

        soundId = sp.load(this, R.raw.test, 1)

        btn_soundpool.setOnClickListener {
            if(spLoaded) {
                sp.play(soundId, 0f, 0f, 0, 0, 0f)
            }
        }
    }
}
