package org.seeknresolve.android.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import org.seeknresolve.android.R;
import org.seeknresolve.android.SeekNResolveAndroid;
import org.seeknresolve.android.rest.SeekNResolve;
import org.seeknresolve.android.rest.SessionIdInterceptor;

import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

public class MainActivity extends Activity {

    @InjectView(R.id.url)
    EditText urlView;

    @InjectView(R.id.login)
    EditText loginView;

    @InjectView(R.id.password)
    EditText passwordView;

    @InjectView(R.id.logIn)
    Button logInButton;

    @Inject
    OkHttpClient okHttpClient;

    @Inject
    OkClient okClient;

    @Inject
    SessionIdInterceptor sessionIdInterceptor;

    @Inject @Named("sessionIdName")
    String sessionIdName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        ((SeekNResolveAndroid) getApplication()).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @OnClick(R.id.logIn)
    public void logIn() {
        clearErrors();
        isInputFilled();
        String login = loginView.getText().toString();
        String password = passwordView.getText().toString();
        getMyRestApi().login(login, password, getMyApiIndexCallback());
    }

    private void clearErrors() {
        urlView.setError(null);
        logInButton.setError(null);
        passwordView.setError(null);
    }

    private boolean isInputFilled() {
        return !isEditTextEmpty(urlView, "Server url is required.") &&
                !isEditTextEmpty(loginView, "Login is required.") &&
                !isEditTextEmpty(passwordView, "Password is required.");
    }

    private boolean isEditTextEmpty(EditText editText, String error) {
        if (TextUtils.isEmpty(editText.getText())) {
            urlView.setError(error);
            return true;
        }
        return false;
    }

    private SeekNResolve getMyRestApi() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(urlView.getText().toString().trim())
                .setRequestInterceptor(sessionIdInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(okClient)
                .build();

        return restAdapter.create(SeekNResolve.class);
    }

    private Callback<Response> getMyApiIndexCallback() {
        return new Callback<Response>() {
            @Override
            public void success(Response myResponse, Response response) {
                try {
                    List<HttpCookie> cookieList = ((CookieManager) okHttpClient.getCookieHandler()).getCookieStore().get(new URI(response.getUrl()));
                    for (HttpCookie cookie : cookieList) {
                        if (cookie.getName().equals(sessionIdName)) {
                            sessionIdInterceptor.setSessionIdValue(cookie.getValue());
                            break;
                        }
                    }
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }
}



