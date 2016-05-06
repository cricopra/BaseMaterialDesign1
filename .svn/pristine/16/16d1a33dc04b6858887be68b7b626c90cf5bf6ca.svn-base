package co.playtech.otrosproductosrd.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.fragments.AutenticarFragment;
import co.playtech.otrosproductosrd.fragments.SplashFragment;
import co.playtech.otrosproductosrd.help.Constants;

public class SplashActivity extends AppCompatActivity {

    private static SplashActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        instance = this;

        SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(this);
        boolean blState = objShared.getBoolean(Constants.SESSION_CURRENT, false);

        if(blState){
            Intent objIntent = new Intent(this, MainActivity.class);
            startActivity(objIntent);
            finish();
        }
        else{
            replaceFragment(new SplashFragment());
        }

    }

    public static void replaceFragment(Fragment fragment){
        if(fragment != null) {
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = SplashActivity.instance.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        //SplashActivity.replaceFragment(new AutenticarFragment());
        super.onBackPressed();
    }
}
