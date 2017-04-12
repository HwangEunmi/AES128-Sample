package com.example.cncn6.encrypdecryptionsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    /**
     * 입력창
     */
    private EditText mEtEmail;

    /**
     * 초기 입력값, 암호화된 값, 복호화된 값
     */
    private TextView mTvInput;

    private TextView mTvEncrypt;

    private TextView mTvResult;

    private boolean mIsClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtEmail = (EditText) findViewById(R.id.et_input_email);
        mTvInput = (TextView) findViewById(R.id.tv_input);
        mTvEncrypt = (TextView) findViewById(R.id.tv_encrypt);
        mTvResult = (TextView) findViewById(R.id.tv_result);

        Button btnEncrypt = (Button) findViewById(R.id.btn_encrypt);
        Button btnDecrypt = (Button) findViewById(R.id.btn_decrypt);
        Button btnClear = (Button) findViewById(R.id.btn_clear);

        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsClick == false) {
                    String inputValue = mEtEmail.getText().toString();
                    String encryptedText = AES128Helper.getInstance().encrypt(inputValue);
                    PropertyManager.getInstance(MainActivity.this).setKeyMessage(encryptedText);
                    mTvInput.setText(inputValue);
                    mTvEncrypt.setText(encryptedText);
                    mIsClick = true;
                }
            }
        });

        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsClick == true) {
                    String encryptedText = PropertyManager.getInstance(MainActivity.this).getKeyMessage();
                    String clearText = AES128Helper.getInstance().decrypt(encryptedText);
                    mTvResult.setText(clearText);
                    mIsClick = false;
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtEmail.setText(null);
                mTvInput.setText(null);
                mTvEncrypt.setText(null);
                mTvResult.setText(null);
                mIsClick = false;
            }
        });
    }
}
