package com.twers.voiceofus.feedback;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockDialogFragment;
import com.twers.voiceofus.R;

import java.util.Random;

import static android.widget.ArrayAdapter.createFromResource;
import static com.twers.voiceofus.util.MailUtil.sendMail;

public class ProvideFeedbackDialog extends SherlockDialogFragment {

    private View feedbackBox;
    private EditText feedbackInput;
    private Spinner moodSelection;
    private Button feedbackButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getActivity().getString(R.string.feedback_dialog_title));
        feedbackBox = inflater.inflate(R.layout.feedback_dialog, container);

        initMoodSelection();
        initFeedbackInput();
        initFeedbackButton();
        return feedbackBox;
    }

    private void initFeedbackButton() {
        feedbackButton = (Button) feedbackBox.findViewById(R.id.feedback_button);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail(getActivity(), (CharSequence) moodSelection.getSelectedItem(), feedbackInput.getText().toString());
            }
        });
    }

    private void initFeedbackInput() {
        feedbackInput = (EditText) feedbackBox.findViewById(R.id.feedback_text);
        feedbackInput.setText(defaultTemplate());
    }

    private void initMoodSelection() {
        moodSelection = (Spinner) feedbackBox.findViewById(R.id.mood_selection);
        moodSelection.setAdapter(createFromResource(getActivity(), R.array.moods, android.R.layout.simple_spinner_item));
        moodSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                TypedArray templateIds = getResources().obtainTypedArray(R.array.mood_templates_ids);
                feedbackInput.setText(getResources().getString(templateIds.getResourceId(position, 0), randomFeatures()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                feedbackInput.setText(defaultTemplate());
            }
        });
    }

    private String defaultTemplate() {
        return getResources().getString(R.string.feedback_template_normal, randomFeatures());
    }

    private String randomFeatures() {
        String[] features = {"idea", "user interface", "function"};
        int randomSelection = new Random(System.currentTimeMillis()).nextInt(features.length);
        return features[randomSelection];
    }
}
