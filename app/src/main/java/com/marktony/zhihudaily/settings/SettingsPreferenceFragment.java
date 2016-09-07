package com.marktony.zhihudaily.settings;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.marktony.zhihudaily.R;

/**
 * Created by lizhaotailang on 2016/7/26.
 */

public class SettingsPreferenceFragment extends PreferenceFragmentCompat
        implements SettingsContract.View{

    private SettingsContract.Presenter presenter;
    private Toolbar toolbar;

    private CheckBoxPreference preferenceNavitionbar;

    public SettingsPreferenceFragment() {

    }

    public static SettingsPreferenceFragment newInstance() {
        return new SettingsPreferenceFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.settings_preference_fragment);

        initViews(getView());

        findPreference("no_picture_mode").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.setNoPictureMode(preference);
                return false;
            }
        });

        findPreference("in_app_browser").setOnPreferenceClickListener(new android.support.v7.preference.Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(android.support.v7.preference.Preference preference) {
                presenter.setInAppBrowser(preference);
                return false;
            }
        });

        findPreference("clear_glide_cache").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.cleanGlideCache();
                return false;
            }
        });

        preferenceNavitionbar.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showNavigationBarTint(preference);
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void showCleanGlideCacheDone() {
        Snackbar.make(toolbar, R.string.clear_image_cache_successfully, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNavigationBarTint(Preference preference) {
        if (preference.getSharedPreferences().getBoolean("navigation_bar_tint", false)){
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        } else {
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getContext(), android.R.color.background_dark));
        }

    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        if (presenter != null){
            this.presenter = presenter;
        }
    }

    @Override
    public void initViews(View view) {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        preferenceNavitionbar = (CheckBoxPreference) findPreference("navigation_bar_tint");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            preferenceNavitionbar.setEnabled(false);
        } else {
            // showNavigationBarTint();
        }
    }

}
