package com.example.QuazarApp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageShareSaveUtil {

    public static void imageDownload(Context ctx, String url, String filename) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(() -> {
                    Uri savedUri = getImageUriFor(ctx, filename, bitmap);
                    if(savedUri != null) {
                        ((Activity) ctx).runOnUiThread(() -> Toast.makeText(ctx, "Saved!", Toast.LENGTH_SHORT).show());
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {}

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        });
    }

    public static void imageShare(Context ctx, String url, String filename) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Uri uri = getImageUriFor(ctx, filename, bitmap);
                if(uri != null) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("image/*");
                    i.putExtra(Intent.EXTRA_STREAM, uri);
                    ctx.startActivity(Intent.createChooser(i, "Share Image"));
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {}
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        });
    }

    private static Uri getImageUriFor(Context ctx, String filename, Bitmap bmp) {
        OutputStream os;
        Uri uri;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

                uri = ctx.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                os = ctx.getContentResolver().openOutputStream(uri);
            } else {
                String imagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                File saveFile = new File(imagePath, filename);
                uri = Uri.fromFile(saveFile);
                os = new FileOutputStream(saveFile);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
            ((Activity) ctx).runOnUiThread(() -> Toast.makeText(ctx, "Could not save image", Toast.LENGTH_SHORT).show());
            return null;
        }

        if(os != null) {
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, os);
            try {
                os.flush();
                os.close();
                return uri;
            } catch (IOException e) {
                e.printStackTrace();
                ((Activity) ctx).runOnUiThread(() -> Toast.makeText(ctx, "Error writing image", Toast.LENGTH_SHORT).show());
            }
        }
        return null;
    }
}