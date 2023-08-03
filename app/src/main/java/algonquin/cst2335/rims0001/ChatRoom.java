package algonquin.cst2335.rims0001;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

import algonquin.cst2335.rims0001.data.MessageDetailsFragment;
import algonquin.cst2335.rims0001.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.rims0001.databinding.ReceiveMessageBinding;
import algonquin.cst2335.rims0001.databinding.SentMessageBinding;


public class ChatRoom extends AppCompatActivity {

    protected ArrayList<ChatMessage> messages = new ArrayList<>();

    protected Button myButton;
    protected Button receButton;
    protected RecyclerView recyclerView;

    /** This holds the edit text for typing into */
    protected EditText theTextInput;
    ChatRoomViewModel chatModel;
    RecyclerView.Adapter myAdapter;
    ChatMessageDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        ActivityChatRoomBinding binding = ActivityChatRoomBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        myButton = binding.send;
        receButton = binding.receive;
        theTextInput = binding.textInput;
        recyclerView = binding.recycleView;

        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<ChatMessage>() );
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() );

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter ));
            });
        }

        myButton.setOnClickListener(click -> {
            String input = theTextInput.getText().toString();

            boolean type = true;
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MMM-yyyy hh:mm a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage cm = new ChatMessage(0,input, currentDateAndTime, type);

            messages.add(cm);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(cm);
            });

            myAdapter.notifyItemInserted(messages.size());

            theTextInput.setText("");
            System.out.println("clicked send button");
        });

        receButton.setOnClickListener(click ->{
            String input = theTextInput.getText().toString();
            System.out.println("clicked receive button");
            boolean type = false;
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MMM-yyyy hh:mm a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage cm = new ChatMessage(0,input, currentDateAndTime, type);

            messages.add(cm);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(cm);
            });

            myAdapter.notifyItemInserted(messages.size());
            theTextInput.setText("");

        });


        recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

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
                //update the widgets:
                ChatMessage atThisRow = messages.get(position);
                Log.d("ChatRoom", "Position: " + position + ", Message: " + atThisRow.getMessage() + ", Time: " + atThisRow.getTimeSent() + ", Type: " + atThisRow.isSentButton());
                holder.timeText.setText(atThisRow.getTimeSent());
                holder.timeText.setText(atThisRow.getTimeSent());
                holder.messageText.setText(atThisRow.getMessage());

                holder.itemView.setOnClickListener(clk -> {

                    ChatMessage selected = messages.get(position);
                    chatModel.selectedMessage.postValue(selected);
                    chatModel.selectedMessagePosition.postValue(position);
                });
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
                if(messages.get(position).isSentButton())
                {
                    return 0;
                }
                else
                {
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatModel.selectedMessage.observe(this, (newMessageValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment( newMessageValue );
            FragmentManager fMgr = getSupportFragmentManager();
            FragmentTransaction tx = fMgr.beginTransaction();
            tx.add(R.id.fragmentLocation, chatFragment);
            tx.addToBackStack("");
            tx.commit();
        });

        setSupportActionBar(binding.myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to delete this message?");
            builder.setTitle("Confirmation");
            builder.setPositiveButton("Yes", (dialog, cl) -> {
                Integer position = chatModel.selectedMessagePosition.getValue();
                if (position != null && position >= 0 && position < messages.size()) {
                    ChatMessage removedMessage = messages.remove((int) position);
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() -> {
                        mDAO.deleteMessage(removedMessage);
                    });
                    myAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                            .setAction("Undo", click -> {
                                messages.add(position, removedMessage);
                                myAdapter.notifyItemInserted(position);
                                thread.execute(() -> {
                                    mDAO.insertMessage(removedMessage);
                                });
                            })
                            .show();
                }
            });
            builder.setNegativeButton("No", (dialog, cl) -> {
            });
            builder.create().show();
        }
        return true;
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

//                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
//                builder.setMessage("Do you want to delete this message: " + messageText.getText());
//                builder.setTitle("Question:");
//                builder.setPositiveButton("No", (dialog, cl) -> {});
//                builder.setNegativeButton("Yes", (dialog, cl) -> {
//                    ChatMessage removedMessage = messages.get(position);
//                    messages.remove(position);
//                    Executor thread = Executors.newSingleThreadExecutor();
//                    thread.execute(() ->
//                    {
//                        mDAO.deleteMessage(removedMessage);
//                    });
//
//                    myAdapter.notifyItemRemoved(position);
//
//                    Snackbar.make(messageText, "You deleted message#" + position, Snackbar.LENGTH_LONG)
//                            .setAction("Undo", click -> {
//                                messages.add(position, removedMessage);
//                                myAdapter.notifyItemInserted(position);
//                                thread.execute(() ->
//                                {
//                                    mDAO.insertMessage(removedMessage);
//                                });
//                            })
//                            .show();
//                }).create().show();
            });
        }
    }
        }
