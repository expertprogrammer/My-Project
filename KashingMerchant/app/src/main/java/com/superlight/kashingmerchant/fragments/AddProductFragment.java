package com.superlight.kashingmerchant.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.superlight.kashingmerchant.R;
import com.superlight.kashingmerchant.selector.OnSettingEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class AddProductFragment extends Fragment implements View.OnClickListener{

    ImageView productPhotoView;
    AlertDialog dialog;
    LinearLayout phoneView;

    Activity activity;

    static final int SELECT_FILE = 1;
    static final int REQUEST_CAMERA = 0;

    String photoData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_add_product, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.activity = activity;
    }

    public void initView(View rootView){
        rootView.findViewById(R.id.back_view).setOnClickListener(this);
        phoneView = (LinearLayout)rootView.findViewById(R.id.phone_view);
        productPhotoView = (ImageView)rootView.findViewById(R.id.iv_product);
        rootView.findViewById(R.id.select_photo_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_view:
                try{
                    ((OnSettingEventListener)activity).onBackProductsPressed();
                }catch (ClassCastException cce){

                }
                break;
            case R.id.select_photo_view:
                popupDialog();
                break;
            case R.id.select_gallery:
                dialog.dismiss();
                Intent intent1 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent1.setType("image/*");
                startActivityForResult(Intent.createChooser(intent1, "Select Image"), SELECT_FILE);
                break;
            case R.id.select_camera:
                dialog.dismiss();
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent2, REQUEST_CAMERA);
                break;
            case R.id.select_cancel:
                dialog.dismiss();
                break;
        }
    }

    public void popupDialog(){
        dialog = new AlertDialog.Builder(getActivity(), R.style.dialog_style).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_action_center);

        //get display width//
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if(phoneView != null){
            window.setLayout(3 * width / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
        }else{
            window.setLayout(2 * width / 3, 17 * height / 64);
        }
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(layoutParams);
        window.findViewById(R.id.select_gallery).setOnClickListener(this);
        window.findViewById(R.id.select_camera).setOnClickListener(this);
        window.findViewById(R.id.select_cancel).setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                productPhotoView.setImageBitmap(thumbnail);
            } else if (requestCode == SELECT_FILE ) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                productPhotoView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            }
            productPhotoView.buildDrawingCache();
            Bitmap bmap = productPhotoView.getDrawingCache();
            String encodedImageData = getEncoded64ImageStringFromBitmap(bmap);
            photoData = encodedImageData;
        }
    }

    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        byte[] byteFormat = stream.toByteArray();
        //get the base 64 String
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

}
