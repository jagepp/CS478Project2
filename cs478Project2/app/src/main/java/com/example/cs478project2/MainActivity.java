package com.example.cs478project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Intent.ACTION_VIEW;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> movieList;
    ArrayList<String> urlList;
    RecyclerView nameView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameView = (RecyclerView) findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        Resources res = getResources();
        movieList.addAll(Arrays.asList(res.getStringArray(R.array.movieNames)));

        RVClickListener listener = (view,position)->{
            TextView name = (TextView) view.findViewById(R.id.textView);
            Toast.makeText(this, name.getText(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Resources r = getResources();
            urlList = new ArrayList<>();
            urlList.addAll(Arrays.asList(r.getStringArray(R.array.urlsToVideo)));
            intent.setData(Uri.parse(urlList.get(position)));
            startActivity(intent);
        };

        MyAdapter adapter = new MyAdapter(movieList, listener);
        nameView.setHasFixedSize(true);
        nameView.setAdapter(adapter);
        layoutManager = new GridLayoutManager(this,2);
        nameView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.gridView:
                if(layoutManager instanceof LinearLayoutManager){
                    layoutManager = new GridLayoutManager(this,2);
                    nameView.setLayoutManager(layoutManager);
                    Log.i("ON_CLICK","Grid button clicked, changed to grid view.");
                }
                else{
                    //do nothing, already a gridview
                    Log.i("ON_CLICK","Grid button clicked, but already grid view: nothing changed.");
                }
                break;
            case R.id.listView:
                if(layoutManager instanceof GridLayoutManager){
                    layoutManager = new LinearLayoutManager(this);
                    nameView.setLayoutManager(layoutManager);
                    Log.i("ON_CLICK","List button clicked, changed to list view.");
                }
                else{
                    //do nothing, already a list view
                    Log.i("ON_CLICK","List button clicked, but already list view: nothing changed.");
                }
                break;

            default:
                return false;
        }
        return true;
    }

    public void changeToGridView(View v){
        if(layoutManager instanceof LinearLayoutManager){
            layoutManager = new GridLayoutManager(this,2);
            nameView.setLayoutManager(layoutManager);
            Log.i("ON_CLICK","Grid button clicked, changed to grid view.");
        }
        else{
            //do nothing, already a gridview
            Log.i("ON_CLICK","Grid button clicked, but already grid view: nothing changed.");
        }
    }

    public void changeToLinearView(View v){
        if(layoutManager instanceof GridLayoutManager){
            layoutManager = new LinearLayoutManager(this);
            nameView.setLayoutManager(layoutManager);
            Log.i("ON_CLICK","List button clicked, changed to list view.");
        }
        else{
            //do nothing, already a list view
            Log.i("ON_CLICK","List button clicked, but already list view: nothing changed.");
        }
    }
}