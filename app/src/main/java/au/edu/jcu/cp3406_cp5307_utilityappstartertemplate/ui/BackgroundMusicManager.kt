package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.ui

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import au.edu.jcu.cp3406_cp5307_utilityappstartertemplate.R

object BackgroundMusicManager {
    private var mediaPlayer: MediaPlayer? = null
    private var isPrepared = false

    fun start(context: Context) {
        val appContext = context.applicationContext

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )

                val afd = appContext.resources.openRawResourceFd(R.raw.background_music)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()

                isLooping = true
                setVolume(0.35f, 0.35f)

                setOnPreparedListener { player ->
                    isPrepared = true
                    player.start()
                }

                setOnCompletionListener { player ->
                    if (isPrepared) {
                        player.seekTo(0)
                        player.start()
                    }
                }

                prepareAsync()
            }
        } else {
            mediaPlayer?.let { player ->
                if (isPrepared && !player.isPlaying) {
                    player.start()
                }
            }
        }
    }

    fun pause() {
        mediaPlayer?.let { player ->
            if (isPrepared && player.isPlaying) {
                player.pause()
            }
        }
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
        isPrepared = false
    }
}

