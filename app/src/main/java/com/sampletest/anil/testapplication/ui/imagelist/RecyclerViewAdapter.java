package com.sampletest.anil.testapplication.ui.imagelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sampletest.anil.testapplication.R;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.model.FlickerImage;

import java.util.List;

/**
 * Created by H211060 on 8/20/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder> {
    private Context mContext;
    private List<FlickerImage>mImageData;
    private IDataHandler dataHandler;

    public RecyclerViewAdapter(Context mContext, List<FlickerImage> mImageData,IDataHandler dataHandler) {
        this.mContext = mContext;
        this.mImageData = mImageData;
        this.dataHandler = dataHandler;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.cardview_item_flickr_image,parent,false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        if(mImageData != null && mImageData.size() >= position) {
            holder.tv_image_title.setText(mImageData.get(position).getTitle());
            dataHandler.updateImageFromFlickr(mContext,holder.iv_image,mImageData.get(position),R.drawable.img_1);
        }
    }

    @Override
    public int getItemCount() {
        return (mImageData == null)?0:mImageData.size();
    }

    public void appendImageItems(List<FlickerImage> mImages) {
        if(this.mImageData != null){
            this.mImageData.addAll(mImages);
        }
        else {
            this.mImageData = mImages;
        }
    }

    public void removeImages(){
        this.mImageData = null;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        TextView tv_image_title;
        ImageView iv_image;
        ProgressBar progressBar;
        public CardViewHolder(View itemView) {
            super(itemView);

            tv_image_title = itemView.findViewById(R.id.flickr_image_title);
            iv_image = itemView.findViewById(R.id.flickr_image_id);
            progressBar = itemView.findViewById(R.id.progressBarCard);
        }
    }

    public void setImageItems(List<FlickerImage> imageItems){
        this.mImageData = imageItems;
    }
}
