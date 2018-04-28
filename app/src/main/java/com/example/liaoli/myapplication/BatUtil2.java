package com.example.liaoli.myapplication;

import android.support.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by liaoli on 2018/4/22.
 */

public class BatUtil2 {

    public static String desFile = "D:\\Android\\xuihui\\20180126\\MyApplication\\app\\AQ.txt";
//    public static String desFile = "C:\\Users\\liaoli\\Desktop\\upgrade.txt";
    private static String path ="E:\\xinhui\\workSpace\\wawajiserver\\tools\\bat\\wawaji\\curl.bat" ;
    private static String filePath = "D:\\Android\\xuihui\\20180126\\MyApplication\\app\\AQ.html" ;

    public static void main(String[] args){

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;

        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;

        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {

            File file = new File(filePath);

            if(!file.exists()){
                file.createNewFile();
            }

            fileInputStream =  new FileInputStream(desFile);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            fileOutputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            String line = null;

            String deviceno = null;
            while ((line = bufferedReader.readLine())!= null){
                String[] ss = line.split(",");
                for(String s : ss){
                    if(s.contains("deviceno")){
                        String[] sss = s.split(":");
                        deviceno = sss[1];
                        continue;
                    }
                    if(s.contains("device")){
                        String[] sss = s.split(":");
                        String device = sss[1];
                        //<a href="https://ali1-ctl.xinhuifun.cn/device/ctrl?serial=32e0709404a6&cmd_type=1&channel=0&data=http://xinhui-st-tx.boomegg.cn/apk/wawaji_tuiliu/wawaji_previewXuebao_release_v1.2.0.12_201804261040.apk">刷新</a>
//                        String  curl = "https://ali1-ctl.xinhuifun.cn/device/ctrl?serial=" + device +"&cmd_type=1&channel=0&data=http://xinhui-st-tx.boomegg.cn/apk/wawaji_tuiliu/wawaji_previewXuebao_release_v1.2.0.11_201804270007.apk";
//
                        String  curl = "https://ali1-ctl.xinhuifun.cn/device/ctrl?serial=" + device +"&cmd_type=1&channel=1&data=http://xinhui-st-tx.boomegg.cn/apk/wawaji_tuiliu/sign_wawaji_companyXuebao_release_v1.2.0.11_201804270026.apk";

                        curl = "<a href=\""+ curl + "\">"+deviceno+"</a>";

                        bufferedWriter.write(curl);
                        bufferedWriter.newLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Nullable
    private static String ip(String line, String deviceno) throws IOException {
        String ip;
        String[] ss = line.split(",");
        for(String s : ss){
            if(s.contains("deviceno")){
                String[] sss = s.split(":");
                deviceno = sss[1];
            }
            if(s.contains("ip")){
                String[] sss = s.split(":");
                ip = sss[1];
                CreateBatFile(deviceno,ip);
                 deviceno = null;
                 ip = null;
            }

        }
        return deviceno;
    }

    private static void CreateBatFile(String deviceno, String ip) throws IOException {
        File file = new File(path+File.separator + deviceno+".bat");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
       // String data = "ECHO\nadb connect "+ ip +"\nadb reboot"; //重启
        String data = "ECHO\nadb connect "+ ip + "\nadb install -r wawaji_previewXuebao_release_v1.2.0.12_201804261040.apk" +"\nadb shell am  startservice -n com.xinhuitech.xhdollmachineserver/com.xinhuitech.xhdollmachineserver.MainActivity" +"\nadb disconnect";
        bufferedWriter.write(data);
        bufferedWriter.close();

    }
}
