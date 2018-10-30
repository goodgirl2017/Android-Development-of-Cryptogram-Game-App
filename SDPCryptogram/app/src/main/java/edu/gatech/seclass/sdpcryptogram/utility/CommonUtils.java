package edu.gatech.seclass.sdpcryptogram.utility;

import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.gatech.seclass.sdpcryptogram.SDPCryptogramActivity;

public class CommonUtils {

    public static String decode(String encodedPhrase, String alphabets, String alphaCipher) {
        String decodedPhrase = encodedPhrase;
        Map<Character, Character> cipher = getCipher(alphaCipher, alphabets);
        // Do something
        char[] charArray = encodedPhrase.toCharArray();
        StringBuilder decodedString = new StringBuilder();
        for (char ch: charArray) {
            if (cipher.containsKey(ch)) {
                decodedString.append(cipher.get(ch));
            } else {
                decodedString.append(ch);
            }
        }
        decodedPhrase = decodedString.toString();
        return decodedPhrase;
    }

    public static String encode(String originalPhrase, String alphabets, String alphaCipher) {
        String encodedPhrase = originalPhrase;
        Map<Character, Character> cipher = getCipher(alphabets, alphaCipher);
        // Do something
        char[] charArray = originalPhrase.toCharArray();
        StringBuilder encodedString = new StringBuilder();
        for (char ch: charArray) {
            if (cipher.containsKey(ch)) {
                encodedString.append(cipher.get(ch));
            } else {
                encodedString.append(ch);
            }
        }
        encodedPhrase = encodedString.toString();
        return encodedPhrase;
    }

    public static String getUniqueAlphabets(String phrase) {
        String validAlphabets = Constants.VALID_ALPHABETS;
        char[] charArray = phrase.toUpperCase().toCharArray();
        Arrays.sort(charArray);
        String sortedString = new String(charArray);
        StringBuilder uniqueAlphabets = new StringBuilder();
        for (int index = 0; index < sortedString.length(); index++) {
            String indexValue = String.valueOf(sortedString.charAt(index));
            if (uniqueAlphabets.indexOf(indexValue) == -1 && validAlphabets.indexOf(indexValue) != -1) {
                uniqueAlphabets.append(sortedString.charAt(index));
            }
        }
        return uniqueAlphabets.toString();
    }

    private static Map<Character, Character> getCipher(String alphabets, String alphaCipher) {
        Map<Character, Character> cipher = new LinkedHashMap<>();
        if (alphaCipher.length() == alphabets.length()) {
            for (int index = 0; index < alphabets.length(); index++) {
                if (alphabets.toUpperCase().charAt(index) != ' ' && alphaCipher.toUpperCase().charAt(index) != ' ') {
                    cipher.put(alphabets.toUpperCase().charAt(index), alphaCipher.toUpperCase().charAt(index));
                }
            }
            for (int index = 0; index < alphabets.length(); index++) {
                if (alphabets.toLowerCase().charAt(index) != ' ' && alphaCipher.toLowerCase().charAt(index) != ' ') {
                    cipher.put(alphabets.toLowerCase().charAt(index), alphaCipher.toLowerCase().charAt(index));
                }
            }
        }
        return cipher;
    }

    public static void startLauncherActivity(Context appContext) {
        // Need to redirect to the launcher activity which is the SDPCryptogramActivity
        // while clearing any of the layouts on the stack.
        Intent intent = new Intent(appContext, SDPCryptogramActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        appContext.startActivity(intent);
    }

    public static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(@Nullable T o) {
                data[0] = o;
                latch.countDown();
                liveData.removeObserver(this);
            }
        };
        liveData.observeForever(observer);
        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
