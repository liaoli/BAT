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

public class BatUtil3 {

    public static String desFile = "E:\\xinhui\\workSpace\\wawajiapp\\20180420\\BAT\\app\\bat_ip.txt";
//    public static String desFile = "C:\\Users\\liaoli\\Desktop\\upgrade.txt";
    private static String path ="E:\\xinhui\\workSpace\\wawajiserver\\tools\\bat\\yuancheng\\" ;
    private static String filePath = "E:\\xinhui\\workSpace\\wawajiserver\\tools\\bat\\wawaji\\curl.bat" ;

    public static void main(String[] args){

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;


        BufferedReader bufferedReader = null;
        try {
            fileInputStream =  new FileInputStream(desFile);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

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
                        String  curl = "curl \"https://ali1-ctl.xinhuifun.cn/device/ctrl?serial=" + device +"&cmd_type=1&channel=0&data=http://xinhui-st-tx.boomegg.cn/apk/wawaji_tuiliu/wawaji_previewXuebao_release_v1.2.0.12_201804261040.apk\"";
                        CreateBatFile(deviceno,curl);
                        break;
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

    private static void CreateBatFile(String deviceno, String curl) throws IOException {
        File file = new File(path+File.separator + deviceno+".bat");
        if(!file.exists()){
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
        bufferedWriter.write(curl);
        bufferedWriter.close();

    }
}
