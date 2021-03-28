package xyz.takablog.customermanagementsystem.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import xyz.takablog.customermanagementsystem.InsertStudentActivity;
import xyz.takablog.customermanagementsystem.R;


public class AsyncNetworkTask extends AsyncTask<String, Integer, String> {

    public WeakReference<TextView> address1;
    public InsertStudentActivity activity;

    public AsyncNetworkTask(Context context) {
        super();
        activity = (InsertStudentActivity) context;
        address1 = new WeakReference<>((TextView) activity.findViewById(R.id.address1));
    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        String[] arr = s.split("\\\"");
        try {
            String tmp = arr[7];
            tmp += arr[11];
            tmp += arr[15];
            address1.get().setText(tmp);
        }catch (Exception e){
            Toast.makeText(activity,R.string.notFindAddress,Toast.LENGTH_SHORT).show();
        }
    }
}