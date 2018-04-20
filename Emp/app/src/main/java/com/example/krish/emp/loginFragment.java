package com.example.krish.emp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;


public class loginFragment extends Fragment {

    private String uname;
    private String pword;
    EditText et1,et2;
    Button btn;
    RequestQueue requestQueue;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        et1 = (EditText) view.findViewById(R.id.edit_un);
        et2 = (EditText) view.findViewById(R.id.edit_pw);
        btn = (Button) view.findViewById(R.id.button_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = et1.getText().toString();
                pword = et2.getText().toString();
                if (uname.equals("admin")) {
                    requestQueue = Volley.newRequestQueue(getContext());
                    progressDialog.setMessage("Loading..");
                    progressDialog.show();
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.getAllEmp,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    progressDialog.dismiss();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if(jsonObject.getString("pword").equals(pword)) {
                                            Intent intent = new Intent(getContext(), AdminMainActivity.class);
                                            intent.putExtra("data", jsonObject.toString());
                                            startActivity(intent);
                                        }
                                        else {
                                            Toast.makeText(getContext(),"Wrong Password",Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {

                                        e.printStackTrace();
                                        Toast.makeText(getContext(), "Error may be", Toast.LENGTH_SHORT).show();
                                    }

                                }


                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
                                }
                            })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
                else {
                progressDialog.setMessage("Logging In");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                et1.setText("");
                                et2.setText("");

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getBoolean("error"))
                                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    else {
                                        Intent intent = new Intent(getContext(), EmployeeDetailsActivity.class);
                                        intent.putExtra("data", jsonObject.toString());
                                        intent.putExtra("def", 0);
                                        startActivity(intent);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getContext(), "Error may be", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.hide();
                                Toast.makeText(getContext(), "Error !!", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("uname", uname);
                        params.put("pword", pword);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);

            }
         }
        });



        return view;
    }


}
