package algonquin.cst2335.rims0001.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public MutableLiveData<Boolean> isSelected = new MutableLiveData<>(false);
    public MutableLiveData<String> editString = new MutableLiveData<>();
}
