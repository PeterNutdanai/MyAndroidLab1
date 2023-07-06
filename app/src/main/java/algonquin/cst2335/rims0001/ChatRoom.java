package algonquin.cst2335.rims0001;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.rims0001.data.ChatRoomViewModel;
import algonquin.cst2335.rims0001.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.rims0001.databinding.SentMessageBinding;




public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;


    ChatRoomViewModel chatModel ;
    private RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        ArrayList<String> messages = chatModel.messages.getValue();

        if(messages ==null)
        {
            chatModel.messages.postValue(messages = new ArrayList<String>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<String> finalMessages = messages;
        ArrayList<String> finalMessages1 = messages;
        binding.send.setOnClickListener(click -> {
            finalMessages.add(binding.textInput.getText().toString());
            myAdapter.notifyItemInserted(finalMessages.size()-1);

            binding.textInput.setText("");

            int position = 0;
            if (position >= 0 && position < finalMessages.size()) {
                finalMessages.remove(position);
                myAdapter.notifyItemRemoved(position);
            }

            myAdapter.notifyDataSetChanged();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(chatMessage, currentDateAndTime, true);
            finalMessages.add(String.valueOf(chatMessage));
            myAdapter.notifyItemInserted(finalMessages.size() - 1);
            binding.textInput.setText("");
        });





        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
                                           @NonNull
                                           @Override
                                           public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                               SentMessageBinding messageBinding = SentMessageBinding.inflate(getLayoutInflater());
                                               return new MyRowHolder( messageBinding.getRoot() );
                                           }

                                           @Override
                                           public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                                               holder.messageText.setText("Hello, my friend.");
                                               holder.timeText.setText("05:00 AM");
                                               String obj = finalMessages.get(position);
                                               holder.messageText.setText(obj);

                                           }

                                           @Override
                                           public int getItemCount() {

                                               return finalMessages.size();
                                           }

                                           public int getItemViewType(int position){
                                               return 0;
                                           }
                                       }
        );

        finalMessages = messages;
        ArrayList<String> finalMessages2 = finalMessages;
        binding.receive.setOnClickListener(click -> {
            String message = binding.textInput.getText().toString();
            if (!TextUtils.isEmpty(message)) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
                String currentDateandTime = sdf.format(new Date());

                ChatMessage chatMessage = new ChatMessage(message, currentDateandTime, false);
                finalMessages2.add(String.valueOf(chatMessage));
                myAdapter.notifyItemInserted(finalMessages2.size() - 1);

                binding.textInput.setText("");
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public class ChatMessage{
        String message;
        String timeSent;
        boolean isSentButton;

        ArrayList<ChatMessage> chatMessages;

        ChatMessage (String m, String t, boolean sent)
        {
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
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

}