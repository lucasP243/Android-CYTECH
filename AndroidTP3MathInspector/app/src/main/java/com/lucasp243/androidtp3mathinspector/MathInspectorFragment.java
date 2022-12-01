package com.lucasp243.androidtp3mathinspector;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public class MathInspectorFragment extends Fragment {

    private View inflatedView;

    private Integer selectedMultiplier = 2;

    private TextView userInput;

    private Button buttonCheck;

    private void updateButtonCheck() {
        buttonCheck.setText(
                String.format(
                        Locale.getDefault(),
                        getString(R.string.action_check),
                        selectedMultiplier
                )
        );
    }

    public MathInspectorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return this.inflatedView = inflater.inflate(R.layout.fragment_math_inspector, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userInput = inflatedView.findViewById(R.id.user_input);
        buttonCheck = inflatedView.findViewById(R.id.btn_check);

        updateButtonCheck();

        // Add onClick and onLongClick listeners for buttons "R.id.keyboard_[1-9]"
        IntStream.range(1, 10)
                .mapToObj(
                        i -> {
                            Pair<Integer, Button> obj = null;
                            try {
                                obj = new Pair<>(i, inflatedView.findViewById(
                                        R.id.class.getField(
                                                String.format(Locale.getDefault(), "keyboard_%d", i)
                                        ).getInt(null)
                                ));
                            } catch (IllegalAccessException | NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                            return obj;
                        }
                )
                .forEach((pair) -> {
                    if (pair == null) return;
                    pair.second.setOnClickListener(handlerWriteNumber(pair.first));
                    pair.second.setOnLongClickListener(handlerSelectMultiplier(pair.first));
                })
        ;

        // Add onClick listeners to other buttons
        inflatedView.findViewById(R.id.keyboard_0).setOnClickListener(handlerWriteNumber(0));
        inflatedView.findViewById(R.id.keyboard_ret).setOnClickListener(handlerEraseNumber);
        inflatedView.findViewById(R.id.keyboard_clr).setOnClickListener(handlerClearNumber);
        inflatedView.findViewById(R.id.btn_check).setOnClickListener(handlerCheckMultiple);
        inflatedView.findViewById(R.id.btn_userguide).setOnClickListener(handlerOpenUserGuide);
    }

    private View.OnClickListener handlerWriteNumber(Integer digit) {
        return key -> {
            int input = Integer.parseInt(userInput.getText() + digit.toString());
            userInput.setText(String.format(Locale.getDefault(), "%d", input));
        };
    }

    private View.OnLongClickListener handlerSelectMultiplier(Integer digit) {
        return key -> {
            selectedMultiplier = digit;
            updateButtonCheck();
            return true;
        };
    }

    private final View.OnClickListener handlerEraseNumber = key ->
            userInput.setText(userInput.getText().toString().substring(0, -1));

    private final View.OnClickListener handlerClearNumber = key -> userInput.setText("0");

    private final View.OnClickListener handlerCheckMultiple = btn -> {
        Integer input = Integer.parseInt(userInput.getText().toString());
        Toast
                .makeText(
                        getActivity(),
                        String.format(
                                Locale.getDefault(),
                                getString(
                                        input % selectedMultiplier == 0 ?
                                                R.string.check_true :
                                                R.string.check_false
                                ),
                                input, selectedMultiplier
                        ),
                        Toast.LENGTH_LONG
                )
                .show()
        ;
    };

    private final View.OnClickListener handlerOpenUserGuide = btn -> Navigation
            .findNavController(requireActivity(), R.id.nav_host_fragment)
            .navigate(R.id.open_userguide);
}