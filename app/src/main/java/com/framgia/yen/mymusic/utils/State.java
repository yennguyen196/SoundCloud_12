package com.framgia.yen.mymusic.utils;

import android.support.annotation.IntDef;

@IntDef({
        State.COMPLETED,
        State.INVALID,
        State.PAUSE,
        State.PLAYING,
        State.PREPARE}
)

public @interface State {
    int INVALID = -1;
    int PLAYING = 0;
    int PAUSE = 1;
    int PREPARE = 2;
    int COMPLETED = 3;
}
