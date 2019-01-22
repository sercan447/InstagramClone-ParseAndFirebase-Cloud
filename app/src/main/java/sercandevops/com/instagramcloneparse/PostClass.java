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

import java.util.ArrayList;

public class PostClass extends ArrayAdapter<String> {

    private final ArrayList<String> username;
    private final ArrayList<String> usercomment;
    private final ArrayList<Bitmap> userImages;
    private final Activity context;



    public PostClass(ArrayList<String> username,ArrayList<String> userComment,ArrayList<Bitmap> userImages,Activity context)
    {
        super(context,R.layout.customview,username);

        this.username = username;
        this.usercomment = userComment;
        this.userImages = userImages;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position,  @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = this.context.getLayoutInflater();

        View customView = layoutInflater.inflate(R.layout.customview,null,true);
        TextView tv_username = customView.findViewById(R.id.tv_username);
        TextView tv_coment = customView.findViewById(R.id.tv_comment);
        ImageView img = customView.findViewById(R.id.img_gosterilenImage);

        tv_username.setText(username.get(position));
        img.setImageBitmap(userImages.get(position));
        tv_coment.setText(usercomment.get(position));

        return customView;
    }
}
