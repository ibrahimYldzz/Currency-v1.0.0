package ibrahim.com.currency;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    double b,d,f;
    TextView dolarsonuctext,eurosonuctext,gbpsonuctext;
    Button yenile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yenile=findViewById(R.id.yenile);
        dolarsonuctext=findViewById(R.id.dolarsonuc);
        eurosonuctext=findViewById(R.id.eurosonuc);
        gbpsonuctext=findViewById(R.id.gbpsonuc);
    }

    public void download2(View view) {

        try {
            DownloadData downloadData = new DownloadData();

            String url = "https://api.exchangeratesapi.io/latest?base=TRY";

            downloadData.execute(url);

        } catch (Exception e) {

        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputstream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputstream);

                int data = inputStreamReader.read();

                while (data > 0) {

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();


                }

                return result;
            } catch (Exception e) {

                return null;
            }


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String rates = jsonObject.getString("rates");
                JSONObject jsonObject1 = new JSONObject(rates);
                String usd = jsonObject1.getString("USD");
                double a = Double.parseDouble(usd);
                b = 1 / a;
                dolarsonuctext.setText(String.format("%.2f",b));




                String euro = jsonObject1.getString("EUR");
                double c = Double.parseDouble(euro);
                d = 1 / c;
                eurosonuctext.setText(String.format("%.2f",d));



                String gbp = jsonObject1.getString("GBP");
                double e = Double.parseDouble(gbp);
                f = 1 / e;
                gbpsonuctext.setText(String.format("%.2f",f));



            } catch (Exception e) {

            }


        }
    }
}
