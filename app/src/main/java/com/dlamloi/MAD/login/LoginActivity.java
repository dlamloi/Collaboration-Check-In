package com.dlamloi.MAD.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dlamloi.MAD.R;
import com.dlamloi.MAD.register.RegisterActivity;
import com.dlamloi.MAD.viewgroups.ViewGroupsActivity;
import com.dlamloi.MAD.utilities.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    public static final int REGISTER_REQUEST_CODE = 1;
    public static final String USER_EMAIL_KEY = "user email";

    private LoginPresenter mLoginPresenter;

    @BindView(R.id.email_edittext)
    EditText mEmailEt;
    @BindView(R.id.password_edittext)
    EditText mPasswordEt;
    @BindView(R.id.login_button)
    Button mLoginBtn;
    @BindView(R.id.login_failed_textview)
    TextView mLoginFailedTv;
    @BindView(R.id.login_progressbar)
    ProgressBar mLoginPb;
    @BindView(R.id.username_imageview)
    ImageView mUsernameIv;
    @BindView(R.id.password_imageview)
    ImageView mPasswordIv;
    @BindView(R.id.signup_button)
    Button mSignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(this);
        shouldLoginBeEnabled();
    }


    @Override
    protected void onStart() {
        super.onStart();
        mLoginPresenter.onStart();
    }

    @OnFocusChange(R.id.email_edittext)
    public void emailEtChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mUsernameIv.setColorFilter(Color.WHITE);
            mEmailEt.setHintTextColor(Color.WHITE);
            mEmailEt.setTextColor(Color.WHITE);
        } else {
            mUsernameIv.clearColorFilter();
            mEmailEt.setHintTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));
            mEmailEt.setTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));
        }
    }

    @OnFocusChange(R.id.password_edittext)
    public void passwordEtChange(View v, boolean hasFocus) {
        if (hasFocus) {
            mPasswordIv.setColorFilter(Color.WHITE);
            mPasswordEt.setHintTextColor(Color.WHITE);
            mPasswordEt.setTextColor(Color.WHITE);
        } else {
            mPasswordIv.clearColorFilter();
            mPasswordEt.setHintTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));
            mPasswordEt.setTextColor(getResources().getColor(R.color.LoginRegisterEditText_TextColorHint));
        }
    }

    @OnTextChanged(value = {R.id.email_edittext, R.id.password_edittext},
            callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void shouldLoginBeEnabled() {
        boolean anyAnyEmptyFields = mEmailEt.getText().length() == 0
                || mPasswordEt.getText().length() == 0;
        mLoginBtn.setEnabled(!anyAnyEmptyFields);
        if (!anyAnyEmptyFields) {
            mLoginBtn.setBackground(getDrawable(R.drawable.round_button_enabled));
        } else {
            mLoginBtn.setBackground(getDrawable(R.drawable.round_button_disable));
        }
    }

    @OnClick(R.id.signup_button)
    public void signUpClick() {
        Utility.startIntent(this, RegisterActivity.class);
    }

    @OnClick(R.id.login_button)
    public void loginClick() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getApplicationWindowToken(), 0);
        String email = mEmailEt.getText().toString().trim();
        String password = mPasswordEt.getText().toString().trim();
        mLoginPresenter.login(email, password);
    }

    @Override
    public void setLoginProgressVisibility(boolean visibility) {
        setVisibility(visibility, mLoginPb);
    }

    @Override
    public void setLoginFailedTextViewVisiblity(boolean visibility) {
        setVisibility(visibility, mLoginFailedTv);

    }

    @Override
    public void setVisibility(boolean visibility, View view) {
        if (visibility) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setEmailNotVerified() {
        mLoginFailedTv.setText(getString(R.string.email_not_verified));
    }

    @Override
    public void loginSuccess() {
        Utility.startIntent(this, ViewGroupsActivity.class);
        finish();
    }
}

