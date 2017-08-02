package ocr.ocr;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MainActivity extends Activity {

    private String locationProvider;//位置提供器
    private LocationManager locationManager;//位置服务
    private Location location;
    private TextView tvLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLocation = (TextView) findViewById(R.id.tv_location);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }
        //获取Location
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            showLocation(location);
        }
        //监视地理位置变化
        locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);

    }

    private void showLocation(Location location) {

        String latitude = location.getLatitude() + "";
        String longitude = location.getLongitude() + "";
        //http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=31.281484,120.553106&output=json&mcode=5A:3A:5C:E8:E9:C1:18:9A:E3:CF:65:53:00:FA:83:C6:C4:1E:EA:F0;com.oceansoft.police&ak=TLTQcGrN8oL8G010M1Tlo7PHygfLe2Ul
        String url = "http://api.map.baidu.com/geocoder/v2/?ak=TLTQcGrN8oL8G010M1Tlo7PHygfLe2Ul&callback=renderReverse&mcode=5A:3A:5C:E8:E9:C1:18:9A:E3:CF:65:53:00:FA:83:C6:C4:1E:EA:F0;com.oceansoft.police&location=" + latitude + "," + longitude + "&output=json";
        new MyAsyncTask(url).execute();
        tvLocation.setText(latitude + ":" + longitude);
        Log.e("url+lsh", url);
    }

    LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            //如果位置发生变化,重新显示
            showLocation(location);
        }
    };


    class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        String url = null;
        String str = null;

        public MyAsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                str = String.valueOf(HttpUtil.loadData(url));
                Log.e("LSH", str);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                str = str.replace("renderReverse&&renderReverse", "");
                str = str.replace("(", "");
                str = str.replace(")", "");
                JSONObject jsonObject = new JSONObject(str);
                JSONObject address = jsonObject.getJSONObject("result");
                String city = address.getString("formatted_address");
                String district = address.getString("sematic_description");
                tvLocation.setText("当前位置：" + city + district);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            super.onPostExecute(aVoid);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //移除监听器
            locationManager.removeUpdates(locationListener);
        }
    }


}
