package com.jatin.recordingapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{

    String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/recording1.3gpp";
    Button btnRecord,btnStop,btnPlay;
    MediaRecorder recorder;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecord = findViewById(R.id.btnRecord);
        btnStop = findViewById(R.id.btnStop);
        btnPlay = findViewById(R.id.btnPlay);
        btnRecord.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        switch (id)
        {
            case R.id.btnRecord:
            {
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile(fileName);
                try
                {
                    recorder.prepare();
                    recorder.start();
                    Toast.makeText(MainActivity.this,"Recording Started", Toast.LENGTH_SHORT).show();
                    btnRecord.setEnabled(false);
                    btnPlay.setEnabled(false);
                    btnStop.setEnabled(true);
                }
                catch (IOException ioe)
                {
                    Log.e("jv", "prepare() failed in recording");
                }
                break;
            }
            case R.id.btnStop:
            {
                recorder.stop();
                recorder.release();
                Toast.makeText(MainActivity.this,"Recording Stopped", Toast.LENGTH_SHORT).show();
                btnPlay.setEnabled(true);
                btnStop.setEnabled(false);
                btnRecord.setEnabled(true);
                break;
            }
            case R.id.btnPlay:
            {
                player = new MediaPlayer();
                try
                {
                    player.setDataSource(fileName);
                    player.prepare();
                    player.start();
                    Toast.makeText(MainActivity.this, "Recording Started Playing", Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    Log.e("jv", "prepare() failed in playing");
                }
                break;
            }
        }

    }
}
