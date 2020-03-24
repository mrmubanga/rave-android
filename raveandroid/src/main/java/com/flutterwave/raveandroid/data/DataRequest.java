package com.flutterwave.raveandroid.data;

import com.flutterwave.raveandroid.rave_java_commons.Payload;
import com.flutterwave.raveandroid.rave_remote.Callbacks;
import com.flutterwave.raveandroid.rave_remote.FeeCheckRequestBody;
import com.flutterwave.raveandroid.rave_remote.SavedCard;
import com.flutterwave.raveandroid.rave_remote.requests.ChargeRequestBody;
import com.flutterwave.raveandroid.rave_remote.requests.EventBody;
import com.flutterwave.raveandroid.rave_remote.requests.LookupSavedCardsRequestBody;
import com.flutterwave.raveandroid.rave_remote.requests.RequeryRequestBody;
import com.flutterwave.raveandroid.rave_remote.requests.RequeryRequestBodyv2;
import com.flutterwave.raveandroid.rave_remote.requests.SaveCardRequestBody;
import com.flutterwave.raveandroid.rave_remote.requests.SendOtpRequestBody;
import com.flutterwave.raveandroid.rave_remote.requests.ValidateChargeBody;

import java.util.List;

/**
 * Created by hamzafetuga on 18/07/2017.
 */

public interface DataRequest {

    interface NetworkRequest {
        void charge(ChargeRequestBody chargeRequestBody, Callbacks.OnChargeRequestComplete callback);

        void chargeWithPolling(ChargeRequestBody chargeRequestBody, Callbacks.OnChargeRequestComplete callback);

        void chargeMobileMoneyWallet(ChargeRequestBody chargeRequestBody, Callbacks.OnGhanaChargeRequestComplete callback);
        void validateChargeCard(ValidateChargeBody cardRequestBody, Callbacks.OnValidateChargeCardRequestComplete callback);
        void validateAccountCard(ValidateChargeBody cardRequestBody, Callbacks.OnValidateChargeCardRequestComplete callback);

        void sendRaveOtp(SendOtpRequestBody requestBody, Callbacks.OnSendRaveOTPRequestComplete callback);
        void requeryTx(RequeryRequestBody requeryRequestBody, Callbacks.OnRequeryRequestComplete callback);
        void requeryPayWithBankTx(RequeryRequestBody requeryRequestBody, Callbacks.OnRequeryRequestComplete callback);
        void requeryTxv2(RequeryRequestBodyv2 requeryRequestBody, Callbacks.OnRequeryRequestv2Complete callback);
        void getBanks(Callbacks.OnGetBanksRequestComplete callback);
        void chargeAccount(ChargeRequestBody accountRequestBody, Callbacks.OnChargeRequestComplete callback);
        void chargeSaBankAccount(ChargeRequestBody accountRequestBody, Callbacks.OnSaChargeRequestComplete callback);
        void chargeToken(Payload payload, Callbacks.OnChargeRequestComplete callback);
        void getFee(FeeCheckRequestBody body, Callbacks.OnGetFeeRequestComplete callback);

        void logEvent(EventBody body, Callbacks.OnLogEventComplete callback);

        void saveCardToRave(SaveCardRequestBody saveCardRequestBody, Callbacks.OnSaveCardRequestComplete callback);

        void lookupSavedCards(
                LookupSavedCardsRequestBody requestBody,
                final Callbacks.OnLookupSavedCardsRequestComplete callback);

    }

    interface SharedPrefsRequest {
        void saveCardToSharedPreference(List<SavedCard> card, String email, String publicKey);

        List<SavedCard> getSavedCards(String phoneNumber, String publicKey);
        void saveFlwRef(String flwRef);
        String fetchFlwRef();

        void savePhoneNumber(String phoneNumber);

        String fetchPhoneNumber();
    }
}
