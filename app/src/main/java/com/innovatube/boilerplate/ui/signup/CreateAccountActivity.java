package com.innovatube.boilerplate.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.innovatube.boilerplate.R;
import com.innovatube.boilerplate.ui.base.BaseActivityWithDialog;
import com.innovatube.boilerplate.ui.home.MainActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAccountActivity extends BaseActivityWithDialog implements
        CreateAccountMvpView,
        DatePickerDialog.OnDateSetListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_join)
    AppCompatButton btnJoin;

    @BindView(R.id.edt_first_name)
    AppCompatEditText edtFirstName;

    @BindView(R.id.edt_last_name)
    AppCompatEditText edtLastName;

    @BindView(R.id.edt_email_address)
    AppCompatEditText edtEmailAddress;

    @BindView(R.id.edt_password)
    AppCompatEditText edtPassword;

    @BindView(R.id.edt_confirm_password)
    AppCompatEditText edtConfirmPassword;

    @BindView(R.id.edt_dob)
    AppCompatTextView txtDob;

    @BindView(R.id.edt_promotion_code)
    AppCompatEditText edtPromotionCode;

    @BindView(R.id.divider_company)
    View dividerCompany;

    @Inject
    CreateAccountPresenter createAccountPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        createAccountPresenter.attachView(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.btn_join)
    public void createAccount() {
        String firstName = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();
        String emailAddress = edtEmailAddress.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPassword = edtConfirmPassword.getText().toString();
        String dob = txtDob.getText().toString();
        String promotionCode = edtPromotionCode.getText().toString();
        createAccountPresenter.createAccount(
                firstName,
                lastName,
                emailAddress,
                password,
                confirmPassword,
                dob,
                promotionCode
        );
    }

    @OnClick(R.id.ln_dob)
    public void showDob() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                CreateAccountActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.showYearPickerFirst(true);
        datePickerDialog.vibrate(false);
        datePickerDialog.show(getFragmentManager(), "Date Of Birth");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void redirectToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (createAccountPresenter != null) {
            createAccountPresenter.detachView();
        }
        dismissDialog();
        super.onDestroy();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(Calendar.YEAR, year);
        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        selectedDate.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String format = sdf.format(selectedDate.getTime());
        txtDob.setText(format);
    }

    @Override
    protected void setupDialogTitle() {
        String title = getString(R.string.title_activity_create_account);
        alertDialog.setTitle(title);
        progressDialog.setTitle(title);
    }
}
