package houji.calender.jp;
import houji.calender.jp.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.ads.mediation.AbstractAdViewAdapter;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

//広告画面
// 動画アワード
public class Adcence_base extends Activity implements RewardedVideoAdListener,OnClickListener {
    private RewardedAd rewardedAd;
    private RewardedVideoAd mRewardedVideoAd;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity1);

        //Button Adsence = (Button) findViewById(R.id.Button_Adsence);
        //Adsence.setOnClickListener((OnClickListener) this);
        final Button Button1 = (Button) findViewById(R.id.Button_Adsence);
        Button1.setOnClickListener((OnClickListener) this);
        MobileAds.initialize(this, "ca-app-pub-7821909322657049/7278841796");
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        //loadRewardedVideoAd();
        rewardedAd = new RewardedAd(this,
                "ca-app-pub-7821909322657049/7278841796");
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

    }

    //setTitle(R.string.app_namwe_advance);
    //Adsence = (Button) findViewById(R.id.Button_Adsence);
    //Adsence.setOnClickListener((OnClickListener) this);
    //Button backBtn = (Button) findViewById(R.id.Button_Adsence);

        private Object context;

        public void onClick (View v){
                /*
                if (v.equals(skip_Button))
                {
                    finish();
                }
                */

            Activity activityContext = (Activity) context;
            if (rewardedAd.isLoaded()) {
                RewardedAdCallback adCallback = new RewardedAdCallback() {
                    public void onRewardedAdOpened() {
                        // Ad opened.
                    }

                    public void onRewardedAdClosed() {
                        // Ad closed.
                    }

                    public void onUserEarnedReward(@NonNull RewardItem reward) {
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
        // finish();



   /*
    if (mRewardedVideoAd.isLoaded()) {
        mRewardedVideoAd.show();
    }
    */
   private void loadRewardedVideoAd() {
       mRewardedVideoAd.loadAd("ca-app-pub-7821909322657049/7278841796",
               new AdRequest.Builder().build());
   }



    @Override
    public void onRewardedVideoAdLoaded() {

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
    public void onRewarded(com.google.android.gms.ads.reward.RewardItem rewardItem) {

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