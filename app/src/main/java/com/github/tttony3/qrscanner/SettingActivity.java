package com.github.tttony3.qrscanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvFile1;
    TextView tvFile2;
    TextView tvFile3;
    EditText tvModelField;
    EditText tvFie1d2;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    String file1;
    String file2;
    String file3;
    String modelField;
    String field;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sp = getPreferences(Context.MODE_PRIVATE);
        initView();
    }

    private void initView() {
        tvFile1 = (TextView) findViewById(R.id.tv_file1);
        tvFile2 = (TextView) findViewById(R.id.tv_file2);
        tvFile3 = (TextView) findViewById(R.id.tv_file3);
        tvModelField = (EditText) findViewById(R.id.editText1);
        tvFie1d2 = (EditText) findViewById(R.id.editText2);
        btn1 = (Button) findViewById(R.id.button1);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button5);
        btn5 = (Button) findViewById(R.id.button6);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        fillText();
    }

    private void fillText() {

        file1 = sp.getString("file1", "");
        file2 = sp.getString("file2", "");
        file3 = sp.getString("file3", "");
        modelField = sp.getString("modelField", "");
        field = sp.getString("field", "");
        tvFile1.setText(file1);
        tvFile2.setText(file2);
        tvFile3.setText(file3);
        tvModelField.setText(modelField);
        tvFie1d2.setText(field);
        if (file1.equals("")) {
            btn1.setText("选择");
        } else {
            btn1.setText("清除");
        }
        if (file2.equals("")) {
            btn2.setText("选择");
        } else {
            btn2.setText("清除");
        }
        if (file3.equals("")) {
            btn3.setText("选择");
        } else {
            btn3.setText("清除");
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button1:
                if (tvFile1.getText().toString().equals("")) {
                    showFileChooser(1);
                } else {
                    SharedPreferences.Editor editor1 = sp.edit();
                    editor1.putString("file1", "");
                    editor1.apply();
                    file1 = "";
                }
                break;
            case R.id.button2:
                if (tvFile2.getText().toString().equals("")) {
                    showFileChooser(2);
                } else {
                    SharedPreferences.Editor editor1 = sp.edit();
                    editor1.putString("file2", "");
                    editor1.apply();
                    file2 = "";
                }
                break;
            case R.id.button3:
                if (tvFile3.getText().toString().equals("")) {
                    showFileChooser(3);
                } else {
                    SharedPreferences.Editor editor1 = sp.edit();
                    editor1.putString("file3", "");
                    editor1.apply();
                    file3 = "";
                }
                break;
            case R.id.button5:
                SharedPreferences.Editor editor1 = sp.edit();
                editor1.putString("modelField", tvModelField.getText().toString());
                editor1.apply();
                break;
            case R.id.button6:
                SharedPreferences.Editor editor2 = sp.edit();
                editor2.putString("field", tvFie1d2.getText().toString());
                editor2.apply();
                break;
        }
        fillText();
    }


    private void showFileChooser(int code) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), code);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    file1 = getPath(this, uri);
                    SharedPreferences.Editor editor1 = sp.edit();
                    editor1.putString("file1", file1);
                    editor1.apply();

                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    file2 = getPath(this, uri);
                    SharedPreferences.Editor editor1 = sp.edit();
                    editor1.putString("file2", file2);
                    editor1.apply();

                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    String file3 = getPath(this, uri);
                    SharedPreferences.Editor editor1 = sp.edit();
                    editor1.putString("file3", file3);
                    editor1.apply();

                }
                break;
        }
        fillText();
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getPath(Context context, Uri uri) {

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
