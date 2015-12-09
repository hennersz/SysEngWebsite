package com.example.sherl_000.weartest_dataitem;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
               String[] dataMap = getIntent().getStringArrayExtra("dataMap");
                if(dataMap != null)
                {
                    StringBuilder builder = new StringBuilder();
                    for(String s : dataMap)
                    {
                        builder.append(s+",");
                    }
                    mTextView.setText(builder.toString());
                }
                else
                {
                    mTextView.setText("Waiting for the dataMap");
                }
            }
        });
    }
}
