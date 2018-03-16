package com.promise.pdfreader.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.promise.pdfreader.R;
import com.promise.pdfreader.entities.PdfInfoEntity;
import com.promise.pdfreader.uitils.DateFormatUtil;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: liulei
 * @date:2018/3/16
 */
public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.PdfViewHolder> {

    private Context context;
    private List<PdfInfoEntity> pdfs;
    private OnItemClickListener onItemClickListener;

    public PdfAdapter(Context context, List<PdfInfoEntity> pdfs) {
        this.context = context;
        this.pdfs = pdfs;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setPdfs(List<PdfInfoEntity> pdfs) {
        this.pdfs = pdfs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PdfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_pdf_info,parent,false);


        return new PdfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfViewHolder holder, final int position) {

        final PdfInfoEntity pdfInfoEntity = pdfs.get(position);

        holder.title.setText(pdfInfoEntity.getFileName());
        Date updateTime = pdfInfoEntity.getUpdateTime();

        holder.update.setText(DateFormatUtil.formateDate(updateTime));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(pdfInfoEntity,position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemLongClick(pdfInfoEntity,position);
                }
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return pdfs.size();
    }

    class PdfViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.pdf_cover)
        ImageView cover;
        @BindView(R.id.pdf_name)
        TextView title;
        @BindView(R.id.pdf_update)
        TextView update;
        View itemView;



        public PdfViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{

        void onItemClick(PdfInfoEntity pdfInfoEntity,int position);
        void onItemLongClick(PdfInfoEntity pdfInfoEntity,int position);

    }
}
