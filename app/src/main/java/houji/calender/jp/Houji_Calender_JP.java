package houji.calender.jp;
//2017.03.05 広告を無効にする、α版アプリファイル
//α版 2017.03.05-->
/*
　　●バージョン番号ルール：
　　　1.偶数をリリース版
　　　2.奇数をα版
 */
//import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//import com.google.gms.ads.AdSize;
import android.content.Intent;
import android.os.Build;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.analytics.FirebaseAnalytics;
import android.util.*;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.HashMap;
//import me.drakeet.materialdialog.MaterialDialog;
//import jp.appshare.AppShareSDK.AppShare;
//作業履歴
//2017.03.05 広告を無効にする、α版アプリファイル
//import base_file_lib_ver006;
public class Houji_Calender_JP extends Activity implements OnInitListener, OnClickListener, RewardedVideoAdListener {
	/**
	 * Called when the activity is first created.
	 */
	// リクエストを識別するための変数宣言。適当な数字でよい
	private static final int REQUEST_CODE = 1;
	private SpeechRecognizer sr;
	private TextToSpeech tts;
	//menu変数定義
	//private SpeechRecognizer sr;
	//音声読み上げ設定用変数
	//private TextToSpeech tts;
	private AdView mAdView;
	private FirebaseAnalytics mFirebaseAnalytics;
	private static final int MENU_ID_TEST1 = (Menu.FIRST + 1);
	private static final int MENU_ID_TEST2 = (Menu.FIRST + 2);
	private static final int MENU_ID_TEST3 = (Menu.FIRST + 3);
	private static final int MENU_ID_TEST4 = (Menu.FIRST + 4);
	String buffer_No=null;
	//private static final int MENU_ID_TEST5 = (Menu.FIRST + 5);
	private int mCheckedItem = -1;
	public static int VIBRATION = 1; //0:OFF 1:ON(DEfault)
	public static int Screen_Color = 1;  //0:Red,1:Green(Default),2:Blue
	// 項目名を定数で定義
	private static final String PREF_KEY_SCREEN_COLOR = "savedCount";
	private static final String PREF_KEY_VIBE_CONFIG = "vibe_config";
	String[] items = {"画面色変更", "バイブの設定"};
	String[] items2 = {"オレンジ", "緑(Default)", "青", "ピンク", "グレー"};
	String[] items3 = {"バイブを停める", "バイブを鳴らす(Default)"};
	Parsonal_Data[] persondata = {
			//1.名前 2.年齢,3.性別,4.生まれた年,5.生まれた月,6.生まれた日
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
			new Parsonal_Data("0", 0, "0", 0, 0, 0),
	};
	int mode = 0;
	int flag = 0;
	int count = 0;
	int No = 0;
	int Heisei_Year = 0; //平成の年
	int Heisei_This_Year = 0; //平成の年今年の年
	int Syouwa_Year = 0; //昭和の年
	int Year = 0; //西歴の入力
	int This_Year = 0; //西歴の入力今年の年
	int Dead_Year = 0; //何回忌かの年
	long saveScreencolor = 0;
	long saveVibration = -1;
	String buf_screen;
	int defaultwagoutype = -1;
	int Select_syouwa_Heisei_List = -1;
	int Select_syouwa_List = -1;
	int Select_Heisei_List = -1;
	ListView lv;
    private RewardedAd rewardedAd;
    private RewardedVideoAd mRewardedVideoAd;
	private static final String TAG = "TestTTS";
    private Object context;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */




	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		//setContentView(R.layout.main);

		Configuration config = getResources().getConfiguration();
		if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
			//APIレベル18以前の機種の場合の処理

			setContentView(R.layout.main_kitkat);

		}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
			//APIレベル21以降の機種の場合の処理
			if (config.smallestScreenWidthDp >= 600) {
				setContentView(R.layout.main_tablet);
			} else {
				setContentView(R.layout.main);
			}
		}
          //Test ID:"ca-app-pub-3940256099942544~3347511713"
        MobileAds.initialize(this, "ca-app-pub-7821909322657049~5382607298");
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);
        rewardedAd = new RewardedAd(this,
                "ca-app-pub-7821909322657049/3998687049");
		//Test ID:"ca-app-pub-3940256099942544/5224354917"
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
                //loadRewardedVideoAd();
            }

            @Override
            public void onRewardedAdFailedToLoad(int errorCode) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        //loadRewardedVideoAd(mRewardedVideoAd);

        loadRewardedVideoAd();
         // TTS インスタンス生成
		tts = new TextToSpeech(this, this);


		//mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

		// ユーザープロパティの設定
		//mFirebaseAnalytics.setUserProperty("key", "value");
		// AppShare.createInstance(this, getString(R.string.app_name), R.drawable.icon);
		AdView mAdView = new AdView(this);
		// tesuto ID
		//adView.setAdSize(AdSize.BANNER);adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		//mAdView.setAdSize(AdSize.BANNER);mAdView.setAdUnitId("ca-app-pub-7821909322657049/6859340493");

		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
			}
		});
		MobileAds.initialize(this, "ca-app-pub-7821909322657049/6859340493");
		mAdView = (AdView) findViewById(R.id.adView2);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		// base_file_lib_ver006 lib= new base_file_lib_ver006;
		tts = new TextToSpeech(getApplicationContext(), this);
		load_Screen_Preferences(); //設定画面色読み込み
		load_Vibration_Preferences();  //バイブの設定読み込み
		//TextView textView = (TextView)findViewById(R.id.text_view);
		//textView.setMovementMethod(ScrollingMovementMethod.getInstance());
		Button one_Button = (Button) findViewById(R.id.one_button);
		one_Button.setOnClickListener((OnClickListener) this);
		Button two_Button = (Button) findViewById(R.id.two_button);
		two_Button.setOnClickListener((OnClickListener) this);
		Button three_Button = (Button) findViewById(R.id.three_button);
		three_Button.setOnClickListener((OnClickListener) this);
		Button four_Button = (Button) findViewById(R.id.four_button);
		four_Button.setOnClickListener((OnClickListener) this);
		Button five_Button = (Button) findViewById(R.id.five_button);
		five_Button.setOnClickListener((OnClickListener) this);
		Button six_Button = (Button) findViewById(R.id.six_button);
		six_Button.setOnClickListener((OnClickListener) this);
		Button seven_Button = (Button) findViewById(R.id.seven_button);
		seven_Button.setOnClickListener((OnClickListener) this);
		Button eight_Button = (Button) findViewById(R.id.eight_button);
		eight_Button.setOnClickListener((OnClickListener) this);

		Button exe_Button = (Button) findViewById(R.id.exe_button);
		exe_Button.setOnClickListener((OnClickListener) this);
		Button Clear_Button = (Button) findViewById(R.id.clear_button);
		Clear_Button.setOnClickListener((OnClickListener) this);
		Button zero_Button = (Button) findViewById(R.id.zero_button);
		zero_Button.setOnClickListener((OnClickListener) this);
		Button nine_Button = (Button) findViewById(R.id.nine_button);
		nine_Button.setOnClickListener((OnClickListener) this);

		//Title
		setTitle(R.string.app_name);
		@SuppressWarnings("unused")
		TextView screen_view = (TextView) findViewById(R.id.view_screen);
		//変数定義
		/* 構造体のインスタンス作成 */
		Parsonal_Data data = new Parsonal_Data();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		//client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.

	}



    // finish();



    /*
     if (mRewardedVideoAd.isLoaded()) {
         mRewardedVideoAd.show();
     }
     */
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-7821909322657049/6859340493",
                new AdRequest.Builder().build());
    }
	private void shutDown(){
		if (null != tts) {
			// to release the resource of TextToSpeech
			tts.shutdown();
		}
	}
	//@Override
	// タッチイベントが起きたら呼ばれる関数
	/*
	public boolean onTouchEvent(MotionEvent event) {
		// 画面から指が離れるイベントの場合のみ実行
		if (event.getAction() == MotionEvent.ACTION_UP) {
			try {
				// 音声認識プロンプトを立ち上げるインテント作成
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				// 言語モデルをfree-form speech recognitionに設定
				// web search terms用のLANGUAGE_MODEL_WEB_SEARCHにすると検索画面になる
				intent.putExtra(
						RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				// プロンプトに表示する文字を設定
				intent.putExtra(
						RecognizerIntent.EXTRA_PROMPT,
						"話してください");
				// インテント発行
				startActivityForResult(intent, REQUEST_CODE);
			} catch (ActivityNotFoundException e) {
				// エラー表示
				Toast.makeText(this,
						"ActivityNotFoundException", Toast.LENGTH_LONG).show();
			}
		}
		return true;
	}
	*/
	// startActivityForResultで起動したアクティビティが終了した時に呼び出される関数
	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 音声認識結果のとき
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			String resultsString = "";
			// 音声入力の結果の最上位のみを取得
			ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String s = results.get(0);

			// 表示
			Toast.makeText(this, s, Toast.LENGTH_LONG).show();

			// 音声合成して発音
			if(tts.isSpeaking()) {
				tts.stop();
			}
			tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);

			switch(flag) {
				case 1:
					Input_get_Number(resultsString);
					break;
				case 2:
					Select_Menu1_Nengou_Wareki_Seireki(resultsString);
					break;
				case 3:
					Input_Voice_Select_Menu1_Nengou_Wareki_Heisei_Syouwa(resultsString);
					break;
			}

*/
			// 結果文字列リスト
			/*
			ArrayList<String> results = data.getStringArrayListExtra(
					RecognizerIntent.EXTRA_RESULTS);

			for (int i = 0; i< results.size(); i++) {
				// ここでは、文字列が複数あった場合に結合しています
				resultsString += results.get(i);
			}
			switch(flag) {
				case 1:
				      Input_get_Number(resultsString);
					  break;
				case 2:
					  Select_Menu1_Nengou_Wareki_Seireki(resultsString);
					  break;
				case 3:
					  Input_Voice_Select_Menu1_Nengou_Wareki_Heisei_Syouwa(resultsString);
					  break;
			}
			*/
			// トーストを使って結果を表示
			//Toast.makeText(getApplicationContext(), resultsString, Toast.LENGTH_LONG).show();
			//Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show();
//		}
//		super.onActivityResult(requestCode, resultCode, data);
		//super.onActivityResult(requestCode, resultCode, data);
//	}

	@Override
	public void finish() {
		super.finish();

	}



	@Override
	protected void onResume() {
		super.onResume();
		//startListening();
		//startListening();

		// Resume the AdView.
		//mAdView.resume();
	}

	@Override
	protected void onPause() {
		//stopListening();
		super.onPause();
	}


	@Override
	public void onDestroy() {


		super.onDestroy();
		tts.shutdown();
		// Destroy the AdView.
		//mAdView.destroy();

	}
	// 音声認識を終了する
/*
	protected void stopListening() {
		if (sr != null) sr.destroy();
		sr = null;
	}
	*/
	// ボタンが押された時に呼び出されるメソッド
	/*
	public void onRecordClick(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please Speech!!");
		startActivityForResult(intent, 0);
	}
	*/
	// startActivityForResultで呼び出したActivityから応答される結果を受け取るコールバックメソッド
	/*
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK) {
			// 結果文字列リスト
			ArrayList<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

			// TODO ここに取得した文字列に対する処理を記述

		}
	}
*/
	// RecognitionListenerの定義
	// 中が空でも全てのメソッドを書く必要がある
	/*
	class listener implements RecognitionListener {
		// 話し始めたときに呼ばれる
		public void onBeginningOfSpeech() {
		*/
            /*Toast.makeText(getApplicationContext(), "onBeginningofSpeech",

                    Toast.LENGTH_SHORT).show();*/
	/*	}

		// 結果に対する反応などで追加の音声が来たとき呼ばれる
		// しかし呼ばれる保証はないらしい
		public void onBufferReceived(byte[] buffer) {
		}

		// 話し終わった時に呼ばれる
		public void onEndOfSpeech() {*/
            /*Toast.makeText(getApplicationContext(), "onEndofSpeech",
                    Toast.LENGTH_SHORT).show();*/
	/*	}

		// ネットワークエラーか認識エラーが起きた時に呼ばれる
		public void onError(int error) {
			String reason = "";
			switch (error) {
				// Audio recording error
				case SpeechRecognizer.ERROR_AUDIO:
					reason = "ERROR_AUDIO";
					break;
				// Other client side errors
				case SpeechRecognizer.ERROR_CLIENT:
					reason = "ERROR_CLIENT";
					break;
				// Insufficient permissions
				case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
					reason = "ERROR_INSUFFICIENT_PERMISSIONS";
					break;
				// 	Other network related errors
				case SpeechRecognizer.ERROR_NETWORK:
					reason = "ERROR_NETWORK";
					*/
                    /* ネットワーク接続をチェックする処理をここに入れる */
		//			break;
				// Network operation timed out
	/*
							case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
					reason = "ERROR_NETWORK_TIMEOUT";
					break;
				// No recognition result matched
				case SpeechRecognizer.ERROR_NO_MATCH:
					reason = "ERROR_NO_MATCH";
					break;
				// RecognitionService busy
				case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
					reason = "ERROR_RECOGNIZER_BUSY";
					break;
					*/
				// Server sends error status
		/*		case SpeechRecognizer.ERROR_SERVER:
					reason = "ERROR_SERVER";*/
                    /* ネットワーク接続をチェックをする処理をここに入れる */
	/*
								break;
				// No speech input
				case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
					reason = "ERROR_SPEECH_TIMEOUT";
					break;
			}
			Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();
			restartListeningService();
		}
*/
		// 将来の使用のために予約されている
/*
		public void onEvent(int eventType, Bundle params) {
		}
*/
		// 部分的な認識結果が利用出来るときに呼ばれる
		// 利用するにはインテントでEXTRA_PARTIAL_RESULTSを指定する必要がある
/*
		public void onPartialResults(Bundle partialResults) {
		}
		*/
		// 音声認識の準備ができた時に呼ばれる
	/*
			public void onReadyForSpeech(Bundle params) {
			int count=0;
			Toast.makeText(getApplicationContext(), R.string.speach_please_Talk_Message2,
					Toast.LENGTH_SHORT).show();
			Text_speach_Message1(R.string.speach_please_Talk_Message2);
			count++;
			if(count > 0)
			{
				stopListening();
			}
		}
  */
		// 認識結果が準備できた時に呼ばれる
/*
public void onResults(Bundle results) {
			// 結果をArrayListとして取得
			ArrayList results_array = results.getStringArrayList(
					SpeechRecognizer.RESULTS_RECOGNITION);
			// 取得した文字列を結合
			String resultsString = "";
			for (int i = 0; i < results.size(); i++) {
				resultsString += results_array.get(i) + ";";
			}
			stopListening();
			get_Number(resultsString);
			// トーストを使って結果表示
			//Toast.makeText(getApplicationContext(), resultsString, Toast.LENGTH_LONG).show();
			restartListeningService();
		}

		// サウンドレベルが変わったときに呼ばれる
		// 呼ばれる保証はない
		public void onRmsChanged(float rmsdB) {
		}
	}

*/


	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			Text_speach_Message1(R.string.speach_Message_start_Message1);
			//tts.speak("準備OKです,入力ボタンを押して下さい", TextToSpeech.QUEUE_FLUSH, null);
		} else {
			//

		}
	}





	// 音声認識を開始する
/*
	protected void startListening() {
		try {
			if (sr == null) {
				sr = SpeechRecognizer.createSpeechRecognizer(this);
				if (!SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
					Toast.makeText(getApplicationContext(), "音声認識が使えません",
							Toast.LENGTH_LONG).show();
					finish();
				}
				//sr.setRecognitionListener(new listener());
			}
			// インテントの作成
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			// 言語モデル指定
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
			sr.startListening(intent);
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), "startListening()でエラーが起こりました",
					Toast.LENGTH_LONG).show();
			finish();
		}
	}

*/





// startActivityForResultで起動したアクティビティが終了した時に呼び出される関数
/*
	@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// 音声認識結果のとき
	if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
		// 結果文字列リストを取得
		ArrayList<String> results = data.getStringArrayListExtra(
				RecognizerIntent.EXTRA_RESULTS);
		// 取得した文字列を結合
		String resultsString = "";
		for (int i = 0; i < results.size(); i++) {
			resultsString += results.get(i)+";";
		}
		Input_get_Number(resultsString);


		// トーストを使って結果表示
		//Toast.makeText(this, resultsString, Toast.LENGTH_LONG).show();
	}

	super.onActivityResult(requestCode, resultCode, data);
}
*/
	@Override
	protected void onRestart() {
		super.onRestart();
		//Tracker t = ((Analytics_Data) getApplication()).getTracker(Analytics_Data.TrackerName.APP_TRACKER);
		//t.setScreenName("Main");
		// Send a screen view.
		//t.send(new HitBuilders.ScreenViewBuilder().build());
	}
	// ...

	private void onButtonClick() {
		// イベントの送信
		//Bundle params = new Bundle();
		//params.putString(FirebaseAnalytics.Param.ITEM_ID, "10");
		//params.putString(FirebaseAnalytics.Param.ITEM_NAME, "name");
		//params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "text");
		//mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
	}

	//画面の切り替え　ここから

	public void Change_Screen_Manual() {
		Intent it = new Intent();
		it.setClass(this, Manual.class);
		startActivity(it);
	}
    public void Change_Screen_Adsence() {
        Intent it = new Intent();
        it.setClass(this, Adcence_base.class);
        startActivity(it);
    }
/*マイク起動処理*/
/*
public void Start_Mic_Dialog()
{
	try {
         // 音声認識の準備
         Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
         intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		 Intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声を入力してください。");
		// インテント発行
		startActivityForResult(intent, REQUEST_CODE);
	}
	catch (ActivityNotFoundException e) {
		Toast.makeText(SpeechRecognizer.this, "Not found Activity", Toast.LENGTH_LONG).show();
	}
}
*/
/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		String input = "";

		// TODO Auto-generated method stub
		// super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			preInp = inpText.getText().toString();
			// 認識結果を取得
			ArrayList<String> candidates = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			Log.v("Speech", "Candidate Num = " + candidates.size());
			if (candidates.size() > 0) {
				input = preInp + candidates.get(0);// 認識結果(1位候補)
			}
			if (input != null) {
				inpText.setText(input);  // 入力文字列を表示
				inpText.setSelection(input.length());    // カーソルを移動
			}
		}
	}
*/
	//All Clear
	public void All_Clear() {
		//Mic_Reset(); //Mic reset
		//変数初期化
		mCheckedItem = -1;
		mode = 0;
		flag = 0;
		count = 0;
		No = 0;
		Heisei_Year = 0; //平成の年
		Heisei_This_Year = 0;
		Year = 0; //西歴の入力
		This_Year = 0; //西歴の入力今年の年
		Dead_Year = 0; //何回忌かの年
		show_screen1(R.string.Start_Message);
		//All_Clear_Message_Adsence_push_Message();
	}
	//レコード処理
	/*
	public void Record_Word()
	{
		// インスタンス生成
		SpeechRecognizer recognizer = SpeechRecognizer.createSpeechRecognizer(this);
		// リスナを設定
		recognizer.setRecognitionListener(new RecognitionListenerImpl());
		// リッスンスタート
		recognizer.startListening(intent);
	}
	*/
	//マイクを起動
    /*
    public void Start_Record_Dialog()
	{
		// 画面から指が離れるイベントの場合のみ実行
		//if (event.getAction() == MotionEvent.ACTION_UP) {
			try {
				// 音声認識プロンプトを立ち上げるインテント作成
				Intent intent = new Intent(
						RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				// 言語モデルをfree-form speech recognitionに設定
				// web search terms用のLANGUAGE_MODEL_WEB_SEARCHにすると検索画面になる
				intent.putExtra(
						RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
				// プロンプトに表示する文字を設定
				intent.putExtra(
						RecognizerIntent.EXTRA_PROMPT,
						"話してください");
				// インテント発行
				startActivityForResult(intent, REQUEST_CODE);
			} catch (ActivityNotFoundException e) {
				// エラー表示
				Toast.makeText(this,
						"ActivityNotFoundException", Toast.LENGTH_LONG).show();
			}
	//	}
		//return true;
	}
*/
	public void How_Reword_Yes_No()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		//alert.setTitle(getTitile);
		alert.setTitle("削除処理");
		alert.setMessage("アドワード広告を表示してもよろしいですか？");
		alert.setPositiveButton("表示します", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				//Yesボタンが押された時の処理
				show_Adsence();

			}});
		alert.setNegativeButton("広告はけっこうです。", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				//Noボタンが押された時の処理
				return;
			}});
		alert.show();
	}
    //文字操作　文字を取り出す
	public void Input_get_Number(String buffer)
	{

		String buffer1=buffer;
		if(buffer1.equals("いちばん"))
		{
			buffer_No="1";
			No=1;
			mode = 1;
		}
		else if(buffer1.equals("にばん"))
		{
			buffer_No="2";
			No=2;
			mode = 12;
		}
		else if(buffer1.equals("さんばん"))
		{
			buffer_No="3";
			No=3;
		}
		else
		{
			buffer_No=buffer1;
			if(buffer_No.equals("0"))
			{
				//Text_speach_Message1(R.string.speach_please_Talk_Message_Select_Nengou_Wareki_Select2);
				show_screen2(R.string.Push_Select_Message_No_Select,No);
				mode=0;
			}
		}

		 //buffer_No;

		//Toast.makeText(getApplicationContext(),buffer_No, Toast.LENGTH_LONG).show();
		//Toast.makeText(this,buffer_No, Toast.LENGTH_LONG).show();

		Select_Input_Dead_Year_Number();
		Toast.makeText(getApplicationContext(),buffer_No, Toast.LENGTH_LONG).show();
	}
	//年号の選択 西暦 or 和暦
	public void Select_Menu1_Nengou_Wareki_Seireki(String buffer)
	{
		//String buffer1=buffer;
		if((buffer.indexOf("われきをせんたく") != -1) || (buffer.indexOf("いちばん") != -1) || (buffer.indexOf("われき") != -1))
		{
			buffer_No="1";
			No=1;
			//mode = 1;
			show_screen2(R.string.Push_Select_Message_Select1,No);
		}
		else if(buffer.indexOf("せいれきをせんたく") != -1|| (buffer.indexOf("にばん") != -1) || (buffer.indexOf("せいれき") != -1))
		{
			buffer_No="2";
			No=2;
			//mode = 9;
			show_screen2(R.string.Push_Select_Message_Select2,No);
		}
		else
		{
			buffer_No=buffer;

		}
		Select_Input_Nengou_type_Number();
		Toast.makeText(getApplicationContext(),buffer_No, Toast.LENGTH_LONG).show();

	}
	//和暦の年号選択 昭和　or 平成
	public void Input_Voice_Select_Menu1_Nengou_Wareki_Heisei_Syouwa(String buffer)
	{
		//String buffer1=buffer;
		if((buffer.indexOf("へいせいをせんたく") != -1) || (buffer.indexOf("いちばん") != -1) || (buffer.indexOf("へいせい") != -1))
		{
			buffer_No="1";
			No=1;
			mode = 4;
			show_screen2(R.string.Push_Select_Message_Select1,No);
		}
		else if(buffer.indexOf("しょうわをせんたく") != -1|| (buffer.indexOf("にばん") != -1) || (buffer.indexOf("しょうわ") != -1))
		{
			buffer_No="2";
			No=2;
			//mode = 9;
			show_screen2(R.string.Push_Select_Message_Select2,No);
		}
		else
		{
			buffer_No=buffer;

		}
	}
	//和暦の年号の選択 昭和　or 平成
	public void Select_Wagou_Heisei_Shouwa_Voice()
	{
		//昭和　or 平成 かの選択
		switch (No) {
			case 1:
				//mode=2;
				show_screen2(R.string.Push_Select_Message_Select1,No);
				break;
			case 2:
				//mode=9;
				show_screen2(R.string.Push_Select_Message_Select2,No);
				break;
		}
		//音声読み上げ
		switch(No)
		{
			case 1: //平成
				   //平成が選択されました よろしければ、入力ボタンを押して下さい。
				    Text_speach_Message1(R.string.speach_please_Talk_Message_Select_Nengou_Wareki_type_Select1);
				    break;
			case 2: //昭和
				    //昭和が選択されました よろしければ、入力ボタンを押して下さい。
				    Text_speach_Message1(R.string.speach_please_Talk_Message_Select_Nengou_Wareki_type_Select2);
				    break;
			case 0:
				    Text_speach_Message1(R.string.speach_please_Talk_Message_Select_Nengou_Wareki_type_No_Select);
				    break;

		}
	}
	//Method Javaここから
	public void Select_Input_Nengou_type_Number() {

		switch (No) {
			case 1:
				//mode=2;
				show_screen2(R.string.Push_Select_Message_Select1, No);
				break;
			case 2:
				//mode=9;
				show_screen2(R.string.Push_Select_Message_Select2, No);
				break;
		}
		if(No == 1) {
			Text_speach_Message1(R.string.speach_please_Talk_Message_Select_Nengou_Wareki_Select1);
		}
		else if(No == 2) {
			Text_speach_Message1(R.string.speach_please_Talk_Message_Select_Nengou_Wareki_Select2);
		}
	}
	public void Select_Input_Dead_Year_Number() {
		switch (No) {
			case 0:
				    show_screen2(R.string.Push_Select_TopMenu_No_Select,No);
				    break;
			case 1:
				    show_screen2(R.string.Push_Select_TopMenu_Select1,No);
				    break;
			case 2:
				    show_screen2(R.string.Push_Select_TopMenu_Select2,No);
				    break;
			case 3:
				    show_screen2(R.string.Push_Select_TopMenu_Select3,No);
				    break;
			default:
				    break;
		}
		if(No >= 1 && No <= 3)
		{
			Text_speach_Message2(R.string.speach_please_Talk_Message_Check_Message1,No);
		}
		else
		{
			flag = 0;
			mode = 1;
			//No = 0;
			//buffer_No = "0";
			Text_speach_Message2(R.string.speach_please_Talk_Message_Check_Message1_Select0,No);
			show_screen2(R.string.Push_Select_TopMenu_No_Select,No);
			//Start_Record_Dialog();
			Toast.makeText(getApplicationContext(),buffer_No, Toast.LENGTH_LONG).show();


		}
	}

	public void Select_Wareki_Syouwa_Heisei_Number_EXE() {
		//和歴、西暦の選択入力ボタンと番号
		switch (No) {
			case 0:
				show_screen2(R.string.Select_Wareki_Select_No_Select, No);
				break;
			case 1:
				show_screen2(R.string.Select_Wareki_Select_Select1, No);
				break;
			case 2:
				show_screen2(R.string.Select_Wareki_Select_Select2, No);
				break;
		}
	}

	public void Input_Wareki_Heisei_EXE_Number() {
		switch (count % 3) {
			case 0:
				Heisei_Year = 0;
				count++;
				Text_speach_Message1(R.string.speach_please_Talk_Select_Wareki_Input_Heisei);
				show_screen2(R.string.Select_Wareki_Input_Heisei, Heisei_Year);
				break;
			case 1:
				Heisei_Year = No;
				count++;

				show_screen2(R.string.Select_Wareki_Input_Heisei, Heisei_Year);
				break;
			case 2:
				Heisei_Year *= 10;
				Heisei_Year += No;
				count++;
				if (Heisei_Year > 27) {
					//平成27年以上ならエラー
					//Error Message
					Input_Error_Dialog(R.string.Input_Heisei_Error_Title, R.string.Input_Heisei_Error_Message);
					Heisei_Year = 0;
					count = 0;
					show_screen2(R.string.Select_Wareki_Input_Heisei, Heisei_Year);
				} else {
					show_screen2(R.string.Select_Wareki_Input_Heisei, Heisei_Year);
				}
				break;
		}
	}

	public void Input_Wareki_Heisei_This_Year() {
		//平成で今年の年を入力
		switch (count%3) {
			case 0:
				count++;
				Heisei_This_Year = 0;
				show_screen2(R.string.Select_Wareki_Input_Heisei_This_Year, Heisei_This_Year);
				Text_speach_Message1(R.string.speach_please_Talk_Select_Wareki_Input_Heisei_This_Year);
				break;
			case 1:
				count++;
				Heisei_This_Year = No;
				show_screen2(R.string.Select_Wareki_Input_Heisei_This_Year, Heisei_This_Year);
				break;
			case 2:
				count++;
				Heisei_This_Year *= 10;
				Heisei_This_Year += No;
				show_screen2(R.string.Select_Wareki_Input_Heisei_This_Year, Heisei_This_Year);
				break;
		}
	}

	public void Result_Complate_death_year_Heisei() {
		//法事の年の計算
		int houji_year1 = Heisei_Year + 1; //1周忌の計算
		int houji_year3 = Heisei_Year + 2; //3回忌の計算
		int houji_year7 = Heisei_Year + 6; //7回忌の計算
		int houji_year13 = Heisei_Year + 12; //13回忌の計算
		int houji_year23 = Heisei_Year + 22; //23回忌の計算
		int houji_year27 = Heisei_Year + 26; //27回忌の計算
		int houji_year33 = Heisei_Year + 32; //33回忌の計算
		int houji_year37 = Heisei_Year + 36; //37回忌の計算
		int houji_year50 = Heisei_Year + 49; //50回忌の計算
		if (houji_year1 == Heisei_This_Year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year1_Heisei, Heisei_This_Year, Heisei_Year);

			show_screen3(R.string.Result_Death_Year1_Heisei, Heisei_This_Year, Heisei_Year);
		} else if (houji_year3 == Heisei_This_Year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year3_Heisei, Heisei_This_Year, Heisei_Year);
			show_screen3(R.string.Result_Death_Year3_Heisei, Heisei_This_Year, Heisei_Year);
		} else if (houji_year7 == Heisei_This_Year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year7_Heisei, Heisei_This_Year, Heisei_Year);
			show_screen3(R.string.Result_Death_Year7_Heisei, Heisei_This_Year, Heisei_Year);
		} else if (houji_year13 == Heisei_This_Year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year13_Heisei, Heisei_This_Year, Heisei_Year);
			show_screen3(R.string.Result_Death_Year13_Heisei, Heisei_This_Year, Heisei_Year);
		} else if (houji_year23 == Heisei_This_Year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year23_Heisei, Heisei_This_Year, Heisei_Year);
			show_screen3(R.string.Result_Death_Year23_Heisei, Heisei_This_Year, Heisei_Year);
		} else if (houji_year27 == Heisei_This_Year) {
			if (Heisei_Year == 1) {
				Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year27_Heisei_Patern2, Heisei_This_Year, Heisei_Year);
				show_screen2(R.string.Result_Death_Year27_Heisei_Patern2, Heisei_This_Year);
			} else {
				Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year27_Heisei, Heisei_This_Year, Heisei_Year);
				show_screen3(R.string.Result_Death_Year27_Heisei, Heisei_This_Year, Heisei_Year);
			}
		} else if (houji_year33 == Heisei_This_Year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year33_Heisei, Heisei_This_Year, Heisei_Year);
			show_screen3(R.string.Result_Death_Year33_Heisei, Heisei_This_Year, Heisei_Year);
		} else if (houji_year37 == Heisei_This_Year) {

			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year37_Heisei, Heisei_This_Year, Heisei_Year);
			show_screen3(R.string.Result_Death_Year37_Heisei, Heisei_This_Year, Heisei_Year);
		} else if (houji_year50 == Heisei_This_Year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year50_Heisei, Heisei_This_Year, Heisei_Year);
			show_screen3(R.string.Result_Death_Year50_Heisei, Heisei_This_Year, Heisei_Year);
		} else {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year_Other_Heisei, Heisei_This_Year, Heisei_Year);
			show_screen3(R.string.Result_Death_Year_Other_Heisei, Heisei_This_Year, Heisei_Year);
		}
	}

	public void Input_Wareki_Syouwa_EXE_Number() {
		switch (count % 3) {
			case 0:
				count++;
				Syouwa_Year = 0;

				Text_speach_Message1(R.string.speach_please_Talk_Select_Wareki_Input_Syouwa);
				show_screen2(R.string.Select_Wareki_Input_Syouwa, Syouwa_Year);
				break;
			case 1:
				count++;
				Syouwa_Year = No;
				show_screen2(R.string.Select_Wareki_Input_Syouwa, Syouwa_Year);
				break;
			case 2:
				count++;
				Syouwa_Year *= 10;
				Syouwa_Year += No;
				show_screen2(R.string.Select_Wareki_Input_Syouwa, Syouwa_Year);
				break;
		}
	}

	public void Result_Complate_death_year_Syouwa() {
		//法事の年の計算
		int houji_year = 64 - Syouwa_Year + Heisei_This_Year;//死期の計算
		int houji_year1 = 1;
		int houji_year3 = 3; //3回忌の計算
		int houji_year7 = 7; //7回忌の計算
		int houji_year13 = 13; //13回忌の計算
		int houji_year17 = 17; //17回忌の計算
		int houji_year23 = 23; //23回忌の計算
		int houji_year27 = 27; //27回忌の計算
		int houji_year33 = 33; //33回忌の計算
		int houji_year37 = 37; //37回忌の計算
		int houji_year50 = 50; //50回忌の計算
		if (houji_year1 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year1_Syouwa,Heisei_This_Year,Syouwa_Year);
			show_screen3(R.string.Result_Death_Year1_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year3 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year3_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year3_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year7 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year7_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year7_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year13 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year13_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year13_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year17 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year17_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year17_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year23 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year23_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year23_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year27 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year27_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year27_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year33 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year33_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year33_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year37 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year37_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year37_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else if (houji_year50 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year50_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year50_Syouwa, Heisei_This_Year, Syouwa_Year);
		} else {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year_Other_Syouwa,Heisei_This_Year,Syouwa_Year);

			show_screen3(R.string.Result_Death_Year_Other_Syouwa, Heisei_This_Year, Syouwa_Year);
		}
	}

	public void Input_Death_Year_Seireki() {
		switch (count % 5) {
			case 0:
				Year = 0;
				count++;
				Text_speach_Message1(R.string.speach_please_Talk_Select_Wareki_Input_Seireki);
				show_screen2(R.string.Select_Wareki_Input_Seireki, Year);
				break;
			case 1:
				Year = No;
				count++;
				show_screen2(R.string.Select_Wareki_Input_Seireki, Year);
				break;
			case 2:
			case 3:
			case 4:
				count++;
				Year *= 10;
				Year += No;
				show_screen2(R.string.Select_Wareki_Input_Seireki, Year);
				break;

		}
	}

	public void Input_Death_Year_Seireki_ThisYear() {
		switch (count % 5) {
			case 0:
				This_Year = 0;
				count++;
				Text_speach_Message1(R.string.speach_please_Talk_Select_Wareki_Input_Seireki_ThisYear);

				show_screen2(R.string.Select_Wareki_Input_Seireki_ThisYear, This_Year);
				break;
			case 1:
				This_Year = No;
				count++;
				show_screen2(R.string.Select_Wareki_Input_Seireki_ThisYear, This_Year);
				break;
			case 2:
			case 3:
			case 4:
				count++;
				This_Year *= 10;
				This_Year += No;
				show_screen2(R.string.Select_Wareki_Input_Seireki_ThisYear, This_Year);
				break;

		}
	}

	public void Result_Complate_death_year_Seireki() {

		//法事の年の計算
		int houji_year = This_Year - Year;//死期の計算
		int houji_year1 = 1;
		int houji_year3 = 2; //3回忌の計算
		int houji_year7 = 6; //7回忌の計算
		int houji_year13 = 12; //13回忌の計算
		int houji_year23 = 22; //23回忌の計算
		int houji_year27 = 26; //27回忌の計算
		int houji_year33 = 32; //33回忌の計算
		int houji_year37 = 36; //37回忌の計算
		int houji_year50 = 49; //50回忌の計算
		if (houji_year1 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year1_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year1_Seireki, This_Year, Year);
		} else if (houji_year3 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year3_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year3_Seireki, This_Year, Year);
		} else if (houji_year7 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year7_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year7_Seireki, This_Year, Year);
		} else if (houji_year13 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year13_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year13_Seireki, This_Year, Year);
		} else if (houji_year23 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year1_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year23_Seireki, This_Year, Year);
		} else if (houji_year27 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year27_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year27_Seireki, This_Year, Year);
		} else if (houji_year33 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year33_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year33_Seireki, This_Year, Year);
		} else if (houji_year37 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year37_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year37_Seireki, This_Year, Year);
		} else if (houji_year50 == houji_year) {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year50_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year50_Seireki, This_Year, Year);
		} else {
			Text_speach_Message3(R.string.speach_please_Talk_Result_Death_Year_Other_Seireki, This_Year, Year);
			show_screen3(R.string.Result_Death_Year_Other_Seireki, This_Year, Year);
		}
	}

	public void Input_Death_Year_Shouwa() {
		int[] Seireki_List = getResources().getIntArray(R.array.Wareki_Seireki_Change);
		String[] Syouwa_List = getResources().getStringArray(R.array.Seireki_Wareki_Change);
		for (int i = 0; i != 64; i++) {

		}
	}

	public void Input_Select_Nendai_Seireki_Wareki() {
		switch (No) {
			case 0: //無選択
				Text_speach_Message1(R.string.speach_please_Talk_Push_Select_Message_No_Select);
				show_screen2(R.string.Push_Select_Message_No_Select, No);
				break;
			case 1: //和暦を選択

				Text_speach_Message1(R.string.speach_please_Talk_Push_Select_Message_Select1);
				show_screen2(R.string.Push_Select_Message_Select1, No);
				break;
			case 2://西暦を選択
				Text_speach_Message1(R.string.speach_please_Talk_Push_Select_Message_Select2);
				show_screen2(R.string.Push_Select_Message_Select2, No);
				break;
		}
	}

	public void Input_Wareki_This_Year() {
		switch (count % 3) {
			case 0:
				No = 0;
				Heisei_This_Year = No;
				count++;
				Text_speach_Message1(R.string.speach_please_Talk_Select_Wareki_Input_Heisei_This_Year);
				show_screen2(R.string.Select_Wareki_Input_Heisei_This_Year,Heisei_This_Year);
				break;
			case 1:
				Heisei_This_Year = No;
				count++;
				show_screen2(R.string.Select_Wareki_Input_Heisei_This_Year, Heisei_This_Year);
				break;
			case 2:
				Heisei_This_Year *= 10;
				Heisei_This_Year += No;
				count++;
				show_screen2(R.string.Select_Wareki_Input_Heisei_This_Year, Heisei_This_Year);
				break;
		}
	}

	public void Input_Dead_Year() {
		switch (count % 3) {
			case 0:
				Dead_Year = 0; //何回忌かの年
				Text_speach_Message1(R.string.speach_please_Talk_Input_Dead_Year);
				show_screen2(R.string.Input_Dead_Year, Dead_Year);
				count++;
				break;
			case 1:
				Dead_Year = No; //何回忌かの年
				count++;
				show_screen2(R.string.Input_Dead_Year, Dead_Year);
				break;
			case 2:
				Dead_Year *= 10;
				Dead_Year += No; //何回忌かの年
				count++;
				show_screen2(R.string.Input_Dead_Year, Dead_Year);
				break;
		}
	}

	//和暦入力
	public void Result_Dead_Year_EXE_Number() {
		int result_dead_year;
		if (Dead_Year == 1) {
			result_dead_year = Heisei_This_Year - Dead_Year;
		} else {
			result_dead_year = Heisei_This_Year - Dead_Year + 1;
		}
		if (result_dead_year > 0) {
			if (result_dead_year == 1) {
				Text_speach_Message2(R.string.speach_please_Talk_Result_Dead_year_Patern1_Heisei2,Dead_Year);
				show_screen2(R.string.Result_Dead_year_Patern1_Heisei2, Dead_Year);
			} else {
				if (Dead_Year == 1) {
					Text_speach_Message3(R.string.speach_please_Talk_Result_Dead_year_Patern0_Heisei, Dead_Year, result_dead_year);
					show_screen3(R.string.Result_Dead_year_Patern0_Heisei, Dead_Year, result_dead_year);
				} else {
					Text_speach_Message3(R.string.speach_please_Talk_Result_Dead_year_Patern1_Heisei, Dead_Year, result_dead_year);

					show_screen3(R.string.Result_Dead_year_Patern1_Heisei, Dead_Year, result_dead_year);
				}
			}
		} else if (result_dead_year < 0) {
			result_dead_year = ((Heisei_This_Year + 64) - Dead_Year);
			Text_speach_Message3(R.string.speach_please_Talk_Result_Dead_year_Patern2_Syouwa, Dead_Year, result_dead_year);
			show_screen3(R.string.Result_Dead_year_Patern2_Syouwa, Dead_Year, result_dead_year);
		}
	}

	//西暦入力
	public void Result_Dead_Year_Seireki_EXE_Number() {
		int result_dead_year;
		if (Dead_Year == 1) {
			result_dead_year = This_Year - Dead_Year;
		} else {
			result_dead_year = This_Year - Dead_Year + 1;
		}
		if (result_dead_year > 0) {

			if (Dead_Year == 1) {
				//1周忌
				Text_speach_Message4(R.string.speach_please_Talk_Result_Dead_year_Patern1_Seireki, This_Year, Dead_Year, result_dead_year);
				show_screen4(R.string.Result_Dead_year_Patern1_Seireki, This_Year, Dead_Year, result_dead_year);
			} else {
				//1周忌以外

				Text_speach_Message4(R.string.speach_please_Talk_Result_Dead_year_Patern2_Seireki, This_Year, Dead_Year, result_dead_year);

				show_screen4(R.string.Result_Dead_year_Patern2_Seireki, This_Year, Dead_Year, result_dead_year);
			}
		}
		/*
    	else if(result_dead_year < 0)
    	{
    		result_dead_year= (Heisei_This_Year + 64) - Dead_Year; 
    		show_screen3(R.string.Result_Dead_year_Patern2_Syouwa,Dead_Year,result_dead_year);
    	}
    	*/
	}

	public void Change_Wareki_Sereki() {
		int[] Seireki_List = getResources().getIntArray(R.array.Wareki_Seireki_Change);
	}

	//Menu2 Select2 西暦入力
	public void Input_Seireki_Menus2_Select2_Number_EXE() {
		switch (count % 5) {
			case 0:
				This_Year = 0; //西歴の入力今年の年
				//count++;
				Text_speach_Message1(R.string.speach_please_Talk_Select_Wareki_Input_Seireki_ThisYear);
				show_screen2(R.string.Select_Wareki_Input_Seireki_ThisYear,This_Year);
				count++;
				break;
			case 1:
				This_Year = No; //西歴の入力今年の年
				count++;
				show_screen2(R.string.Select_Wareki_Input_Seireki_ThisYear, This_Year);
				break;
			case 2:
			case 3:
			case 4:
				This_Year *= 10;
				This_Year += No; //西歴の入力今年の年
				count++;
				show_screen2(R.string.Select_Wareki_Input_Seireki_ThisYear, This_Year);
				break;
		}
	}

	public void Result_Houjireki_List_Wareki() {
		//////////
		//今年:平成%1$d年\n法事年一覧リスト\n一周忌:%2$s%3$d年\n三周忌:%4$s%5$d年\n七周忌:%6$s%7$d年\n十三周忌:%8$s%9$d年\n二十三周忌:%10$s%11$d年\n二十七周忌:%12$s%13$d年\n三十三周忌:%14$s%15$d年\n三十七周忌:%16$s%17$d年\n五十周忌:%18$s%19$d年
		//Result_Houji_Tosi_List_Wareki

		String[] wareki_type = getResources().getStringArray(R.array.Wareki_Type);
		//1回忌
		String buf_warekitype1 = null;
		int one_year_of_dead = Heisei_This_Year - 1;
		//3回忌
		String buf_warekitype3 = null;
		int three_year_of_dead = Heisei_This_Year - 2;
		//7回忌
		String buf_warekitype7 = null;
		int seven_year_of_dead = Heisei_This_Year - 6;
		//13回忌
		String buf_warekitype13 = null;
		int thirty_year_of_dead = Heisei_This_Year - 12;
		//17回忌
		String buf_warekitype17 = null;
		int seventeen_year_of_dead = Heisei_This_Year - 16;
		//23回忌
		String buf_warekitype23 = null;
		int twentythree_year_of_dead = Heisei_This_Year - 22;
		//27回忌
		String buf_warekitype27 = null;
		int twentyseven_year_of_dead = Heisei_This_Year - 26;
		//33回忌
		String buf_warekitype33 = null;
		int thirtythree_year_of_dead = Heisei_This_Year - 32;
		//37回忌
		String buf_warekitype37 = null;
		int thirtyseven_year_of_dead = Heisei_This_Year - 36;
		//50回忌
		String buf_warekitype50 = null;
		int fifty_year_of_dead = Heisei_This_Year - 49;
		//  1回忌 -->
		if (one_year_of_dead > 0) {
			buf_warekitype1 = wareki_type[0];
		} else if (one_year_of_dead < 0) {

			buf_warekitype1 = wareki_type[1];
		}
		//  3回忌 -->
		if (three_year_of_dead > 0) {
			buf_warekitype3 = wareki_type[0];
		} else if (three_year_of_dead < 0) {
			buf_warekitype3 = wareki_type[1];
		}
		//  7回忌 -->
		if (seven_year_of_dead > 0) {
			buf_warekitype7 = wareki_type[0];
		} else if (seven_year_of_dead < 0) {
			buf_warekitype7 = wareki_type[1];
		}
		//  13回忌 -->
		if (thirty_year_of_dead > 0) {
			buf_warekitype13 = wareki_type[0];
		} else if (thirty_year_of_dead < 0) {
			buf_warekitype13 = wareki_type[1];
		}
		//17回忌
		if (seventeen_year_of_dead > 0) {
			buf_warekitype17 = wareki_type[0];
		} else if (seventeen_year_of_dead < 0) {
			buf_warekitype17 = wareki_type[1];
		}
		//  23回忌 -->
		if (twentythree_year_of_dead > 0) {
			buf_warekitype23 = wareki_type[0];
		} else if (twentythree_year_of_dead < 0) {
			buf_warekitype23 = wareki_type[1];
		}
		//  27回忌 -->
		if (twentyseven_year_of_dead > 0) {
			buf_warekitype27 = wareki_type[0];
		} else if (twentyseven_year_of_dead <= 0) {
			int buf_twentyseven = twentyseven_year_of_dead;
			if (twentyseven_year_of_dead == 0) {
				twentyseven_year_of_dead = 63;
			} else {
				twentyseven_year_of_dead = 63 + buf_twentyseven;

			}
			buf_warekitype27 = wareki_type[1];
		}
		//  33回忌 -->
		if (thirtythree_year_of_dead > 0) {
			buf_warekitype33 = wareki_type[0];
		} else if (thirtythree_year_of_dead <= 0) {
			int buf_thirty_three = thirtythree_year_of_dead;
			if (thirtythree_year_of_dead == 0) {
				thirtythree_year_of_dead = 63;
			} else {
				thirtythree_year_of_dead = 63 + buf_thirty_three;

			}
			buf_warekitype33 = wareki_type[1];
		}
		//  37回忌 -->
		if (thirtyseven_year_of_dead > 0) {
			buf_warekitype37 = wareki_type[0];
		} else if (thirtyseven_year_of_dead <= 0) {
			int buf_thirty_seven = thirtyseven_year_of_dead;
			thirtyseven_year_of_dead = 63 + buf_thirty_seven;
			buf_warekitype37 = wareki_type[1];
		}
		//  50回忌 -->
		if (fifty_year_of_dead > 0) {
			buf_warekitype50 = wareki_type[0];
		} else if (fifty_year_of_dead <= 0) {
			int buf_fifty_year = fifty_year_of_dead;
			fifty_year_of_dead = 63 + buf_fifty_year;

			buf_warekitype50 = wareki_type[1];
		}
		String buf_screen = String.format(getString(R.string.Result_Houji_Tosi_List_Wareki), Heisei_This_Year, buf_warekitype1, one_year_of_dead, buf_warekitype3, three_year_of_dead, buf_warekitype7, seven_year_of_dead, buf_warekitype13, thirty_year_of_dead, buf_warekitype17, seventeen_year_of_dead, buf_warekitype23, twentythree_year_of_dead, buf_warekitype27, twentyseven_year_of_dead, buf_warekitype33, thirtythree_year_of_dead, buf_warekitype37, thirtyseven_year_of_dead, buf_warekitype50, fifty_year_of_dead);
		//今年:平成%1$d年\n法事年一覧リスト\n一周忌:%2$s%3$d年\n三周忌:%4$s%5$d年\n七周忌:%6$s%7$d年\n十三周忌:%8$s%9$d年\n二十三周忌:%10$s%11$d年\n二十七周忌:%12$s%13$d年\n三十三周忌:%14$s%15$d年\n三十七周忌:%16$s%17$d年\n五十周忌:%18$s%19$d年
		Result_MessegeDialogScreen(R.string.Result_Houji_YearList_Title, buf_screen);
		Text_speach_Message2(R.string.speach_please_Talk_Result_Houji_Tosi_List_Wareki, Heisei_This_Year);


		//show_screen20(R.string.Result_Houji_Tosi_List_Wareki,Heisei_This_Year,buf_warekitype1,one_year_of_dead,buf_warekitype3,three_year_of_dead,buf_warekitype7,seven_year_of_dead,buf_warekitype13,thirty_year_of_dead,buf_warekitype23,twentythree_year_of_dead,buf_warekitype27,twentyseven_year_of_dead,buf_warekitype33,thirtythree_year_of_dead,buf_warekitype37,thirtyseven_year_of_dead,buf_warekitype50,fifty_year_of_dead);
	}

	public void Result_Houjireki_List_Seireki() {

		//This_Year
		//1回忌 -->
		int dead_of_year1 = This_Year - 1;
		// 3回忌 -->
		int dead_of_year3 = This_Year - 2;
		// 7回忌 -->
		int dead_of_year7 = This_Year - 6;
		// 13回忌 -->
		int dead_of_year13 = This_Year - 12;
		// 17回忌 -->
		int dead_of_year17 = This_Year - 16;
		// 23回忌 -->
		int dead_of_year23 = This_Year - 22;
		// 27回忌 -->
		int dead_of_year27 = This_Year - 26;
		// 33回忌 -->
		int dead_of_year33 = This_Year - 32;
		// 37回忌 -->
		int dead_of_year37 = This_Year - 36;
		// 50回忌 -->
		int dead_of_year50 = This_Year - 49;
		//ここを変更
		String buf_screen = String.format(getString(R.string.Result_Houji_Tosi_List_Seireki), This_Year, dead_of_year1, dead_of_year3, dead_of_year7, dead_of_year13, dead_of_year17, dead_of_year23, dead_of_year27, dead_of_year33, dead_of_year37, dead_of_year50);
		//String buf_screen=String.format(getString(R.string.Result_Houji_YearList_Title));
		//Result_MessegeDialogScreen(R.string.Result_Houji_YearList_Title,buf_screen);
		Result_MessegeDialogScreen(R.string.Result_Houji_YearList_Title, buf_screen);
		Text_speach_Message2(R.string.speach_please_Talk_Result_Houji_Tosi_List_Seireki,This_Year);



		//Result_Houji_YearList_Title
		//show_screen10(R.string.Result_Houji_Tosi_List_Seireki,This_Year,dead_of_year1,dead_of_year3,dead_of_year7,dead_of_year13,dead_of_year23,dead_of_year27,dead_of_year33,dead_of_year37,dead_of_year50);
	}

	//Method Javaここまで
	private void SaveScreen_Color_Preferences() {
		//設定の保存処理
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor prefEditor = pref.edit();
		// ScreenColorを保存
		prefEditor.putLong(PREF_KEY_SCREEN_COLOR, Screen_Color);
		//バイブの設定
		prefEditor.putLong(PREF_KEY_VIBE_CONFIG, VIBRATION);
		// 最後commit
		prefEditor.commit();
		Toast.makeText(this, R.string.Save_Message, Toast.LENGTH_SHORT).show();

	}

	private void SaveScreen_Vibration_Preferences() {
		//設定の保存処理
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor prefEditor = pref.edit();
		//バイブの設定
		prefEditor.putLong(PREF_KEY_VIBE_CONFIG, VIBRATION);
		// 最後commit
		prefEditor.commit();
		Toast.makeText(this, R.string.Save_Message, Toast.LENGTH_SHORT).show();

	}

	//設定ファイルの読み込み
	private void load_Screen_Preferences() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		// savedAtをlong型で読み出し。デフォルト値は0
		saveScreencolor = pref.getLong(PREF_KEY_SCREEN_COLOR, 0);
		SetColor1((int) saveScreencolor);

		if (saveScreencolor == 0) {
			// 0つまり、未保存ならnullとする
			mCheckedItem = -1;
		} else {
			// Date型に変換
			Screen_Color = (int) saveScreencolor;
			mCheckedItem = Screen_Color;
		}

	}

	private void load_Vibration_Preferences() {
		SharedPreferences pref_vibe = PreferenceManager.getDefaultSharedPreferences(this);
		// savedAtをlong型で読み出し。デフォルト値は0
		saveVibration = pref_vibe.getLong(PREF_KEY_VIBE_CONFIG, 0);
		VIBRATION = (int) saveVibration;

		if (saveVibration == -1) {
			// 0つまり、未保存ならnullとする
			mCheckedItem = -1;
		} else {
			// Date型に変換
			Screen_Color = (int) saveScreencolor;
			mCheckedItem = Screen_Color;
		}

	}

	//トースト
	public void All_Clear_Message_Adsence_push_Message() {
		//オールクリアメッセージ
		// 第3引数は、表示期間（LENGTH_SHORT、または、LENGTH_LONG）
		Toast.makeText(this, R.string.All_Clear_Message, Toast.LENGTH_SHORT).show();
		Toast.makeText(this, R.string.Push_Adsence_Message, Toast.LENGTH_SHORT).show();

	}

	//画面の切り替え　ここまで
	//Menu部分 ここから
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MENU_ID_TEST1, Menu.NONE, R.string.Version_Menu);
		menu.add(Menu.NONE, MENU_ID_TEST2, Menu.NONE, R.string.Config_Menu);
		menu.add(Menu.NONE, MENU_ID_TEST3, Menu.NONE, R.string.Change_Nengou);
		menu.add(Menu.NONE, MENU_ID_TEST4, Menu.NONE,R.string.Help_Manual);
		//menu.add(Menu.NONE,MENU_ID_TEST3,Menu.NONE,R.string.Config_Personal);
		//menu.add(0,0,0,R.string.Version_Menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret = true;
		switch (item.getItemId()) {
			default:
				ret = super.onOptionsItemSelected(item);
				break;
			case MENU_ID_TEST1:
				//case 0:
				Versiondialog();
				ret = true;
				break;
			case MENU_ID_TEST2:
				Config_Select_Screen();
				//Change_Config_Screen();
				//Config_Screen_List();
				//Change_Config_Screen_Vibration();
				//Adcense_base adsense= new Adcense_base();
				//SetColor(adsense.Screen_Color);
				ret = true;
				break;
			/*
			case MENU_ID_TEST3:
				//Parsoal_List();
				ret = true;
				break;
			*/
			case MENU_ID_TEST3:
				Select_Wagoutype();
				ret = true;
				break;
			case MENU_ID_TEST4:
				               Change_Screen_Manual();
				              Help_Manual();
				              ret = true;
				              break;
		}
		return ret;
	}

	public void Parsoal_List() {
		final String[] array_list_data = getResources().getStringArray(R.array.ARRAY_List);
    	/*
    	for(int i=0;i!=10;i++)
    	{
    		final String[] buf_screen_list=new String[10];
    		buf_screen_list[i]=String.format((array_list_data[i],substring(R.string.Data_Statas_rate)));
    	}
    	*/
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(R.string.Config_Personal_Massage);

		alert.setSingleChoiceItems(array_list_data, mCheckedItem, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(Houji_Calender_JP.this, String.format("%s が選択されました", array_list_data[which]), Toast.LENGTH_SHORT).show();
				mCheckedItem = which;
			}
		});
		alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (mCheckedItem) {
					case 0:

						break;
					case 1:

						break;
					case 2:
						break;
				}
			}
		});
		alert.show();
	}

	public void Config_Select_Screen() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(R.string.Config_Menu);

		alert.setSingleChoiceItems(items, mCheckedItem, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(Houji_Calender_JP.this, String.format("%s が選択されました", items[which]), Toast.LENGTH_LONG).show();
				mCheckedItem = which;
			}
		});
		alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (mCheckedItem) {
					case 0:
						Config_Set_Color_Dialog();
						break;
					case 1:
						Config_Set_Vibrate_Dialog();
						break;
				}
			}
		});
		alert.show();
	}

	public void Config_Set_Color_Dialog() {
		//final int mCheckedItem = -1;
		load_Screen_Preferences();


		//final String[] items = {"item1","item2","item3"};
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(R.string.Change_Color_Title);
		alert.setSingleChoiceItems(items2, Screen_Color, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				//0:"オレンジ", 1:"緑(Default)", 2:"青",3:"ピンク",4:"白"
				Toast.makeText(Houji_Calender_JP.this, String.format("%s が選択されました", items2[which]), Toast.LENGTH_LONG).show();
				mCheckedItem = which;
				Screen_Color = which;  //
			}
		});
		alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(Houji_Calender_JP.this, String.format("%s に変更しました", items2[Screen_Color]), Toast.LENGTH_LONG).show();
				SetColor1(Screen_Color);
				//設定を保存する
				SaveScreen_Color_Preferences();
			}
		});
		alert.show();

	}

	public void Config_Set_Vibrate_Dialog() {
		//final int mCheckedItem = -1;


		load_Vibration_Preferences();
		//final String[] items = {"item1","item2","item3"};
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(R.string.Change_Vibration_Title);
		alert.setSingleChoiceItems(items3, VIBRATION, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(Houji_Calender_JP.this, String.format("%s が選択されました", items3[which]), Toast.LENGTH_LONG).show();
				VIBRATION = which;
			}
		});
		alert.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(Houji_Calender_JP.this, String.format("%s に変更しました", items3[VIBRATION]), Toast.LENGTH_LONG).show();
				ON_OFF_Vibrate(VIBRATION);
				//バイブの設定を保存
				SaveScreen_Vibration_Preferences();
			}
		});
		alert.show();

	}

	public void ON_OFF_Vibrate(int vibrate) {
		switch (vibrate) {
			case 1: //1:ONのとき、バイブを鳴らす
				vibration();
				break;
			case 0:
				break;
		}
	}

	//バイブレート
	public void vibration() {
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		long[] pattern = new long[]{0, 350, 500};
		vibrator.vibrate(pattern, -1);
	}
//  マニュアル
    public void Help_Manual()
	{
		Change_Screen_Manual();
	}
	//Menu部分 ここまで
	//Menu部分 ここまで
	public void SetColor1(int color) {

		TableLayout Screen_color = (TableLayout) findViewById(R.id.Screen_Color);
		Resources res = getResources();
		//"オレンジ", "緑(Default)", "青","ピンク","白"
		int Orenge_color = res.getColor(R.color.Orenge);
		int Lime_color = res.getColor(R.color.Lime);
		int Blue_color = res.getColor(R.color.Blue);
		int Pink_color = res.getColor(R.color.Pink);
		int Gray_color = res.getColor(R.color.Gray);

		switch (color) {
			case 0:
				Screen_color.setBackgroundColor(Orenge_color);
				break;
			case 1:
				Screen_color.setBackgroundColor(Lime_color);
				break;
			case 2:
				Screen_color.setBackgroundColor(Blue_color);
				break;
			case 3:
				Screen_color.setBackgroundColor(Pink_color);
				break;
			case 4:
				Screen_color.setBackgroundColor(Gray_color);
				break;
		}
	}
	//Base file 共通class　ここから
	//1.画面表示メソッド
	public void clear()  //画面消去
	{
		//TextView screen_view = (TextView) findViewById(R.id.view_screen);
		//screen_view.setText(R.string.Clear);
	}

	public void show_screen1(int Message) {
		TextView screen_view = (TextView) findViewById(R.id.view_screen);
		screen_view.setMovementMethod(ScrollingMovementMethod.getInstance());
		screen_view.setText(Message);
	}

	public void show_screen2(int Message, int flag) {
		TextView screen_view = (TextView) findViewById(R.id.view_screen);
		String buf_screen = String.format(getString(Message), flag);
		screen_view.setMovementMethod(ScrollingMovementMethod.getInstance());
		screen_view.setText(buf_screen);
	}

	public void show_screen3(int Message, int flag, int flag2) {
		TextView screen_view = (TextView) findViewById(R.id.view_screen);
		String buf_screen = String.format(getString(Message), flag, flag2);
		screen_view.setMovementMethod(ScrollingMovementMethod.getInstance());
		screen_view.setText(buf_screen);

	}

	public void show_screen4(int Message, int flag, int flag2, int flag3) {
		TextView screen_view = (TextView) findViewById(R.id.view_screen);
		String buf_screen = String.format(getString(Message), flag, flag2, flag3);
		screen_view.setMovementMethod(ScrollingMovementMethod.getInstance());
		screen_view.setText(buf_screen);

	}

	public void show_screen10(int Message, int flag, int flag2, int flag3, int flag4, int flag5, int flag6, int flag7, int flag8, int flag9, int flag10) {
		TextView screen_view = (TextView) findViewById(R.id.view_screen);
		String buf_screen = String.format(getString(Message), flag, flag2, flag3, flag4, flag5, flag6, flag7, flag8, flag9, flag10);
		screen_view.setMovementMethod(ScrollingMovementMethod.getInstance());
		screen_view.setText(buf_screen);

	}

	public void show_screen20(int Message, int flag, String flag2, int flag3, String flag4, int flag5, String flag6, int flag7, String flag8, int flag9, String flag10, int flag11, String flag12, int flag13, String flag14, int flag15, String flag16, int flag17, String flag18, int flag19) {
		//今年:平成%1$d年\n法事年一覧リスト\n一周忌:%2$s%3$d年\n三周忌:%4$s%5$d年\n七周忌:%6$s%7$d年\n十三周忌:%8$s%9$d年\n二十三周忌:%10$s%11$d年\n二十七周忌:%12$s%13$d年\n三十三周忌:%14$s%15$d年\n三十七周忌:%16$s%17$d年\n五十周忌:%18$s%19$d年
		TextView screen_view = (TextView) findViewById(R.id.view_screen);
		String buf_screen = String.format(getString(Message), flag, flag2, flag3, flag4, flag5, flag6, flag7, flag8, flag9, flag10, flag11, flag12, flag13, flag14, flag15, flag16, flag17, flag18, flag19);
		screen_view.setMovementMethod(ScrollingMovementMethod.getInstance());
		screen_view.setText(buf_screen);
	}

	public void buf_show_screen(String Message, String flag) {
		buf_screen = String.format(Message, flag);
	}
	//1.画面表示メソッド　ここまで
    //2.テキストスピーチメッセージ
	public void Text_speach_Message1(int Message)
	{
		String buf_apeach_Message = String.format(getString(Message));
		tts.speak(buf_apeach_Message, TextToSpeech.QUEUE_FLUSH, null);
	}
	public void Text_speach_Message2(int Message,int flag1)
	{
		String buf_apeach_Message = String.format(getString(Message),flag1);
		tts.speak(buf_apeach_Message, TextToSpeech.QUEUE_FLUSH, null);
	}
	public void Text_speach_Message3(int Message,int flag1,int flag2)
	{
		String buf_apeach_Message = String.format(getString(Message),flag1,flag2);
		tts.speak(buf_apeach_Message, TextToSpeech.QUEUE_FLUSH, null);
	}
	public void Text_speach_Message4(int Message,int flag1,int flag2,int flag3)
	{
		String buf_apeach_Message = String.format(getString(Message),flag1,flag2,flag3);
		tts.speak(buf_apeach_Message, TextToSpeech.QUEUE_FLUSH, null);
	}
	//2.テキストスピーチメッセージ　ここまで
	//メッセージダイアログ
	public void Result_MessegeDialogScreen(int Title, String Message) {
		AlertDialog.Builder AlertDlgBldr = new AlertDialog.Builder(this);
		AlertDlgBldr.setTitle(Title);
		AlertDlgBldr.setMessage(Message);
		AlertDlgBldr.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				All_Clear();
			}

		});
		AlertDialog AlertDlg = AlertDlgBldr.create();
		AlertDlg.show();
	}

	//エラーダイアログ
	public void Input_Error_Dialog(int Title, int Message) {
		AlertDialog.Builder AlertDlgBldr = new AlertDialog.Builder(this);
		AlertDlgBldr.setTitle(Title);
		AlertDlgBldr.setMessage(Message);
		AlertDlgBldr.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}

		});
		AlertDialog AlertDlg = AlertDlgBldr.create();
		AlertDlg.show();
	}

	public void Errordialog() {
		AlertDialog.Builder AlertDlgBldr = new AlertDialog.Builder(this);
		AlertDlgBldr.setTitle(R.string.Error_dialog_Title);
		AlertDlgBldr.setMessage(R.string.Error_dialog_Message);
		AlertDlgBldr.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

			}

		});
		AlertDialog AlertDlg = AlertDlgBldr.create();
		AlertDlg.show();
	}

	public void Select_Syouwa_HeiseiList() {
		//平成か昭和を選ぶダイアログ
		String[] Select_Syouwa_Heisei_List = getResources().getStringArray(R.array.Select_Wareki_type);
		final String[] Select_Wagou_type = {Select_Syouwa_Heisei_List[1], Select_Syouwa_Heisei_List[2]};
		Select_syouwa_Heisei_List = -1;

		//final int defaultwagoutype = -1; // デフォルトでチェックされているアイテム

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(Select_Syouwa_Heisei_List[0]);
		alert.setSingleChoiceItems(Select_Wagou_type, Select_syouwa_Heisei_List, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Select_syouwa_Heisei_List = which;
			}
		})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (Select_syouwa_Heisei_List) {
							case 0: //昭和を西暦に変換
								Select_Wareki_Seireki_SyouwaList();
								break;
							case 1: //平成を西暦に変換
								Select_Wareki_Heiei_Seireki_HeiseiList();
								break;
						}
						defaultwagoutype = -1;
					}

				})

				.show();

	}

	//和号（平成、昭和の選択),西暦の選択肢
	public void Select_Wagoutype() {
		String[] Select_Wagou_List = getResources().getStringArray(R.array.NenGou_Change_Select_type);
		final String[] Select_Wagou_type = {Select_Wagou_List[1], Select_Wagou_List[2]};
		defaultwagoutype = -1;

		//final int defaultwagoutype = -1; // デフォルトでチェックされているアイテム

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(Select_Wagou_List[0]);
		alert.setSingleChoiceItems(Select_Wagou_type, defaultwagoutype, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				defaultwagoutype = which;
			}
		})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (defaultwagoutype) {
							case 0:
								//（昭和か平成）から西暦を選択
								Select_Syouwa_HeiseiList();


								break;
							case 1:  //西暦から和暦(平成　or 昭和)に変換
								Input_YearAD_Dialog();
								break;
						}
						defaultwagoutype = -1;
					}

				})

				.show();
	}

	//1.和号 昭和を西暦に変換
	public void Select_Wareki_Seireki_SyouwaList() {
		String[] Select_Wagou_List_Syouwa = getResources().getStringArray(R.array.Select_Syouwa_List);
		final String[] SyouwaList = {Select_Wagou_List_Syouwa[1], Select_Wagou_List_Syouwa[2], Select_Wagou_List_Syouwa[3], Select_Wagou_List_Syouwa[4],
				Select_Wagou_List_Syouwa[5], Select_Wagou_List_Syouwa[6], Select_Wagou_List_Syouwa[7], Select_Wagou_List_Syouwa[8],
				Select_Wagou_List_Syouwa[9], Select_Wagou_List_Syouwa[10], Select_Wagou_List_Syouwa[11], Select_Wagou_List_Syouwa[12],
				Select_Wagou_List_Syouwa[13], Select_Wagou_List_Syouwa[14], Select_Wagou_List_Syouwa[15], Select_Wagou_List_Syouwa[16],
				Select_Wagou_List_Syouwa[17], Select_Wagou_List_Syouwa[18], Select_Wagou_List_Syouwa[19], Select_Wagou_List_Syouwa[20],
				Select_Wagou_List_Syouwa[21], Select_Wagou_List_Syouwa[22], Select_Wagou_List_Syouwa[23], Select_Wagou_List_Syouwa[24],
				Select_Wagou_List_Syouwa[25], Select_Wagou_List_Syouwa[26], Select_Wagou_List_Syouwa[27], Select_Wagou_List_Syouwa[28],
				Select_Wagou_List_Syouwa[29], Select_Wagou_List_Syouwa[30], Select_Wagou_List_Syouwa[31], Select_Wagou_List_Syouwa[32],
				Select_Wagou_List_Syouwa[33], Select_Wagou_List_Syouwa[34], Select_Wagou_List_Syouwa[35], Select_Wagou_List_Syouwa[36],
				Select_Wagou_List_Syouwa[37], Select_Wagou_List_Syouwa[38], Select_Wagou_List_Syouwa[39], Select_Wagou_List_Syouwa[40],
				Select_Wagou_List_Syouwa[41], Select_Wagou_List_Syouwa[42], Select_Wagou_List_Syouwa[43], Select_Wagou_List_Syouwa[44],
				Select_Wagou_List_Syouwa[45], Select_Wagou_List_Syouwa[46], Select_Wagou_List_Syouwa[47], Select_Wagou_List_Syouwa[48],
				Select_Wagou_List_Syouwa[49], Select_Wagou_List_Syouwa[50], Select_Wagou_List_Syouwa[51], Select_Wagou_List_Syouwa[52],
				Select_Wagou_List_Syouwa[53], Select_Wagou_List_Syouwa[54], Select_Wagou_List_Syouwa[55], Select_Wagou_List_Syouwa[56],
				Select_Wagou_List_Syouwa[57], Select_Wagou_List_Syouwa[58], Select_Wagou_List_Syouwa[59], Select_Wagou_List_Syouwa[60],
				Select_Wagou_List_Syouwa[61], Select_Wagou_List_Syouwa[62], Select_Wagou_List_Syouwa[63], Select_Wagou_List_Syouwa[64]

		};


		Select_syouwa_List = -1; // デフォルトでチェックされているアイテム

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(Select_Wagou_List_Syouwa[0]);
		alert.setSingleChoiceItems(SyouwaList, Select_syouwa_List, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Select_syouwa_List = which;

			}
		})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (Select_syouwa_List) {
							case 0:
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							case 7:
							case 8:
							case 9:
							case 10:
							case 11:
							case 12:
							case 13:
							case 14:
							case 15:
							case 16:
							case 17:
							case 18:
							case 19:
							case 20:
							case 21:
							case 22:
							case 23:
							case 24:
							case 25:
							case 26:
							case 27:
							case 28:
							case 29:
							case 30:
							case 31:
							case 32:
							case 33:
							case 34:
							case 35:
							case 36:
							case 37:
							case 38:
							case 39:
							case 40:
							case 41:
							case 42:
							case 43:
							case 44:
							case 45:
							case 46:
							case 47:
							case 48:
							case 49:
							case 50:
							case 51:
							case 52:
							case 53:
							case 54:
							case 55:
							case 56:
							case 57:
							case 58:
							case 59:
							case 60:
							case 61:
							case 62:
							case 63:
							case 64:
							case 65:
								Show_Seireki_Dialog(Select_syouwa_List+1);
								break;
							default:
								break;
						}
						Select_syouwa_List = -1;
					}
				})

				.show();
	}

	//2.和号 平成を西暦に変換
	public void Select_Wareki_Heiei_Seireki_HeiseiList() {
		String[] Select_Wagou_List_Heisei = getResources().getStringArray(R.array.Select_Heisei_List);


		final String[] HeiseiList = {Select_Wagou_List_Heisei[1], Select_Wagou_List_Heisei[2], Select_Wagou_List_Heisei[3], Select_Wagou_List_Heisei[4],
				Select_Wagou_List_Heisei[5], Select_Wagou_List_Heisei[6], Select_Wagou_List_Heisei[7], Select_Wagou_List_Heisei[8],
				Select_Wagou_List_Heisei[9], Select_Wagou_List_Heisei[10], Select_Wagou_List_Heisei[11], Select_Wagou_List_Heisei[12],
				Select_Wagou_List_Heisei[13], Select_Wagou_List_Heisei[14], Select_Wagou_List_Heisei[15], Select_Wagou_List_Heisei[16],
				Select_Wagou_List_Heisei[17], Select_Wagou_List_Heisei[18], Select_Wagou_List_Heisei[19], Select_Wagou_List_Heisei[20],
				Select_Wagou_List_Heisei[21], Select_Wagou_List_Heisei[22], Select_Wagou_List_Heisei[23], Select_Wagou_List_Heisei[24],
				Select_Wagou_List_Heisei[25], Select_Wagou_List_Heisei[26], Select_Wagou_List_Heisei[27], Select_Wagou_List_Heisei[28],
		};

		Select_Heisei_List = -1; // デフォルトでチェックされているアイテム

		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(Select_Wagou_List_Heisei[0]);
		alert.setSingleChoiceItems(HeiseiList, Select_Heisei_List, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Select_Heisei_List = which;

			}
		})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (Select_Heisei_List) {
							case 0:
							case 1:
							case 2:
							case 3:
							case 4:
							case 5:
							case 6:
							case 7:
							case 8:
							case 9:
							case 10:
							case 11:
							case 12:
							case 13:
							case 14:
							case 15:
							case 16:
							case 17:
							case 18:
							case 19:
							case 20:
							case 21:
							case 22:
							case 23:
							case 24:
							case 25:
							case 26:
							case 27:

								Show_Seireki_Dialog2(Select_Heisei_List + 1);
								break;
							default:
								break;
						}
						Select_Heisei_List = -1;
					}
				})

				.show();


	}

	public void Input_YearAD_Dialog() {
		//西暦を和暦に変換する
		//数字入力、４桁に制限
		final EditText editView = new EditText(Houji_Calender_JP.this);
		InputFilter[] filters = new InputFilter[1];
		filters[0] = new InputFilter.LengthFilter(4);
		editView.setFilters(filters);

		new AlertDialog.Builder(Houji_Calender_JP.this)

				.setTitle(R.string.Title_Seireki_Wareki_Henkan)
				//setViewにてビューを設定します。R.string.
				.setView(editView)

				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String str = editView.getText().toString();
						int Year = Integer.parseInt(str);
						Show_YearAD_Wareki(Year);
						//入力した文字をトースト出力する
					}
				}).show();

	}

	//1.和暦(昭和)を西暦に変換結果ダイアログ
	public void Show_Seireki_Dialog(int SyuwaNo) {


		String[] Select_Wagou_List_Syouwa = getResources().getStringArray(R.array.Select_Syouwa_List);
		final String Result_Syouwa_SeirekiYear = String.format(getString(R.string.Result_Syouwa_Heisei_Seireki), Select_Wagou_List_Syouwa[SyuwaNo], 1924 + SyuwaNo);
		Result_MessegeDialogScreen(R.string.Title_Syouwa_Seireki, Result_Syouwa_SeirekiYear);

	}

	//2.和暦(平成)を西暦に変換結果ダイアログ
	public void Show_Seireki_Dialog2(int HeiseiNo) {


		String[] Select_Wagou_List_Syouwa = getResources().getStringArray(R.array.Select_Heisei_List);
		final String Result_Heisei_SeirekiYear = String.format(getString(R.string.Result_Syouwa_Heisei_Seireki), Select_Wagou_List_Syouwa[HeiseiNo], 1988 + HeiseiNo);
		Result_MessegeDialogScreen(R.string.Title_Heisei_Seireki, Result_Heisei_SeirekiYear);

	}

	//3.西暦を和歴(昭和)に変換
	public void Show_Seireki_Dialog3(int Syouwa_Year) {
		//西暦から昭和に変換
		int Syouwa_No = Syouwa_Year - 1925;
		final String Result_SeirekiYear_SyouwaYear = String.format(getString(R.string.Message_SeirekiWareki), Syouwa_Year, Syouwa_No);
		Result_MessegeDialogScreen(R.string.Title_SeirekiWareki, Result_SeirekiYear_SyouwaYear);
	}

	//3.西暦を和歴(平成)に変換
	public void Show_Seireki_Dialog4(int Heisei_Year) {
		//西暦から昭和に変換
		int Heisei_No = Heisei_Year - 1988;
		if (Heisei_Year == 1989) {
			final String Result_SeirekiYear_HeiseiYear = String.format(getString(R.string.Message_SeirekiWareki_HeiseiGannen_Syouwa64Year), Heisei_Year);
			Result_MessegeDialogScreen(R.string.Title_SeirekiWareki, Result_SeirekiYear_HeiseiYear);
		} else {
			final String Result_SeirekiYear_HeiseiYear = String.format(getString(R.string.Message_SeirekiWareki2), Heisei_Year, Heisei_No);
			Result_MessegeDialogScreen(R.string.Title_SeirekiWareki, Result_SeirekiYear_HeiseiYear);
		}
	}

	public void Show_YearAD_Wareki(int year) {
		switch (year) {
			//和暦を昭和に変換
			case 1926:
			case 1927:
			case 1928:
			case 1929:
			case 1930:
			case 1931:
			case 1932:
			case 1933:
			case 1934:
			case 1935:
			case 1936:
			case 1937:
			case 1938:
			case 1939:
			case 1940:
			case 1941:
			case 1942:
			case 1943:
			case 1944:
			case 1945:
			case 1946:
			case 1947:
			case 1948:
			case 1949:
			case 1950:
			case 1951:
			case 1952:
			case 1953:
			case 1954:
			case 1955:
			case 1956:
			case 1957:
			case 1958:
			case 1959:
			case 1960:
			case 1961:
			case 1962:
			case 1963:
			case 1964:
			case 1965:
			case 1966:
			case 1967:
			case 1968:
			case 1969:
			case 1970:
			case 1971:
			case 1972:
			case 1973:
			case 1974:
			case 1975:
			case 1976:
			case 1977:
			case 1978:
			case 1979:
			case 1980:
			case 1981:
			case 1982:
			case 1983:
			case 1984:
			case 1985:
			case 1986:
			case 1987:
			case 1988:
				Show_Seireki_Dialog3(year);
				break;


			//和暦を平成に変換
			case 1989:  //平成元年、昭和64年
			case 1990:
			case 1991:
			case 1992:
			case 1993:
			case 1994:
			case 1995:
			case 1996:
			case 1997:
			case 1998:
			case 1999:
			case 2000:
			case 2001:
			case 2002:
			case 2003:
			case 2004:
			case 2005:
			case 2006:
			case 2007:
			case 2008:
			case 2009:
			case 2010:
			case 2011:
			case 2012:
			case 2013:
			case 2014:
			case 2015:
			case 2016:
			case 2017:
			case 2018:
				Show_Seireki_Dialog4(year);
				break;
			default:
				Input_Error_Dialog(R.string.Title_Error_Dialog, R.string.Message_Error_Dialog);

				break;
		}
	}
    //広告表示
    //public void Show_Adsence(){



    //}
	//バージョン

	public void Versiondialog() {
	    /*
		final MaterialDialog mMaterialDialog = new MaterialDialog(this);
		mMaterialDialog.setBackgroundResource(R.color.accent);
		mMaterialDialog.setTitle(R.string.Version_Title);
		mMaterialDialog.setMessage(R.string.VersionMessage);
		mMaterialDialog.setPositiveButton(R.string.OK,new OnClickListener() {
			@Override
			public void onClick(View v) {
				mMaterialDialog.dismiss();
			}

		});*/
				//mMaterialDialog.dismiss();
               //  finish();





		//mMaterialDialog.show();

		AlertDialog.Builder AlertDlgBldr = new AlertDialog.Builder(this);
		AlertDlgBldr.setTitle(R.string.Version_Title);
		AlertDlgBldr.setMessage(R.string.VersionMessage);
		AlertDlgBldr.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}

		});
		AlertDialog AlertDlg = AlertDlgBldr.create();
		AlertDlg.show();

	}

	//Yes no dialogのひな形　
	public void Yes_No_Dialog_base() {
		new AlertDialog.Builder(this)
				.setTitle("Hello, AlertDialog!")
				.setPositiveButton(
						R.string.Yes,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						})
				.setNegativeButton(
						R.string.No,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						})
				.show();
	}
	//Base file 共通class ここまで
	@Override
	public void onClick(View v) {

		Button one_Button = (Button) findViewById(R.id.one_button);
		Button two_Button = (Button) findViewById(R.id.two_button);
		Button three_Button = (Button) findViewById(R.id.three_button);
		Button four_Button = (Button) findViewById(R.id.four_button);
		Button five_Button = (Button) findViewById(R.id.five_button);
		Button six_Button = (Button) findViewById(R.id.six_button);
		Button seven_Button = (Button) findViewById(R.id.seven_button);
		Button eight_Button = (Button) findViewById(R.id.eight_button);
		Button nine_Button = (Button) findViewById(R.id.nine_button);
		Button EXE_Button = (Button) findViewById(R.id.exe_button);
		Button Clear_Button = (Button) findViewById(R.id.clear_button);
		Button zero_Button = (Button) findViewById(R.id.zero_button);


		if (v.equals(one_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 1;
			switch (flag) {
				case 1:
					//和暦を選択
					mode = 1;
					Select_Input_Dead_Year_Number();
					break;
				case 2:
					mode = 2;
					Select_Input_Nengou_type_Number();
					break;

				case 3: //平成を選択
					mode = 3;
					Select_Wagou_Heisei_Shouwa_Voice();
					Select_Wareki_Syouwa_Heisei_Number_EXE();
					break;
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 11: //和暦を選択
					mode = 13;
					Input_Select_Nendai_Seireki_Wareki();
					break;
				case 12:
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14: //Select2西暦入力
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;
				case 16:
					mode = 20;
					Input_Select_Nendai_Seireki_Wareki();
					break;
				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(two_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 2;
			switch (flag) {
				case 1:
					mode = 12;
					Select_Input_Dead_Year_Number();
					break;
				case 2: //西暦を選択
					mode = 9;
					Select_Input_Nengou_type_Number();
					break;
				case 3: //昭和を選択
					mode = 6;
					Select_Wagou_Heisei_Shouwa_Voice();
					Select_Wareki_Syouwa_Heisei_Number_EXE();
					break;
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 11:
					mode = 16;
					Input_Select_Nendai_Seireki_Wareki();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;
				case 16:
					mode = 22;
					Input_Select_Nendai_Seireki_Wareki();
					break;
				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(three_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 3;
			switch (flag) {
				case 1:
					mode = 19;
					Select_Input_Dead_Year_Number();
					break;
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;
				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(four_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 4;
			switch (flag) {
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;

				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(five_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 5;
			switch (flag) {
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;

				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(six_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 6;
			switch (flag) {
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;

				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(seven_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 7;
			switch (flag) {
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;

				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(eight_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 8;
			switch (flag) {
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;

				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(nine_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 9;
			switch (flag) {
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;

				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(zero_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 0;
			switch (flag) {
				case 4:
					mode = 4;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 5:
					mode = 5;
					Input_Wareki_Heisei_This_Year();
					break;
				case 6: //何もしない
					break;
				case 7:
					mode = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;
				case 8:
					mode = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 9:
					mode = 10;
					Input_Death_Year_Seireki();
					break;
				case 10:
					mode = 11;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 12: //平成の今年入力
					mode = 14;
					Input_Wareki_This_Year();

					break;
				case 13:
					mode = 15;
					Input_Dead_Year();
					break;
				case 14:
					mode = 17;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 15:
					mode = 18;
					Input_Dead_Year();
					break;

				case 17:
					mode = 21;
					Input_Wareki_This_Year();
					break;
				case 18:
					mode = 23;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
			}
		} else if (v.equals(EXE_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			No = 0;
			//Change_Screen();
			switch (mode) {
				case 0:
					flag = 1;
					//マイクを起動
					//Start_Record_Dialog();
					//startListening();
					Text_speach_Message1(R.string.speach_please_Talk_Message_TopMenu1_Select);
					show_screen2(R.string.Push_Select_TopMenu_No_Select, No);
					break;
				case 1:
					flag = 2;
					//Start_Record_Dialog();
					// インテント発行
					//startActivityForResult(intent, REQUEST_CODE);
					Text_speach_Message1(R.string.speach_please_Talk_Message_Select_Nengou_Wareki_seireki);
					show_screen2(R.string.Push_Select_Message_No_Select,No);
					break;
				case 2:
					flag = 3;
					Select_Wareki_Syouwa_Heisei_Number_EXE();
					Select_Wagou_Heisei_Shouwa_Voice();
					break;
				case 3:
					//平成年号入力
					flag = 4;
					count = 0;
					Input_Wareki_Heisei_EXE_Number();
					break;
				case 4:
					flag = 5;
					count = 0;
					Input_Wareki_Heisei_This_Year();
					break;
				case 5:
					count = 0;
					flag = 6;

					Result_Complate_death_year_Heisei();
					break;
				case 6: //昭和を選択した時
					count = 0;
					flag = 7;
					Input_Wareki_Syouwa_EXE_Number();
					break;

				case 7:
					count = 0;
					flag = 8;
					Input_Wareki_Heisei_This_Year();
					break;
				case 8:
					flag = 6;
					//ここに広告を追加
					Result_Complate_death_year_Syouwa();
					break;
				case 9:
					flag = 9;
					Input_Death_Year_Seireki();
					break;
				case 10:
					flag = 10;
					Input_Death_Year_Seireki_ThisYear();
					break;
				case 11:
					flag = 6;
					//ここに広告を追加
					Change_Screen_Adsence();
					Result_Complate_death_year_Seireki();
					break;
				//TopMenu Select2
				case 12: //和暦、西暦の選択
					flag = 11;
					Input_Select_Nendai_Seireki_Wareki();
					break;

				case 13: //今年の年を入力(平成)
					count = 0;
					flag = 12;
					Input_Wareki_This_Year();
					break;
				case 14: //何回忌か入力
					count = 0;
					flag = 13;
					Input_Dead_Year();
					break;
				case 15:
					switch (Dead_Year) {
						case 1:
						case 3:
						case 7:
						case 13:
						case 17:
						case 23:
						case 27:
						case 33:
						case 37:
						case 50:
							flag = 6;
							mode = 15;
							//ここに広告を追加
							Result_Dead_Year_EXE_Number();
							break;
						default:
							Input_Error_Dialog(R.string.Error_dialog_Title, R.string.Error_dialog_Message);
							count = 0;
							mode = 14;
							Dead_Year = 0;
							Input_Dead_Year();
							break;
					}

					break;
				case 16:
					count = 0;
					flag = 14;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 17:
					flag = 15;
					count=0;
					Input_Dead_Year();
					break;
				case 18: //結果表示西暦入力
					switch (Dead_Year) {
						case 1:
						case 3:
						case 7:
						case 13:
						case 17:
						case 23:
						case 27:
						case 33:
						case 37:
						case 50:
							flag = 6;
							mode = 18;
							Result_Dead_Year_Seireki_EXE_Number();
							break;
						default:
							Input_Error_Dialog(R.string.Error_dialog_Title, R.string.Error_dialog_Message);
							count = 0;
							mode = 17;
							Dead_Year = 0;
							Input_Dead_Year();
							break;
					}
					break;
				case 19: //Select3を選択和暦西暦の選択
					flag = 16;
					Input_Select_Nendai_Seireki_Wareki();
					break;
				case 20:
					flag = 17;
					Input_Wareki_This_Year();
					break;
				case 21:
					mode = 21;
					flag = 6;
					Result_Houjireki_List_Wareki();
					break;
				case 22: //Select3 西暦入力
					flag = 18;
					Input_Seireki_Menus2_Select2_Number_EXE();
					break;
				case 23:
					flag = 6;
					mode = 23;
					//ここに広告を追加
					Change_Screen_Adsence();
					Result_Houjireki_List_Seireki();
					break;
			}
		} else if (v.equals(Clear_Button)) {
			ON_OFF_Vibrate(VIBRATION);
			//Change_Screen_Adsence();
			How_Reword_Yes_No();


			//show_Adsence();
	//		stopListening();
			All_Clear();
			Text_speach_Message1(R.string.speach_All_Clear_Message);
		}
	}

    private void show_Adsence() {

        Activity activityContext = (Activity) context;
        if (rewardedAd.isLoaded()) {
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                public void onRewardedAdOpened() {
                    // Ad opened.
                    loadRewardedVideoAd();
                }

                public void onRewardedAdClosed() {
                    // Ad closed.
                }

                public void onUserEarnedReward(@NonNull com.google.android.gms.ads.rewarded.RewardItem reward) {
                    // User earned reward.
                }

                public void onRewardedAdFailedToShow(int errorCode) {
                    // Ad failed to display
                }
            };
            rewardedAd.show(activityContext, adCallback);
        } else Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        // finish();
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
}



