package com.tools.git.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tools.git.exception.AppException;
import com.tools.git.exception.ModuleException;

/**
 * ファイルユーティリティークラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class CMN_FileUtil {

    /** ログ設定 */
    private static final Logger logger = LoggerFactory.getLogger(CMN_FileUtil.class);

    /** デフォルトコンストラクタ */
    private CMN_FileUtil() {
    }

    /**
     * ファイル作成処理
     * @param outStream アウトプットストリーム
     * @throws ModuleException モジュール例外
     */
    public static void createFile(ByteArrayOutputStream outStream, String filename) throws ModuleException {

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            throw new ModuleException(e);
        }
        try {
            fos.write(outStream.toByteArray());
        } catch (IOException e) {
            throw new ModuleException(e);
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                throw new ModuleException(e);
            }
        }
    }
}
