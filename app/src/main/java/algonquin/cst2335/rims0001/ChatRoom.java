package algonquin.cst2335.rims0001;

import android.os.Bundle;
import android.view.LayoutInflater;
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
import algonquin.cst2335.rims0001.databinding.ReceiveMessageBinding;
import algonquin.cst2335.rims0001.databinding.SentMessageBinding;




public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;


    ChatRoomViewModel chatModel ;
    private RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<String> messages = chatModel.messages.getValue();

        if(messages ==null)
        {
            chatModel.messages.postValue(messages = new ArrayList<String>());
        }


        ArrayList<String> msg = messages;
        binding.send.setOnClickListener(click -> {
            msg.add(binding.textInput.getText().toString());
            myAdapter.notifyItemInserted(msg.size()-1);

            binding.textInput.setText("");

            int position = 0;
            if (position >= 0 && position < msg.size()) {
                msg.remove(position);
                myAdapter.notifyItemRemoved(position);
            }

            myAdapter.notifyDataSetChanged();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(chatMessage, currentDateAndTime, true);
            msg.add(String.valueOf(chatMessage));
            myAdapter.notifyItemInserted(msg.size() - 1);
            binding.textInput.setText("");
        });





        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
                                           @NonNull
                                           @Override
                                           public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                           LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                                           if (viewType == 0) {
                                           SentMessageBinding messageBinding = SentMessageBinding.inflate(inflater, parent, false);
                                           return new MyRowHolder(messageBinding.getRoot());
                                            } else {
                                           ReceiveMessageBinding messageBinding = ReceiveMessageBinding.inflate(inflater, parent, false);
                                           return new MyRowHolder(messageBinding.getRoot());
                                            }
                                           }
                                           @Override
                                           public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                                               holder.messageText.setText("Howdy, partner.");
                                               holder.timeText.setText("05:00 AM");
                                               String obj = msg.get(position);
                                               holder.messageText.setText(obj);

                                           }

                                           @Override
                                           public int getItemCount() {

                                               return msg.size();
                                           }

                                           public int getItemViewType(int position){
                                               ChatMessage chatMessage = msg.get(position);
                                               if (chatMessage.isSentButton()) {
                                                   return 0;
                                               } else {
                                                   return 1;
                                               }
                                           }
                                       }
        );

        binding.receive.setOnClickListener(click -> {

            String message = binding.textInput.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(message, currentDateAndTime, false);
            msg.add(String.valueOf(chatMessage));
            myAdapter.notifyItemInserted(msg.size() - 1);

            binding.textInput.setText("");
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