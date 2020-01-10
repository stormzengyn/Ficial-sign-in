package com.example.air.facial_sign_in.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.air.facial_sign_in.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.annotations.Nullable;

public class ShareActivity extends AppCompatActivity {
    private ImageView imgView;
    private Intent intent;
    private TextView group_name;
    private TextView group_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        imgView = findViewById(R.id.imgcontainer);
        group_name = findViewById(R.id.group_name);
        group_id = findViewById(R.id.group_id);

        intent = getIntent();
        group_name.setText(intent.getStringExtra("mname"));
        group_id.setText(intent.getStringExtra("mid"));

        Bitmap mBitmap = createQRCodeBitmap("facial://" + intent.getStringExtra("mid"),480, 480,"UTF-8", "H", "2", Color.BLACK, Color.WHITE);
        imgView.setImageBitmap(mBitmap);
    }

    @Nullable
    public Bitmap createQRCodeBitmap(String content, int width, int height,
                                            @Nullable String character_set, @Nullable String error_correction, @Nullable String margin,
                                            @ColorInt int color_black, @ColorInt int color_white){

        /** 1.参数合法性判断 */
        if(TextUtils.isEmpty(content)){ // 字符串内容判空
            return null;
        }

        if(width < 0 || height < 0){ // 宽和高都需要>=0
            return null;
        }

        try {
            /** 2.设置二维码相关配置,生成BitMatrix(位矩阵)对象 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();

            if(!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set); // 字符转码格式设置
            }

            if(!TextUtils.isEmpty(error_correction)){
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction); // 容错级别设置
            }

            if(!TextUtils.isEmpty(margin)){
                hints.put(EncodeHintType.MARGIN, margin); // 空白边距设置
            }
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    if(bitMatrix.get(x, y)){
                        pixels[y * width + x] = color_black; // 黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white; // 白色色块像素设置
                    }
                }
            }

            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,之后返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void onClickBack(View v) {
        // TODO Auto-generated method stub
        this.finish();
    }
}