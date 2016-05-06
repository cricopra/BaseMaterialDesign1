package co.playtech.otrosproductosrd.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import co.playtech.otrosproductosrd.R;
import co.playtech.otrosproductosrd.handlers.ConfiguracionHandler;

public class ConfiguracionActivity extends AppCompatActivity {

    public Context context;
    public EditText etIp;
    public EditText etPuerto;
    public EditText etPrinter;
    public Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init(){
        context = this;
        etIp = (EditText)findViewById(R.id.etIp);
        etPuerto = (EditText)findViewById(R.id.etPuerto);
        etPrinter = (EditText)findViewById(R.id.etMacPrinter);
        btnGuardar = (Button)findViewById(R.id.btnSaveConfig);
        ConfiguracionHandler objHandler = new ConfiguracionHandler(this);
        btnGuardar.setOnClickListener(objHandler);
        etPrinter.setOnClickListener(objHandler);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
