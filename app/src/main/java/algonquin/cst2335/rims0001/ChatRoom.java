package algonquin.cst2335.rims0001;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import algonquin.cst2335.rims0001.data.ChatMessage;
import algonquin.cst2335.rims0001.data.ChatRoomViewModel;
import algonquin.cst2335.rims0001.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.rims0001.databinding.ReceiveMessageBinding;
import algonquin.cst2335.rims0001.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    protected ArrayList<ChatMessage> messages = new ArrayList<>();

    protected EditText theTextInput;

    ChatRoomViewModel chatModel ;
    RecyclerView.Adapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        ActivityChatRoomBinding binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(messages ==null)
        {
            chatModel.messages.postValue(messages = new ArrayList<ChatMessage>());
        }

        binding.send.setOnClickListener(click -> {
            String message = theTextInput.getText().toString();
            binding.textInput.setText("");

            boolean type = true;
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(message, currentDateAndTime, type);
            messages.add(chatMessage);

            myAdapter.notifyItemInserted(messages.size());

            binding.textInput.setText("Clicked send");
        });

        binding.receive.setOnClickListener(click -> {
            String message = theTextInput.getText().toString();
            boolean type = false;
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MMM-yyyy hh:mm:ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(message, currentDateAndTime, type);
            messages.add(chatMessage);

            myAdapter.notifyItemInserted(messages.size() );

            binding.textInput.setText("");
        });


        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                if (viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot());
                } else {
                    ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage message = messages.get(position);
                Log.d("ChatRoom", "Position: " + position + ", Message: " + message.getMessage() + ", Time: " + message.getTimeSent() + ", Type: " + message.isSentButton());
                holder.timeText.setText(message.getTimeSent());
                holder.timeText.setText(message.getTimeSent());
                holder.messageText.setText(message.getMessage());
            }
            @Override
            public int getItemCount() {
                return messages.size();
            }

            public int getItemViewType(int position){
                if (messages.get(position).isSentButton()) {
                    return 0;
                } else {
                    return 1;
                }
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

   
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);

//            itemView.setOnClickListener(clk -> {
//                int position = getAbsoluteAdapterPosition();
//                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
//                builder.setMessage("Do you want to delete this message: " + messageText.getText());
//                builder.setTitle("Question:");
//                builder.setPositiveButton("No", (dialog, cl) -> {});
//                builder.setNegativeButton("Yes", (dialog, cl) -> {
//                    ChatMessage removedMessage = messages.get(position);
//                    messages.remove(position);
//                    myAdapter.notifyItemRemoved(position);
//
//                    Snackbar.make(messageText, "You deleted message#" + position, Snackbar.LENGTH_LONG)
//                            .setAction("Undo", click -> {
//                                messages.add(position, removedMessage);
//                                myAdapter.notifyItemInserted(position);
//                            });
//                });
//            });
        }

    }

}