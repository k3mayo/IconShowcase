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

package jahirfiquitiva.iconshowcase.services;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.google.android.apps.muzei.api.UserCommand;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

import jahirfiquitiva.iconshowcase.BuildConfig;
import jahirfiquitiva.iconshowcase.R;
import jahirfiquitiva.iconshowcase.config.Config;
import jahirfiquitiva.iconshowcase.logging.CrashReportingTree;
import jahirfiquitiva.iconshowcase.utilities.JSONParser;
import jahirfiquitiva.iconshowcase.utilities.Preferences;
import timber.log.Timber;

public class MuzeiArtSourceService extends RemoteMuzeiArtSource {

    private static final String ARTSOURCE_NAME = "IconShowcase - MuzeiExtension";
    private static final int COMMAND_ID_SHARE = 1337;
    private final ArrayList<String> names = new ArrayList<>();
    private final ArrayList<String> authors = new ArrayList<>();
    private final ArrayList<String> urls = new ArrayList<>();
    private Preferences mPrefs;

    public MuzeiArtSourceService() {
        super(ARTSOURCE_NAME);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String command = intent.getExtras().getString("service");
        if (command != null) {
            try {
                onTryUpdate(UPDATE_REASON_USER_NEXT);
            } catch (RetryException e) {
                Timber.d("Error updating Muzei: " + e.getLocalizedMessage());
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Config.init(this);
        if (BuildConfig.DEBUG || Config.get().allowDebugging()) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //Disable debug & verbose logging on release
            Timber.plant(new CrashReportingTree());
        }
        mPrefs = new Preferences(MuzeiArtSourceService.this);
        ArrayList<UserCommand> commands = new ArrayList<>();
        commands.add(new UserCommand(BUILTIN_COMMAND_ID_NEXT_ARTWORK));
        commands.add(new UserCommand(COMMAND_ID_SHARE, getString(R.string.share)));
        setUserCommands(commands);
    }

    @Override
    public void onCustomCommand(int id) {
        super.onCustomCommand(id);
        if (id == COMMAND_ID_SHARE) {
            Artwork currentArtwork = getCurrentArtwork();
            Intent shareWall = new Intent(Intent.ACTION_SEND);
            shareWall.setType("text/plain");
            String wallName = currentArtwork.getTitle();
            String authorName = currentArtwork.getByline();
            String storeUrl = Config.MARKET_URL + getPackageName();
            String iconPackName = getString(R.string.app_name);
            shareWall.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.share_text, wallName, authorName, iconPackName, storeUrl));
            shareWall = Intent.createChooser(shareWall, getString(R.string.share_title));
            shareWall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(shareWall);
        }
    }

    @Override
    protected void onTryUpdate(int reason) throws RetryException {
        if (mPrefs.areFeaturesEnabled()) {
            try {
                new DownloadJSONAndSetWall(getApplicationContext()).execute();
            } catch (Exception e) {
                Timber.d("Error updating Muzei: " + e.getLocalizedMessage());
                throw new RetryException();
            }
        }

    }

    private void setImageForMuzei(String name, String author, String url) {
        publishArtwork(new Artwork.Builder()
                .title(name)
                .byline(author)
                .imageUri(Uri.parse(url))
                .viewIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                .build());
        scheduleUpdate(System.currentTimeMillis() + mPrefs.getRotateTime());
    }

    public class DownloadJSONAndSetWall extends AsyncTask<Void, String, Boolean> {

        private final WeakReference<Context> context;
        public JSONObject mainObject, wallItem;
        public JSONArray wallInfo;

        public DownloadJSONAndSetWall(Context context) {
            this.context = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            names.clear();
            authors.clear();
            urls.clear();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            boolean worked;
            try {
                mainObject = JSONParser.getJSONFromURL(getApplicationContext(),
                        getResources().getString(R.string.wallpapers_json_link));
                if (mainObject != null) {
                    try {
                        wallInfo = mainObject.getJSONArray("wallpapers");
                        for (int i = 0; i < wallInfo.length(); i++) {
                            wallItem = wallInfo.getJSONObject(i);
                            names.add(wallItem.getString("name"));
                            authors.add(wallItem.getString("author"));
                            urls.add(wallItem.getString("url"));
                        }
                        worked = true;
                    } catch (JSONException e) {
                        worked = false;
                        Timber.d("Error downloading JSON for Muzei: " + e.getLocalizedMessage());
                    }
                } else {
                    worked = false;
                }
            } catch (Exception e) {
                worked = false;
                Timber.d("Error in Muzei: " + e.getLocalizedMessage());
            }
            return worked;
        }

        @Override
        protected void onPostExecute(Boolean worked) {
            if (worked) {
                int i;
                try {
                    i = new Random().nextInt(names.size());
                    setImageForMuzei(names.get(i), authors.get(i), urls.get(i));
                    Timber.d("Setting picture: " + names.get(i));
                } catch (IllegalArgumentException e) {
                    Timber.d("Muzei error: " + e.getLocalizedMessage());
                }

            }

        }

    }

}