package com.tools.git.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tools.git.util.CMN_Const;
import com.tools.git.util.CMN_Util;

/**
 * PackageXML情報DTOクラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class CMN_PackageDto {

    /** デフォルトコンストラクタ */
    public CMN_PackageDto() { }

    private Map<String, CMN_MetaDto> types;

    public Map<String, CMN_MetaDto> getTypes() {
        return types;
    }

    /**
     * メタ情報追加処理
     * @param filename フルネイム
     */
    public void addChangeFile(String filename) {

        if (this.types == null) {
            this.types = new HashMap<String, CMN_MetaDto>();
        }
        
        if (filename.matches(CMN_Const.IGNORE_FILE)) return;
        String type = CMN_Util.getDir(filename);
        CMN_MetaDto dto = this.types.get(type);
        if (dto == null) {
            dto = new CMN_MetaDto();
        }
        
        dto.setDirname(type);
        dto.addMember(filename);
        
        this.types.put(type, dto);
    }
}
