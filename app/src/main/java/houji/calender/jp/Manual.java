package houji.calender.jp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import jp.co.fullspeed.polymorphicads.PolymorphicAds;
//import jp.co.fullspeed.polymorphicads.PolymorphicAdsCallback;

public class Manual extends Activity /*implements PolymorphicAdsCallback.Callbackable,
        PolymorphicAdsCallback.InitializeCallback,
        PolymorphicAdsCallback.RequestCallback,
        PolymorphicAdsCallback.DisplayCallback,
        PolymorphicAdsCallback.ClickCallback*/ {
    private static final String AD_UNIT_ID = "6f233fc9164421fa2e12f25fac0f215e";

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_manual);
        setTitle(R.string.Help_Manual_Title);

        //
        //setContentView(R.layout.manual);
        //レイアウトで指定したWebViewのIDを指定する。
        WebView myWebView = (WebView) findViewById(R.id.webView1);

        //リンクをタップしたときに標準ブラウザを起動させない
        myWebView.setWebViewClient(new WebViewClient());

        //最初にgoogleのページを表示する。
        myWebView.loadUrl("http://bahoosoft.net/Houji_calender/docs/index.html");

        //jacascriptを許可する
        myWebView.getSettings().setJavaScriptEnabled(true);
        //

        //final WebView webView = (WebView) findViewById(R.id.webview);
        //WebSettings webSettings = webView.getSettings();
        //webSettings.setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());
        //webView.loadUrl("file:///android_asset/index.html");
        /*
         PolymorphicAds.setLoggingMode(true);
         PolymorphicAds.setTestMode(false);
        // ① CV計測開始
       // PolymorphicAdsAnalytics.startTracking(this, CONVERSION_ID, this);
        // ① 広告ユニット初期化
        PolymorphicAds.init(this, AD_UNIT_ID, PolymorphicAds.AdType.BANNER, R.id.ad_container);

        // ② 広告ロード（Ad情報取得、表示）
        PolymorphicAds.load(AD_UNIT_ID);
*/
    }


    @Override
    protected void onPause() {
        super.onPause();
        // 広告ローテーション中断
       // PolymorphicAds.pause(AD_UNIT_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 広告ローテーション再開
        //PolymorphicAds.resume(AD_UNIT_ID);

        // （任意） コールバックセット
        //PolymorphicAds.setCallback(
        /*
                AD_UNIT_ID,
                this,
                PolymorphicAdsCallback.CallbackType.INITIALIZE,
                PolymorphicAdsCallback.CallbackType.REQUEST,
                PolymorphicAdsCallback.CallbackType.DISPLAY,
                PolymorphicAdsCallback.CallbackType.CLICK);
                */
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ② CV計測停止
        //PolymorphicAdsAnalytics.stopTracking(this);
    }
    /*
    @Override
    public void onSuccessClickAd(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onFailureClickAd(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onSuccessShowAd(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onFailureShowAd(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onSkipShowAd(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onCloseAd(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onFailureUseOverlay(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onSuccessInitRequest(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onFailureInitRequest(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onFailureSendAdRequest(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onSuccessResponseAdRequest(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onFailureResponseAdRequest(String s, PolymorphicAds.AdType adType) {

    }

    @Override
    public void onReceiveNoAds(String s, PolymorphicAds.AdType adType) {

    }
    */
}