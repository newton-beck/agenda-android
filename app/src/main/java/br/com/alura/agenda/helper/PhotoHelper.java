package br.com.alura.agenda.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class PhotoHelper {

    private final ImageView imageView;

    public PhotoHelper(ImageView imageView) {
        this.imageView = imageView;
    }

    public void show(String photoPath) {
        if (photoPath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
            imageView.setImageBitmap(bitmap);
        }
    }

}
