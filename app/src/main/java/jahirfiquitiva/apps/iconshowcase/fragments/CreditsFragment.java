package jahirfiquitiva.apps.iconshowcase.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import jahirfiquitiva.apps.iconshowcase.R;
import jahirfiquitiva.apps.iconshowcase.utilities.ThemeUtils;
import jahirfiquitiva.apps.iconshowcase.utilities.Util;

public class CreditsFragment extends Fragment {

    private Context context;
    private ViewGroup layout;
    Drawable person, facebook, gplus, twitter, website, youtube, community, playstore, github,
            bugs, donate, thanksIcon, libs, sherryIcon;
    ImageView iconAuthor, iconDev, iconAuthorFacebook, iconAuthorGPlus, iconAuthorCommunity,
            youtubeIcon, twitterIcon, playStoreIcon, iconAuthorWebsite, iconDevGPlus,
            iconDevGitHub, iconDevWebsite, donateIcon, bugIcon, iconDevCommunity, thanksIV,
            libsIcon, sherryIV, iconDevPlayStore;
    View jahir;
    LinearLayout authorFB, authorGPlus, authorTwitter, authorWebsite, authorYouTube,
            authorCommunity, authorPlayStore, devGitHub, devGPlus, devWebsite, libraries,
            thanksSherry, specialThanks, devPlayStore, donateL, bugsL, communityL;
    boolean withCreditsToDeveloper = true,
            withLinkToFacebook, withLinkToTwitter, withLinkToGPlus, withLinkToYouTube,
            withLinkToCommunity, withLinkToPlayStore, withLinkToDonate, withLinkToWebsite;
    String[] libsLinks, thanksLinks;

    private void setupBooleans() {
        withLinkToFacebook = true;
        withLinkToTwitter = true;
        withLinkToGPlus = true;
        withLinkToYouTube = true;
        withLinkToCommunity = true;
        withLinkToPlayStore = true;
        withLinkToDonate = true;
        withLinkToWebsite = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity();

        setupBooleans();

        libsLinks = context.getResources().getStringArray(R.array.libs_links);
        thanksLinks = context.getResources().getStringArray(R.array.thanks_links);

        ActionBar toolbar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (toolbar != null)
            toolbar.setTitle(R.string.section_six);

        if (layout != null) {
            ViewGroup parent = (ViewGroup) layout.getParent();
            if (parent != null) {
                parent.removeView(layout);
            }
        }
        try {
            layout = (ViewGroup) inflater.inflate(R.layout.credits_section, container, false);
        } catch (InflateException e) {

        }

        setupViewsIDs(layout);
        setupJahirCredits();
        setupLayout(getActivity());

        return layout;
    }

    private void setupViewsIDs(final ViewGroup layout) {
        jahir = layout.findViewById(R.id.jahirCredits);
        iconAuthor = (ImageView) layout.findViewById(R.id.icon_author);
        iconDev = (ImageView) layout.findViewById(R.id.icon_dev);
        iconAuthorFacebook = (ImageView) layout.findViewById(R.id.icon_facebook_author);
        iconAuthorGPlus = (ImageView) layout.findViewById(R.id.icon_google_plus_author);
        iconAuthorCommunity = (ImageView) layout.findViewById(R.id.icon_community_author);
        youtubeIcon = (ImageView) layout.findViewById(R.id.icon_youtube);
        twitterIcon = (ImageView) layout.findViewById(R.id.icon_twitter);
        playStoreIcon = (ImageView) layout.findViewById(R.id.icon_play_store);
        iconAuthorWebsite = (ImageView) layout.findViewById(R.id.icon_website_author);
        iconDevGPlus = (ImageView) layout.findViewById(R.id.icon_google_plus);
        iconDevGitHub = (ImageView) layout.findViewById(R.id.icon_github);
        iconDevWebsite = (ImageView) layout.findViewById(R.id.icon_website);
        donateIcon = (ImageView) layout.findViewById(R.id.icon_donate);
        bugIcon = (ImageView) layout.findViewById(R.id.icon_bug_report);
        iconDevCommunity = (ImageView) layout.findViewById(R.id.icon_google_plus_community);
        libsIcon = (ImageView) layout.findViewById(R.id.icon_libs);
        thanksIV = (ImageView) layout.findViewById(R.id.icon_thanks);
        sherryIV = (ImageView) layout.findViewById(R.id.icon_sherry);
        iconDevPlayStore = (ImageView) layout.findViewById(R.id.icon_dev_playstore);

        authorFB = (LinearLayout) layout.findViewById(R.id.author_facebook);
        authorFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isAppInstalled(context, "com.facebook.katana")) {
                    Util.openLink(context,
                            Util.getStringFromResources(context, R.string.iconpack_author_fb));
                } else {
                    Util.openLinkInChromeCustomTab(context,
                            Util.getStringFromResources(context, R.string.iconpack_author_fb_alt));
                }
            }
        });

        authorGPlus = (LinearLayout) layout.findViewById(R.id.add_to_google_plus_circles);
        authorGPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.iconpack_author_gplus));
            }
        });

        authorTwitter = (LinearLayout) layout.findViewById(R.id.twitter);
        authorTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Util.openLink(context,
                            Util.getStringFromResources(context, R.string.iconpack_author_twitter));
                } catch (Exception e) {
                    Util.openLink(context,
                            Util.getStringFromResources(context, R.string.iconpack_author_twitter_alt));
                }
            }
        });

        authorWebsite = (LinearLayout) layout.findViewById(R.id.visit_website);
        authorWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.iconpack_author_website));
            }
        });

        authorYouTube = (LinearLayout) layout.findViewById(R.id.youtube);
        authorYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.iconpack_author_youtube));
            }
        });

        authorCommunity = (LinearLayout) layout.findViewById(R.id.community);
        authorCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.iconpack_author_gplus_community));
            }
        });

        authorPlayStore = (LinearLayout) layout.findViewById(R.id.play_store);
        authorPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.iconpack_author_playstore));
            }
        });

        devPlayStore = (LinearLayout) layout.findViewById(R.id.dev_playstore);
        devPlayStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.dashboard_author_playstore));
            }
        });

        devGitHub = (LinearLayout) layout.findViewById(R.id.dev_github);
        devGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.dashboard_author_github));
            }
        });

        devGPlus = (LinearLayout) layout.findViewById(R.id.dev_google_plus);
        devGPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.dashboard_author_gplus));
            }
        });

        devWebsite = (LinearLayout) layout.findViewById(R.id.dev_website);
        devWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.dashboard_author_website));
            }
        });

        thanksSherry = (LinearLayout) layout.findViewById(R.id.thanksSherry);
        thanksSherry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title(R.string.sherry_title)
                        .neutralText(R.string.follow_her)
                        .positiveText(R.string.close)
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                Util.openLinkInChromeCustomTab(context,
                                        Util.getStringFromResources(context, R.string.sherry_link));
                            }
                        })
                        .show();
            }
        });

        libraries = (LinearLayout) layout.findViewById(R.id.libraries);
        libraries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title(R.string.implemented_libraries)
                        .negativeText(R.string.close)
                        .items(R.array.libs_names)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog materialDialog, View view,
                                                    final int i, CharSequence charSequence) {
                                Util.openLinkInChromeCustomTab(context, libsLinks[i]);
                            }
                        }).show();
            }
        });

        specialThanks = (LinearLayout) layout.findViewById(R.id.thanks);
        specialThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(context)
                        .title(R.string.special_thanks_to)
                        .negativeText(R.string.close)
                        .items(R.array.thanks_names)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog materialDialog, View view,
                                                    final int i, CharSequence charSequence) {
                                Util.openLinkInChromeCustomTab(context, thanksLinks[i]);
                            }
                        }).show();
            }
        });

        donateL = (LinearLayout) layout.findViewById(R.id.donate);
        donateL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showSimpleSnackbar(layout, "Coming soon", 1);
            }
        });

        bugsL = (LinearLayout) layout.findViewById(R.id.report_bugs);
        bugsL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.dashboard_bugs_report));
            }
        });

        communityL = (LinearLayout) layout.findViewById(R.id.join_google_plus_community);
        communityL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.openLinkInChromeCustomTab(context,
                        Util.getStringFromResources(context, R.string.dashboard_author_gplus_community));
            }
        });

    }

    private void setupLayout(Context context) {
        final int light = context.getResources().getColor(android.R.color.white);
        final int dark = context.getResources().getColor(R.color.grey);

        person = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_account)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        facebook = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_facebook)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        gplus = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_google_plus)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        community = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_group_work)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        twitter = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_twitter)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        github = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_github)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        youtube = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_youtube_play)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        playstore = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_case_play)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        website = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_globe_alt)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        bugs = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_bug)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        donate = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_money_box)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        thanksIcon = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_thumb_up)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        libs = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_file_text)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        sherryIcon = new IconicsDrawable(context)
                .icon(GoogleMaterial.Icon.gmd_star)
                .color(ThemeUtils.darkTheme ? light : dark)
                .sizeDp(24);

        iconAuthor.setImageDrawable(person);
        iconDev.setImageDrawable(person);
        iconAuthorGPlus.setImageDrawable(gplus);
        iconAuthorCommunity.setImageDrawable(community);
        youtubeIcon.setImageDrawable(youtube);
        twitterIcon.setImageDrawable(twitter);
        playStoreIcon.setImageDrawable(playstore);
        iconAuthorWebsite.setImageDrawable(website);
        iconDevGPlus.setImageDrawable(gplus);
        iconDevGitHub.setImageDrawable(github);
        iconDevWebsite.setImageDrawable(website);
        donateIcon.setImageDrawable(donate);
        bugIcon.setImageDrawable(bugs);
        iconDevCommunity.setImageDrawable(community);
        iconAuthorFacebook.setImageDrawable(facebook);
        libsIcon.setImageDrawable(libs);
        thanksIV.setImageDrawable(thanksIcon);
        sherryIV.setImageDrawable(sherryIcon);
        iconDevPlayStore.setImageDrawable(playstore);

    }

    private void setupJahirCredits() {
        if (!withCreditsToDeveloper) {
            jahir.setVisibility(View.GONE);
        }
    }

}