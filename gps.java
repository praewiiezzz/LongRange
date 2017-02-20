package com.example.chuti.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import android.location.Location;



import java.util.Objects;

/**
 * Created by chuti on 2/20/2017.
 */
public abstract class gps extends Sec implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

   /* Location loc1 = new Location("point A");
    loc1.setLatitude(111.00);
    loc1.setLongitude(lon1);

    Location loc2 = new Location("");
    loc2.setLatitude(lat2);
    loc2.setLongitude(lon2);

    float distanceInMeters = loc1.distanceTo(loc2);*/
}
