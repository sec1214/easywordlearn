package level4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rootsproject.R;
import com.rootsproject.mypublicvalue;
import com.rootsproject.play;


public class tutorial4 extends AppCompatActivity {

    private mypublicvalue myapp;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myapp = (mypublicvalue) getApplication();

        setContentView(R.layout.ztutorial);

        description = (TextView) this.findViewById(R.id.description);
        description.setText(R.string.tutorial4);
        description.setLineSpacing(0, 1.5f);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(R.string.tutorial);
        ab.setSubtitle(R.string.level4);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            myapp.empty();

            Intent intent = new Intent(tutorial4.this, play.class);
            startActivity(intent);
            finish();

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickNever(View v)
    {
        SharedPreferences settings = getSharedPreferences(mypublicvalue.prefsName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("hideTutorials", true);
        editor.commit();

        myapp.playmusic(1);
        Intent intent = new Intent(tutorial4.this, idroortsl4.class);
        startActivity(intent);

        finish();
    }

    public void onClickOkay(View v)
    {
        myapp.playmusic(1);
        Intent intent = new Intent(tutorial4.this, idroortsl4.class);
        startActivity(intent);

        finish();
    }
}