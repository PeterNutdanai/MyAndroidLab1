package algonquin.cst2335.rims0001.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;
    @ColumnInfo(name = "message")
    protected String message;
    @ColumnInfo(name = "TimeSent")
    protected String timeSent;
    @ColumnInfo(name = "IsSentButton")
    protected boolean isSentButton;

    public ChatMessage() {
        // Empty constructor required by Room
    }
    public ChatMessage(int id,String m, String t, boolean sent)
    {
        this.id = id;
        this.message = m;
        this.timeSent = t;
        this.isSentButton = sent;
    }
    public String getMessage() {
        return message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }
}
