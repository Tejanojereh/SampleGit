import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.example.android.samplegit.My_Schedule_Patient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.Runnable;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Notify_Patient extends AppCompatActivity {

AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
AlertDialog alertDialog = alertDialogBuilder.create();

//WebService_Medication().execute();

}
/*public  class WebService extends AsyncTask
{
    @Override
    protected Void doInBackground(Object... objects)
    {
        byte[] data;
        HttpPost httpPost;
        StringBuffer buffer;
        HttpResponse response;
        HttpClient httpclient;
        InputStream inputStream;
        final String message;

        List<NameValuePair> nameValuePairs;
        nameValuePairs= new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("P_ID","1"));

        try
        {
            httpclient = new DefaultHttpClient();
            httpPost = new HttpPost("http://192.168.137.1/retrieve_medication.php");

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response=httpclient.execute(httpPost);
            inputStream=response.getEntity().getContent();

            data= new byte[256];
            buffer=new StringBuffer();
            int len=0;

            while(-1!=(len=inputStream.read(data))) {
                buffer.append(new String(data, 0, len));


            }
            String s= buffer.toString();
            JSONObject jsonObj= new JSONObject(s);
            JSONArray record = jsonObj.getJSONArray("results");
            final JSONObject c = record.getJSONObject(0);

                @Override
                public void run() {


                    try {
                       // med_date.setText("Medicine Intake Schedule:"+ c.getString("M_InitialTime").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            });
        }catch (final Exception e){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Notify_Patient.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }



}*/
