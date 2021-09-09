package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.Model.User;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.example.teleworldonlineshop.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public static User user = null;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin=(Button)findViewById(R.id.btn_login);

        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String username = ((EditText) findViewById(R.id.edt_username)).getText().toString();
                    final String password = ((EditText) findViewById(R.id.edt_password)).getText().toString();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.loginLink, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String response) {
                            if ("Login successful".equals(response.toString().trim())) {
                                user = new User(username,password);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("username",username);
                                CheckConnection.ShowToast_Short(getApplicationContext(),"Username ="+username);
                                startActivity(intent);
                            }
                            else
                            {
                                CheckConnection.ShowToast_Short(getApplicationContext(),response.toString().trim());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("userName",username);
                            hashMap.put("passWord",password);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            });
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check again your connection");
            finish();
        }
    }
}