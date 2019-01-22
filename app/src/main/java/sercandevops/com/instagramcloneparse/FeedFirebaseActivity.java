package sercandevops.com.instagramcloneparse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FeedFirebaseActivity extends AppCompatActivity {

        ListView listView;
    private  ArrayList<String> username;
    private  ArrayList<String> usercomment;
     private  ArrayList<String> userImages;

    PostFirebaseClass postFirebaseClass;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedfirebase);

        listView = (ListView)findViewById(R.id.listviewf);

        username = new ArrayList<>();
        usercomment = new ArrayList<>();
        userImages = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        downloadFirebase();
        postFirebaseClass = new PostFirebaseClass(username,usercomment,userImages,FeedFirebaseActivity.this);
        listView.setAdapter(postFirebaseClass);
    }

    public void downloadFirebase()
    {
        DatabaseReference newRefernce = firebaseDatabase.getReference("Posts");
        newRefernce.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                   // Log.i("DATALAR",ds.getValue().toString());
                    HashMap<String,String> hasMap = (HashMap<String,String>)ds.getValue();

                    username.add(hasMap.get("useremail"));
                    usercomment.add(hasMap.get("comment"));
                    userImages.add(hasMap.get("resimURL"));



                    /*Log.i("DATAM",hasMap.get("useremail"));
                    Log.i("DATAM",hasMap.get("comment"));
                    Log.i("DATAM",hasMap.get("resimURL"));
*/
                }
                postFirebaseClass.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }//FUNC
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            if(item.getItemId() == R.id.logoutfirebase)
            {
                firebaseAuth.signOut();
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);

            }else if(item.getItemId() == R.id.add_postfirebase)
            {
                Intent intent = new Intent(getApplicationContext(),UploadFirebaseActivity.class);
                startActivity(intent);
            }

        return super.onOptionsItemSelected(item);
    }



}
