package cn.edu.gdmec.s07150808.toasttest;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button btn2;
    private TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn2 = (Button) findViewById(R.id.btn2);
        tv2 = (TextView) findViewById(R.id.tv2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(runnable).start();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();

            String str2 = bundle.getString("result");

            tv2.setText(str2);
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {

                Socket socket = new Socket("127.0.0.1",9000);

                InputStream is = socket.getInputStream();

                DataInputStream dis = new DataInputStream(is);

                String str = dis.readLine();

                Message message = new Message();

                Bundle bundle = new Bundle();

                bundle.putString("return", str);

                message.setData(bundle);

                handler.sendMessage(message);

                OutputStream os = socket.getOutputStream();

                PrintWriter pw = new PrintWriter(os);

                pw.println("来自于客户端，连接成功！");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

}