package com.framgia.yen.mymusic.utils;

import android.support.annotation.IntDef;

@IntDef({
        LoopType.NO_LOOP,
        LoopType.LOOP_ONE,
        LoopType.LOOP_LIST}
)
public @interface LoopType {
    int NO_LOOP = -1;
    int LOOP_ONE = 0;
    int LOOP_LIST = 1;
}
