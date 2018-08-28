package com.framgia.yen.mymusic.utils;

import android.support.annotation.IntDef;

@IntDef({
        TabEntity.TAB_HOME,
        TabEntity.TAB_DOWNLOAD,
        TabEntity.TAB_FAVORITE,
})
public @interface TabEntity {
    int TAB_HOME = 0;
    int TAB_DOWNLOAD = 1;
    int TAB_FAVORITE = 2;
}
