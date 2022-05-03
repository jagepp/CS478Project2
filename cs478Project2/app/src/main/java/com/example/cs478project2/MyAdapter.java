package com.example.cs478project2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import static java.security.AccessController.getContext;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<String> nameList;
    private int[] imageIDs;
    private RVClickListener RVlistener;
    private Context context;
    public Resources res;

    public MyAdapter(ArrayList<String> list, RVClickListener listener){
        nameList = list;
        this.RVlistener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        res = parent.getResources();
        imageIDs = new int[]{
                R.drawable.thor_ragnarok,
                R.drawable.deadpool,
                R.drawable.infinity_war,
                R.drawable.endgame,
                R.drawable.guardians,
                R.drawable.return_of_the_jedi,
                R.drawable.insidious,
                R.drawable.pet_sematary
        };
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<String> directorList = new ArrayList<>();
        directorList.addAll(Arrays.asList(res.getStringArray(R.array.directorNames)));

        holder.name.setText(nameList.get(position)+ "\n" + directorList.get(position));
        int ImageId = imageIDs[position];
        holder.image.setImageDrawable(res.getDrawable(ImageId));
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        public TextView name;
        public ImageView image;
        private RVClickListener listener;
        private View itemView;

        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this);
            this.listener = passedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
            Log.i("ON_CLICK","short click");
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.context_menu,menu);
            menu.getItem(0).setOnMenuItemClickListener(openVideo);
            menu.getItem(1).setOnMenuItemClickListener(openIMDb);
            menu.getItem(2).setOnMenuItemClickListener(openWiki);
        }

        private final MenuItem.OnMenuItemClickListener openVideo = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("ON_CLICK","Opening Trailer to Movie");

                int position = getAdapterPosition();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                ArrayList<String> urlList = new ArrayList<>();
                urlList.addAll(Arrays.asList(res.getStringArray(R.array.urlsToVideo)));
                intent.setData(Uri.parse(urlList.get(position)));
                itemView.getContext().startActivity(intent);
                return true;
            }
        };

        private final MenuItem.OnMenuItemClickListener openIMDb = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("ON_CLICK","Opening Movie IMDb");

                int position = getAdapterPosition();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                ArrayList<String> urlList = new ArrayList<>();
                urlList.addAll(Arrays.asList(res.getStringArray(R.array.urlsToMovieIMDb)));
                intent.setData(Uri.parse(urlList.get(position)));
                itemView.getContext().startActivity(intent);
                return true;
            }
        };

        private final MenuItem.OnMenuItemClickListener openWiki = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("ON_CLICK","Opening Director's Wiki");

                int position = getAdapterPosition();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                ArrayList<String> urlList = new ArrayList<>();
                urlList.addAll(Arrays.asList(res.getStringArray(R.array.urlsToDirectorWiki)));
                intent.setData(Uri.parse(urlList.get(position)));
                itemView.getContext().startActivity(intent);
                return true;
            }
        };


    }
}
