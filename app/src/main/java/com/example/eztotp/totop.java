package com.example.eztotp;
import java.nio.ByteBuffer;
import java.util.Date;
import android.os.Handler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class totop {

    private static final String HMAC_ALGORITHM = "HmacSHA1";
    private static final int DIGITS = 6;
    private static final int TIME_STEP_SECONDS = 30;

    public static String totopgen(String secret){
        String cade="";
        try{
            byte[] decodeserct=Base64.decode(secret,Base64.DEFAULT);

            long counter= new Date().getTime()/1000/TIME_STEP_SECONDS;
            byte[] conter_bytes=ByteBuffer.allocate(8).putLong(counter).array();
            SecretKeySpec keySpec=new SecretKeySpec(decodeserct,HMAC_ALGORITHM);
            Mac hmac=Mac.getInstance(HMAC_ALGORITHM);
            hmac.init(keySpec);

            byte[] hash=hmac.doFinal(conter_bytes);
            int offset=hash[hash.length-1]&0xF;
            int turncatehash=((hash[offset]&0x7F)<<24)
                    |((hash[offset+1]&0xFF)<<16)
                    |((hash[offset+2]&0xFF)<<8)
                    |(hash[offset+3]&0xFF);

            int otp=turncatehash%(int) Math.pow(10,DIGITS);
            String otpString=String.valueOf(otp);
            while (otpString.length()<DIGITS){
                otpString="0"+otpString;
            }
            cade=otpString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return cade;
    }


    public static long getTimeUntilNextRefresh(int i) {
        long currentTime = System.currentTimeMillis() / 1000;
        long nextRefreshTime = (currentTime / 30 + 1) * 30;
        return nextRefreshTime - currentTime;
    }


    private static Handler handler;
    private static Runnable runnable;
    public static void startRefreshTask(String secret) {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                String otp = totopgen(secret);

                // 输出当前验证码
                System.out.println("当前验证码：" + otp);

                // 获取下次刷新时间的剩余秒数
                long timeUntilRefresh = getTimeUntilNextRefresh(30);

                if (timeUntilRefresh > 0) {
                    // 若剩余秒数大于0，则继续定时刷新
                    handler.postDelayed(this, timeUntilRefresh * 1000);
                } else {
                    // 若剩余秒数为0，则立即刷新
                    handler.post(this);
                }
            }
        };

        // 启动定时刷新任务
        handler.post(runnable);
    }
    private static long getCurremtCounter(){
        long epochsecouds=new Date().getTime()/1000;
        return epochsecouds/TIME_STEP_SECONDS;
    }

}
