package jahirfiquitiva.iconshowcase.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;

import jahirfiquitiva.iconshowcase.R;
import jahirfiquitiva.iconshowcase.activities.ShowcaseActivity;
import jahirfiquitiva.iconshowcase.models.HomeCard;
import jahirfiquitiva.iconshowcase.utilities.ThemeUtils;
import jahirfiquitiva.iconshowcase.utilities.Utils;

/**
 * Created by 7681 on 2016-02-24.
 */
public class HomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private View view;
    private int cards = 3;
    private ArrayList<HomeCard> homeCards;

    public HomeListAdapter(ArrayList<HomeCard> homeCards, Context context) {
        this.context = context;
        this.homeCards = homeCards;
        if (context.getResources().getBoolean(R.bool.hide_pack_info)) {
            cards -= 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case 0:
                View welcomeCard = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.item_welcome_card,
                        viewGroup, false);
                return new WelcomeCard(welcomeCard);
            case 1:
                View infoCard = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.item_packinfo_card,
                        viewGroup, false);
                return new AppInfoCard(infoCard);
            case 2:
                View moreAppsCard = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.item_moreapps_card,
                        viewGroup, false);
                return new MoreAppsCard(moreAppsCard);
            default:
                final View appCard = LayoutInflater.from(
                        viewGroup.getContext()).inflate(R.layout.item_app_card,
                        viewGroup, false);
                return new AppCard(appCard, i);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return homeCards.size() + cards;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class WelcomeCard extends RecyclerView.ViewHolder {

        AppCompatButton ratebtn, iconsbtn;

        public WelcomeCard(View itemView) {
            super(itemView);
            ratebtn = (AppCompatButton) itemView.findViewById(R.id.rate_button);
            ratebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent rate = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
                    context.startActivity(rate);
                }
            });
            iconsbtn = (AppCompatButton) itemView.findViewById(R.id.icons_button);
            iconsbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowcaseActivity.drawerItemClick(ShowcaseActivity.iconsPickerIdentifier);
                    ShowcaseActivity.drawer.setSelection(ShowcaseActivity.iconsPickerIdentifier);
                }
            });
        }
    }

    public class AppInfoCard extends RecyclerView.ViewHolder {

        String themedIcons = String.valueOf(context.getResources().getInteger(R.integer.icons_amount));
        String availableWallpapers = String.valueOf(context.getResources().getInteger(R.integer.walls_amount));
        String includedWidgets = String.valueOf(context.getResources().getInteger(R.integer.zooper_widgets));

        ImageView iconsIV, wallsIV, widgetsIV;
        TextView iconsT, wallsT, widgetsT;
        LinearLayout packInfo, widgets;

        public AppInfoCard(View itemView) {
            super(itemView);

            iconsIV = (ImageView) itemView.findViewById(R.id.icon_themed_icons);
            wallsIV = (ImageView) itemView.findViewById(R.id.icon_available_wallpapers);
            widgetsIV = (ImageView) itemView.findViewById(R.id.icon_included_widgets);
            setupIcons(context, iconsIV, wallsIV, widgetsIV);

            iconsT = (TextView) itemView.findViewById(R.id.text_themed_icons);
            iconsT.setText(context.getResources().getString(R.string.themed_icons, themedIcons));

            wallsT = (TextView) itemView.findViewById(R.id.text_available_wallpapers);
            wallsT.setText(context.getResources().getString(R.string.available_wallpapers, availableWallpapers));

            widgetsT = (TextView) itemView.findViewById(R.id.text_included_widgets);
            widgetsT.setText(context.getResources().getString(R.string.included_widgets, includedWidgets));

            packInfo = (LinearLayout) itemView.findViewById(R.id.appDetails);
            packInfo.setVisibility(context.getResources().getBoolean(R.bool.hide_pack_info) ?
                    View.GONE :
                    View.VISIBLE);

            if (!ShowcaseActivity.WITH_ZOOPER_SECTION) {
                widgets = (LinearLayout) itemView.findViewById(R.id.widgets);
                widgets.setVisibility(View.GONE);
            }

        }
    }

    private void setupIcons(Context context, ImageView iconsIV, ImageView wallsIV,
                            ImageView widgetsIV) {
        final int light = ContextCompat.getColor(context, R.color.drawable_tint_dark);
        final int dark = ContextCompat.getColor(context, R.color.drawable_tint_light);

        Drawable iconsDrawable = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_android_alt)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        Drawable wallsDrawable = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_collection_image)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        Drawable widgetsDrawable = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_widgets)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        iconsIV.setImageDrawable(iconsDrawable);
        wallsIV.setImageDrawable(wallsDrawable);
        widgetsIV.setImageDrawable(widgetsDrawable);
    }

    public class MoreAppsCard extends RecyclerView.ViewHolder {

        final int light = ContextCompat.getColor(context, R.color.drawable_tint_dark);
        final int dark = ContextCompat.getColor(context, R.color.drawable_tint_light);

        LinearLayout lly, subLly;
        TextView title, desc;
        ImageView icon;

        public MoreAppsCard(View itemView) {
            super(itemView);
            view = itemView;
            lly = (LinearLayout) itemView.findViewById(R.id.more_apps);
            title = (TextView) itemView.findViewById(R.id.more_apps_text);
            desc = (TextView) itemView.findViewById(R.id.more_apps_description);
            icon = (ImageView) itemView.findViewById(R.id.more_apps_icon);
            subLly = (LinearLayout) itemView.findViewById(R.id.more_apps_sub_layout);
            icon.setImageDrawable(new IconicsDrawable(context)
                    .icon(GoogleMaterial.Icon.gmd_case_play)
                    .color(ThemeUtils.darkTheme ? light : dark)
                    .sizeDp(24));
            lly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.openLink(context, context.getResources().getString(R.string.iconpack_author_playstore));
                }
            });
        }
    }

    public class AppCard extends RecyclerView.ViewHolder {

        LinearLayout lly, subLly;
        TextView cardTitle, cardDesc;
        ImageView cardIcon;

        public AppCard(View itemView, int i) {
            super(itemView);
            view = itemView;

            String description;

            final int pos = i;

            view.setOnClickListener(new View.OnClickListener() { //TODO make clicks work for more than just links
                @Override
                public void onClick(View v) {
                    if (homeCards.get(pos - cards).isInstalled && homeCards.get(pos - cards).intent != null) {
                        context.startActivity(homeCards.get(pos - cards).intent);
                    } else if (view.getVisibility() == View.VISIBLE) {
                        Utils.openLink(context, homeCards.get(pos - cards).onClickLink);
                    }
                }
            });

            if (homeCards.get(i - cards).isInstalled) {
                description = context.getResources().getString(
                        R.string.tap_to_open,
                        homeCards.get(i - cards).desc);
            } else {
                description = context.getResources().getString(
                        R.string.tap_to_download,
                        homeCards.get(pos - cards).desc);
            }

            lly = (LinearLayout) itemView.findViewById(R.id.home_card);
            cardTitle = (TextView) itemView.findViewById(R.id.home_card_text);
            cardDesc = (TextView) itemView.findViewById(R.id.home_card_description);
            cardIcon = (ImageView) itemView.findViewById(R.id.home_card_image);
            subLly = (LinearLayout) itemView.findViewById(R.id.home_card_sub_layout);
            cardTitle.setText(homeCards.get(i - cards).title);
            cardDesc.setText(description);
            if (homeCards.get(i - cards).imgEnabled) {
                cardIcon.setImageDrawable(homeCards.get(i - cards).img);
            } else {
                subLly.removeView(cardIcon);
            }
        }
    }

}