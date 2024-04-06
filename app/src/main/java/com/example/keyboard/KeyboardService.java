package com.example.keyboard;

import android.app.Service;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;


public class KeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private KeyboardView KeyboardView;
    private Keyboard Keyboard;


    @Override
    public View onCreateInputView() {
        KeyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboardlayout,null);
        Keyboard = new Keyboard(this,R.xml.qwerty);
        KeyboardView.setKeyboard(Keyboard);
        KeyboardView.setOnKeyboardActionListener(this);
        return  KeyboardView;
    }

    private boolean isCap = false;

    @Override
    public void onPress(int primaryCode) {
           }

    @Override
    public void onRelease(int primaryCode) {

    }


    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection inputConnection=getCurrentInputConnection();
        playClick(primaryCode);

        switch (primaryCode){
            case KeyEvent.KEYCODE_DEL:
                inputConnection.deleteSurroundingText(1,0);
            break;

            case KeyEvent.KEYCODE_SHIFT_LEFT:
                isCap=!isCap;Keyboard.setShifted(isCap);
            break;

            case KeyEvent.KEYCODE_ENTER:
                inputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            break;

            default:
                char code=(char)primaryCode;
                if (Character.isLetter(code)&&isCap)code =Character.toUpperCase(code);
                inputConnection.commitText(String.valueOf(code),1);
        }
    }
private void playClick(int i){
    AudioManager audioManager=(AudioManager)getSystemService(AUDIO_SERVICE);
    switch (i){
        case 32:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
        break;

        case  KeyEvent.KEYCODE_ENTER:
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
        break;

        case KeyEvent.KEYCODE_DEL:
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
        break;

        default:audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
    }

}
    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}