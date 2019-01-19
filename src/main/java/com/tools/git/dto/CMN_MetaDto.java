package com.tools.git.dto;

import java.util.HashSet;
import java.util.Set;


import com.tools.git.util.CMN_Const;
import com.tools.git.util.CMN_Util;

/**
 * PackageXML情報DTOクラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class CMN_MetaDto {

    public CMN_MetaDto() {
    }

    private String name;
 
    private String dirname;

    private Set<String> members;

    public void setDirname(String name) {
        this.name = CMN_Util.getTypeName(name);
        this.dirname = name;
    }

    public String getName() {
        return name;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void addMember(String filename) {
   
        if (this.members == null) {
            this.members = new HashSet<String>();
        }

		// Ignore File
		if (filename.matches(CMN_Const.IGNORE_FILE)) return;
		filename = CMN_Util.getFile(this.dirname, filename);

		if (CMN_Const.META_NAME_CUSTOM_METADATA.equals(this.name)
				|| CMN_Const.META_NAME_QUICK_ACTION.equals(this.name)) {
			this.members.add(CMN_Util.getNameWithoutExtension(filename));
			return;
		}
		if (CMN_Const.META_NAME_REPORT.equals(this.name) || CMN_Const.META_NAME_DASHBOARD.equals(this.name)) {
			// Ignore File
			if (filename.matches(CMN_Const.IGNORE_REPORT_FILE)) return;
			this.members.add(CMN_Util.getNameWithoutExtension(filename.replace("\\", "/")));
			this.members.add(CMN_Util.getFolder(filename));
			return;
		}
		if (CMN_Const.META_NAME_LIGHTNING_COMPONENT.equals(this.name)) {
			this.members.add(CMN_Util.getFolder(filename));
			return;
		}
		// CustomMetadata, QuickAction
		this.members.add(CMN_Util.getNameWithoutExtension(CMN_Util.getNameWithoutExtension(filename)));
    }
}
