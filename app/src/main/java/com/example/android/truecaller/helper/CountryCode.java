package com.example.android.truecaller.helper;

import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.Locale;

/**
 * Created by Duke on 11/15/2015.
 */
public class CountryCode {

    TelephonyManager telephonyManager;


    public String getCountry() {
        String result;
        result = getNetworkBasedCountry();
        if (result == null) {
            result = getSimBasedCountry();
        }
        if (result == null) {
            result = getLocaleCountry();
        }
        if (result != null) result = result.toUpperCase();
        return result;
    }


    protected String getNetworkBasedCountry() {
        String countryIso = null;

        if (telephonyManager.getPhoneType() == TelephonyManager.PHONE_TYPE_GSM) {
            countryIso = telephonyManager.getNetworkCountryIso();
            if (!TextUtils.isEmpty(countryIso)) {
                return countryIso;
            }
        }
        return null;
    }


    protected String getSimBasedCountry() {
        String countryIso = null;
        countryIso = telephonyManager.getSimCountryIso();

        if (!TextUtils.isEmpty(countryIso)) {
            return countryIso;
        }
        return null;
    }


    protected String getLocaleCountry() {
        Locale defaultLocale = Locale.getDefault();
        if (defaultLocale != null) {
            return defaultLocale.getCountry();
        } else {
            return null;
        }
    }

}
