package com.tools.git.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 汎用ユーティリティークラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class CMN_Util {

    /** ログ設定 */
    private static final Logger logger = LoggerFactory.getLogger(CMN_Util.class);

    /** デフォルトコンストラクタ */
    private CMN_Util() {
    }

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    /**
     * 
     * @return 階層文字を取得します
     */
    public static String pathChr() {
        if (OS_NAME.startsWith("linux") || OS_NAME.startsWith("mac") || OS_NAME.startsWith("ubuntu")) {
            return "/";
        }
        return "\\";
    }
    
    public static String getTypeName(String dirname) {
 
		for (int i = 0; i < CMN_Const.META_NAME.length; i++) {
			if (CMN_Const.META_NAME[i][1].equals(dirname)) {
				return CMN_Const.META_NAME[i][0];
			}
		}
		return "";
    }

    public static String getFile(String dirname, String filename) {
    	return filename.replace(CMN_Const.SRC_PATH + dirname + CMN_Util.pathChr(), "");
    }
 
    public static String getFolder(String filename) {
    	return filename.split("/")[0];
    }
   
    public static String getDir(String filename) {
    	return filename.split("/")[1];
    }
    
	public static String getNameWithoutExtension(String fileName) {

		  int index = fileName.lastIndexOf('.');
		  if (index != -1) {
		    return fileName.substring(0, index);
		  }
		  return fileName;
	}
}
