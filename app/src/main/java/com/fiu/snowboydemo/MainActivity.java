package com.fiu.snowboydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ai.kitt.snowboy.AppResCopy;
import ai.kitt.snowboy.MsgEnum;
import ai.kitt.snowboy.audio.AudioDataSaver;
import ai.kitt.snowboy.audio.RecordingThread;

public class MainActivity extends AppCompatActivity {

    private int activeTimes = 0;
    private final int DEMO_PERMISSION_CODE = 111;
    private RecordingThread recordingThread;
    private TextView detectOutput;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detectOutput = findViewById(R.id.detectOutput);
        startButton = findViewById(R.id.startButton);

        // Check permissions, else will silently fail
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Grant permission(s) dialog
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    DEMO_PERMISSION_CODE);
        } else {
            initRecording();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == DEMO_PERMISSION_CODE) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initRecording();
            } //else permission denied
        }
    }

    private void initRecording() {
        AppResCopy.copyResFromAssetsToSD(this);
        recordingThread = new RecordingThread(handle, new AudioDataSaver());
        startButton.setOnClickListener(startButtonHandler);
    }

    private View.OnClickListener startButtonHandler = new View.OnClickListener() {
        public void onClick(View arg0) {
            recordingThread.startRecording();
//            if(record_button.getText().equals(getResources().getString(R.string.btn1_start))) {
//                stopPlayback();
//                sleep();
//                startRecording();
//            } else {
//                stopRecording();
//                sleep();
//            }
        }
    };

    @SuppressLint("HandlerLeak")
    public Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            MsgEnum message = MsgEnum.getMsgEnum(msg.what);
            switch(message) {
                case MSG_ACTIVE:
                    activeTimes++;
                    detectOutput.setText(activeTimes + "");
//                    updateLog(" ----> Detected " + activeTimes + " times", "green");
//                    // Toast.makeText(Demo.this, "Active "+activeTimes, Toast.LENGTH_SHORT).show();
//                    showToast("Active "+activeTimes);
                    break;
                case MSG_INFO:
//                    updateLog(" ----> "+message);
                    break;
                case MSG_VAD_SPEECH:
//                    updateLog(" ----> normal voice", "blue");
                    break;
                case MSG_VAD_NOSPEECH:
//                    updateLog(" ----> no speech", "blue");
                    break;
                case MSG_ERROR:
//                    updateLog(" ----> " + msg.toString(), "red");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    @Override
    public void onDestroy() {
//        restoreVolume();
        recordingThread.stopRecording();
        super.onDestroy();
    }
}