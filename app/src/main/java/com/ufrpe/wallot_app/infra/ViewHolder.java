package com.ufrpe.wallot_app.infra;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public ClipData.Item currentTime;

    public ViewHolder(@NonNull View v) {
        super(v);
        view = v;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }

    /*@Override
    public void onBindViewHolder(ViewHolder viewHolder, int i){
        viewHolder.currentTime = item.get(i)
    }*/
}
