package co.playtech.otrosproductosrd.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.adt.AutenticarADT;
import co.playtech.otrosproductosrd.fragments.AnulacionBancaFragment;
import co.playtech.otrosproductosrd.fragments.AnulacionRecargasFragment;
import co.playtech.otrosproductosrd.fragments.BancaFragment;
import co.playtech.otrosproductosrd.fragments.CambiarClaveFragment;
import co.playtech.otrosproductosrd.fragments.ConsultasRecargaFragment;
import co.playtech.otrosproductosrd.fragments.ResultadosFragment;
import co.playtech.otrosproductosrd.fragments.TotalVentaFragment;
import co.playtech.otrosproductosrd.fragments.VentaRecargaFragment;
import co.playtech.otrosproductosrd.fragments.VerificacionMainFragment;
import co.playtech.otrosproductosrd.fragments.VerificacionTiqueteFragment;
import co.playtech.otrosproductosrd.help.AppException;
import co.playtech.otrosproductosrd.help.Constants;
import co.playtech.otrosproductosrd.help.Utilities;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Context context;
    boolean exit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            context = getApplicationContext();

            if(findViewById(R.id.frame_container) != null){
                if(savedInstanceState != null)
                    return;

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new BancaFragment()).commit();
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

            View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
            TextView tvNameCurrentUser = (TextView) headerLayout.findViewById(R.id.tvCurrentUser);
            TextView tvVersion = (TextView) headerLayout.findViewById(R.id.tvVersionAppHeader);

            String sbData = Utilities.getDataUserPreferences(context);
            JSONObject jsDataUser = new JSONObject(sbData);
            String sbVendedor = jsDataUser.getString(Constants.VENDEDOR);
            tvNameCurrentUser.setText(sbVendedor);
            tvVersion.setText(context.getString(R.string.lblVersion)+Utilities.getVersion(context));
            navigationView.setNavigationItemSelectedListener(this);

        }catch (Exception e){
            Utilities.showAlertDialog(this, e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            if (exit) {
                //finish(); // finish activity
                moveTaskToBack(true);
            } else {
                Toast.makeText(this, getString(R.string.msj_press_back_again),
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 2 * 1000);

            }
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_banca) {
            fragment = new BancaFragment();
        }
        else if (id == R.id.nav_verificacion) {
            fragment = new VerificacionTiqueteFragment();
        }
        else if (id == R.id.nav_resultado_loterias) {
            fragment = new ResultadosFragment();
        }
        else if (id == R.id.nav_anulacion_banca) {
            fragment = new AnulacionBancaFragment();
        }
        else if (id == R.id.nav_venta_recarga) {
            fragment = new VentaRecargaFragment();
        }
        else if (id == R.id.nav_consultas_recargas) {
            fragment = new ConsultasRecargaFragment();
        }
        else if (id == R.id.nav_anulacion_recargas) {
            fragment = new AnulacionRecargasFragment();
        }
        else if (id == R.id.nav_change_password) {
            fragment = new CambiarClaveFragment();
        }
        else if (id == R.id.nav_total_sale) {
            fragment = new TotalVentaFragment();
        }
        else if(id == R.id.nav_exit_app){
            exitApp();
        }

        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exitApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.lblExitSession))
                .setTitle(getString(R.string.app_name))
                .setCancelable(true)
                .setNegativeButton(getString(R.string.btn_no), new
                        DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel(); }
				 })
                .setPositiveButton(getString(R.string.btn_yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // metodo que se debe implementar
                                /*SharedPreferences objShared = PreferenceManager.getDefaultSharedPreferences(context);
                                SharedPreferences.Editor edit = objShared.edit();
                                edit.putBoolean(Constants.SESSION_CURRENT, false);
                                edit.commit();*/
                                Utilities.clearAllPreferencesSession(context);

                                Intent objIntent = new Intent(context, SplashActivity.class);
                                startActivity(objIntent);
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        VerificacionTiqueteFragment objFragment = VerificacionTiqueteFragment.getInstance();
        objFragment.onActivityResult(requestCode,resultCode,data);
    }
}
