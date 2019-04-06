//
//  MainActivity.java - java file for first screen which show list of all the player in descending order.
//  Project Name: PXA172730
//
//  Created by Prashuk Ajmera (PXA172730) on 4/1/2019.
//  Last Modified by Prashuk Ajmera (PXA172730) on 4/7/2019.
//  Copyright © 2019 Prashuk Ajmera. All rights reserved.
//

package com.example.pxa172730asg5;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String FILE_NAME = "database.txt";

    ListView listOfPlayers;

    InputStream isc;
    BufferedReader brc;
    InputStream isl;
    BufferedReader brl;

    String[] playerListAdd;
    List<String> sortPlayerList = new ArrayList<>();
    int lines = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getString(R.string.playerListTitle));

        loadData();

        listOfPlayers = findViewById(R.id.playerList);

        CustomAdoptor customAdoptor = new CustomAdoptor();
        listOfPlayers.setAdapter(customAdoptor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_player) {
            openAddPlayerActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openAddPlayerActivity() {
        Intent intent = new Intent(this, AddPlayer.class);
        startActivity(intent);
    }

    public void loadData() {

        try {
            isc = openFileInput(FILE_NAME);

            if ( isc != null ) {
                InputStreamReader inputStreamReaderC = new InputStreamReader(isc);
                brc = new BufferedReader(inputStreamReaderC);

                while (brc.readLine() != null) {
                    lines++;
                }

                isc.close();
            }
        }
        catch (Exception e) { }

        try {
            isl = openFileInput(FILE_NAME);

            if ( isl != null ) {
                InputStreamReader inputStreamReaderL = new InputStreamReader(isl);
                brl = new BufferedReader(inputStreamReaderL);

                int i = 0;
                playerListAdd = new String[lines];

                while ( i < lines ) {
                    sortPlayerList.add(brl.readLine());
                    i++;
                }

                sortArray(sortPlayerList);

                isl.close();
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
    }

    public void sortArray(List<String> str) {
        Collections.sort(str, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o2) - extractInt(o1);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\t[0-9-]*$", "");
                String num1 = num.replaceAll("^[a-zA-Z\\s]*\\t", "");
                return num1.isEmpty() ? 0 : Integer.parseInt(num1);
            }
        });
    }

    class CustomAdoptor extends BaseAdapter {

        @Override
        public int getCount() {
            if(playerListAdd != null) {
                return playerListAdd.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.player_list_cell,null);

            TextView textView_name = (TextView)convertView.findViewById(R.id.textView_cus_name);
            TextView textView_score = (TextView)convertView.findViewById(R.id.textView_cus_score);
            TextView textView_date = (TextView)convertView.findViewById(R.id.textView_cus_date);

            String[] data = sortPlayerList.get(position).split("\t");
            textView_name.setText(data[0]);
            textView_score.setText("Score:   " + data[1]);
            textView_date.setText(data[2]);

            return convertView;
        }
    }
}
