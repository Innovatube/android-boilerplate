package com.innovatube.boilerplate.ui.signup;

import android.text.TextUtils;

import com.innovatube.boilerplate.RxSchedulerOverrideRule;
import com.innovatube.boilerplate.data.InnovatubeRepository;
import com.innovatube.boilerplate.data.model.UserId;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.realm.Realm;
import retrofit2.Retrofit;
import rx.Observable;

import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * Created by quanlt on 27/12/2016.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Retrofit.class, Realm.class, TextUtils.class})
public class CreateAccountPresenterTest {
    @Mock
    InnovatubeRepository mMockInnovatubeRepository;
    @Mock
    CreateAccountMvpView mMockCreateAccountMvpView;
    @Mock
    Realm mMockRealm;
    @Mock
    Retrofit mMockRetrofit;

    @Rule
    private final RxSchedulerOverrideRule mOverrideRule = new RxSchedulerOverrideRule();

    private CreateAccountPresenter mCreateAccountPresenter;

    @Before
    public void setUp() throws Exception {
        mCreateAccountPresenter = new CreateAccountPresenter(mMockInnovatubeRepository
                , mMockRetrofit, mMockRealm);
        mCreateAccountPresenter.attachView(mMockCreateAccountMvpView);
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(any(CharSequence.class))).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                CharSequence charSequence = (CharSequence) invocation.getArguments()[0];
                return charSequence == null || charSequence.length() == 0;
            }
        });
    }

    @Test
    public void testCreateAccountSuccess() throws Exception {
        UserId userId = new UserId();
        userId.setUserId(1);
        when(mMockInnovatubeRepository.createAccount(anyString(), anyString(), anyString()
                , anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(userId));
        doNothing().when(mMockInnovatubeRepository).saveUserInfo(any(UserId.class),
                any(Realm.class));
        mCreateAccountPresenter.createAccount("1", "2", "3", "4", "5", "6", "7");
        verify(mMockCreateAccountMvpView).showProgressDialog(true);
        verify(mMockCreateAccountMvpView).showProgressDialog(false);
        verify(mMockCreateAccountMvpView).redirectToHome();
    }

    @Test
    public void testCreateAccountError() throws Exception {
        when(mMockInnovatubeRepository.createAccount(anyString(), anyString(), anyString()
                , anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.<UserId>error(new Exception("blah blah")));
        mCreateAccountPresenter.createAccount("1", "2", "3", "4", "5", "6", "7");
        verify(mMockCreateAccountMvpView).showProgressDialog(true);
        verify(mMockCreateAccountMvpView).showProgressDialog(false);
        verify(mMockCreateAccountMvpView).showAlertDialog("General error, please try again later");
    }

    @After
    public void tearDown() throws Exception {
        mCreateAccountPresenter.detachView();
        assertNull(mCreateAccountPresenter.getMvpView());
    }
}
