package com.example.krish.emp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class EmpLoginActivity extends AppCompatActivity {

    TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);
        tv1 = (TextView) findViewById(R.id.name_tv);
        tv2 = (TextView) findViewById(R.id.ctype_tv);
        tv3 = (TextView) findViewById(R.id.hrs_tv);

        try {
            JSONObject jsonObject = new JSONObject(getIntent().getStringExtra("data"));
                tv1.setText(jsonObject.getString("name"));
                tv2.setText(jsonObject.getString("type"));
                tv3.setText(jsonObject.getString("hours"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
