package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class AddPlayerActivity extends AppCompatActivity {


    EditText playerOneName, playerTwoName;
    SwitchCompat switchTheme;
    Button playGame;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        playerOneName = findViewById(R.id.playerOneName);
        playerTwoName = findViewById(R.id.playerTwoName);


        playGame = findViewById(R.id.playGame);
        playGame.setOnClickListener(v->{
            String getPlayerOneName = playerOneName.getText().toString();
            String getPlayerTwoName = playerTwoName.getText().toString();
            if (!getPlayerOneName.isEmpty() || !getPlayerTwoName.isEmpty()){
                Intent intent = new Intent(this,MainActivity.class);
                intent.putExtra("playerOneName",getPlayerOneName);
                intent.putExtra("playerTwoName",getPlayerTwoName);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Please enter players name", Toast.LENGTH_SHORT).show();
            }
        });

        switchTheme = findViewById(R.id.switchTheme);
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            switchTheme.setChecked(true);
        }

    }


    @Override
    public void recreate() {
        finish();
        startActivity(new Intent(this,getClass()));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}