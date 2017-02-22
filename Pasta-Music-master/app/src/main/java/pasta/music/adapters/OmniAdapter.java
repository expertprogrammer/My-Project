package pasta.music.adapters;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.async.Action;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import pasta.music.Pasta;
import pasta.music.R;
import pasta.music.activities.PlayerActivity;
import pasta.music.data.AlbumListData;
import pasta.music.data.ArtistListData;
import pasta.music.data.PlaylistListData;
import pasta.music.data.TrackListData;
import pasta.music.dialogs.AddToPlaylistDialog;
import pasta.music.dialogs.NewPlaylistDialog;
import pasta.music.fragments.AlbumFragment;
import pasta.music.fragments.ArtistFragment;
import pasta.music.fragments.PlaylistFragment;
import pasta.music.utils.ImageUtils;
import pasta.music.utils.PreferenceUtils;
import pasta.music.utils.StaticUtils;
import pasta.music.views.CustomImageView;

public class OmniAdapter extends RecyclerView.Adapter<OmniAdapter.ViewHolder> {

    private List original;
    private List list;
    private AppCompatActivity activity;
    private Pasta pasta;
    private boolean thumbnails, cards, trackList, palette, dark;
    private boolean isFavoriteBehavior;

    public OmniAdapter(AppCompatActivity activity, List list, boolean isFavoriteBehavior) {
        original = list;
        if (list != null) {
            this.list = new ArrayList();
            try {
                this.list.addAll(original);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.activity = activity;
        pasta = (Pasta) activity.getApplicationContext();

        thumbnails = PreferenceUtils.isThumbnails(activity);
        cards = PreferenceUtils.isCards(activity);
        trackList = PreferenceUtils.isListTracks(activity);
        palette = PreferenceUtils.isPalette(activity);
        dark = PreferenceUtils.isDarkTheme(activity);

        this.isFavoriteBehavior = isFavoriteBehavior;
    }

    public void addData(Parcelable data) {
        if (data == null) return;
        original.add(data);
        int pos = list.size();
        list.add(pos, data);
        notifyItemInserted(pos);
    }

    public void addData(List data) {
        if (data == null) return;
        original.addAll(data);
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void swapData(List list) {
        original = list;
        this.list = new ArrayList();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        if (position < 0 || position > original.size()) return;
        original.remove(original.indexOf(list.get(position)));
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void removeData(Parcelable data) {
        original.remove(original.indexOf(data));
        int pos = list.indexOf(data);
        list.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= list.size() || position < 0) return -1;
        Object o = list.get(position);
        if (o instanceof TrackListData) return 0;
        else if (o instanceof AlbumListData) return 1;
        else if (o instanceof PlaylistListData) return 2;
        else if (o instanceof ArtistListData) return 3;
        else return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType) {
            case 0:
                return new TrackViewHolder(inflater.inflate(trackList ? R.layout.track_item : (cards ? R.layout.track_item_card : R.layout.track_item_tile), null));
            case 1:
                return new AlbumViewHolder(inflater.inflate(cards ? R.layout.album_item_card : R.layout.album_item_tile, null));
            case 2:
                return new PlaylistViewHolder(inflater.inflate(cards ? R.layout.playlist_item_card : R.layout.playlist_item_tile, null));
            case 3:
                return new ArtistViewHolder(inflater.inflate(cards ? R.layout.artist_item_card : R.layout.artist_item_tile, null));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String image;

        switch (getItemViewType(position)) {
            case 0:
                View trackMenu = holder.v.findViewById(R.id.menu);
                if (trackMenu.getVisibility() == View.GONE) trackMenu.setVisibility(View.VISIBLE);

                trackMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(v.getContext(), v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.menu_track, popup.getMenu());

                        final MenuItem fav = popup.getMenu().findItem(R.id.action_fav);
                        if (isFavoriteBehavior) fav.setTitle(R.string.unfav);
                        else new Action<Boolean>() {
                            @NonNull
                            @Override
                            public String id() {
                                return "isTrackFav";
                            }

                            @Nullable
                            @Override
                            protected Boolean run() throws InterruptedException {
                                return pasta.isFavorite((TrackListData) list.get(holder.getAdapterPosition()));
                            }

                            @Override
                            protected void done(@Nullable Boolean result) {
                                if (result == null) return;
                                if (result) {
                                    fav.setTitle(R.string.unfav);
                                } else {
                                    fav.setTitle(R.string.fav);
                                }
                            }

                        }.execute();

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(final MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_fav:
                                        new Action<Boolean>() {
                                            @NonNull
                                            @Override
                                            public String id() {
                                                return "favTrack";
                                            }

                                            @Nullable
                                            @Override
                                            protected Boolean run() throws InterruptedException {
                                                if (!pasta.toggleFavorite((TrackListData) list.get(holder.getAdapterPosition()))) {
                                                    return null;
                                                } else return pasta.isFavorite((TrackListData) list.get(holder.getAdapterPosition()));
                                            }

                                            @Override
                                            protected void done(@Nullable Boolean result) {
                                                if (result == null) {
                                                    pasta.onError(activity, "favorite track action");
                                                    return;
                                                }
                                                if (result) item.setTitle(R.string.unfav);
                                                else {
                                                    item.setTitle(R.string.fav);
                                                    if (isFavoriteBehavior) removeData(holder.getAdapterPosition());
                                                }
                                            }

                                        }.execute();
                                        break;
                                    case R.id.action_add:
                                        new AddToPlaylistDialog(activity, ((TrackListData) list.get(holder.getAdapterPosition()))).show();
                                        break;
                                    case R.id.action_album:
                                        new Action<AlbumListData>() {
                                            @NonNull
                                            @Override
                                            public String id() {
                                                return "gotoAlbum";
                                            }

                                            @Nullable
                                            @Override
                                            protected AlbumListData run() throws InterruptedException {
                                                return pasta.getAlbum(((TrackListData) list.get(holder.getAdapterPosition())).albumId);
                                            }

                                            @Override
                                            protected void done(@Nullable AlbumListData result) {
                                                if (result == null) {
                                                    pasta.onError(activity, "album menu action");
                                                    return;
                                                }

                                                Bundle args = new Bundle();
                                                args.putParcelable("album", result);

                                                Fragment f = new AlbumFragment();
                                                f.setArguments(args);

                                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).addToBackStack(null).commit();
                                            }
                                        }.execute();
                                        break;
                                    case R.id.action_artist:
                                        TrackListData track = (TrackListData) list.get(holder.getAdapterPosition());
                                        if (track.artists.size() > 0) {
                                            new Action<ArtistListData>() {
                                                @NonNull
                                                @Override
                                                public String id() {
                                                    return "gotoArtist";
                                                }

                                                @Nullable
                                                @Override
                                                protected ArtistListData run() throws InterruptedException {
                                                    return pasta.getArtist(((TrackListData) list.get(holder.getAdapterPosition())).artists.get(0).artistId);
                                                }

                                                @Override
                                                protected void done(@Nullable ArtistListData result) {
                                                    if (result == null) {
                                                        pasta.onError(activity, "artist menu action");
                                                        return;
                                                    }

                                                    Bundle args = new Bundle();
                                                    args.putParcelable("artist", result);

                                                    Fragment f = new ArtistFragment();
                                                    f.setArguments(args);

                                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).addToBackStack(null).commit();
                                                }
                                            }.execute();
                                        }
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                });

                TrackListData trackData = (TrackListData) list.get(position);

                image = trackData.trackImage;

                ((TextView) holder.v.findViewById(R.id.name)).setText(trackData.trackName);
                TextView extra = (TextView) holder.v.findViewById(R.id.extra);
                if (trackData.artists.size() > 0)
                    extra.setText(trackData.artists.get(0).artistName);
                else extra.setText("");

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = 0;
                        List<TrackListData> trackList = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i) instanceof TrackListData) {
                                if (i == holder.getAdapterPosition()) position = trackList.size();
                                trackList.add((TrackListData) list.get(i));
                            }
                        }
                        StaticUtils.play(position, trackList, activity);

                        ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(activity, R.anim.slide_up, R.anim.blank);
                        activity.startActivity(new Intent(activity, PlayerActivity.class), options.toBundle());
                    }
                });
                break;
            case 1:
                holder.v.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(v.getContext(), v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.menu_basic, popup.getMenu());

                        final MenuItem fav = popup.getMenu().findItem(R.id.action_fav);
                        if (isFavoriteBehavior) fav.setTitle(R.string.unfav);
                        else new Action<Boolean>() {
                            @NonNull
                            @Override
                            public String id() {
                                return "isAlbumFav";
                            }

                            @Nullable
                            @Override
                            protected Boolean run() throws InterruptedException {
                                return pasta.isFavorite((AlbumListData) list.get(holder.getAdapterPosition()));
                            }

                            @Override
                            protected void done(@Nullable Boolean result) {
                                if (result == null) return;
                                if (result) {
                                    fav.setTitle(R.string.unfav);
                                } else {
                                    fav.setTitle(R.string.fav);
                                }
                            }

                        }.execute();

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(final MenuItem item) {
                                switch (item.getItemId()) {
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
                                                if (!pasta.toggleFavorite((AlbumListData) list.get(holder.getAdapterPosition()))) {
                                                    return null;
                                                } else return pasta.isFavorite((AlbumListData) list.get(holder.getAdapterPosition()));
                                            }

                                            @Override
                                            protected void done(@Nullable Boolean result) {
                                                if (result == null) {
                                                    pasta.onError(activity, "favorite album menu action");
                                                    return;
                                                }
                                                if (result) {
                                                    item.setTitle(R.string.unfav);
                                                } else {
                                                    item.setTitle(R.string.fav);

                                                    if (isFavoriteBehavior) removeData(holder.getAdapterPosition());
                                                }
                                            }

                                        }.execute();
                                        break;
                                    case R.id.action_web:
                                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(StaticUtils.getAlbumUrl(((AlbumListData) list.get(holder.getAdapterPosition())).albumId))));
                                        break;
                                    case R.id.action_share:
                                        AlbumListData data = (AlbumListData) list.get(holder.getAdapterPosition());

                                        Intent s = new Intent(android.content.Intent.ACTION_SEND);
                                        s.setType("text/plain");
                                        s.putExtra(Intent.EXTRA_SUBJECT, data.albumName);
                                        s.putExtra(Intent.EXTRA_TEXT, StaticUtils.getAlbumUrl(data.albumId));
                                        activity.startActivity(Intent.createChooser(s, data.albumName));
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                });

                AlbumListData albumData = (AlbumListData) list.get(position);

                image = albumData.albumImage;

                View artist = holder.v.findViewById(R.id.artist);
                if (artist != null) {
                    if (albumData.artists != null && albumData.artists.size() > 0) {
                        ((TextView) holder.v.findViewById(R.id.artist_name)).setText(albumData.artists.get(0).artistName);
                        ((TextView) holder.v.findViewById(R.id.artist_extra)).setText(albumData.albumDate);

                        new Action<ArtistListData>() {
                            @NonNull
                            @Override
                            public String id() {
                                return "gotoArtist";
                            }

                            @Nullable
                            @Override
                            protected ArtistListData run() throws InterruptedException {
                                return pasta.getArtist(((AlbumListData) list.get(holder.getAdapterPosition())).artists.get(0).artistId);
                            }

                            @Override
                            protected void done(@Nullable ArtistListData result) {
                                if (result == null) {
                                    pasta.onError(activity, "artist action");
                                    return;
                                }

                                holder.v.findViewById(R.id.artist).setTag(result);

                                ImageView artistImage = (ImageView) holder.v.findViewById(R.id.artist_image);
                                if (artistImage != null)
                                    Glide.with(activity).load(result.artistImage).into((ImageView) holder.v.findViewById(R.id.artist_image));
                            }
                        }.execute();

                        artist.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArtistListData artist = (ArtistListData) v.getTag();
                                if (artist != null) {
                                    Bundle args = new Bundle();
                                    args.putParcelable("artist", artist);

                                    Fragment f = new ArtistFragment();
                                    f.setArguments(args);

                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).addToBackStack(null).commit();
                                } else {
                                    new Action<ArtistListData>() {
                                        @NonNull
                                        @Override
                                        public String id() {
                                            return "gotoArtist";
                                        }

                                        @Nullable
                                        @Override
                                        protected ArtistListData run() throws InterruptedException {
                                            return pasta.getArtist(((AlbumListData) list.get(holder.getAdapterPosition())).artists.get(0).artistId);
                                        }

                                        @Override
                                        protected void done(@Nullable ArtistListData result) {
                                            if (result == null) {
                                                pasta.onError(activity, "artist action");
                                                return;
                                            }

                                            Bundle args = new Bundle();
                                            args.putParcelable("artist", result);

                                            Fragment f = new ArtistFragment();
                                            f.setArguments(args);

                                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).addToBackStack(null).commit();
                                        }
                                    }.execute();
                                }
                            }
                        });
                    } else artist.setVisibility(View.GONE);
                }

                ((TextView) holder.v.findViewById(R.id.name)).setText(albumData.albumName);
                ((TextView) holder.v.findViewById(R.id.extra)).setText(String.valueOf(albumData.tracks) + (albumData.tracks == 1 ? " track" : " tracks"));

                holder.v.findViewById(R.id.album).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putParcelable("album", (AlbumListData) list.get(holder.getAdapterPosition()));

                        Fragment f = new AlbumFragment();
                        f.setArguments(args);

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).addToBackStack(null).commit();
                    }
                });
                break;
            case 2:
                holder.v.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(v.getContext(), v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.menu_playlist, popup.getMenu());

                        final MenuItem fav = popup.getMenu().findItem(R.id.action_fav);
                        if (isFavoriteBehavior) fav.setTitle(R.string.unfav);
                        else new Action<Boolean>() {
                            @NonNull
                            @Override
                            public String id() {
                                return "isPlaylistFav";
                            }

                            @Nullable
                            @Override
                            protected Boolean run() throws InterruptedException {
                                return pasta.isFavorite((PlaylistListData) list.get(holder.getAdapterPosition()));
                            }

                            @Override
                            protected void done(@Nullable Boolean result) {
                                if (result == null) return;
                                if (result) {
                                    fav.setTitle(R.string.unfav);
                                } else {
                                    fav.setTitle(R.string.fav);
                                }
                            }

                        }.execute();

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(final MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_fav:
                                        new Action<Boolean>() {
                                            @NonNull
                                            @Override
                                            public String id() {
                                                return "favPlaylist";
                                            }

                                            @Nullable
                                            @Override
                                            protected Boolean run() throws InterruptedException {
                                                if (!pasta.toggleFavorite((PlaylistListData) list.get(holder.getAdapterPosition()))) {
                                                    return null;
                                                } else return pasta.isFavorite((PlaylistListData) list.get(holder.getAdapterPosition()));
                                            }

                                            @Override
                                            protected void done(@Nullable Boolean result) {
                                                if (result == null) {
                                                    pasta.onError(activity, "favorite playlist menu action");
                                                    return;
                                                }
                                                if (result) {
                                                    item.setTitle(R.string.unfav);
                                                } else {
                                                    item.setTitle(R.string.fav);
                                                    if (isFavoriteBehavior) removeData(holder.getAdapterPosition());
                                                }
                                            }

                                        }.execute();
                                        break;
                                    case R.id.action_edit:
                                        new NewPlaylistDialog(activity).setPlaylist((PlaylistListData) list.get(holder.getAdapterPosition())).setOnCreateListener(new NewPlaylistDialog.OnCreateListener() {
                                            @Override
                                            public void onCreate() {
                                                notifyItemChanged(holder.getAdapterPosition());
                                            }
                                        }).show();
                                        break;
                                    case R.id.action_web:
                                        PlaylistListData data = (PlaylistListData) list.get(holder.getAdapterPosition());
                                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(StaticUtils.getPlaylistUrl(data.playlistOwnerId, data.playlistId))));
                                        break;
                                    case R.id.action_share:
                                        PlaylistListData data2 = (PlaylistListData) list.get(holder.getAdapterPosition());

                                        Intent s = new Intent(android.content.Intent.ACTION_SEND);
                                        s.setType("text/plain");
                                        s.putExtra(Intent.EXTRA_SUBJECT, data2.playlistName);
                                        s.putExtra(Intent.EXTRA_TEXT, StaticUtils.getPlaylistUrl(data2.playlistOwnerId, data2.playlistId));
                                        activity.startActivity(Intent.createChooser(s, data2.playlistName));
                                        break;
                                }
                                return false;
                            }
                        });

                        popup.getMenu().findItem(R.id.action_edit).setVisible(true);
                        popup.show();
                    }
                });

                PlaylistListData playlistData = (PlaylistListData) list.get(position);

                image = playlistData.playlistImage;

                ((TextView) holder.v.findViewById(R.id.name)).setText(playlistData.playlistName);
                ((TextView) holder.v.findViewById(R.id.extra)).setText(playlistData.tracks + (playlistData.tracks == 1 ? " track" : " tracks"));

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putParcelable("playlist", (PlaylistListData) list.get(holder.getAdapterPosition()));

                        Fragment f = new PlaylistFragment();
                        f.setArguments(args);

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).addToBackStack(null).commit();
                    }
                });
                break;
            case 3:
                holder.v.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(v.getContext(), v);
                        MenuInflater inflater = popup.getMenuInflater();
                        inflater.inflate(R.menu.menu_basic, popup.getMenu());

                        final MenuItem fav = popup.getMenu().findItem(R.id.action_fav);
                        if (isFavoriteBehavior) fav.setTitle(R.string.unfav);
                        else new Action<Boolean>() {
                            @NonNull
                            @Override
                            public String id() {
                                return "isTrackFav";
                            }

                            @Nullable
                            @Override
                            protected Boolean run() throws InterruptedException {
                                return pasta.isFavorite((ArtistListData) list.get(holder.getAdapterPosition()));
                            }

                            @Override
                            protected void done(@Nullable Boolean result) {
                                if (result == null) return;
                                if (result) {
                                    fav.setTitle(R.string.unfav);
                                } else {
                                    fav.setTitle(R.string.fav);
                                }
                            }

                        }.execute();

                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(final MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.action_fav:
                                        new Action<Boolean>() {
                                            @NonNull
                                            @Override
                                            public String id() {
                                                return "favArtist";
                                            }

                                            @Nullable
                                            @Override
                                            protected Boolean run() throws InterruptedException {
                                                if (!pasta.toggleFavorite((ArtistListData) list.get(holder.getAdapterPosition()))) {
                                                    return null;
                                                } else
                                                    return pasta.isFavorite((ArtistListData) list.get(holder.getAdapterPosition()));
                                            }

                                            @Override
                                            protected void done(@Nullable Boolean result) {
                                                if (result == null) {
                                                    pasta.onError(activity, "favorite artist menu action");
                                                    return;
                                                }
                                                if (result) {
                                                    item.setTitle(R.string.unfav);
                                                } else {
                                                    item.setTitle(R.string.fav);
                                                    if (isFavoriteBehavior) removeData(holder.getAdapterPosition());
                                                }
                                            }

                                        }.execute();
                                        break;
                                    case R.id.action_web:
                                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(StaticUtils.getArtistUrl(((ArtistListData) list.get(holder.getAdapterPosition())).artistId))));
                                        break;
                                    case R.id.action_share:
                                        ArtistListData data = (ArtistListData) list.get(holder.getAdapterPosition());

                                        Intent s = new Intent(android.content.Intent.ACTION_SEND);
                                        s.setType("text/plain");
                                        s.putExtra(Intent.EXTRA_SUBJECT, data.artistName);
                                        s.putExtra(Intent.EXTRA_TEXT, StaticUtils.getArtistUrl(data.artistId));
                                        activity.startActivity(Intent.createChooser(s, data.artistName));
                                        break;
                                }
                                return false;
                            }
                        });
                        popup.show();
                    }
                });

                ArtistListData artistData = (ArtistListData) list.get(position);

                image = artistData.artistImage;

                ((TextView) holder.v.findViewById(R.id.name)).setText(artistData.artistName);
                ((TextView) holder.v.findViewById(R.id.extra)).setText(String.valueOf(artistData.followers) + (artistData.followers == 1 ? " follower" : " followers"));

                holder.v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putParcelable("artist", (ArtistListData) list.get(holder.getAdapterPosition()));

                        Fragment f = new ArtistFragment();
                        f.setArguments(args);

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment, f).addToBackStack(null).commit();
                    }
                });
                break;
            default:
                return;
        }

        ImageView imageView = (ImageView) holder.v.findViewById(R.id.image);

        if (!thumbnails) imageView.setVisibility(View.GONE);
        else {
            Glide.with(activity).load(image).asBitmap().placeholder(ImageUtils.getVectorDrawable(activity, R.drawable.preload)).into(new BitmapImageViewTarget(imageView) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ((CustomImageView) getView()).transition(resource);
                    if (!thumbnails) getView().setVisibility(View.GONE);

                    View bg = holder.v.findViewById(R.id.bg);
                    if (!thumbnails || !palette || bg == null) return;
                    Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            int defaultColor = dark ? Color.DKGRAY : Color.WHITE;
                            int color = palette.getLightVibrantColor(defaultColor);
                            if (dark) color = palette.getDarkVibrantColor(defaultColor);

                            ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), defaultColor, color);
                            animator.setDuration(250);
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    int color = (int) animation.getAnimatedValue();
                                    holder.v.findViewById(R.id.bg).setBackgroundColor(color);

                                    View artist = holder.v.findViewById(R.id.artist);
                                    if (artist != null) artist.setBackgroundColor(Color.argb(255, Math.max(Color.red(color) - 10, 0), Math.max(Color.green(color) - 10, 0), Math.max(Color.blue(color) - 10, 0)));
                                }
                            });
                            animator.start();
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class TrackViewHolder extends ViewHolder {
        public View v;

        public TrackViewHolder(View v) {
            super(v);
            this.v = v;
        }
    }

    public static class AlbumViewHolder extends ViewHolder {
        public View v;

        public AlbumViewHolder(View v) {
            super(v);
            this.v = v;
        }
    }

    public static class PlaylistViewHolder extends ViewHolder {
        public View v;

        public PlaylistViewHolder(View v) {
            super(v);
            this.v = v;
        }
    }

    public static class ArtistViewHolder extends ViewHolder {
        public View v;

        public ArtistViewHolder(View v) {
            super(v);
            this.v = v;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View v;

        public ViewHolder(View v) {
            super(v);
            this.v = v;
        }
    }
}
