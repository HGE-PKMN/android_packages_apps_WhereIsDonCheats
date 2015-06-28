package de.myself5.whereisdoncheats;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

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
            // updateTV();

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("WIDCheatCodeAnswer".equals(type)) {
                updateTVPart2(intent); // Handle text being sent
            }
        }
    }

    public void update_won_games() {
        String WonGamesS = mETWonGames.getText().toString();
        if (!WonGamesS.equals("")) {
            int won_games = Integer.parseInt(WonGamesS);
            if (won_games > 0 && won_games <= 400) {
                sendIntent(WonGamesS, "WIDCheatCodeInput");
                // updateTV();
            } else {
                Toast.makeText(this, getString(R.string.invalid_won_matches), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.invalid_won_matches), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendIntent(String param, String type) {
/*        PackageManager pm = getPackageManager();
        Intent sendIntent = pm.getLaunchIntentForPackage("de.hg_epp.whereisdon");*/
        Intent sendIntent = new Intent();
        sendIntent.setComponent(new ComponentName("de.hg_epp.whereisdon", "de.hg_epp.whereisdon.FightEngine"));
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, param);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, type);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void updateTV() {
        sendIntent("", "WIDCheatCodeUpdate");
    }

    private void updateTVPart2(Intent intent) {
        int n = Integer.parseInt(intent.getStringExtra(Intent.EXTRA_TEXT));
        mCurrentWonGames.setText(getString(R.string.current_won_matches) + " " + n);
    }
}
