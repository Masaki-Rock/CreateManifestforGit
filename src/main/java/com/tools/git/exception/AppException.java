package com.tools.git.exception;

/**
 * Application例外クラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class AppException extends Exception {

    /** シリアルバージョンID */
    private static final long serialVersionUID = 2036326356365071730L;

    public AppException() {
        super();
    }

    public AppException(String msg) {
        super(msg);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
