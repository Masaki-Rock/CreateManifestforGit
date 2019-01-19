package com.tools.git.exception;

/**
 * データアクセス例外クラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class ModuleException extends DAOException {

    /** シリアルバージョンID */
    private static final long serialVersionUID = 3982314799399087539L;

    public ModuleException() {
        super();
    }

    public ModuleException(String msg) {
        super(msg);
    }

    public ModuleException(Throwable cause) {
        super(cause);
    }

    public ModuleException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
