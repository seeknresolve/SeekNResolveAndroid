package org.seeknresolve.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

import org.seeknresolve.android.R;
import org.seeknresolve.android.SeekNResolveAndroid;
import org.seeknresolve.android.model.User;
import org.seeknresolve.android.model.UserProvider;
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

public class LogInActivity extends Activity {

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

    @Inject
    UserProvider loggedUserProvider;

    @Inject @Named("sessionIdName")
    String sessionIdName;

    @Inject
    RestAdapter.Builder restAdapterBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((SeekNResolveAndroid) getApplication()).inject(this);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.logIn)
    public void logIn() {
        clearErrors();
        if (isInputFilled()) {
            String login = loginView.getText().toString();
            String password = passwordView.getText().toString();
            getSeekNResolveApi().login(login, password, getMyApiIndexCallback());
        }
    }

    private void clearErrors() {
        urlView.setError(null);
        loginView.setError(null);
        passwordView.setError(null);
    }

    private boolean isInputFilled() {
        boolean isFilled = isEditTextNotEmpty(urlView, "Server url is required.");
        isFilled &= isEditTextNotEmpty(loginView, "Login is required.");
        isFilled &= isEditTextNotEmpty(passwordView, "Password is required.");
        return isFilled;
    }

    private boolean isEditTextNotEmpty(EditText editText, String error) {
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setError(error);
            return false;
        }
        return true;
    }

    private SeekNResolve getSeekNResolveApi() {
        return restAdapterBuilder
                .setEndpoint(urlView.getText().toString().trim())
                .build()
                .create(SeekNResolve.class);
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
                            setLoggedUser(cookie.getValue());
                            Intent intent = new Intent(getApplicationContext(), ProjectListActivity.class);
                            startActivity(intent);
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

    private void setLoggedUser(String sessionId) {
        User loggedUser = new User();
        loggedUser.setLogin(loginView.getText().toString());
        loggedUser.setSessionId(sessionId);
        loggedUserProvider.setLoggedUser(loggedUser);
    }
}