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
import android.widget.Toolbar;

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
import algonquin.cst2335.rims0001.databinding.ActivityMainBinding;
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
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }

        myButton.setOnClickListener(click -> {
            String input = theTextInput.getText().toString();

            boolean type = true;
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MMM-yyyy hh:mm a");
            String currentDateAndTime = sdf.format(new Date());

            ChatMessage cm = new ChatMessage(0,input, currentDateAndTime, type);
            //insert into ArrayList
            messages.add(cm);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(cm);
            });


            myAdapter.notifyItemInserted(messages.size()); //updates the rows
//            myAdapter.notifyDataSetChanged(); //updates the rows

            //clear input
            theTextInput.setText("");
            System.out.println("clicked send button");
        });

        receButton.setOnClickListener(click ->{
            String input = theTextInput.getText().toString();
            System.out.println("clicked receive button");
            boolean type = false;
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MMM-yyyy hh:mm a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage cm = new ChatMessage(0,input, currentDateandTime, type);
            //insert into ArrayList
            messages.add(cm);
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(cm);
            });

            myAdapter.notifyItemInserted(messages.size()); //updates the rows
//            myAdapter.notifyDataSetChanged(); //updates the rows


            //clear input
            theTextInput.setText("");

        });


        /**
         * A RecyclerView.Adapter object needs 3 functions to tell the view how to draw items in the list.
         */
        recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //this inflates the row layout

                //int viewType is what layout to load
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


            /**
             * This initializes a ViewHolder to go at the row specified by the position parameter
             * @param holder The ViewHolder which should be updated to represent the contents of the
             *        item at the given position in the data set.
             * @param position The position of the item within the adapter's data set.
             */
            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                //update the widgets:
                ChatMessage atThisRow = messages.get(position);
                Log.d("ChatRoom", "Position: " + position + ", Message: " + atThisRow.getMessage() + ", Time: " + atThisRow.getTimeSent() + ", Type: " + atThisRow.isSentButton());
                holder.timeText.setText(atThisRow.getTimeSent());
                holder.timeText.setText(atThisRow.getTimeSent());
                holder.messageText.setText(atThisRow.getMessage());//puts the string in position at theWord TextView

                holder.itemView.setOnClickListener(clk -> {

                    ChatMessage selected = messages.get(position);
                    chatModel.selectedMessage.postValue(selected);
                    chatModel.selectedMessagePosition.postValue(position);
                });
            }

            /**
             * This function just returns an int specifying how many items to draw.
             * @return
             */
            @Override
            public int getItemCount() {
                return messages.size();
            }

            /**
             * load different layouts for different rows, this function lets you return an int to indicate which layout to load. For now, we are just loading one layout, so you can just return 0 for this function:
             * @param position position to query
             * @return an int which is the parameter which gets passed in to the onCreateViewHolder(ViewGroup parent, int viewType) function
             */
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

        /**
         * LayoutManager
         * The RecyclerView supports 1 or more columns for showing data, and you can either scroll in a Vertical or Horizontal direction through the items.
         * To specify a single column scrolling in a Vertical direction, you call:
         */
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chatModel.selectedMessage.observe(this, (newMessageValue) -> {
            MessageDetailsFragment chatFragment = new MessageDetailsFragment( newMessageValue );  //newValue is the newly set ChatMessage
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


    //        The whole point of the MyRowHolder class is to maintain variables for what you want to set on each row in your list.
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
