package com.github.tttony3.qrscanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import jxl.Sheet;
import jxl.Workbook;

public class ResultActivity extends AppCompatActivity {
    ArrayList<BomModel> bomList = new ArrayList<>();
    ArrayList<BomModel> bomAList = new ArrayList<>();
    ArrayList<BomModel> bomBList = new ArrayList<>();
    private EditText tvResult;
    private String TAG = "QRScnner";
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;

    private ScrollView sv1;
    private ScrollView sv2;
    private ScrollView sv3;
    private ScrollView sv4;
    private Button btn;
    String result;
    SharedPreferences sp;
    private String file1;
    private String file2;
    private String file3;
    private String modelField;
    private String field;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvResult = (EditText) findViewById(R.id.tv_result);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        sv1 = (ScrollView) findViewById(R.id.scrollView4);
        sv2 = (ScrollView) findViewById(R.id.scrollView3);
        sv3 = (ScrollView) findViewById(R.id.scrollView2);
        sv4 = (ScrollView) findViewById(R.id.scrollView1);
        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = tvResult.getText().toString().toUpperCase();
                if (result != null && result.length() > 0)
                    search();
            }
        });

        sp = getPreferences(Context.MODE_PRIVATE);
        file1 = sp.getString("file1", "");
        file2 = sp.getString("file2", "");
        file3 = sp.getString("file3", "");
        modelField = sp.getString("modelField", "");
        field = sp.getString("field", "");

        bomList.addAll(getXlsData("bom.xls", 0));
        bomAList.addAll(getXlsData("boma.xls", 0));
        bomBList.addAll(getXlsData("bomb.xls", 0));

        Intent intent = getIntent();
        result = intent.getStringExtra("result");
        if (result != null) {
            result = result.toUpperCase();
            tvResult.setText(result);
            search();
        }



    }

    private void search() {
        tv2.setText("");
        tv3.setText("");
        tv4.setText("");
        tv5.setText("");
        for (BomModel model : bomList) {
            for (int i = 0; i < result.length() && i < 4; i++) {
                if (model.getModel().contains(result.substring(i, result.length()))) {
                    if (model.getAt() != null) {
                        sv1.setVisibility(View.VISIBLE);
                        tv2.append(model.getModel() + "\n" + " AT:" + "\n" + model.getAt() + "\n" + "--------------------------------------------------\n");
                    }
                    if (model.getSt() != null) {
                        sv2.setVisibility(View.VISIBLE);
                        tv3.append(model.getModel() + "\n" + " ST:" + "\n" + model.getSt() + "\n" + "--------------------------------------------------\n");
                    }
                    break;
                }
            }
        }
        for (BomModel model : bomAList) {
            for (int i = 0; i < result.length() && i < 4; i++) {
                if (model.getModel().contains(result.substring(i, result.length()))) {
                    sv3.setVisibility(View.VISIBLE);
                    tv4.append(model.getModel() + "\n" + " 核心板A面:" + "\n" + model.getPosi() + "\n" + "--------------------------------------------------\n");
                    break;
                }
            }
        }
        for (BomModel model : bomBList) {
            for (int i = 0; i < result.length() && i < 4; i++) {
                if (model.getModel().contains(result.substring(i, result.length()))) {
                    sv4.setVisibility(View.VISIBLE);
                    tv5.append(model.getModel() + "\n" + " 核心板B面:" + "\n" + model.getPosi() + "\n" + "--------------------------------------------------\n");
                    break;
                }
            }
        }
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

//            Log.d(TAG, "the num of sheets is " + sheetNum);
//            Log.d(TAG, "the name of sheet is  " + sheet.getName());
//            Log.d(TAG, "total rows is 行=" + sheetRows);
//            Log.d(TAG, "total cols is 列=" + sheetColumns);
            int modelIndex = -1;
            int atnumIndex = -1;
            int stnumIndex = -1;
            int atIndex = -1;
            int stIndex = -1;
            int posiIndex = -1;
            int numIndex = -1;
            for (int i = 0; i < sheetColumns; i++) {
                if (sheet.getCell(i, 0).getContents().equals("在库规格型号") || sheet.getCell(i, 0).getContents().equals("zhi")) {
                    modelIndex = i;
                } else if (sheet.getCell(i, 0).getContents().equals("at_num")) {
                    atnumIndex = i;
                } else if (sheet.getCell(i, 0).getContents().equals("st_num")) {
                    stnumIndex = i;
                } else if (sheet.getCell(i, 0).getContents().toUpperCase().equals("AT")) {
                    atIndex = i;
                } else if (sheet.getCell(i, 0).getContents().toUpperCase().equals("ST")) {
                    stIndex = i;
                } else if (sheet.getCell(i, 0).getContents().equals("posi")) {
                    posiIndex = i;
                } else if (sheet.getCell(i, 0).getContents().equals("num")) {
                    numIndex = i;
                }
                //   mModel.setAreaNumber(sheet.getCell(2, i).getContents());


            }
            for (int i = 0; i < sheetRows; i++) {
                BomModel mModel = new BomModel();
                if (modelIndex != -1)
                    mModel.setModel(sheet.getCell(modelIndex, i).getContents().toUpperCase());
                if (atnumIndex != -1)
                    mModel.setAtnum(sheet.getCell(atnumIndex, i).getContents());
                if (stnumIndex != -1)
                    mModel.setStnum(sheet.getCell(stnumIndex, i).getContents());
                if (atIndex != -1)
                    mModel.setAt(sheet.getCell(atIndex, i).getContents());
                if (stIndex != -1)
                    mModel.setSt(sheet.getCell(stIndex, i).getContents());
                if (numIndex != -1)
                    mModel.setNum(sheet.getCell(numIndex, i).getContents());
                if (posiIndex != -1)
                    mModel.setPosi(sheet.getCell(posiIndex, i).getContents());
                list.add(mModel);
            }

            workbook.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return list;
    }

    private ArrayList<BomModel> getXlsDataFromFile(String filename, int index) {
        File file = new File(filename);
        if (!file.exists())
            return null;
        ArrayList<BomModel> list = new ArrayList<>();
        InputStream in;
        try {
            in = new FileInputStream(file);
            Workbook workbook = Workbook.getWorkbook(in);
            Sheet sheet = workbook.getSheet(index);

            int sheetNum = workbook.getNumberOfSheets();
            int sheetRows = sheet.getRows();
            int sheetColumns = sheet.getColumns();

//            Log.d(TAG, "the num of sheets is " + sheetNum);
//            Log.d(TAG, "the name of sheet is  " + sheet.getName());
//            Log.d(TAG, "total rows is 行=" + sheetRows);
//            Log.d(TAG, "total cols is 列=" + sheetColumns);
            int modelIndex = -1;
            int atnumIndex = -1;
            int stnumIndex = -1;
            int atIndex = -1;
            int stIndex = -1;
            int posiIndex = -1;
            int numIndex = -1;
            for (int i = 0; i < sheetColumns; i++) {
                String[] ss = modelField.split(",");
                for (String s : ss) {
                    if (sheet.getCell(i, 0).getContents().equals(s)) {
                        modelIndex = i;
                    }
                }
                if (sheet.getCell(i, 0).getContents().equals("at_num")) {
                    atnumIndex = i;
                } else if (sheet.getCell(i, 0).getContents().equals("st_num")) {
                    stnumIndex = i;
                } else if (sheet.getCell(i, 0).getContents().toUpperCase().equals("AT")) {
                    atIndex = i;
                } else if (sheet.getCell(i, 0).getContents().toUpperCase().equals("ST")) {
                    stIndex = i;
                } else if (sheet.getCell(i, 0).getContents().equals("posi")) {
                    posiIndex = i;
                } else if (sheet.getCell(i, 0).getContents().equals("num")) {
                    numIndex = i;
                }
                //   mModel.setAreaNumber(sheet.getCell(2, i).getContents());


            }
            for (int i = 0; i < sheetRows; i++) {
                BomModel mModel = new BomModel();
                if (modelIndex != -1)
                    mModel.setModel(sheet.getCell(modelIndex, i).getContents().toUpperCase());
                if (atnumIndex != -1)
                    mModel.setAtnum(sheet.getCell(atnumIndex, i).getContents());
                if (stnumIndex != -1)
                    mModel.setStnum(sheet.getCell(stnumIndex, i).getContents());
                if (atIndex != -1)
                    mModel.setAt(sheet.getCell(atIndex, i).getContents());
                if (stIndex != -1)
                    mModel.setSt(sheet.getCell(stIndex, i).getContents());
                if (numIndex != -1)
                    mModel.setNum(sheet.getCell(numIndex, i).getContents());
                if (posiIndex != -1)
                    mModel.setPosi(sheet.getCell(posiIndex, i).getContents());
                list.add(mModel);
            }

            workbook.close();

        } catch (Exception e) {
            Log.e(TAG, "read error=" + e, e);
        }

        return list;
    }

}
