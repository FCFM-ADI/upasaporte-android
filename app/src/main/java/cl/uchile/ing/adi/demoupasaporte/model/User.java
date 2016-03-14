package cl.uchile.ing.adi.demoupasaporte.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String alias;
    private String img;

    public User(String alias, String img){
        this.alias = alias;
        this.img = img;
    }

    private User(Parcel in) {
        alias = in.readString();
        img = in.readString();
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(alias);
        dest.writeString(img);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    public String getAlias() {
        return alias;
    }

    public String getImg() {
        return img;
    }

}
