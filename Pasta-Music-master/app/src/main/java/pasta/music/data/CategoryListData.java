package pasta.music.data;

import android.os.Parcel;
import android.os.Parcelable;

public class CategoryListData  implements Parcelable {
    public static final Creator<CategoryListData> CREATOR = new Creator<CategoryListData>() {
        public CategoryListData createFromParcel(Parcel in) {
            return new CategoryListData(in);
        }

        public CategoryListData[] newArray(int size) {
            return new CategoryListData[size];
        }
    };

    public String categoryId;
    public String categoryName;
    public String categoryImage;

    public CategoryListData(Parcel in) {
        ReadFromParcel(in);
    }

    private void ReadFromParcel(Parcel in) {
        categoryId = in.readString();
        categoryName = in.readString();
        categoryImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(categoryId);
        out.writeString(categoryName);
        out.writeString(categoryImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
