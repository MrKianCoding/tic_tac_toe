package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView playerOneName, playerOneScore, playerTwoName, playerTwoScore;
    LinearLayout playerOneLayout, playerTwoLayout;
    ImageView buttonReset, buttonHome;
    ImageView[] buttons = new ImageView[9];
    private int playerOneScoreCount, playerTwoScoreCount, setCount;
    boolean activePlayer;

    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};  //9

    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneName = findViewById(R.id.playerOneName);
        playerOneScore = findViewById(R.id.playerOneScore);
        playerOneLayout = findViewById(R.id.playerOneLayout);

        playerTwoName = findViewById(R.id.playerTwoName);
        playerTwoScore = findViewById(R.id.playerTwoScore);
        playerTwoLayout = findViewById(R.id.playerTwoLayout);

        Intent intent = getIntent();
        playerOneName.setText(intent.getStringExtra("playerOneName"));
        playerTwoName.setText(intent.getStringExtra("playerTwoName"));


        buttonReset = findViewById(R.id.buttonReset);
        buttonHome = findViewById(R.id.buttonHome);


        buttonHome.setOnClickListener(v -> {
            startActivity(new Intent(this, AddPlayerActivity.class));
            finish();
        });


        buttonReset.setOnClickListener(v -> {
            resetGame();
            playerOneScoreCount = 0;
            playerTwoScoreCount = 0;
            updatePlayerScore();
        });

        for (int i = 0; i < buttons.length; i++) {
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());

            buttons[i] = (ImageView) findViewById(resourceID);
            buttons[i].setOnClickListener(this::onClick);

        }

        setCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

        playerOneLayout.setForeground(this.getResources().getDrawable(R.drawable.active_layout));
        playerTwoLayout.setForeground(null);


    }


    public void onClick(View view) {
        if (!(((ImageView) view).getDrawable() == null)) {
            return;
        }

        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1, buttonID.length()));
        if (activePlayer) {
            playerOneLayout.setForeground(null);
            playerTwoLayout.setForeground(this.getResources().getDrawable(R.drawable.active_layout));
            ((ImageView) view).setImageDrawable(this.getResources().getDrawable(R.drawable.x_image));
            gameState[gameStatePointer] = 0;
        } else {
            playerOneLayout.setForeground(this.getResources().getDrawable(R.drawable.active_layout));
            playerTwoLayout.setForeground(null);
            ((ImageView) view).setImageDrawable(this.getResources().getDrawable(R.drawable.o_image));
            gameState[gameStatePointer] = 1;
        }
        setCount++;

        if (checkWinner()) {
            if (activePlayer) {
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player one won!", Toast.LENGTH_SHORT).show();
                resetGame();
            } else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player two won!", Toast.LENGTH_SHORT).show();
                resetGame();
            }
        } else if (setCount == 9) {
            Toast.makeText(this, "No winner!", Toast.LENGTH_SHORT).show();
            resetGame();
        } else {
            activePlayer = !activePlayer;
        }
    }

    private void updatePlayerScore() {
        playerOneScore.setText(String.valueOf(playerOneScoreCount));
        playerTwoScore.setText(String.valueOf(playerTwoScoreCount));
    }

    private boolean checkWinner() {
        boolean result = false;

        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                return true;
            }
        }
        return result;
    }

    private void resetGame() {
        setCount = 0;
        activePlayer = true;
        for (int i = 0; i < buttons.length; i++) {
            gameState[i] = 2;
            buttons[i].setImageDrawable(null);

        }
        playerOneLayout.setForeground(this.getResources().getDrawable(R.drawable.active_layout));
        playerTwoLayout.setForeground(null);
    }
}