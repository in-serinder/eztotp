package com.example.eztotp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private TextView unixtimeprint_text,code_part,pwtt,update_time_tole;   //textview

    private SeekBar update_sk;
    private String pwt="1234567812345678";
    private Handler handler;
    private Button updata_btn;
    private EditText input_editText;
    private ListView pwtview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        code_part=findViewById(R.id.codeprt);
        code_part.setText("Code:\t"+totop.totopgen(pwt));

        unixtimeprint_text=findViewById(R.id.unixtxprint);
        unixtimeprint_text.setText(unix_time.unix_time_str());

        pwtt=findViewById(R.id.pwt);
        pwtt.setText("PWT:"+pwt);

        update_sk=findViewById(R.id.tptpsk);
        update_sk.setMax(30);

        update_time_tole=findViewById(R.id.nexts);


        update(code_part,update_sk,unixtimeprint_text);


//        view pwt list
        pwtview=findViewById(R.id.pwtlist_view);
        ArrayList<String> itemlist=new ArrayList<>();

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,itemlist);
        //btn
        updata_btn=findViewById(R.id.updatebtn);
        input_editText=findViewById(R.id.new_pwt_input);
        updata_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemlist.add(String.valueOf(input_editText.getText()));

                pwtview.setAdapter(adapter);
            }
        });

    }


    private void update(TextView code, SeekBar seekBar,TextView unixt) {
        totop.startRefreshTask(pwt);
        handler = new Handler(Looper.getMainLooper());
        final int[] progress = {30 - (int) totop.getTimeUntilNextRefresh(30)};
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                unixt.setText(unix_time.unix_time_str());


                long timeUntilRefresh = totop.getTimeUntilNextRefresh(30);
                update_time_tole.setText("距离下次刷新:" + timeUntilRefresh);
                progress[0] =30-(int)timeUntilRefresh;
                if (progress[0]<0){
                    progress[0]=0;
                } else if (progress[0]>30) {
                    progress[0]=30;

                }
                seekBar.setProgress(progress[0]);
              if (progress[0]==0){
                  code.setText("Code:\t"+totop.totopgen(pwt));
               }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
