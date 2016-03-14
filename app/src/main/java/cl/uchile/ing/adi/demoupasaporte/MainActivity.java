package cl.uchile.ing.adi.demoupasaporte;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cl.uchile.ing.adi.demoupasaporte.model.User;

public class MainActivity extends AppCompatActivity {
    private User user;
    private TextView userName;
    private ImageView userImage;
    private TextView userSessId;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.userName = (TextView) this.findViewById(R.id.username);
        this.userImage = (ImageView) this.findViewById(R.id.userimage);
        this.userSessId = (TextView) this.findViewById(R.id.sess_id);

    }

    @Override protected void onStart() {
        super.onStart();
        if(user==null){
            Intent i = new Intent(this, UPasaporteActivity.class);
            startActivityForResult(i, 1);
        }
        else populateView();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) user = data.getParcelableExtra("user");
    }

    private void populateView(){
        this.userName.setText(user.getAlias());
        this.userSessId.setText(user.getSessId());
        new AsyncTask<User, Void, Bitmap>(){
            @Override protected Bitmap doInBackground(User... params) {
                Bitmap img = null;
                try {
                    URL newurl = new URL(user.getImg());
                    img = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return img;
            }
            @Override protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                MainActivity.this.userImage.setImageBitmap(bitmap);
            }
        }.execute(this.user);
    }
}
