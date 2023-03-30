package com.example.xoxoyunu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;
    private TextView textViewCurrentPlayer;
    private TextView textViewWinner;
    private Button buttonReset;
    private Button buttonNewGame;
    private BreakIterator text_view_current_player, text_view_winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);
        textViewCurrentPlayer = findViewById(R.id.text_view_current_player);
        textViewWinner = findViewById(R.id.text_view_winner);
        buttonReset = findViewById(R.id.button_clear_board);
        buttonNewGame = findViewById(R.id.button_new_game);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
            textViewCurrentPlayer.setText(R.string.player2_turn);
        } else {
            ((Button) v).setText("O");
            textViewCurrentPlayer.setText(R.string.player1_turn);
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    public void onNewGameButtonClick(View view) {
        clearBoard();
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
    }

    public void onClearBoardButtonClick(View view) {
        clearBoard();
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][1].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[1][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[1][1].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[1][1].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        updatePointsText();
        Toast.makeText(this, "Oyuncu 1 Kazandı!", Toast.LENGTH_SHORT).show();
        textViewWinner.setText(R.string.player1_wins);
        disableButtons();
    }

    private void player2Wins() {
        player2Points++;
        updatePointsText();
        Toast.makeText(this, "Oyuncu 2 Kazandı!", Toast.LENGTH_SHORT).show();
        textViewWinner.setText(R.string.player2_wins);
        disableButtons();
    }

    private void draw() {
        Toast.makeText(this, "Berabere!", Toast.LENGTH_SHORT).show();
        textViewWinner.setText(R.string.draw);
        disableButtons();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Oyuncu 1: " + player1Points);
        textViewPlayer2.setText("Oyuncu 2: " + player2Points);
    }

    private void disableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void enableButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(true);
            }
        }
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        newGame();
    }

    private void clearBoard() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
        text_view_current_player.setText("1.Oyuncunun Sırası");
        text_view_winner.setText("");
    }

    private void newGame() {
        roundCount = 0;
        player1Turn = true;
        textViewCurrentPlayer.setText(R.string.player1_turn);
        textViewWinner.setText("");
        enableButtons();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }
}
