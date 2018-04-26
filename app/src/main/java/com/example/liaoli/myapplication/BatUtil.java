package com.example.liaoli.myapplication;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by liaoli on 2018/4/22.
 */

public class BatUtil {

    public static String desFile = "E:\\xinhui\\workSpace\\wawajiapp\\20180420\\BAT\\app\\tuiliu.txt";
    //    public static String desFile = "C:\\Users\\liaoli\\Desktop\\upgrade.txt";
    private static String path = "E:\\xinhui\\workSpace\\wawajiserver\\tools\\bat\\wawaji\\xinhui";

    public static void main(String[] args) {

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;

        BufferedReader bufferedReader = null;
        try {

            fileInputStream = new FileInputStream(desFile);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line = null;
            String deviceno = null;
            String ip = null;
            while ((line = bufferedReader.readLine()) != null) {


                if (!line.contains("deviceno")) {
                    continue;
                }

                String[] ss = line.split(",");
                for (String s : ss) {
                    if (s.contains("deviceno")) {
                        String[] sss = s.split(":");
                        deviceno = sss[1];
                    }
                    if (s.contains("ip")) {
                        String[] sss = s.split(":");
                        ip = sss[1];
                        CreateBatFile(deviceno, ip);
                        deviceno = null;
                        ip = null;
                    }

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private static void CreateBatFile(String deviceno, String ip) throws IOException {
        File file = new File(path + File.separator + deviceno + ".bat");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        // String data = "ECHO\nadb connect "+ ip +"\nadb reboot"; //重启
        String data = "ECHO\nadb connect " + ip + "\nadb install -r wawaji_previewXuebao_release_v1.2.0.12_201804261040.apk" + "\nadb shell am start -n com.xinhui.upgradeapp/com.xinhui.upgradeapp.MainActivity" + "\nadb disconnect";
        bufferedWriter.write(data);
        bufferedWriter.close();

    }
}
