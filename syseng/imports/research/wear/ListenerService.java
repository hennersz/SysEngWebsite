package com.example.sherl_000.weartest_dataitem;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by sherl_000 on 2015/12/1.
 */
public class ListenerService extends WearableListenerService
{
    public static final String TAG = "MyDataMap";
    public static final String WEARABLE_DATA_PATH = "/wearable/data/path";


    @Override
    public void onDataChanged(DataEventBuffer dataEvents)
    {
        DataMap dataMap;
        for(DataEvent dataEvent:dataEvents)
        {
            if(dataEvent.getType() == DataEvent.TYPE_CHANGED)
            {
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equalsIgnoreCase(WEARABLE_DATA_PATH))
                {
                    dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                    Log.v(TAG, "DataMap received on Wearable Device" + dataMap);

                    Intent startIntent = new Intent(this, MainActivity.class);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    String[] myArray = new String[2];
                    myArray[0] = String.valueOf(dataMap.getLong("time"));
                    myArray[1] = dataMap.getString("message");

                    startIntent.putExtra("dataMap", myArray);
                    startActivity(startIntent);
                }
            }
        }
    }

}
