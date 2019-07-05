package wagonchase.version1.wagonchase;


import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

        Log.d("jrJava1", "point onCreate");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
        setContentView(new GameView(this));

    }

    public void onStart() {
        super.onStart();
        Log.d("jrJava1", "point onStart");
    }

    public void onPause() {
        super.onPause();
        Log.d("jrJava1", "point onPause");
    }

    public void onResume() {
        super.onResume();
        Log.d("jrJava1", "point onResume");
    }

    public void onRestart() {
        super.onRestart();
        Log.d("jrJava1", "point onRestart");
    }

    public void onStop() {
        super.onStop();
        Log.d("jrJava1", "point onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d("jrJava1", "point onDestroy");
    }
}
