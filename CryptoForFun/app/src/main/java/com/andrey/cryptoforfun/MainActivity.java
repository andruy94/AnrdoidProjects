package com.andrey.cryptoforfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView encodedTextView;
    TextView decodedTextView;
    String testText;
    TextView originalTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Original text

        testText = getString(R.string.text);
        originalTextView = (TextView) findViewById(R.id.textViewOriginal);
        originalTextView.setText("[ORIGINAL]:\n" + testText + "\n");
        findViewById(R.id.btnTest).setOnClickListener(this);
        encodedTextView = (TextView)findViewById(R.id.textViewEncoded);
        decodedTextView = (TextView)findViewById(R.id.textViewDecoded);
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        Date time=calendar.getTime();
       // long time=new Date().getTime();
        Log.d("Crypto", "Start");
        // Generate key pair for 4096-bit RSA encryption and decryption
        Key publicKey = null;
        Key privateKey = null;
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(4096);
            KeyPair kp = kpg.genKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            Log.e("Crypto", "RSA key pair error");
        }

        // Encode the original data with RSA private key
        byte[] encodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, privateKey);
            encodedBytes = c.doFinal(testText.getBytes());
        } catch (Exception e) {
            Log.e("Crypto", "RSA encryption error"+e.toString());
        }


        encodedTextView.setText("[ENCODED]:\n" +
                Base64.encodeToString(encodedBytes, Base64.DEFAULT) + "\n");

        // Decode the encoded data with RSA public key
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, publicKey);
            decodedBytes = c.doFinal(encodedBytes);
        } catch (Exception e) {
            Log.e("Crypto", "RSA decryption error"+e.toString());
        }



        decodedTextView.setText("[DECODED]:\n" + new String(decodedBytes) + "\n");
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");
        long Longtime=calendar.getTime().getTime()-time.getTime();

        Log.d("Crypto", "End");
    }
}
