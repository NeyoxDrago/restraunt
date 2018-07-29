package com.example.nirnaymittal.restraunt;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextInputLayout firstname,lastname,phone,address,restname;
    Spinner type;
    Button send;
    int SendType =0;
    database db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new database(this);
        firstname = (TextInputLayout) findViewById(R.id.FirstName);
        lastname = (TextInputLayout) findViewById(R.id.lastName);
        phone = (TextInputLayout) findViewById(R.id.phone);
        address = (TextInputLayout) findViewById(R.id.Address);
        restname = (TextInputLayout) findViewById(R.id.restaurantName);
        type = (Spinner) findViewById(R.id.typespinner);
        send = (Button) findViewById(R.id.sendRequest);

        String[] types = {"Owner","Manager" ,"Other"};
        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item , types);
        type.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = firstname.getEditText().getText().toString().trim();
                String last_name = lastname.getEditText().getText().toString().trim();
                String Phone = (phone.getEditText().getText().toString().trim());
                String Address = address.getEditText().getText().toString().trim();
                String restaurant = restname.getEditText().getText().toString().trim();
                String Type = type.getSelectedItem().toString();

                if(Type.equals("Manager")){
                    SendType = 2;
                }
                if(Type.equals("Owner"))
                {
                    SendType = 1;
                }
                if(Type.equals("Other"))
                {
                    SendType = 3;
                }



                Data d = new Data(first_name,last_name,Phone , Address,restaurant , SendType);
                Data d2 = new Data("admin","1234");

                Toast.makeText(MainActivity.this,first_name+last_name+Phone + Address+restaurant + SendType, Toast.LENGTH_SHORT).show();

                db.insert(first_name,last_name,Phone , Address,restaurant , SendType);
                SendReq(d);

                //Toast.makeText(MainActivity.this, "Well Done,\n Request Sent", Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, "{"+first_name+","+ last_name+","+Phone+","+Address+","+restaurant+","+Type+","+SendReq+"}", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void SendReq(final Data d)
    {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://api.shoocal.com/test/manager/")
                .client(okHttpClient)
                .addConverterFactory(SimpleXmlConverterFactory.create());

        Retrofit retro = builder.build();

        API a = retro.create(API.class);

        Call<Data> call = a.sendData(d);

        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                Toast.makeText(MainActivity.this, "Work completed Successfully", Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();

                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "server returned so many repositories: " + response.body().getRestau_name(), Toast.LENGTH_SHORT).show();
                    // todo display the data instead of just a toast
                }
                else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(MainActivity.this, "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(MainActivity.this, "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

                if(t.getMessage().equals("connect timed out")){
                    SendReq(d);
                }
                AlertDialog.Builder a = new AlertDialog.Builder(MainActivity.this);
                a.setMessage(t.getMessage());
                a.show();
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();

        try{
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            {
                final int RequestInternetpermissionId = 1001;
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.INTERNET}
                , RequestInternetpermissionId);
            }


        }catch(Exception e){

            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show();
        }


    }
}
