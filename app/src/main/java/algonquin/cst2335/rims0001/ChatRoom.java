package algonquin.cst2335.rims0001;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import algonquin.cst2335.rims0001.databinding.ActivityChatRoomBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_chat_room);
    }


}