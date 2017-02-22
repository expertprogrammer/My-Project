package pasta.music.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.async.Action;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pasta.music.Pasta;
import pasta.music.R;
import pasta.music.activities.PlayerActivity;
import pasta.music.adapters.TrackAdapter;
import pasta.music.data.AlbumListData;
import pasta.music.data.TrackListData;
import pasta.music.utils.ImageUtils;
import pasta.music.utils.PreferenceUtils;
import pasta.music.utils.StaticUtils;
import pasta.music.views.CustomImageView;

public class AlbumFragment extends FullScreenFragment {

    @Bind(R.id.topTenTrackListView)
    RecyclerView recycler;
    @Bind(R.id.progressBar2)
    ProgressBar spinner;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.header)
    CustomImageView header;
    @Bind(R.id.bar)
    View bar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.tracksLength)
    TextView tracksLength;

    private AlbumListData data;
    private List<TrackListData> trackList;
    private Pasta pasta;
    private Action action;
    private int selectedOrder;
    private TrackAdapter adapter;
    private boolean palette;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = DataBindingUtil.inflate(inflater, R.layout.fragment_tracks, container, false).getRoot();
        ButterKnife.bind(this, rootView);

        pasta = (Pasta) getContext().getApplicationContext();
        data = getArguments().getParcelable("album");

        palette = PreferenceUtils.isPalette(getContext());

        fab.setBackgroundTintList(ColorStateList.valueOf(PreferenceUtils.getAccentColor(getContext())));
        fab.setImageDrawable(ImageUtils.getVectorDrawable(getContext(), R.drawable.ic_play));

        setHasOptionsMenu(true);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        toolbar.inflateMenu(R.menu.menu_basic_view);
        modifyMenu(toolbar.getMenu());
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMenuClick(item);
                return false;
            }
        });

        collapsingToolbarLayout.setTitle(data.albumName);
        tracksLength.setText(String.valueOf(data.tracks) + (data.tracks == 1 ? " track" : " tracks"));

        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (fab == null) return;
                if (appBarLayout.getHeight() / 2 < -verticalOffset) {
                    fab.hide();
                } else {
                    fab.show();
                }
            }
        });

        spinner.setVisibility(View.VISIBLE);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        adapter = new TrackAdapter((AppCompatActivity) getActivity(), null);
        adapter.setAlbumBehavior();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), PreferenceUtils.isListTracks(getContext()) ? 1 : PreferenceUtils.getColumnNumber(getContext(), metrics.widthPixels > metrics.heightPixels)));
        recycler.setHasFixedSize(true);

        action = new Action<List<TrackListData>>() {
            @NonNull
            @Override
            public String id() {
                return "getAlbumTracks";
            }

            @Nullable
            @Override
            protected List<TrackListData> run() throws InterruptedException {
                return pasta.getTracks(data);
            }

            @Override
            protected void done(@Nullable List<TrackListData> result) {
                if (spinner != null) spinner.setVisibility(View.GONE);
                if (result == null) {
                    pasta.onCriticalError(getContext(), "album tracks action");
                    return;
                }
                adapter.swapData(result);
                adapter.sort(PreferenceUtils.getTrackOrder(getContext()));
                trackList = result;
            }
        };
        action.execute();

        Glide.with(getContext()).load(data.albumImageLarge).placeholder(ImageUtils.getVectorDrawable(getContext(), R.drawable.preload)).into(new GlideDrawableImageViewTarget(header) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                if (header != null) header.transition(resource);

                if (palette) {
                    Palette.from(ImageUtils.drawableToBitmap(resource)).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            int primary = palette.getMutedColor(Color.GRAY);
                            if (collapsingToolbarLayout != null)
                                collapsingToolbarLayout.setContentScrimColor(primary);
                            if (fab != null)
                                fab.setBackgroundTintList(ColorStateList.valueOf(palette.getVibrantColor(ImageUtils.darkColor(primary))));
                            if (bar != null) bar.setBackgroundColor(primary);
                            setData(data.albumName, primary, palette.getDarkVibrantColor(primary));
                        }
                    });
                }
            }
        });

        return rootView;
    }

    @OnClick(R.id.fab)
    public void startFirst(View v) {
        if (trackList == null || trackList.size() < 1) return;
        StaticUtils.play(0, trackList, getContext());
        startActivity(new Intent(getActivity(), PlayerActivity.class));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_basic_view, menu);
        modifyMenu(menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        onMenuClick(item);
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (action != null && action.isExecuting()) action.cancel();
        ButterKnife.unbind(this);
    }

    private void modifyMenu(final Menu menu) {
        new Action<Boolean>() {
            @NonNull
            @Override
            public String id() {
                return "isAlbumFav";
            }

            @Nullable
            @Override
            protected Boolean run() throws InterruptedException {
                return pasta.isFavorite(data);
            }

            @Override
            protected void done(@Nullable Boolean result) {
                if (result == null) {
                    pasta.onError(getActivity(), "favorite album action");
                    return;
                }
                if (result) {
                    menu.findItem(R.id.action_fav).setIcon(R.drawable.ic_fav);
                } else {
                    menu.findItem(R.id.action_fav).setIcon(R.drawable.ic_unfav);
                }
            }

        }.execute();
    }

    private void onMenuClick(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.action_fav:
                new Action<Boolean>() {
                    @NonNull
                    @Override
                    public String id() {
                        return "favAlbum";
                    }

                    @Nullable
                    @Override
                    protected Boolean run() throws InterruptedException {
                        if (!pasta.toggleFavorite(data)) {
                            return null;
                        } else return pasta.isFavorite(data);
                    }

                    @Override
                    protected void done(@Nullable Boolean result) {
                        if (result == null) {
                            pasta.onError(getActivity(), "favorite album menu action");
                            return;
                        }
                        if (result) {
                            item.setIcon(R.drawable.ic_fav);
                        } else {
                            item.setIcon(R.drawable.ic_unfav);
                        }
                    }

                }.execute();
                break;
            case R.id.action_share:
                Intent s = new Intent(android.content.Intent.ACTION_SEND);
                s.setType("text/plain");
                s.putExtra(Intent.EXTRA_SUBJECT, data.albumName);
                s.putExtra(Intent.EXTRA_TEXT, StaticUtils.getAlbumUrl(data.albumId));
                startActivity(Intent.createChooser(s, data.albumName));
                break;
            case R.id.action_web:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(StaticUtils.getAlbumUrl(data.albumId))));
                break;
            case R.id.action_order:
                PreferenceUtils.getOrderingDialog(getContext(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedOrder = which;
                    }
                }, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface d, int which) {
                        PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putInt(PreferenceUtils.ORDER, selectedOrder).apply();
                        adapter.sort(PreferenceUtils.getTrackOrder(getContext()));
                        d.dismiss();
                    }
                }).show();
                break;
        }
    }
}
