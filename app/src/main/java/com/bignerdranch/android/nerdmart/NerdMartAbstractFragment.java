package com.bignerdranch.android.nerdmart;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.nerdmart.inject.Injector;
import com.bignerdranch.android.nerdmart.model.service.NerdMartServiceManager;
import com.bignerdranch.android.nerdmartservice.service.NerdMartServiceInterface;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class NerdMartAbstractFragment extends Fragment {
    @Inject
    NerdMartServiceManager mNerdMartServiceManager;
    private CompositeDisposable mCompositeDisposable;
    private ProgressDialog mDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //gets the graph
        Injector.obtain(getContext()).inject(this);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setUpLoadingDialog();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCompositeDisposable.clear();
    }

    public void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    private void setUpLoadingDialog() {
        mDialog = new ProgressDialog(getContext());
        mDialog.setIndeterminate(true);
        mDialog.setMessage(getString(R.string.loading_text));
    }

    protected <T> ObservableTransformer<T, T> loadingTransformer() {
        return observable -> observable
                .doOnSubscribe(disposable -> showDialog())
                .doOnComplete(() -> {
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                });
    }

    private void showDialog() {
        if(mDialog != null) {
            mDialog.show();
        }
    }
}
