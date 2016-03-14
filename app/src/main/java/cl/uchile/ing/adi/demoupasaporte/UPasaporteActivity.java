package cl.uchile.ing.adi.demoupasaporte;

import android.content.Intent;
import android.net.UrlQuerySanitizer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import cl.uchile.ing.adi.demoupasaporte.model.User;

public class UPasaporteActivity extends AppCompatActivity {
    private WebView webView;

    private static final String UPASAPORTE_SERVICE = ""; // Your U-Pasaporte's service ID
    private static final String UPASAPORTE_URL = "https://www.u-cursos.cl/upasaporte/login?servicio="+UPASAPORTE_SERVICE;
    private static final String UPASAPORTE_REDIRECT_URL = ""; //Your redirect URL defined in your UPasaporte script

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upasaporte);
        this.webView = (WebView)this.findViewById(R.id.webview);
        this.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(UPASAPORTE_REDIRECT_URL)) {
                    UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
                    sanitizer.setAllowUnregisteredParamaters(true);
                    sanitizer.registerParameter("alias", new UrlQuerySanitizer.ValueSanitizer() {
                        @Override
                        public String sanitize(String value) {
                            try {
                                return URLDecoder.decode(value, "ISO-8859-1");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                                return (new UrlQuerySanitizer.IllegalCharacterValueSanitizer(0)).sanitize(value);
                            }
                        }
                    });
                    sanitizer.parseUrl(url);
                    String alias = sanitizer.getValue("alias");
                    String img = sanitizer.getValue("img");
                    String sessId = sanitizer.getValue("PHPSESSID");
                    User user = new User(alias, img, sessId);

                    Intent i = new Intent();
                    i.putExtra("user", user);

                    UPasaporteActivity.this.setResult(1, i);
                    UPasaporteActivity.this.finish();
                    return false;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        if(UPASAPORTE_SERVICE.length() == 0 || UPASAPORTE_REDIRECT_URL.length() == 0){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.pasaporte_not_configured)
                    .show();
        }
        else this.webView.loadUrl(UPASAPORTE_URL);
    }
}
