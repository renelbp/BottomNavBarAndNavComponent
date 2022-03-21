package com.example.multimediaenandroidstudio

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.multimediaenandroidstudio.databinding.FragmentMediaPlayerBinding


class MediaPlayerFragment : Fragment() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable:Runnable
    private var handler: Handler = Handler()
    private  var pause:Boolean = false
     private lateinit var binding: FragmentMediaPlayerBinding




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {

        binding = FragmentMediaPlayerBinding.inflate(inflater,container,false)

        // Start the media player


        binding.playBtn.setOnClickListener{
            binding.songIv.setImageResource(R.drawable.img)

            if(!pause){

                mediaPlayer = MediaPlayer.create(requireContext(),R.raw.music)
                mediaPlayer.start()
                Toast.makeText(requireContext(),"media playing",Toast.LENGTH_SHORT).show()
            }else{
                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                mediaPlayer.start()
                pause = false
                Toast.makeText(requireContext(),"media playing",Toast.LENGTH_SHORT).show()

            }
            initializeSeekBar()
            binding.playBtn.isEnabled = false
            binding.pauseBtn.isEnabled = true
            binding.stopBtn.isEnabled = true

            mediaPlayer.setOnCompletionListener {
                binding.playBtn.isEnabled = true
                binding.pauseBtn.isEnabled = false
                binding.stopBtn.isEnabled = false
                Toast.makeText(requireContext(),"end",Toast.LENGTH_SHORT).show()
                binding.songIv.setImageResource(R.drawable.music)
            }

        }
        // Pause the media player
        binding.pauseBtn.setOnClickListener {
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
                pause = true
                binding.playBtn.isEnabled = true
                binding.pauseBtn.isEnabled = false
                binding.stopBtn.isEnabled = true
                Toast.makeText(requireContext(),"media pause",Toast.LENGTH_SHORT).show()
            }
        }
        // Stop the media player
        binding.stopBtn.setOnClickListener{
            if(mediaPlayer.isPlaying || pause.equals(true)){
                pause = false
                binding.seekBar.setProgress(0)
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
                handler.removeCallbacks(runnable)

                binding.playBtn.isEnabled = true
                binding.pauseBtn.isEnabled = false
                binding.stopBtn.isEnabled = false
                binding.tvPass.text = ""
                binding.tvDue.text = ""
                binding.songIv.setImageResource(R.drawable.music)
                Toast.makeText(requireContext(),"media stop",Toast.LENGTH_SHORT).show()
            }
        }
        // Seek bar change listener
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                if (b) {
                    mediaPlayer.seekTo(i * 1000)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        return binding.root
    }



    // Method to initialize seek bar and audio stats
    private fun initializeSeekBar() {
        binding.seekBar.max = mediaPlayer.seconds

        runnable = Runnable {
            binding.seekBar.progress = mediaPlayer.currentSeconds

            binding.tvPass.text = "${mediaPlayer.currentSeconds} sec"
            val diff = mediaPlayer.seconds - mediaPlayer.currentSeconds
            binding.tvDue.text = "$diff sec"

            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)//ask for this handler
    }
}
// Creating an extension property to get the media player time duration in seconds
val MediaPlayer.seconds:Int
    get() {
        return this.duration / 1000
    }
// Creating an extension property to get media player current position in seconds
val MediaPlayer.currentSeconds:Int
    get() {
        return this.currentPosition/1000
    }


