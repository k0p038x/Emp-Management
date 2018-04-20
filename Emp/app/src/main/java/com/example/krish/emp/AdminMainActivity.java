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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.HashMap;
import java.util.Map;

public class AdminMainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    String empId;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager_id);
        AdminPageAdapter adapter = new AdminPageAdapter(getSupportFragmentManager(),3);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.empicon);
        tabLayout.getTabAt(1).setIcon(R.drawable.jobclass);
        tabLayout.getTabAt(2).setIcon(R.drawable.proj);



    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out :
                finish();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.ch_pword :
                chPassword("admin");
                return true;
            case R.id.add_emp :
                addEmp();
                return true;
            case R.id.add_project:
                addProj();
                return true;
            default:
                addJClass();
                return true;
        }
    }
    void addJClass() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.new_jclass,null);
        mBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final EditText et1 = (EditText) mView.findViewById(R.id.class_edit);
                final EditText et2 = (EditText) mView.findViewById(R.id.rate_edit);
                final String e1,e2;
                e1 = et1.getText().toString();
                e2 = et2.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.newJClass,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    Log.e("Resp : ",response);
                                    Toast.makeText(AdminMainActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                                    if(!object.getBoolean("error")) {
                                        Intent intent = new Intent(AdminMainActivity.this,AdminMainActivity.class);
                                        intent.putExtra("data",object.getJSONObject("data").toString());
                                        Log.e("Data  : ",object.getJSONObject("data").toString());
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(AdminMainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AdminMainActivity.this,"Error in Response",Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("class",e1);
                        params.put("rate",e2);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });
        AlertDialog dialog = mBuilder.create();
        dialog.setView(mView);
        dialog.show();
    }
    void addProj() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.new_project,null);
        mBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final EditText et1 = (EditText) mView.findViewById(R.id.id_edit);
                final EditText et2 = (EditText) mView.findViewById(R.id.name_edit);
                final EditText et3 = (EditText) mView.findViewById(R.id.lead_edit);
                final String e1,e2,e3;
                e1 = et1.getText().toString();
                e2 = et2.getText().toString();
                e3 = et3.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.newProj,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    Log.e("Resp : ",response);
                                    Toast.makeText(AdminMainActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                                    if(object.getBoolean("error")==false) {
                                        Intent intent = new Intent(AdminMainActivity.this,AdminMainActivity.class);
                                        intent.putExtra("data",object.getJSONObject("data").toString());
                                        Log.e("Data  : ",object.getJSONObject("data").toString());
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(AdminMainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(AdminMainActivity.this,"Error in Response",Toast.LENGTH_SHORT).show();
                            }
                        })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("pid",e1);
                        params.put("name",e2);
                        params.put("lead",e3);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });
        AlertDialog dialog = mBuilder.create();
        dialog.setView(mView);
        dialog.show();
    }

    void addEmp() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.new_employee,null);
        mBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final EditText et1 = (EditText) mView.findViewById(R.id.id_edit);
                final EditText et2 = (EditText) mView.findViewById(R.id.name_edit);
                final EditText et3 = (EditText) mView.findViewById(R.id.class_edit);
                final String e1,e2,e3;
                e1 = et1.getText().toString();
                e2 = et2.getText().toString();
                e3 = et3.getText().toString();
              RequestQueue requestQueue = Volley.newRequestQueue(AdminMainActivity.this);
              StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.newEmp,
                      new Response.Listener<String>() {
                          @Override
                          public void onResponse(String response) {
                              try {
                                  JSONObject object = new JSONObject(response);
                                  Log.e("Resp : ",response);
                                  Toast.makeText(AdminMainActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                                  if(object.getBoolean("error")==false) {
                                      Intent intent = new Intent(AdminMainActivity.this,AdminMainActivity.class);
                                      intent.putExtra("data",object.getJSONObject("data").toString());
                                      Log.e("Data  : ",object.getJSONObject("data").toString());
                                      startActivity(intent);
                                      finish();
                                  }
                              } catch (JSONException e) {
                                  Toast.makeText(AdminMainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                              }
                          }
                      },
                      new Response.ErrorListener() {
                          @Override
                          public void onErrorResponse(VolleyError error) {
                              Toast.makeText(AdminMainActivity.this,"Error in Response",Toast.LENGTH_SHORT).show();
                          }
                      })
              {
                  @Override
                  protected Map<String, String> getParams() throws AuthFailureError {
                      Map<String,String> params = new HashMap<>();
                      params.put("eid",e1);
                      params.put("name",e2);
                      params.put("class",e3);
                      return params;
                  }
              };
              requestQueue.add(stringRequest);
            }
        });
        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //
            }
        });
        AlertDialog dialog = mBuilder.create();
        dialog.setView(mView);
        dialog.show();
    }
    void chPassword(String id) {
        requestQueue = Volley.newRequestQueue(this);
        progressDialog = new ProgressDialog(this);
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        final View mView = getLayoutInflater().inflate(R.layout.change_password,null);
        mBuilder.setView(mView);
        mBuilder.setTitle("Change Password");
        mBuilder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
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
                                    Toast.makeText(AdminMainActivity.this,object.getString("message"),Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Toast.makeText(AdminMainActivity.this,"Failed to Update",Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(AdminMainActivity.this,"Failed to Update",Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("eid","admin");
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
    public void onBackPressed() {
        Toast.makeText(AdminMainActivity.this,"Kindly log out from the session",Toast.LENGTH_SHORT).show();
    }



}
