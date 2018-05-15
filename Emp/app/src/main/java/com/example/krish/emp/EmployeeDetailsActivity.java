package com.example.krish.emp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDetailsActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    String empId;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("data"));
            empId = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        viewPager = (ViewPager) findViewById(R.id.view_pager_id);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.empicon);
        tabLayout.getTabAt(1).setIcon(R.drawable.proj);
        int def = getIntent().getIntExtra("def",0);
        TabLayout.Tab tab =  tabLayout.getTabAt(def);
        tab.select();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"Kindly log out from the session",Toast.LENGTH_SHORT).show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out :
                finish();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.ch_pword :
                chPassword(empId);
                return true;
            default:
                return true;
        }
    }
    void chPassword(String id) {
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.change_password,null);
        mBuilder.setView(mView);
        mBuilder.setTitle("Change Password");
        mBuilder.setPositiveButton("Proceed",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final EditText et_1 = (EditText) mView.findViewById(R.id.old_pass);
                final EditText et_2 = (EditText) mView.findViewById(R.id.new_pass);
                final String oldp = et_1.getText().toString();
                final String newp = et_2.getText().toString();
                progressDialog.setMessage("Updating..");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.changePword,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    Log.e("Here : ",response);
                                    JSONObject object = new JSONObject(response);
                                    Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(),"Failed to Update",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(EmployeeDetailsActivity.this,"Failed to Update",Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("eid",empId);
                        params.put("old",oldp);
                        params.put("new",newp);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }


}
