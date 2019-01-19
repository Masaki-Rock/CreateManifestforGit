package com.tools.git.exception;

/**
 * データアクセス例外クラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class DAOException extends AppException {

    /** シリアルバージョンID */
    private static final long serialVersionUID = 6823607359479310955L;

    public DAOException() {
        super();
    }

    public DAOException(String msg) {
        super(msg);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }

    public DAOException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
