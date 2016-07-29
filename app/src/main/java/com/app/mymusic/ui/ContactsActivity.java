package com.app.mymusic.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.app.mymusic.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactsActivity extends AppCompatActivity {


    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.tv3)
    TextView tv3;
    @BindView(R.id.tv4)
    TextView tv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                tv1.setText("22222222");
                break;
            case R.id.tv2:
                tv1.setText("22222222");
                break;
            case R.id.tv3:
                tv1.setText("22222222");
                break;
            case R.id.tv4:
                tv1.setText("22222222");
                break;
        }
    }
}
