/*
 * Copyright (C) 2016 CarbonROM
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bananadroid.bananasplit;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.os.Bundle;
import android.provider.Settings;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.SearchIndexableResource;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;

import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;
import com.android.settingslib.search.SearchIndexable;

import java.util.ArrayList;
import java.util.List;

@SearchIndexable
public class BananaSplit extends SettingsPreferenceFragment implements Indexable {

    private static final int MENU_HELP  = 0;
    private static final String KEY_GESTURE_MAIN_CATEGORY = "gestures_main_category";
    private static final String KEY_SYSTEM_MAIN_CATEGORY = "system_main_category";
    private static final String KEY_LOCKSCREEN_MAIN_CATEGORY = "lockscreen_main_category";
    private static final String KEY_STATUSBAR_MAIN_CATEGORY = "statusbar_main_category";
    private static final String KEY_BUTTONS_MAIN_CATEGORY = "buttons_main_category";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.main_menu);

        setHasOptionsMenu(true);
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.BANANASPLIT;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, MENU_HELP, 0, R.string.bananasplit_dialog_title)
                .setIcon(R.drawable.ic_bananasplit_info)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_HELP:
                showDialogInner(MENU_HELP);
                Toast.makeText(getActivity(),
                (R.string.bananasplit_dialog_toast),
                Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialogInner(int id) {
        FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
        // Create and show the dialog.
        MyAlertDialogFragment newFragment = new MyAlertDialogFragment();
        newFragment.show(ft, "dialog");
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.bananasplit_dialog_title)
                .setMessage(R.string.bananasplit_dialog_message)
                .setCancelable(false)
                .setNegativeButton(R.string.dlg_ok,
                    new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
            .create();
        }

        @Override
        public void onCancel(DialogInterface dialog) {

        }
    }

    public static final Indexable.SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider() {
                @Override
                public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                                                                            boolean enabled) {
                    ArrayList<SearchIndexableResource> result =
                            new ArrayList<SearchIndexableResource>();

                    SearchIndexableResource sir = new SearchIndexableResource(context);
                    sir.xmlResId = R.xml.main_menu;
                    result.add(sir);
                    return result;
                }

                @Override
                public List<String> getNonIndexableKeys(Context context) {
                    ArrayList<String> keys = new ArrayList<String>();
                    keys.add(KEY_GESTURE_MAIN_CATEGORY);
                    keys.add(KEY_SYSTEM_MAIN_CATEGORY);
                    keys.add(KEY_LOCKSCREEN_MAIN_CATEGORY);
                    keys.add(KEY_STATUSBAR_MAIN_CATEGORY);
                    keys.add(KEY_BUTTONS_MAIN_CATEGORY);
                    return keys;
                }
            };
}
