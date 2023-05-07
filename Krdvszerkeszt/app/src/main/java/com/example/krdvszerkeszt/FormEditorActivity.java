package com.example.krdvszerkeszt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FormEditorActivity extends AppCompatActivity {
    private static final String LOG_TAG = FormEditorActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private NotificationHandler mNotificationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_editor);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            Log.d(LOG_TAG, "Hitelesített felhasználó!");
        } else {
            Log.d(LOG_TAG, "Nem hitelesített felhasználó!");
            finish();
        }

        mNotificationHandler = new NotificationHandler(this);

    }

    public void putTextBox(View view) {
        LinearLayout linearLayout = findViewById(R.id.form_view);

        TextView textView1 = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView1.setLayoutParams(layoutParams);

        linearLayout.addView(textView1);


        EditText textView2 = new EditText(this);

        textView2.setLayoutParams(layoutParams);
        textView2.setHint("A kérdés:");
        textView2.setMaxLines(3);

        linearLayout.addView(textView2);
    }

    public void putCheckBox(View view) {
        LinearLayout linearLayout = findViewById(R.id.form_view);

        EditText editText = new EditText(this);
        editText.setHint("Add meg az opciók számát!");
        linearLayout.addView(editText);

        CheckBox addButton = new CheckBox(this);
        addButton.setText("Hozzáadás");
        linearLayout.addView(addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                int numOptions = Integer.parseInt(input);

                linearLayout.removeView(editText);
                linearLayout.removeView(addButton);

                RadioGroup radioGroup = new RadioGroup(FormEditorActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                radioGroup.setLayoutParams(layoutParams);

                for (int i = 0; i < numOptions; i++) {
                    EditText optionEditText = new EditText(FormEditorActivity.this);
                    optionEditText.setHint("Opció " + (i+1));
                    linearLayout.addView(optionEditText);

                    CheckBox optionButton = new CheckBox(FormEditorActivity.this);
                    optionButton.setText("Opció hozzáadása");
                    optionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckBox checkBox = new CheckBox(FormEditorActivity.this);
                            checkBox.setText(optionEditText.getText().toString());
                            radioGroup.addView(checkBox);

                            optionEditText.setText("");
                            linearLayout.removeView(optionEditText);
                            linearLayout.removeView(optionButton);
                        }
                    });
                    linearLayout.addView(optionButton);
                }

                linearLayout.addView(radioGroup);
            }
        });



    }

    public void putRadioButton(View view) {
        LinearLayout linearLayout = findViewById(R.id.form_view);

        EditText editText = new EditText(this);
        editText.setHint("Add meg az opciók számát!");
        linearLayout.addView(editText);

        RadioButton addButton = new RadioButton(this);
        addButton.setText("Hozzáadás");
        linearLayout.addView(addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                int numOptions = Integer.parseInt(input);

                linearLayout.removeView(editText);
                linearLayout.removeView(addButton);

                RadioGroup radioGroup = new RadioGroup(FormEditorActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                radioGroup.setLayoutParams(layoutParams);

                for (int i = 0; i < numOptions; i++) {
                    EditText optionEditText = new EditText(FormEditorActivity.this);
                    optionEditText.setHint("Opció " + (i+1));
                    linearLayout.addView(optionEditText);

                    RadioButton optionButton = new RadioButton(FormEditorActivity.this);
                    optionButton.setText("Opció hozzáadása");
                    optionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RadioButton radioButton = new RadioButton(FormEditorActivity.this);
                            radioButton.setText(optionEditText.getText().toString());
                            radioGroup.addView(radioButton);

                            optionEditText.setText("");
                            linearLayout.removeView(optionEditText);
                            linearLayout.removeView(optionButton);
                        }
                    });
                    linearLayout.addView(optionButton);
                }

                linearLayout.addView(radioGroup);
            }
        });


    }

    public void putSpinner(View view) {
        //TODO: megvalositani vh h lehessen opciok szamat es nevet allitani

    }


    public void putTrashCan(View view) {
        LinearLayout linearLayout = findViewById(R.id.form_view);


        int numViews = linearLayout.getChildCount();
        if (numViews > 0) {
            View lastView = linearLayout.getChildAt(numViews - 1);
            linearLayout.removeView(lastView);
        }

        mNotificationHandler.Send("Utoljára hozzáadott eszköz törölve!");
    }
}