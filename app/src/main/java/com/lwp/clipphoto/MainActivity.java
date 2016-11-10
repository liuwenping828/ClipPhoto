package com.lwp.clipphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.lwp.library.ClipImageLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ClipImageLayout mClipImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);

        mClipImageLayout.setClipRectSize(1080, 600);

        mClipImageLayout.setImageDrawable(getResources().getDrawable(R.drawable.a));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_clip:
                Bitmap bitmap = mClipImageLayout.clip();

                compress(bitmap, 400, 400);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                byte[] datas = baos.toByteArray();

//                int length = datas.length / 1024;


                Intent intent = new Intent(this, ShowImageActivity.class);
                Bundle b = new Bundle();
                b.putByteArray("bitmap", datas);
                intent.putExtra("bundle", b);
//                intent.putExtra("bitmap", datas);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 根据要求的宽高压缩图片
     * @param bm
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public Bitmap compress(Bitmap bm, int reqWidth, int reqHeight) {

        if (null == bm || reqHeight == 0 || reqWidth == 0){
            throw new IllegalArgumentException("Parametres is error");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        int quality = 100;
        while (baos.toByteArray().length / 1024 > 1024) {  // 小于1M
            baos.reset(); //重置baos即清空baos
            quality -= 5;
            bm.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(bais, null, options);

        int h = options.outHeight;
        int w = options.outWidth;

        int ratio = 1;   // 缩放率

        if (h > w && h > reqHeight) {
            ratio = h / reqHeight;
        } else if (w > h && w > reqWidth) {
            ratio = w / reqWidth;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = ratio;

        bais = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(bais, null, options);
//        Log.d(TAG,"width:" + bitmap.getWidth() + ", height:" + bitmap.getHeight());

    }

    /**
     * 将Bitmap压缩成size大小
     *
     * @param bitmap 压缩图片
     * @param size   单位KB
     * @return
     */
    public Bitmap compress(Bitmap bitmap, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        int quality = 100;
        while (baos.toByteArray().length / 1024 > size) {  // size大小
            baos.reset(); //重置baos即清空baos
            quality -= 5;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        }

        return BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
    }
}
