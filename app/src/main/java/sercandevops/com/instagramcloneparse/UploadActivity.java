package sercandevops.com.instagramcloneparse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadActivity extends AppCompatActivity {

    ImageView imageView;
    EditText ed_comment;
    Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        ed_comment = (EditText)findViewById(R.id.ed_comment);
        imageView = (ImageView)findViewById(R.id.img_postImage);

    }

    public void UploadPost(View view) {

        String commentext = ed_comment.getText().toString();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();


        ParseFile parseFile = new ParseFile("image.png",bytes);


        ParseObject object = new ParseObject("Posts");
        object.put("image",parseFile);
        object.put("comment",commentext);
        object.put("username",ParseUser.getCurrentUser().getUsername());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e != null)
                {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"başarılı", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);
                }
            }
        });
    }



    public void chooseImage(View view) {

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }else{
            Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

            if(requestCode == 2)
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,1);
                }
            }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            try {
                bitmapImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                imageView.setImageBitmap(bitmapImage);

                Toast.makeText(getApplicationContext(),"try catc ıcerısı",Toast.LENGTH_LONG).show();

                if(bitmapImage != null)
                {
                    Toast.makeText(getApplicationContext(),"dolu",Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(),"boş",Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
        {
            Toast.makeText(getApplicationContext(),"DATA NULL",Toast.LENGTH_LONG).show();
        }
    }
}
