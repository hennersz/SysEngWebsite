package com.example.sherl_000.weartest_dataitem;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    GoogleApiClient mGoogleApiClient = null;
    public static final String TAG = "MyDataMap";
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path"; //can be any string that starts with a forward slash

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
        builder.addApi(Wearable.API);
        builder.addConnectionCallbacks(this);
        builder.addOnConnectionFailedListener(this);
        mGoogleApiClient = builder.build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected())
        {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        sendDataMapToDataLayer();
    }

    private DataMap createDataMap()
    {
        DataMap dataMap = new DataMap();
        dataMap.putLong("time", System.currentTimeMillis());
        dataMap.putString("message", "hello world");
        return dataMap;
    }


    public void sendDataMapToDataLayer()
    {
        if(mGoogleApiClient.isConnected())
        {
            DataMap dataMap = createDataMap();
            new SendDataMapToDataLayer(WEARABLE_DATA_PATH,dataMap).start();
        }
        else
        {
            Log.v(TAG, "Connection is closed");
        }

    }

    public void sendDataMapOnClick(View view)
    {
        sendDataMapToDataLayer();
    }



    public class SendDataMapToDataLayer extends  Thread
    {
        String mPath;
        DataMap mDataMap;

        public SendDataMapToDataLayer(String path, DataMap dataMap)
        {
            this.mPath = path;
            this.mDataMap = dataMap;
        }

        @Override
        public void run()
        {
            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create(WEARABLE_DATA_PATH);
            putDataMapRequest.getDataMap().putAll(mDataMap); //wrap the DataMap object inside the PutDataMapRequest
            PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();

            DataApi.DataItemResult dataItemResult = Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest).await(); // await() blocks the thread until we got the result
            if(dataItemResult.getStatus().isSuccess())
            {
                Log.v(TAG,"DataItem successfully sent");
            }
            else
            {
                Log.v(TAG,"error while sending DataItem");
            }

        }

    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}