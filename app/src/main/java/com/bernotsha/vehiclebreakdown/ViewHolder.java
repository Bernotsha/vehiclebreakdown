package com.bernotsha.vehiclebreakdown;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mview;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview=itemView;
    }
    public void setDetails(Context ctx,String Shopimage,String distance,String shopname, String shop)
    {
        TextView mtitle=mview.findViewById(R.id.posttitle);
        TextView mdistance=mview.findViewById(R.id.postdistance);
        TextView mstatus=mview.findViewById(R.id.postshopstatus);
        ImageView mimage=mview.findViewById(R.id.postimage);
        mtitle.setText(shopname);
        mdistance.setText(distance +" meters");
        mstatus.setText(shop);
        Picasso.get().load(Shopimage).into(mimage);
    }
}
