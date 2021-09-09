package com.example.teleworldonlineshop.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.teleworldonlineshop.Adapter.LaptopAdapter;
import com.example.teleworldonlineshop.Model.Device;
import com.example.teleworldonlineshop.R;
import com.example.teleworldonlineshop.Ultil.CheckConnection;
import com.example.teleworldonlineshop.Ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarLaptop;
    ListView listviewLaptop;
    LaptopAdapter laptopAdapter;
    public static ArrayList<Device> lstLaptop;
    int laptopId=0;
    int page = 1;
    boolean limitData = false;
    boolean inLoading = false;
    View footerView;
    myHandler myHandler;
    com.getbase.floatingactionbutton.FloatingActionButton fabDevice;
    com.getbase.floatingactionbutton.FloatingActionButton fabHome;
    private MenuItem menuLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);

        if(CheckConnection.haveNetworkConnection(getApplicationContext())) {
            Mapping();
            GetCategoryId();
            ActionToolBar();
            GetLaptopData(page);
            LoadMoreData();
            if(LoginActivity.user!=null){
                CatchOnItemListView();
            }
        }
        else{
            CheckConnection.ShowToast_Short(getApplicationContext(),"Please check again your connection");
            finish();
        }
        if(LoginActivity.user==null){
            fabDevice.setVisibility(View.INVISIBLE);
        }
        fabDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CreateNewDeviceActivity.class);
                startActivity(intent);
            }
        });
        fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void CatchOnItemListView() {
        listviewLaptop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LaptopActivity.this);
                builder.setTitle("Manage option");
                builder.setMessage("Select your manage option");
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.deleteDeviceLink, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (lstLaptop.size() > 0) {
                                    if ("Delete successful".equals(response.toString().trim())) {
                                        lstLaptop.remove(position);
                                        laptopAdapter.notifyDataSetChanged();
                                        CheckConnection.ShowToast_Short(getApplicationContext(), response.toString());
                                    } else {
                                        CheckConnection.ShowToast_Short(getApplicationContext(), response.toString());
                                    }
                                } else {
                                    CheckConnection.ShowToast_Short(getApplicationContext(), "Phone data error");
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("deviceId", String.valueOf(lstLaptop.get(position).getDeviceId()));
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                });
                builder.setNeutralButton("Modify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent(getApplicationContext(),ModifyActivity.class);
                        intent.putExtra("Modify device",lstLaptop.get(position));
                        //intent.putExtra("Selected device Id",lstLaptop.get(position).getDeviceId());
                        startActivity(intent);
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void LoadMoreData() {
        listviewLaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DeviceDetailsActivity.class);
                intent.putExtra("Device details",lstLaptop.get(position));
                startActivity(intent);
            }
        });
        listviewLaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && inLoading ==false && limitData==false){
                    inLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }

            }
        });
    }

    private void GetLaptopData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String dataLink = Server.phoneLink+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, dataLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id = 0;
                String laptopName = "";
                int price = 0;
                String pictureUrl ="";
                String description ="";
                int categoryId = 0;
                if(response != null&&response.length()!=2)
                {
                    listviewLaptop.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i<jsonArray.length();i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            laptopName = jsonObject.getString("name");
                            price = jsonObject.getInt("price");
                            pictureUrl = jsonObject.getString("pictureUrl");
                            description = jsonObject.getString("description");
                            categoryId= jsonObject.getInt("categoryId");
                            lstLaptop.add(new Device(id, laptopName, price, pictureUrl, description, categoryId));
                            laptopAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    limitData=true;
                    listviewLaptop.removeFooterView(footerView);
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Out of data");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("Id",String.valueOf(laptopId));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetCategoryId() {
        laptopId = getIntent().getIntExtra("Id",-1);
        Log.d("Category Id is:",laptopId+"");
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        menuLogin=menu.findItem(R.id.menuLogin);
        if(LoginActivity.user!=null) {
            menuLogin.setIcon(getResources().getDrawable(R.drawable.logout));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuCart:
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
        }
        switch (item.getItemId()){
            case R.id.menuLogin:
                if(LoginActivity.user!=null){
                    LoginActivity.user=null;
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void invalidateOptionsMenu() {
        super.invalidateOptionsMenu();
    }


    private void Mapping() {
        toolbarLaptop = (Toolbar) findViewById(R.id.toolbarLaptop);
        listviewLaptop = (ListView) findViewById(R.id.listviewLaptop);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        lstLaptop=new ArrayList<>();
        myHandler = new myHandler();
        laptopAdapter = new LaptopAdapter(getApplicationContext(),lstLaptop);
        listviewLaptop.setAdapter(laptopAdapter);
        fabDevice = findViewById(R.id.fabDevice);
        fabHome = findViewById(R.id.fabHome);
    }
    public class myHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    listviewLaptop.addFooterView(footerView);
                    break;
                case 1:
                    GetLaptopData(++page);
                    inLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            myHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myHandler.obtainMessage(1);
            myHandler.sendMessage(message);
            super.run();
        }
    }
    
}
