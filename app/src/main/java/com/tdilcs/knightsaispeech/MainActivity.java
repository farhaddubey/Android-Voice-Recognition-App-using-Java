package com.tdilcs.knightsaispeech;
import static com.tdilcs.knightsaispeech.Functions.Wishme;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SpeechRecognizer recognizer;
    private TextView resulttxt;
    //For Conveting Human Language to Machine Language We'll be using Text to Speech
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dexter.withContext(this)
                .withPermission(android.Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override public void onPermissionGranted(PermissionGrantedResponse response) {}
                    @Override public void onPermissionDenied(PermissionDeniedResponse response) {
                        System.exit(0);
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


        resulttxt= findViewById(R.id.result);
        Button btn1;
        btn1= findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording(v);
            }
        });
        initializeResults();
        initializeTextToSpeech();
    }

    private void initializeTextToSpeech() {
        tts=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(tts.getEngines().size()==0){
                    Toast.makeText(MainActivity.this, "Engine's Doesnt' Exist", Toast.LENGTH_SHORT).show();
                }else{
                    String s=Wishme();
                    speak("Hello Sir, I'm Knights..your personal AI Assistance"+s);
                }
            }
        });
    }
    private void speak(String msg) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);

        }
    }

    private void initializeResults() {
        if(SpeechRecognizer.isRecognitionAvailable(this)){
            recognizer=SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result=results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Toast.makeText(MainActivity.this, ""+result.get(0), Toast.LENGTH_SHORT).show();
                    resulttxt.setText(result.get(0));
                    response(result.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void response(String msg) {
        String msgs=msg.toLowerCase();
        if(msgs.indexOf("hi")!=-1) {
            speak("Hello Sir, How are you???");
        }
    }
    public void startRecording(View view){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        recognizer.startListening(intent);
    }
}