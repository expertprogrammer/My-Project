package pasta.music.activities;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.afollestad.async.Action;
import com.afollestad.async.Async;
import com.afollestad.async.Done;
import com.afollestad.async.Pool;
import com.afollestad.async.Result;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pasta.music.Pasta;
import pasta.music.R;
import pasta.music.data.AlbumListData;
import pasta.music.data.ArtistListData;
import pasta.music.data.PlaylistListData;
import pasta.music.data.TrackListData;
import pasta.music.fragments.AboutFragment;
import pasta.music.fragments.AlbumFragment;
import pasta.music.fragments.ArtistFragment;
import pasta.music.fragments.CategoriesFragment;
import pasta.music.fragments.FabFragment;
import pasta.music.fragments.FavoritesFragment;
import pasta.music.fragments.FullScreenFragment;
import pasta.music.fragments.HomeFragment;
import pasta.music.fragments.SearchFragment;
import pasta.music.fragments.SettingsFragment;
import pasta.music.utils.ImageUtils;
import pasta.music.utils.PreferenceUtils;
import pasta.music.utils.StaticUtils;
import pasta.music.views.Playbar;

public class HomeActivity extends AppCompatActivity implements ColorChooserDialog.ColorCallback {

    @Bind(R.id.playbar)
    View playbarView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Nullable @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.drawer)
    FrameLayout drawer_container;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.status_background)
    FrameLayout statusBackground;
    @Nullable @Bind(R.id.content)
    FrameLayout content;

    private Playbar playbar;
    private Drawer materialDrawer;
    private AccountHeader materialHeader;

    private Fragment f;
    private long selected;
    private String title;
    private int limit;
    private Pool searchPool;
    private ArrayList searchDatas;
    private boolean preload;
    private Pasta pasta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceUtils.isDarkTheme(this)) setTheme(R.style.AppTheme_Transparent_Dark);
        DataBindingUtil.setContentView(this, R.layout.activity_home);
        ButterKnife.bind(this);

        limit = (PreferenceUtils.getLimit(this) + 1) * 10;

        preload = PreferenceUtils.isPreload(this);
        pasta = (Pasta) getApplicationContext();
        pasta.setScreen(this);

        if (false) { //TODO: implement error checking
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        setSupportActionBar(toolbar);
        if (drawer_layout != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        }

        if (content != null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.topMargin = StaticUtils.getStatusBarMargin(this);
            content.setLayoutParams(layoutParams);
        }

        fab.setBackgroundTintList(ColorStateList.valueOf(PreferenceUtils.getAccentColor(this)));

        Drawable home = ImageUtils.getVectorDrawable(this, R.drawable.ic_home);
        Drawable fav = ImageUtils.getVectorDrawable(this, R.drawable.ic_fav);
        Drawable bookmark = ImageUtils.getVectorDrawable(this, R.drawable.ic_bookmark);
        Drawable playing = ImageUtils.getVectorDrawable(this, R.drawable.ic_now_playing);
        Drawable settings = ImageUtils.getVectorDrawable(this, R.drawable.ic_settings);

        int tint = ContextCompat.getColor(this, R.color.material_drawer_primary_icon);
        DrawableCompat.setTint(home, tint);
        DrawableCompat.setTint(fav, tint);
        DrawableCompat.setTint(bookmark, tint);
        DrawableCompat.setTint(playing, tint);
        DrawableCompat.setTint(settings, tint);

        materialHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withHeaderBackground(R.mipmap.drawer_bg)
                .withProfileImagesClickable(false)
                .withSelectionListEnabledForSingleProfile(false)
                .build();

        if (false) {
            ProfileDrawerItem profile;
            try {
                profile = new ProfileDrawerItem().withName("hi").withEmail("hai@something.somethingelse");
            } catch (Exception e) {
                e.printStackTrace();
                profile = new ProfileDrawerItem().withName("hi").withEmail("hai@something.somethingelse");
            }
            materialHeader.addProfiles(profile);

            if (false) {
                Glide.with(this).load("something.png").into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        materialHeader.getActiveProfile().withIcon(resource);
                        materialHeader.getHeaderBackgroundView().setImageBitmap(ImageUtils.blurBitmap(ImageUtils.drawableToBitmap(resource)));
                    }
                });
            }
        }

        DrawerBuilder builder = new DrawerBuilder()
                .withActivity(this)
                .withFullscreen(true)
                .withToolbar(toolbar)
                .withAccountHeader(materialHeader)
                .withSelectedItem(0)
                .addDrawerItems(
                        new SecondaryDrawerItem().withName(R.string.title_activity_home).withIdentifier(1).withIcon(home),
                        new SecondaryDrawerItem().withName(R.string.title_activity_favorites).withIdentifier(2).withIcon(fav),
                        new SecondaryDrawerItem().withName(R.string.title_activity_categories).withIdentifier(3).withIcon(bookmark),
                        new SecondaryDrawerItem().withName(R.string.title_activity_playing).withIdentifier(4).withIcon(playing).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.title_activity_settings).withIdentifier(5).withIcon(settings),
                        new SecondaryDrawerItem().withName(R.string.title_about).withIdentifier(6)
                )
                .withOnDrawerItemClickListener(new com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (selected == drawerItem.getIdentifier()) return false;

                        switch (((int) drawerItem.getIdentifier())) {
                            case 1:
                                f = new HomeFragment();
                                title = getString(R.string.title_activity_home);
                                break;
                            case 2:
                                f = new FavoritesFragment();
                                title = getString(R.string.title_activity_favorites);
                                break;
                            case 3:
                                f = new CategoriesFragment();
                                title = getString(R.string.title_activity_categories);
                                break;
                            case 4:
                                if (!playbar.playing) {
                                    pasta.showToast(getString(R.string.nothing_playing));

                                    if (drawer_layout != null) drawer_layout.closeDrawer(Gravity.LEFT);
                                    return false;
                                }

                                startActivity(new Intent(HomeActivity.this, PlayerActivity.class));
                                return true;
                            case 5:
                                f = new SettingsFragment();
                                title = getString(R.string.title_activity_settings);
                                break;
                            case 6:
                                f = new AboutFragment();
                                title = getString(R.string.title_activity_about);
                                break;
                            default:
                                return false;
                        }

                        selected = drawerItem.getIdentifier();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).commit();

                        setListeners(f);

                        if (drawer_layout != null) drawer_layout.closeDrawer(Gravity.LEFT);
                        return true;
                    }
                });

        materialDrawer = builder.buildView();
        View v = materialDrawer.getSlider();
        v.setLayoutParams(new ViewGroup.LayoutParams(DrawerUIUtils.getOptimalDrawerWidth(this), ViewGroup.LayoutParams.MATCH_PARENT));
        drawer_container.addView(v);

        playbar = new Playbar(this);
        playbar.initPlayBar(playbarView);
        playbar.setPlaybarListener(new Playbar.PlaybarListener() {
            @Override
            public void onHide(boolean hidden) {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
                layoutParams.bottomMargin = hidden ? (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()) : getResources().getDimensionPixelSize(R.dimen.bottom_playbar_padding);
                fab.setLayoutParams(layoutParams);
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                HomeActivity.this.f = getSupportFragmentManager().findFragmentById(R.id.fragment);
                setListeners(f);

                ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
                animator.setDuration(250);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        View v = findViewById(R.id.fragment);
                        if (v != null) v.setAlpha(Math.abs((float) animation.getAnimatedValue()));
                    }
                });
                animator.start();
            }
        });

        if (savedInstanceState != null) {
            f = getSupportFragmentManager().findFragmentById(R.id.fragment);
            if (f != null) {
                setListeners(f);
                return;
            }
        }

        if (getIntent().getParcelableExtra("artist") != null) {
            Bundle args = new Bundle();
            args.putParcelable("artist", getIntent().getParcelableExtra("artist"));
            f = new ArtistFragment();
            f.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, f).commit();

            setDrawerEnabled(false);
        } else if (getIntent().getParcelableExtra("album") != null) {
            Bundle args = new Bundle();
            args.putParcelable("album", getIntent().getParcelableExtra("album"));
            f = new AlbumFragment();
            f.setArguments(args);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, f).commit();

            setDrawerEnabled(false);
        } else if (getIntent().getStringExtra("query") != null) {
            f = new SearchFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, f).commit();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setDrawerEnabled(false);

            search(getIntent().getStringExtra("query"), true);
            title = getIntent().getStringExtra("query");
        } else {
            f = new HomeFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment, f).commit();

            title = getString(R.string.title_activity_home);
        }

        setListeners(f);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (playbar != null) playbar.registerPlaybar();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (playbar != null) playbar.unregisterPlaybar();
    }

    public void setDrawerEnabled(boolean enabled) {
        if (drawer_layout != null)
            drawer_layout.setDrawerLockMode(enabled ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawer_container.setVisibility(enabled ? View.VISIBLE : View.GONE);
        if (drawer_layout != null || !enabled)
            getSupportActionBar().setHomeAsUpIndicator(enabled ? R.drawable.ic_drawer : R.drawable.ic_back);
    }

    public void setListeners(Fragment f) {
        if (f instanceof FullScreenFragment) {
            appbar.setExpanded(false, false);
            fab.hide();

            ((FullScreenFragment) f).setDataListener(new FullScreenFragment.DataListener(){
                @Override
                public void onDataReady(String title, int statusColor, int windowColor) {
                    setTitle(title);
                    statusBackground.setBackgroundColor(ImageUtils.darkColor(statusColor));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        setTaskDescription(new ActivityManager.TaskDescription(getTitle().toString(), ImageUtils.drawableToBitmap(ContextCompat.getDrawable(HomeActivity.this, R.mipmap.ic_launcher)), windowColor));
                    }
                }
            });

            materialDrawer.setSelection(-1, false);
        } else {
            appbar.setExpanded(true, false);

            setTitle(title);
            if (f instanceof HomeFragment) materialDrawer.setSelection(1, false);
            else if (f instanceof FavoritesFragment) materialDrawer.setSelection(2, false);
            else if (f instanceof CategoriesFragment) materialDrawer.setSelection(3, false);
            else if (f instanceof SettingsFragment) materialDrawer.setSelection(5, false);
            else if (f instanceof AboutFragment) materialDrawer.setSelection(6, false);

            statusBackground.setBackgroundColor(ImageUtils.darkColor(PreferenceUtils.getPrimaryColor(this)));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ActivityManager.TaskDescription desc = new ActivityManager.TaskDescription(title, ImageUtils.drawableToBitmap(ContextCompat.getDrawable(this, R.mipmap.ic_launcher)), PreferenceUtils.getPrimaryColor(this));
                setTaskDescription(desc);
            }

            if (f instanceof FabFragment) {
                ((FabFragment) f).setFabListener(new FabFragment.FabListener() {
                    @Override
                    public void onDataReady(boolean visible, int iconRes, View.OnClickListener clickListener) {
                        if (visible) fab.show();
                        else fab.hide();
                        fab.setImageDrawable(ImageUtils.getVectorDrawable(HomeActivity.this, iconRes));
                        fab.setOnClickListener(clickListener);
                    }
                });
            }

            fab.hide();

            if (f instanceof SearchFragment || f instanceof CategoriesFragment || f instanceof SettingsFragment || f instanceof AboutFragment) {
                ViewCompat.setElevation(findViewById(R.id.collapsing_toolbar), TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
            } else ViewCompat.setElevation(findViewById(R.id.collapsing_toolbar), 0);

            if (f instanceof SearchFragment && (searchPool == null || !searchPool.isExecuting()) && (searchDatas != null && searchDatas.size() > 0)) {
                ((SearchFragment) f).swapData(searchDatas);
            }
        }

        if (searchPool != null && searchPool.isExecuting() && !(f instanceof SearchFragment))
            searchPool.cancel();
    }

    private void search(final String searchTerm, final boolean pre) {
        if (searchTerm == null || searchTerm.length() < 1) return;

        if (!(f instanceof SearchFragment)) {
            f = new SearchFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).addToBackStack(null).commit();
        }

        ((SearchFragment) f).clear();

        if (searchPool != null && searchPool.isExecuting()) searchPool.cancel();
        searchPool = Async.parallel(new Action<List<TrackListData>>() {
            @NonNull
            @Override
            public String id() {
                return "searchTracks";
            }

            @Nullable
            @Override
            protected List<TrackListData> run() throws InterruptedException {
                return pasta.searchTracks(searchTerm, limit);
            }

            @Override
            protected void done(@Nullable List<TrackListData> result) {
                if (result == null || pre) return;
                ((SearchFragment) f).addData(result);
            }
        }, new Action<List<AlbumListData>>() {
            @NonNull
            @Override
            public String id() {
                return "searchAlbums";
            }

            @Nullable
            @Override
            protected List<AlbumListData> run() throws InterruptedException {
                return pasta.searchAlbums(searchTerm, limit);
            }

            @Override
            protected void done(@Nullable List<AlbumListData> result) {
                if (result == null || pre) return;
                ((SearchFragment) f).addData(result);
            }
        }, new Action<List<PlaylistListData>>() {
            @NonNull
            @Override
            public String id() {
                return "searchPlaylists";
            }

            @Nullable
            @Override
            protected List<PlaylistListData> run() throws InterruptedException {
                return pasta.searchPlaylists(searchTerm, limit);
            }

            @Override
            protected void done(@Nullable List<PlaylistListData> result) {
                if (result == null || pre) return;
                ((SearchFragment) f).addData(result);
            }
        }, new Action<List<ArtistListData>>() {
            @NonNull
            @Override
            public String id() {
                return "searchArtists";
            }

            @Nullable
            @Override
            protected List<ArtistListData> run() throws InterruptedException {
                return pasta.searchArtists(searchTerm, limit);
            }

            @Override
            protected void done(@Nullable List<ArtistListData> result) {
                if (result == null || pre) return;
                ((SearchFragment) f).addData(result);
            }
        }).done(new Done() {
            @Override
            public void result(@NonNull Result result) {
                if (!pre) return;
                searchDatas = new ArrayList();

                Action<?> tracksResult = result.get("searchTracks");
                Action<?> playlistsResult = result.get("searchPlaylists");
                Action<?> artistsResult = result.get("searchArtists");
                Action<?> albumsResult = result.get("searchAlbums");

                if (tracksResult != null && tracksResult.getResult() != null) searchDatas.addAll((ArrayList<TrackListData>) tracksResult.getResult());
                if (playlistsResult != null && playlistsResult.getResult() != null) searchDatas.addAll((ArrayList<PlaylistListData>) playlistsResult.getResult());
                if (artistsResult != null && artistsResult.getResult() != null) searchDatas.addAll((ArrayList<ArtistListData>) artistsResult.getResult());

                ((SearchFragment) f).swapData(searchDatas);

                if (albumsResult != null && !albumsResult.isCancelled() && albumsResult.getResult() != null) {
                    ArrayList<AlbumListData> results = (ArrayList<AlbumListData>) albumsResult.getResult();
                    searchDatas.add(results);
                    ((SearchFragment) f).addData(results);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query, false);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (preload) search(newText, true);
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (f instanceof SearchFragment) onBackPressed();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         if (item.getItemId() == android.R.id.home) {
             if (drawer_layout != null && drawer_layout.getDrawerLockMode(Gravity.LEFT) != DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                 drawer_layout.openDrawer(Gravity.LEFT);
             else onBackPressed();
         }
        return false;
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (f instanceof SettingsFragment) ((SettingsFragment) f).onColorSelection(dialog, selectedColor);
    }
}
