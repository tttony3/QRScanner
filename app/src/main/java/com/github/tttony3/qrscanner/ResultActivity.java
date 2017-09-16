package com.github.tttony3.qrscanner;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.widget.TextView;


import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

public class ResultActivity extends AppCompatActivity {
    private TextView tvResult;
    private String TAG = "QRScnner";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvResult = (TextView) findViewById(R.id.tv_result);
        Intent i = getIntent();
        String result = i.getStringExtra("result");
        if(result != null)
         tvResult.setText(result);
        ArrayList<BomModel> list=  getXlsData("test.xls",0);
        Log.d(TAG,list.toString());
    }
    /**
     * 获取 excel 表格中的数据,不能在主线程中调用
     *
     * @param xlsName excel 表格的名称
     * @param index   第几张表格中的数据
     */
    private ArrayList<BomModel> getXlsData(String xlsName, int index) {
        ArrayList<BomModel> list = new ArrayList<>();
        AssetManager assetManager = getAssets();

        try {
            Workbook workbook = Workbook.getWorkbook(assetManager.open(xlsName));
            Sheet sheet = workbook.getSheet(index);

            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();

            Log.d(TAG, "the num of sheets is " + sheetNum);
            Log.d(TAG, "the name of sheet is  " + sheet.getName());
            Log.d(TAG, "total rows is 行=" + sheetRows);
            Log.d(TAG, "total cols is 列=" + sheetColumns);
            int lnum=0;
            for (int i = 0; i < sheetColumns; i++) {
               if(sheet.getCell(i, 0).getContents().equals("编号")){
                   lnum =i;
                   break;
               }
                //   mModel.setAreaNumber(sheet.getCell(2, i).getContents());


            }
            for (int i = 0; i < sheetRows; i++) {
                BomModel mModel = new BomModel();
                mModel.setNum(sheet.getCell(lnum, i).getContents());

                list.add(mModel);
            }

            workbook.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return list;
    }
}
