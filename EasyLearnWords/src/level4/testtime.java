package level4;

import java.util.Random;

import com.easylearnwords.R;
import com.easylearnwords.mypublicvalue;

import android.R.integer;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class testtime extends Activity {

	CountDownTimer timer = new CountDownTimer(15000, 1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub

			int k = (int) (millisUntilFinished / 1000);

			timeTextView.setText("Time: " + k + "'s");

			if (k == 5) {
				timeTextView.setBackgroundColor(Color.RED);
			}
			if (k <= 5) {
				myapp.playmusic(2);
			}

		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			timeTextView.setText("Time over");
			myapp.playmusic(3); // Ê§°ÜÒôÀÖ
			timer.cancel();

		}
	};

	private TextView timeTextView;
	private mypublicvalue myapp;
	private String[][] words;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.idroots);

		myapp = (mypublicvalue) getApplication();
		words = myapp.getwords();

		words = get10ranwords(words);

		for (int i = 0; i < words.length; i++) {
			System.out.println(words[i][0]+" ++");
		}

		timeTextView = (TextView) this.findViewById(R.id.wrtext);

		timer.start();
		
		String ss= "aecdefghe";
		String s= "efgh";
		
		int k=ss.indexOf(s);
		
		System.out.println(k);
		
        double a1=0;
        double a2=0;
        System.out.println((int)(a1/a2));
		
	}

	private String[][] get10ranwords(String[][] words) {

		String[][] word = words;

		for (int i = 10; i < word.length; i++) {
			for (int j = 0; j < word[0].length; j++) {

				word[i][0] = "";

			}
		}

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int p = random.nextInt(10);
			String[] k = word[i];
			word[i] = word[p];
			word[p] = k;
		}

		random = null;

		return words;
	}

}
