package com.example.cris.mytabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by cris on 27/02/17.
 */



public class AdapterSpecialist extends RecyclerView.Adapter<AdapterSpecialist.SpecialistViewHolder> {

    private ArrayList<Specialist> data;

    public AdapterSpecialist(ArrayList<Specialist> data) {
        this.data=data;
    }

    @Override
    public SpecialistViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.especialist_element_list, viewGroup, false);

        SpecialistViewHolder tvh = new SpecialistViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(SpecialistViewHolder viewHolder, int pos) {
        Specialist item = data.get(pos);

        viewHolder.bindSpecialist(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class SpecialistViewHolder extends RecyclerView.ViewHolder {

        private TextView specialistName;
        private ImageView specialistImg;

        public SpecialistViewHolder(View itemView) {
            super(itemView);

            specialistName=(TextView) itemView.findViewById(R.id.txv_specialist);
            specialistImg=(ImageView) itemView.findViewById(R.id.imv_specialist);

        }

        public void bindSpecialist(Specialist specialist){
            specialistName.setText(specialist.getName());
//            Bitmap myBitmap= BitmapFactory.decodeResource(null,specialist.getImg());
//            specialistImg.setImageBitmap(myBitmap);
            specialistImg.setImageResource(specialist.getImg());

            //Picasso.with(specialistName.getContext()).load(specialist.getImg()).resize(40, 40).into(specialistImg);

        }
    }
}
