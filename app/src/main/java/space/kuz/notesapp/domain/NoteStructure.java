package space.kuz.notesapp.domain;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import java.util.Date;

public class NoteStructure  implements Parcelable {
    @Nullable
    private Integer id;
    private String head;
    private String description;
    private Date date;

    public NoteStructure(){

    }

    public NoteStructure( String head, String description, Date date) {
        this.head = head;
        this.description = description;
        this.date = date;
    }

    protected NoteStructure(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        head = in.readString();
        description = in.readString();
    }

    public static final Creator<NoteStructure> CREATOR = new Creator<NoteStructure>() {
        @Override
        public NoteStructure createFromParcel(Parcel in) {
            return new NoteStructure(in);
        }

        @Override
        public NoteStructure[] newArray(int size) {
            return new NoteStructure[size];
        }
    };

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(head);
        dest.writeString(description);
    }
}
