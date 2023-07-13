package algonquin.cst2335.rims0001;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.rims0001.data.ChatMessage;
import algonquin.cst2335.rims0001.data.ChatMessageDAO;
import algonquin.cst2335.rims0001.data.ChatRoomViewModel;
import algonquin.cst2335.rims0001.data.MessageDatabase;
import algonquin.cst2335.rims0001.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.rims0001.databinding.ReceiveMessageBinding;
import algonquin.cst2335.rims0001.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    protected ArrayList<ChatMessage> messages = new ArrayList<>();

    protected EditText theTextInput;

    ChatRoomViewModel chatModel ;
    RecyclerView.Adapter myAdapter;
    ChatMessageDAO mDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>() );
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }

        binding.send.setOnClickListener(click -> {
            String message = theTextInput.getText().toString();
            binding.textInput.setText("");

            boolean type = true;
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(0,message, currentDateAndTime, type);
            messages.add(chatMessage);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(chatMessage);
            });

            myAdapter.notifyItemInserted(messages.size());

            // clear input
            binding.textInput.setText("");
            System.out.println("Clicked send button");
        });

        binding.receive.setOnClickListener(click -> {
            String message = theTextInput.getText().toString();
            System.out.println("Clicked receive button");
            boolean type = false;
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MMM-yyyy hh:mm:ss a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage chatMessage = new ChatMessage(0,message, currentDateAndTime, type);
            messages.add(chatMessage);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(chatMessage);
            });

            myAdapter.notifyItemInserted(messages.size() );

            //clear input
            binding.textInput.setText("");
        });


        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                if(viewType == 0) {
                    SentMessageBinding binding =                           //how big is parent?
                            SentMessageBinding.inflate(getLayoutInflater(), parent, false);

                    return new MyRowHolder(binding.getRoot());
                }
                else {
                    ReceiveMessageBinding binding =
                            ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
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

            itemView.setOnClickListener(clk -> {
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setMessage("Do you want to delete this message: " + messageText.getText());
                builder.setTitle("Question:");
                builder.setPositiveButton("No", (dialog, cl) -> {});
                builder.setNegativeButton("Yes", (dialog, cl) -> {
                    ChatMessage removedMessage = messages.get(position);
                    messages.remove(position);
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() ->
                    {
                        mDAO.deleteMessage(removedMessage);
                    });

                    myAdapter.notifyItemRemoved(position);

                    Snackbar.make(messageText, "You deleted message#" + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", click -> {
                                messages.add(position, removedMessage);
                                myAdapter.notifyItemInserted(position);
                                thread.execute(() ->
                                {
                                    mDAO.insertMessage(removedMessage);
                                });
                            })
                            .show();
                }).create().show();
            });
        }
    }
}