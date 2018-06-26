package com.bignerdranch.android.nerdmart;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.bignerdranch.android.nerdmart.databinding.ActivityNerdmartAbstractBinding;
import com.bignerdranch.android.nerdmart.inject.Injector;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmart.viewmodel.NerdMartViewModel;
import com.bignerdranch.android.nerdmartservice.service.payload.Cart;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class NerdMartAbstractActivity extends AppCompatActivity {
    private CompositeDisposable mCompositeDisposable;
    @Inject
    NerdMartServiceManager mNerdMartServiceManager;
    @Inject
    NerdMartViewModel mNerdMartViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCompositeDisposable = new CompositeDisposable();
        Injector.obtain(this).inject(this);
        ActivityNerdmartAbstractBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_nerdmart_abstract);
        binding.setLogoutButtonClickListener(v -> signout());
        binding.setNerdMartViewModel(mNerdMartViewModel);
        if (savedInstanceState == null) {
            getSupportFragmentManager() .beginTransaction()
                    .add(binding.activityAbstractNerdmartFragmentFrame.getId(),
                            getFragment())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    protected abstract Fragment getFragment();

    public void updateCartStatus(Cart cart) {
        mNerdMartViewModel.updateCartStatus(cart);
    }

    private void signout() {
        addDisposable(mNerdMartServiceManager
                .signout()
                .subscribe(aBoolean -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }));
    }
}
