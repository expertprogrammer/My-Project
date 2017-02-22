package pasta.music.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.ThumbnailUtils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.afollestad.async.Action;

import pasta.music.utils.ImageUtils;

public class CircleImageView extends CustomImageView {
    Paint paint;

    public CircleImageView(final Context context) {
        super(context);
        paint = new Paint();
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint();
    }

    @Override
    public void onDraw(Canvas canvas) {
        Bitmap image = ImageUtils.drawableToBitmap(getDrawable());
        if (image != null) {
            int size = Math.min(getWidth(), getHeight());
            image = ThumbnailUtils.extractThumbnail(image, size, size);

            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), image);

            roundedBitmapDrawable.setCornerRadius(size / 2);
            roundedBitmapDrawable.setAntiAlias(true);

            canvas.drawBitmap(ImageUtils.drawableToBitmap(roundedBitmapDrawable), 0, 0, paint);
        }
    }

    @Override
    public void transition(final Bitmap second) {
        if (second == null || second.getWidth() < 1 || second.getHeight() < 1) return;
        final int size = Math.min(getWidth(), getHeight());
        final Resources resources = getResources();
        new Action<Bitmap>() {
            @NonNull
            @Override
            public String id() {
                return "transitionDrawable";
            }

            @Nullable
            @Override
            protected Bitmap run() throws InterruptedException {
                RoundedBitmapDrawable rSecond;

                try {
                    rSecond = RoundedBitmapDrawableFactory.create(resources, ThumbnailUtils.extractThumbnail(second, size, size));
                    rSecond.setCornerRadius(size / 2) ;
                    rSecond.setAntiAlias(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

                return rSecond.getBitmap();
            }

            @Override
            protected void done(@Nullable final Bitmap result) {
                if (result == null) {
                    setImageBitmap(second);
                    return;
                }

                Animation exitAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
                exitAnim.setDuration(150);
                exitAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {

                    }

                    @Override public void onAnimationRepeat(Animation animation) {

                    }

                    @Override public void onAnimationEnd(Animation animation) {
                        setImageBitmap(result);
                        Animation enterAnim = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in);
                        enterAnim.setDuration(150);
                        startAnimation(enterAnim);
                    }
                });
                startAnimation(exitAnim);
            }
        }.execute();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size = getMeasuredWidth();
        setMeasuredDimension(size, size);
    }
}
