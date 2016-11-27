/*
 * Copyright (c) 2016 Jahir Fiquitiva
 *
 * Licensed under the CreativeCommons Attribution-ShareAlike
 * 4.0 International License. You may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *    http://creativecommons.org/licenses/by-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Special thanks to the project contributors and collaborators
 * 	https://github.com/jahirfiquitiva/IconShowcase#special-thanks
 */

package jahirfiquitiva.iconshowcase.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;

import jahirfiquitiva.iconshowcase.R;
import jahirfiquitiva.iconshowcase.utilities.Preferences;
import jahirfiquitiva.iconshowcase.utilities.color.ColorUtils;
import jahirfiquitiva.iconshowcase.utilities.utils.Utils;

/**
 * @author Allan Wang
 */
public class IconDialog extends DialogFragment {

    private static final String NAME = "Icon name";
    private static final String RESID = "Icon resId";
    private static final String TAG = "icon_dialog";

    private String name;
    private int resId;

    public static void show(final FragmentActivity context, String name, int resId) {
        Fragment frag = context.getSupportFragmentManager().findFragmentByTag(TAG);
        if (frag != null) ((IconDialog) frag).dismiss();
        IconDialog.newInstance(name, resId).show(context.getSupportFragmentManager(), TAG);
    }

    public static void dismiss(final FragmentActivity context) {
        Fragment frag = context.getSupportFragmentManager().findFragmentByTag(TAG);
        if (frag != null) ((IconDialog) frag).dismiss();
    }

    private static IconDialog newInstance(String name, int resId) {
        IconDialog f = new IconDialog();
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putInt(RESID, resId);
        f.setArguments(args);
        return f;
    }

    private Preferences getPrefs() {
        return new Preferences(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.name = getArguments().getString(NAME);
        this.resId = getArguments().getInt(RESID);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity());

        builder.customView(R.layout.dialog_icon, false)
                .title(Utils.makeTextReadable(name))
                .positiveText(R.string.close)
                .positiveColor(ColorUtils.getColorFromIcon(ContextCompat.getDrawable(
                        getActivity(), resId), getActivity()));

        MaterialDialog dialog = builder.build();

        ImageView iconView = (ImageView) dialog.getCustomView().findViewById(R.id.dialogicon);
        if (iconView != null && resId > 0) {
            iconView.setImageResource(resId);
        }

        return dialog;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            name = savedInstanceState.getString(NAME);
            resId = savedInstanceState.getInt(RESID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(NAME, name);
        outState.putInt(RESID, resId);
        super.onSaveInstanceState(outState);
    }

}