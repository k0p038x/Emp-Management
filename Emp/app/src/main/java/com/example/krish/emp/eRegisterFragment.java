package com.example.krish.emp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class eRegisterFragment extends Fragment {


   View mView;
   ProgressDialog progressDialog;
   RequestQueue requestQueue;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_e_register, container, false);
        final EditText et1 = (EditText) mView.findViewById(R.id.edit_un);
        final EditText et2 = (EditText) mView.findViewById(R.id.edit_pw);
        Button bt1 = (Button) mView.findViewById(R.id.button_reg);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getContext());
                requestQueue = Volley.newRequestQueue(getContext());
                progressDialog.setMessage("Loading..");
                progressDialog.show();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                progressDialog.dismiss();
                                try {
                                    JSONObject object = new JSONObject(response);
                                    Toast.makeText(getContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                                    if(!object.getBoolean("error")) {
                                        Intent intent = new Intent(getContext(),MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();

                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(getContext(),"Failed",Toast.LENGTH_SHORT).show();
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
                        params.put("uname",et1.getText().toString());
                        params.put("pword",et2.getText().toString());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        return mView;
    }

}
