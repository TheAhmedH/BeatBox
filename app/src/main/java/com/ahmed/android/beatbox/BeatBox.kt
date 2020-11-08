package com.ahmed.android.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.IOException
import java.lang.Exception

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class BeatBox(private val assets: AssetManager) {

    val sounds: List<Sound>

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUNDS).build()

    init {
        sounds = loadSounds()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun loadSounds(): List<Sound> {
        val soundsNames: Array<String>
        try {
            soundsNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "loadSounds: could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()

        soundsNames.forEach { filename ->

            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "loadSounds:Could not load filename $sound ")
            }
        }
        return sounds
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun load(sound: Sound) {
        val assetFileDescriptor: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundID = soundPool.load(assetFileDescriptor, 1)
        sound.soundId = soundID
    }
}