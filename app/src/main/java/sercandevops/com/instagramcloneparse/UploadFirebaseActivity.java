package sercandevops.com.instagramcloneparse;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class UploadFirebaseActivity extends AppCompatActivity {

    ImageView imageView;
    EditText ed_comment;
    Bitmap bitmapImage;
    Uri uri;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    StorageReference mStorageRef;

    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadfirebase);

        ed_comment = (EditText)findViewById(R.id.ed_commentf);
        imageView = (ImageView)findViewById(R.id.img_postImagef);

    firebaseDatabase = FirebaseDatabase.getInstance();
    myRef = firebaseDatabase.getReference();
    mAuth = FirebaseAuth.getInstance();
    firebaseUser = mAuth.getCurrentUser();
    mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    public void UploadPostFirebase(View view) {

        final String commentext = ed_comment.getText().toString();

        final UUID uuid = UUID.randomUUID();
        final String imageName = "resimler/"+uuid.toString()+".jpg";

        StorageReference storageReference1 = mStorageRef.child(imageName);
        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //DWLONAD URL
                StorageReference newReferance = FirebaseStorage.getInstance().getReference(imageName);
                newReferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String downloadURL = uri.toString();

                        UUID uuid1 = UUID.randomUUID();
                        String uuidString = uuid1.toString();

                        myRef.child("Posts").child(uuidString).child("resimURL").setValue(downloadURL);
                        myRef.child("Posts").child(uuidString).child("comment").setValue(commentext);
                        myRef.child("Posts").child(uuidString).child("useremail").setValue(firebaseUser.getEmail());


                        Intent intent = new Intent(getApplicationContext(),FeedFirebaseActivity.class);
                        startActivity(intent);

                      //  Log.i("DOWNLOAD URL : ",downloadURL);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


                //USERNAME -COMMENT SAVE


                Toast.makeText(getApplicationContext(),"başarılı KAYIT", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(),FeedFirebaseActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }



    public void chooseImageFirebase(View view) {

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
             uri = data.getData();
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
