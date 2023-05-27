package algonquin.cst2335.rims0001;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.text.BreakIterator;
import java.text.CollationElementIterator;

public class ActivityMainBinding {

    public TextView textview;
    public Button mybutton;
    public BreakIterator myedit;
    public CollationElementIterator mytext;
    public CompoundButton theCheckbox;
    public CompoundButton theSwitch;
    public CompoundButton radioButton;
    public View picture;
    public View myimagebutton;
    private int root;

    public static ActivityMainBinding inflate(LayoutInflater layoutInflater) {
        return null;
    }

    public int getRoot() {
        return root;
    }

    public void setRoot(int root) {
        this.root = root;
    }
}

