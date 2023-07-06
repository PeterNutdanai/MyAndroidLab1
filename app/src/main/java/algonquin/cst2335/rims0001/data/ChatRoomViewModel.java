package algonquin.cst2335.rims0001.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.rims0001.ChatRoom;

public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<String>> messages = new MutableLiveData<>();}


