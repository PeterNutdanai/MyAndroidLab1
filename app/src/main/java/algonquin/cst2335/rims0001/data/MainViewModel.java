package algonquin.cst2335.rims0001.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.content.Context;
import android.widget.Toast;

public class MainViewModel extends ViewModel {
    public MutableLiveData<Boolean> isSelected = new MutableLiveData<>(false);
    public MutableLiveData<String> editString = new MutableLiveData<>();

    public void toastMessage(final Context context){
        isSelected.observeForever(isChecked -> {

            String message = "The value is now: " + isChecked.toString();
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

        });
    }
}
