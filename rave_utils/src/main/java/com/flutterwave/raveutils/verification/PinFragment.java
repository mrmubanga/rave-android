package com.flutterwave.raveutils.verification;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.flutterwave.raveandroid.rave_logger.Event;
import com.flutterwave.raveandroid.rave_logger.EventLogger;
import com.flutterwave.raveandroid.rave_logger.events.ScreenLaunchEvent;
import com.flutterwave.raveandroid.rave_logger.events.StartTypingEvent;
import com.flutterwave.raveandroid.rave_logger.events.SubmitEvent;
import com.flutterwave.raveutils.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import static com.flutterwave.raveandroid.rave_java_commons.RaveConstants.EMBED_FRAGMENT;
import static com.flutterwave.raveandroid.rave_java_commons.RaveConstants.OTP_REQUEST_CODE;
import static com.flutterwave.raveandroid.rave_java_commons.RaveConstants.RESULT_SUCCESS;
import static com.flutterwave.raveandroid.rave_java_commons.RaveConstants.VERIFICATION_REQUEST_KEY;
import static com.flutterwave.raveutils.verification.Utils.REQUEST_CODE;
import static com.flutterwave.raveutils.verification.Utils.RESULT_CODE;
import static com.flutterwave.raveutils.verification.VerificationActivity.PUBLIC_KEY_EXTRA;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinFragment extends Fragment {
    public static final String EXTRA_PIN = "extraPin";
    private String pin;
    @Inject
    EventLogger logger;

    public PinFragment() {
        // Required empty public constructor
    }

    private boolean embedFragment = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.rave_sdk_fragment_pin, container, false);
        Button pinBtn = v.findViewById(R.id.rave_pinButton);
        final TextInputEditText pinEv = v.findViewById(R.id.rave_pinEv);
        final TextInputLayout pinTil = v.findViewById(R.id.rave_pinTil);
        final TextInputEditText pinEt = v.findViewById(R.id.rave_pinEv);

        if (getArguments() != null) {
            embedFragment = getArguments().getBoolean(EMBED_FRAGMENT);
            if (getArguments().getInt("theme", 0) != 0) {
                getActivity().setTheme(getArguments().getInt("theme", 0));
            }
        }

        injectComponents(embedFragment);

        logEvent(new ScreenLaunchEvent("PIN Fragment").getEvent());

        pinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = pinEt.getText().toString();

                pinTil.setError(null);
                pinTil.setErrorEnabled(false);

                if (pin.length() != 4) {
                    pinTil.setError("Enter a valid pin");
                }
                else {
                    goBack();
                }
            }
        });

        pinEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    logEvent(new StartTypingEvent("PIN").getEvent());
                }
            }
        });

        return v;
    }

    private void injectComponents(boolean embedFragment) {
        if (getActivity() != null) {
            if (embedFragment) {
                VerificationFragment.getVerificationComponent()
                        .inject(this);
            } else {
                ((VerificationActivity) getActivity()).getVerificationComponent()
                        .inject(this);
            }
        }
    }

    private void logEvent(Event event) {
        if (getArguments() != null
                & getArguments().getString(PUBLIC_KEY_EXTRA) != null
                & logger != null) {
            String publicKey = getArguments().getString(PUBLIC_KEY_EXTRA);
            event.setPublicKey(publicKey);
            logger.logEvent(event);
        }
    }

    public void goBack(){

        if (embedFragment) {
            Bundle bundle = new Bundle();
            bundle.putInt(REQUEST_CODE, OTP_REQUEST_CODE);
            bundle.putString(EXTRA_PIN,pin);
            bundle.putInt(RESULT_CODE, RESULT_SUCCESS);

            logEvent(new SubmitEvent("PIN").getEvent());

            getParentFragmentManager().setFragmentResult(VERIFICATION_REQUEST_KEY, bundle);
            getParentFragmentManager().popBackStack();
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }

        }else {
           Intent intent = new Intent();
            intent.putExtra(EXTRA_PIN,pin);
            logEvent(new SubmitEvent("PIN").getEvent());
            if (getActivity() != null) {
                getActivity().setResult(RESULT_SUCCESS, intent);
                getActivity().finish();
            }
        }

    }

}
