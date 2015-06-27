package de.myself5.whereisdoncheats;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public static final String PREFS_NAME = "WIDPrefs";
    private EditText mETWonGames;
    private TextView mCurrentWonGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mETWonGames = (EditText) findViewById(R.id.new_won_matches);
        mCurrentWonGames = (TextView) findViewById(R.id.current_won_matches);
        Button mUpdateWonGames = (Button) findViewById(R.id.update_won_matches);

        mUpdateWonGames.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        update_won_games();
                    }
                });

        updateTV();
    }

    public void update_won_games() {
        String WonGamesS = mETWonGames.getText().toString();
        if (!WonGamesS.equals("")) {
            int won_games = Integer.parseInt(WonGamesS);
            if (won_games > 0 && won_games <= 400) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_WORLD_WRITEABLE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("won_games", won_games);
                editor.apply();
                updateTV();
            }
        } else {
            Toast.makeText(this, getString(R.string.invalid_won_matches), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTV() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_WORLD_WRITEABLE);
        int nFights = settings.getInt("won_games", 1);
        mCurrentWonGames.setText(getString(R.string.current_won_matches) + " " + nFights);
    }
}
