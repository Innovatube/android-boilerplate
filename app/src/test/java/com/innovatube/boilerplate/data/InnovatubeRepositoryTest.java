package com.innovatube.boilerplate.data;

import com.innovatube.boilerplate.data.local.LocalDataSource;
import com.innovatube.boilerplate.data.local.PreferenceDataSource;
import com.innovatube.boilerplate.data.model.UserId;
import com.innovatube.boilerplate.data.remote.RemoteDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import io.realm.Realm;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by quanlt on 28/12/2016.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(Realm.class)
public class InnovatubeRepositoryTest {
    @Mock
    private LocalDataSource mMockLocalDataSource;
    @Mock
    private RemoteDataSource mMockRemoteDataSource;
    @Mock
    private PreferenceDataSource mMockPreferenceDataSource;
    @Mock
    private Realm mMockRealm;

    private InnovatubeRepository mInnovatubeRepository;

    @Before
    public void setUp() throws Exception {
        mInnovatubeRepository = new InnovatubeRepositoryImpl(mMockRemoteDataSource,
                mMockPreferenceDataSource, mMockLocalDataSource);
    }

    @Test
    public void testCreateAccount() throws Exception {
        mInnovatubeRepository.createAccount("1", "2", "3", "4", "5", "6", "7");
        verify(mMockRemoteDataSource, times(1))
                .createJobSeekerAccount("1", "2", "3", "4", "5", "6", "7");
    }

    @Test
    public void testSaveUserInfo() throws Exception {
        UserId userId = new UserId();
        mInnovatubeRepository.saveUserInfo(userId, mMockRealm);
        verify(mMockLocalDataSource, times(1)).saveUserInfo(userId, mMockRealm);
    }
}
