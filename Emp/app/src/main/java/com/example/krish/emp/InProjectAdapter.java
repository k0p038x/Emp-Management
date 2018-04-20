package com.example.krish.emp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

/**
 * Created by krish on 14/4/18.
 */

public class InProjectAdapter extends ArrayAdapter<InProjectDetails> {

    private Context mContext;
    ArrayList<String> hList;
    ArrayList<InProjectDetails> arrayList;
    private String pid;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;

    public InProjectAdapter(Context context, ArrayList<InProjectDetails> al, String pid) {
        super(context, 0, al);
        mContext = context;
        arrayList = al;
        this.pid = pid;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentView = convertView;
        if (currentView == null)
            currentView = LayoutInflater.from(mContext).inflate(R.layout.inproject_item, parent, false);

        TextView tv1 = (TextView) currentView.findViewById(R.id.emp_id);
        final TextView tv2 = (TextView) currentView.findViewById(R.id.project_hrs);
        final TextView tv3 = (TextView) currentView.findViewById(R.id.income_tv);

        final InProjectDetails details = arrayList.get(position);

        tv1.setText(details.getId());
        tv2.setText("Hours Spent : " + details.getHrs());
        try {
            tv3.setText("Income Earned : $ " + details.getRate() * details.getHrs());
        } catch (Exception e) {
            tv3.setText("Income Earned : -");
        }


        return currentView;
    }
}
