package com.ultimahora.persistencia;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.ultimahora.MainActivity;
import com.ultimahora.R;
import com.ultimahora.Visualizar;
import com.ultimahora.modelo.Articles;
import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    private OnItemClickListener onItemClickListener;
    private List<Articles> articlesList;
    private LayoutInflater inflater;
    private ApiRequest apiRequest;

    public Adaptador(Context context, List<Articles> articles){
        this.inflater = LayoutInflater.from(context);
        this.articlesList = articles;
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = inflater.inflate(R.layout.items, parent, false);
        return new ViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holders, int position) {
        final ViewHolder holder = holders;
        holder.title.setText(articlesList.get(position).getTitle());
        holder.description.setText(articlesList.get(position).getDescription());
        Picasso.get().load(articlesList.get(position).getUrlToImage()).into(holder.thumbnail);
        holder.published.setText("\u2022" + apiRequest.DateFormat(articlesList.get(position).getPublished()) );
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        MainActivity mainActivity;
        OnItemClickListener onItemClickListener;
        TextView title, description, published;
        ImageView thumbnail;


        public ViewHolder(@NonNull final View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            published= itemView.findViewById(R.id.published);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            description = itemView.findViewById(R.id.description);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), Visualizar.class);
            intent.putExtra("url", articlesList.get(getAdapterPosition()).getUrl());
            v.getContext().startActivity(intent);
        }
    }

}
