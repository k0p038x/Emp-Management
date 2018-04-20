package com.example.krish.emp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class editProject extends AppCompatActivity {

    ArrayList<InProjectDetails> arrayList;
    ProgressDialog progressDialog;
    RequestQueue requestQueue,requestQueue1;
    ArrayList<String> hList;
    String empId;
    String ppId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        hList=new ArrayList<>();
        hList.add("Select..");
        for(int kk=1;kk<24;kk++) {
            if(kk<10) {
                hList.add("0"+kk);
            }
            else {
                hList.add(kk+"");
            }
        }
        progressDialog=new ProgressDialog(this);
        arrayList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        final String pid = getIntent().getStringExtra("pid");
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.eDetails_PROJ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("editProject : ",response);
                            empId = getIntent().getStringExtra("empId");
                            JSONArray jsonArray = jsonObject.getJSONArray("employee");
                            for (int i=0;i<jsonArray.length();i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("eid");

                                int hrs = object.getInt("hours");
                                float rate = (float)object.getDouble("rate");
                                arrayList.add(new InProjectDetails(id,hrs,rate));
                            }
                            Log.e("edit project : ",""+arrayList.size());
                            final InProjectAdapter adapter = new InProjectAdapter(getApplicationContext(),arrayList,pid);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    final int position = i;
                                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(editProject.this);
                                    View mView = getLayoutInflater().inflate(R.layout.hours_spinner,null);
                                    mBuilder.setTitle("Add Hours");
                                    final Spinner mSpinner = (Spinner) mView.findViewById(R.id.spinner);
                                    ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(editProject.this,
                                            R.layout.support_simple_spinner_dropdown_item,hList);
                                    mSpinner.setAdapter(spinnerAdapter);
                                    mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            if(!mSpinner.getSelectedItem().toString().equals("Select..")) {
                                                int num = Integer.parseInt(mSpinner.getSelectedItem().toString());
                                                arrayList.get(position).setHrs(arrayList.get(position).getHrs()+num);
                                                updateHrs(arrayList.get(position).getId(),pid,arrayList.get(position).getHrs());
                                                listView.setAdapter(adapter);
                                               
                                            }
                                        }
                                    });
                                    mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    mBuilder.setView(mView);
                                    AlertDialog dialog =  mBuilder.create();
                                    dialog.show();

                                }
                            });

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"Error in JSON",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     progressDialog.dismiss();
                     Toast.makeText(getApplicationContext(),"Error while requesting",Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("pid",pid);
                return map;
            }


        };
        requestQueue.add(stringRequest);

    }

    void updateHrs(final String eid, final String pid, final int hrs) {
        final String shrs = String.valueOf(hrs);
        progressDialog = new ProgressDialog(editProject.this);
        progressDialog.setMessage("Updating..");
        progressDialog.show();
        requestQueue1 = Volley.newRequestQueue(editProject.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.updateHrs,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(response);
                            String msg = object.getString("message");
                            Toast.makeText(editProject.this,msg,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(editProject.this,"Failed to Update",Toast.LENGTH_SHORT).show();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(editProject.this,"Failed to Update",Toast.LENGTH_SHORT).show();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("pid",pid);
                param.put("eid",eid);
                param.put("hrs",shrs);
                return param;
            }
        };
        requestQueue1.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        funBack();

    }
    public void funBack() {
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.empDetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Hereee : ",response);
                        progressDialog.dismiss();
                        Intent intent = new Intent(getApplicationContext(),EmployeeDetailsActivity.class);
                        intent.putExtra("data",response);
                        intent.putExtra("def",1);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),EmployeeDetailsActivity.class);
                        intent.putExtra("data","");
                        startActivity(intent);
                        finish();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("eid",empId);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_project,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.check :
                funBack();

                return true;

            default:
                return true;
        }
    }
}

