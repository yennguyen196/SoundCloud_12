package com.framgia.yen.mymusic.utils;

import com.framgia.yen.mymusic.BuildConfig;

import java.util.concurrent.TimeUnit;

public class StringUtil {
    private static final String TRACK_QUERY_FORMAT = "%s%s%s&%s=%s&%s=%d&%s=%d";
    private static final String BASE_URL = "https://api-v2.soundcloud.com/";
    private static final String MUSIC_GENRE = "charts?kind=top&genre=soundcloud%3Agenres%3A";
    private static final String CLIENT_ID = "client_id";
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    public static String formatTrackUrl(String genre, int limit, int offSet) {
        return String.format(TRACK_QUERY_FORMAT, BASE_URL,
                MUSIC_GENRE, genre, CLIENT_ID, BuildConfig.API_KEY, LIMIT, limit, OFFSET, offSet);
    }
    public static String convertMilisecondToTimer(long milliseconds) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
    }
}
