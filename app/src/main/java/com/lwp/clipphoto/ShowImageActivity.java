package com.lwp.clipphoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;

/**
 * Created by Administrator on 2016/11/10.
 */
public class ShowImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);

        byte[] byteArray = getIntent().getBundleExtra("bundle").getByteArray("bitmap");

        ImageView imageview = (ImageView) findViewById(R.id.id_showImage);

        Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArray));
        imageview.setImageBitmap(bitmap);
    }
}
