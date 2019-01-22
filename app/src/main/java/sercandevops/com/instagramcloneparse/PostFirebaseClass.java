package sercandevops.com.instagramcloneparse;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostFirebaseClass extends ArrayAdapter<String> {

    private final ArrayList<String> username;
    private final ArrayList<String> usercomment;
    private final ArrayList<String> userImages;
    private final Activity context;



    public PostFirebaseClass(ArrayList<String> username, ArrayList<String> userComment, ArrayList<String> userImages, Activity context)
    {
        super(context,R.layout.customviewfirebase,username);

        this.username = username;
        this.usercomment = userComment;
        this.userImages = userImages;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = this.context.getLayoutInflater();

        View customView = layoutInflater.inflate(R.layout.customviewfirebase,null,true);
        TextView tv_username = customView.findViewById(R.id.tv_usernamef);
        TextView tv_coment = customView.findViewById(R.id.tv_commentf);
        ImageView img = customView.findViewById(R.id.img_gosterilenImagef);

        tv_username.setText(username.get(position));
        tv_coment.setText(usercomment.get(position));

        Picasso.get().load(userImages.get(position)).into(img);


        return customView;
    }
}
