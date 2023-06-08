package algonquin.cst2335.rims0001;

import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import java.lang.NullPointerException;
import java.text.BreakIterator;
import java.text.CollationElementIterator;

public class ActivityMainBinding {
    public View loginButton;
    public AppCompatTextView emailText;
    public View goBackButton;
    public CollationElementIterator textView3;
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
