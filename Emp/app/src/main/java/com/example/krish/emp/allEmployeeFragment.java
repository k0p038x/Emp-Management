package com.example.krish.emp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class allEmployeeFragment extends Fragment {


    View mView;
    ArrayList<AdminEmpDetails> al;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    ListView listView;
    AllEmpAdapter adapter;
    int first=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         al =new ArrayList<>();
         mView=inflater.inflate(R.layout.fragment_all_employee, container, false);
         listView = (ListView) mView.findViewById(R.id.list_view);
         try {
             JSONObject object = new JSONObject(getActivity().getIntent().getStringExtra("data"));
             JSONArray array = object.getJSONArray("employee");
             for(int i=0;i<array.length();i++) {
                 JSONObject temp = array.getJSONObject(i);
                 String id = temp.getString("id");
                 String name = temp.getString("name");
                 String cla = temp.getString("class");
                 al.add(new AdminEmpDetails(id,name,cla));
             }
             adapter = new AllEmpAdapter(getContext(),al);
             listView.setAdapter(adapter);
         }
         catch (Exception e) {
             Toast.makeText(getContext(),"Failed to Load",Toast.LENGTH_SHORT).show();
         }
         listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                 AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                 final View kView = getLayoutInflater().inflate(R.layout.change_empdetails,null);
                 EditText et_id = (EditText) kView.findViewById(R.id.emp_id);
                 final EditText et_name = (EditText) kView.findViewById(R.id.name);
                 final EditText et_class = (EditText) kView.findViewById(R.id.job_class);
                 et_id.setText(al.get(pos).getId());
                 et_id.setFocusable(false);
                 et_name.setText(al.get(pos).getName());
                 et_class.setText(al.get(pos).getJobClass());
                 mBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         progressDialog = new ProgressDialog(getContext());
                         progressDialog.setMessage("Updating..");
                         progressDialog.show();
                         final String new_name = et_name.getText().toString();
                         final String new_class = et_class.getText().toString();

                         requestQueue = Volley.newRequestQueue(getContext());
                         StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.UpdateEmp,
                                 new Response.Listener<String>() {
                                     @Override
                                     public void onResponse(String response) {
                                         progressDialog.dismiss();
                                         try {
                                             JSONObject object = new JSONObject(response);
                                             Log.e("Resp : ",response);
                                             Toast.makeText(getContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                                             if(!object.getBoolean("error")) {
                                                 Intent intent = new Intent(getContext(),AdminMainActivity.class);
                                                 intent.putExtra("data",object.getJSONObject("data").toString());
                                                 Log.e("Data  : ",object.getJSONObject("data").toString());
                                                 startActivity(intent);
                                                 getActivity().finish();
                                             }
                                         }catch (JSONException e) {
                                             Toast.makeText(getContext(),"Error in JSON",Toast.LENGTH_SHORT).show();
                                         }

                                     }
                                 },
                                 new Response.ErrorListener() {
                                     @Override
                                     public void onErrorResponse(VolleyError error) {
                                         progressDialog.dismiss();
                                         Toast.makeText(getContext(),"Error in Response",Toast.LENGTH_SHORT).show();

                                     }
                                 })
                         {
                             @Override
                             protected Map<String, String> getParams() throws AuthFailureError {
                                 Map<String,String> params = new HashMap<>();
                                 params.put("eid",al.get(pos).getId());
                                 params.put("name",new_name);
                                 params.put("class",new_class);
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
                 mBuilder.setView(kView);
                 AlertDialog dialog = mBuilder.create();
                 dialog.show();
                 return true;
             }
         });


        return mView;
    }


}
