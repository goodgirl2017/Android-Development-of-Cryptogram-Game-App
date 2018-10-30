package edu.gatech.seclass.sdpcryptogram.utility;

import java.net.FileNameMap;

public final class Constants {

    public static final String CURRENT_USER = "edu.gatech.seclass.sdpcryptogram.CURRENT_USER";
    public static final String CRYPTOGRAM_TO_SOLVE = "edu.gatech.seclass.sdpcryptogram.CRPTOGRAM_TO_SOLVE";
    public static final String STATS_MODE = "edu.gatech.seclass.sdpcryptogram.STATS_MODE";
    public static final String SELECTED_PUZZLE = "edu.gatech.seclass.sdpcryptogram.SELECTED_PUZZLE";
    public static final String SELECTED_PUZZLE_CREATED = "edu.gatech.seclass.sdpcryptogram.SELECTED_PUZZLE_CREATED";
    public static final String SELECTED_USER = "edu.gatech.seclass.sdpcryptogram.SELECTED_USER";

    public static final int SELECT_PLAYER_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_PLAYER_ACTIVITY_REQUEST_CODE = 2;
    public static final int CREATE_CRYPTOGRAM_ACTIVITY_REQUEST_CODE = 10;
    public static final int SOLVE_CRYPTOGRAM_ACTIVITY_REQUEST_CODE = 15;
    public static final int SELECT_CRYPTOGRAM_ACTIVITY_REQUEST_CODE = 20;
    public static final int CRYPTOGRAM_STATS_ACTIVITY_REQUEST_CODE = 25;
    public static final int TOP_N_PLAYERS_TO_SOLVE = 3;

    public static final String VALID_ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String UNSOLVED_STATS = "UNSOLVED_STATS";
    public static final String COMPLETED_STATS = "COMPLETED_STATS";
    public static final String OVERALL_STATS = "OVERALL_STATS";
    public static final String CRYPTOGRAM_STATS = "CRYPTOGRAM_STATS";

    public static final String CRYPTOGRAM_DB = "cryptogram_database";
    public static final String PLAYER_TABLE = "player_table";
    public static final String CRYPTOGRAM_TABLE = "cryptogram_table";
    public static final String STATISTIC_TABLE = "statistic_table";

    public static final String PLAYER_USERNAME_COLUMN = "p_username";
    public static final String PLAYER_FIRSTNAME_COLUMN = "firstname";
    public static final String PLAYER_LASTNAME_COLUMN = "lastname";
    public static final String PLAYER_EMAIL_COLUMN = "email";

    public static final String CRYPTOGRAM_PUZZLENAME_COLUMN = "c_puzzlename";
    public static final String CRYPTOGRAM_PHRASE_COLUMN = "phrase";
    public static final String CRYPTOGRAM_ENCRYPTED_COLUMN = "encrypted";
    public static final String CRYPTOGRAM_ALLOWED_COLUMN = "allowed";
    public static final String CRYPTOGRAM_ALPHABETS_COLUMN = "alphabets";
    public static final String CRYPTOGRAM_CIPHER_COLUMN = "cipher";
    public static final String CRYPTOGRAM_CREATEDATE_COLUMN = "createdate";

    public static final String STATISTIC_USERNAME_COLUMN = "s_username";
    public static final String STATISTIC_PUZZLENAME_COLUMN = "s_puzzlename";
    public static final String STATISTIC_ATTEMPTS_COLUMN = "attempts";
    public static final String STATISTIC_SOLVED_COLUMN = "solved";
    public static final String STATISTIC_COMPLETEDATE_COLUMN = "completedate";
}
