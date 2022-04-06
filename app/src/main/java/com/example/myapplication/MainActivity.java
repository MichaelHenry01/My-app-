package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    // declaring required variables
    private Socket client;
    private PrintWriter printwriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private EditText textField;
    private TextView Screen;
    private Button Send;
    private Button Connect;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // reference to the text field
        textField = (EditText) findViewById(R.id.EditText);

        // reference to the send button
        Send = (Button) findViewById(R.id.sendbutton);
        Connect=(Button) findViewById(R.id.connectbutton);
        Screen=(TextView) findViewById(R.id.textView);

        // Button press event listener
        Send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // get the text message on the text field
                message = textField.getText().toString();

                // start the Thread to connect to server
                new Thread(new ClientThread(message)).start();

            }
        });
        Connect.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // get the text message on the text field

                // start the Thread to connect to server
                new Thread(new ConnectThread()).start();

            }
        });
    }

    // the ClientThread class performs
    // the networking operations
    class ClientThread implements Runnable {
        private final String message;

        ClientThread(String message) {
            this.message = message;
        }
        @Override
        public void run() {
            try {
                // the IP and port should be correct to have a connection established
                // Creates a stream socket and connects it to the specified port number on the named host.
                client = new Socket("172.20.10.8", 4444); // connect to server
                printwriter = new PrintWriter(client.getOutputStream(),true);
                printwriter.write(message); // write the message to output stream
                inputStreamReader = new InputStreamReader(client.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
                String rmessage = bufferedReader.readLine();
                Screen.setText(rmessage);
                printwriter.flush();
                printwriter.close();



            } catch (IOException e) {
                e.printStackTrace();
            }

            // updating the UI
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textField.setText("");
                }
            });
        }
    }


class ConnectThread implements Runnable {
    @Override
    public void run() {
        try {
            // the IP and port should be correct to have a connection established
            // Creates a stream socket and connects it to the specified port number on the named host.
            client = new Socket("10.0.0.2", 4447); // connect to server

        } catch (IOException e) {
            e.printStackTrace();
        }
        // updating the UI

    }
}}



