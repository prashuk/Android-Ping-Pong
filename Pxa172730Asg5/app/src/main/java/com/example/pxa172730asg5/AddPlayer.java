//
//  AddPlayer.java - java file for adding players to player list and update the listview on the first screen.
//  Project Name: PXA172730
//
//  Created by Prashuk Ajmera (PXA172730) on 4/1/2019.
//  Last Modified by Prashuk Ajmera (PXA172730) on 4/7/2019.
//  Copyright © 2019 Prashuk Ajmera. All rights reserved.
//

package com.example.pxa172730asg5;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddPlayer extends AppCompatActivity {

    public String FILE_NAME = "database.txt";

    EditText name;
    EditText score;
    EditText date;

    FileOutputStream fos = null;

    File newFile = new File(FILE_NAME);

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    Date todaysDate = new Date();
    Date gameCurrDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_player);

        getSupportActionBar().setTitle(getString(R.string.addPlayerTitle));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.playerName);
        score = findViewById(R.id.playerScore);
        date = findViewById(R.id.playerGameDate);
        String d = dateFormat.format(todaysDate);
        date.setText(d);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveBtn_pressed(View v) {
        String textName = name.getText().toString();
        String textScore = score.getText().toString();
        String textDate = date.getText().toString();

        if(textName.equals("")) {
            Toast.makeText(this, getString(R.string.enterName), Toast.LENGTH_LONG).show();
            return;
        }

        if(textScore.equals("")) {
            Toast.makeText(this, getString(R.string.enterScore), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Integer.parseInt(textScore);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.scoreCorrectForm), Toast.LENGTH_LONG).show();
            return;
        }

        if (Integer.parseInt(textScore) < 0) {
            Toast.makeText(this, getString(R.string.scoreGreaterZero), Toast.LENGTH_LONG).show();
            return;
        }

        if(textDate.equals("")) {
            Toast.makeText(this, getString(R.string.enterDate), Toast.LENGTH_LONG).show();
            return;
        }


        dateFormat.setLenient(false);

        try {
            dateFormat.parse(textDate.trim());
            gameCurrDate = dateFormat.parse(textDate);
        } catch (ParseException e) {
            Toast.makeText(this, getString(R.string.dateCorrectForm), Toast.LENGTH_LONG).show();
            return;
        }

        if(gameCurrDate.compareTo(todaysDate) > 0) {
            Toast.makeText(this, getString(R.string.dateLessTodays), Toast.LENGTH_LONG).show();
            return;
        }

        newFile.openFile();

        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            fos = openFileOutput(FILE_NAME, MODE_APPEND);

            fos.write(textName.getBytes());
            fos.write("\t".getBytes());
            fos.write(textScore.getBytes());
            fos.write("\t".getBytes());
            fos.write(textDate.getBytes());
            fos.write("\n".getBytes());

            name.getText().clear();
            score.getText().clear();
            date.getText().clear();

            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
