package sercandevops.com.instagramcloneparse;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {


    EditText ed_username,ed_pass;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ed_username = (EditText)findViewById(R.id.ed_username);
        ed_pass = (EditText)findViewById(R.id.ed_pass);

        //FIREBASE CLOUD CODE
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser != null)
        {
                Intent intent = new Intent(getApplicationContext(),FeedFirebaseActivity.class);
                startActivity(intent);
        }

        // PARSE CLOUD CODE
            ParseUser parseUser = ParseUser.getCurrentUser();

            if(parseUser != null)
            {
                Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                startActivity(intent);
            }


    }

    public void SignIn(View view) {
        String username = ed_username.getText().toString().trim();
        String pass = ed_pass.getText().toString().trim();

        ParseUser.logInInBackground(username, pass, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if( e != null)
                {
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(),"Welcome to "+user.getUsername(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public void signUp(View view) {

        ParseUser user = new ParseUser();
        user.setUsername(ed_username.getText().toString().trim());
        user.setPassword(ed_pass.getText().toString().trim());

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if(e != null)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Kayıt Sorunlu"+e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(getApplicationContext(),"Başarılı Kayıt",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),FeedActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void signupFirebasei(View view) {

            firebaseAuth.createUserWithEmailAndPassword(ed_username.getText().toString(),ed_pass.getText().toString())
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(),"basarıl akyıt", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),FeedFirebaseActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),"kayıtta sorun var.", Toast.LENGTH_SHORT).show();;

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"KAYIT FAILE OLDU : "+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });


    }

    public void signinFirebase(View view) {

        firebaseAuth.signInWithEmailAndPassword(ed_username.getText().toString(),ed_pass.getText().toString())
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"basarıl GİRİS FIREBASE ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),FeedFirebaseActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"kayıtta sorun var fırebase", Toast.LENGTH_SHORT).show();;

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"GIRIS FAILURE OLDU ."+e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();;
            }
        });
    }
}
