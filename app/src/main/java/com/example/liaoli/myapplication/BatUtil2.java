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

    public static String desFile = "E:\\xinhui\\workSpace\\wawajiapp\\20180420\\BAT\\app\\bat_ip.txt";
//    public static String desFile = "C:\\Users\\liaoli\\Desktop\\upgrade.txt";
    private static String path ="E:\\xinhui\\workSpace\\wawajiserver\\tools\\bat\\wawaji\\curl.bat" ;
    private static String filePath = "E:\\xinhui\\workSpace\\wawajiserver\\tools\\bat\\wawaji\\curl.bat" ;

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

            while ((line = bufferedReader.readLine())!= null){
                String[] ss = line.split(",");
                for(String s : ss){
                    if(s.contains("device")){
                        String[] sss = s.split(":");
                        String device = sss[1];
                        String  curl = "curl https://ali1-ctl.xinhuifun.cn/device/ctrl?serial=" + device +"&cmd_type=0&channel=0&data=http://xinhui-st-tx.boomegg.cn/apk/wawaji_company_release_v1.0.10_201712271112.apk";
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
        String data = "ECHO\nadb connect "+ ip + "\nadb install -r wawaji_companyXuebao_release_v1.2.0.12_201804241751.apk" +"\nadb shell am  startservice -n com.xinhuitech.xhdollmachineserver/com.xinhuitech.xhdollmachineserver.MainActivity" +"\nadb disconnect";
        bufferedWriter.write(data);
        bufferedWriter.close();

    }
}
