package com.example.andresmontoya.tictacgame;

import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // 0 = yellow || 1 = Red
    int active_player = 0;
    boolean game_active = true;
    //2 mean unplayed
    int[] game_state = {2,2,2,2,2,2,2,2,2};
    int[][] winning_positions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view){
        ImageView counter = (ImageView)view;
        int tapped_counter = Integer.parseInt(counter.getTag().toString());
        if (game_state[tapped_counter] == 2 && game_active){

            game_state[tapped_counter] = active_player;
            counter.setTranslationY(-1000f);
            if(active_player == 0){
                counter.setImageResource(R.drawable.yellow);
                active_player = 1;
            }
            else
            {
                counter.setImageResource(R.drawable.red);
                active_player = 0;
            }
            counter.animate().translationYBy(1000f).rotation(3600).setDuration(300);
            checkWinner(view);
        }

    }

    public void checkWinner(View view){
        for(int[] winning_position : winning_positions){
            if(game_state[winning_position[0]] == game_state[winning_position[1]] &&
                    game_state[winning_position[1]] == game_state[winning_position[2]] &&
                    game_state[winning_position[0]] != 2){
                game_active = false;
                String winner = "Red";
                int colorWin = Color.RED;
                if(game_state[winning_position[0]] == 0){
                    winner = "Yellow";
                    colorWin = Color.YELLOW;
                }

                EditText editText = (EditText)findViewById(R.id.winnerMsg);
                editText.setText(winner+" has won!");
                //System.out.println("the winner is: "+game_state[winning_position[0]]);
                LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layoutPlayAgain);
                linearLayout.setBackgroundColor(colorWin);
                linearLayout.setVisibility(view.VISIBLE);
            }
            else{
                boolean gameOver = true;
                for(int counterState : game_state){
                    if(counterState == 2) gameOver = false;
                }
                if(gameOver){
                    EditText editText = (EditText)findViewById(R.id.winnerMsg);
                    editText.setText("It's a draw");
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layoutPlayAgain);
                    linearLayout.setVisibility(view.VISIBLE);
                }
            }
        }
    }

    public void playAgain(View view) {
        game_active = true;
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layoutPlayAgain);
        linearLayout.setVisibility(view.INVISIBLE);
        active_player = 0;
        for (int i = 0; i<game_state.length;i++){
            game_state[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i = 0; i<gridLayout.getChildCount();i++){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}
