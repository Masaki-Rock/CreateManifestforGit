package com.tools.git;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tools.git.dto.CMN_MigrationDto;
import com.tools.git.dto.CMN_PackageDto;
import com.tools.git.exception.ModuleException;
import com.tools.git.util.CMN_Const;
import com.tools.git.util.CMN_FileUtil;
import com.tools.git.util.CMN_PackageXMLWriter;
import com.tools.git.util.GIT_Util;

public class Main {
    /** ログ設定 */
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		
		logger.info("Start to create the manifest file...  ");
		
		if (args.length == 0) {
			//　入力情報セット
			System.out.println("-- Option help -------");
			System.out.println("    Arg0 is Start commit Id.");
			System.out.println("    Arg1 is End commit Id.");
			System.out.println("----------------------");
		}
		if (args.length < 2) return;
		String fCommitId = args[0];
		String tCommitId = args[1];
		logger.info("from commit Id is " + fCommitId);
		logger.info("to commit Id is " + tCommitId);
		
		GIT_Util util = new GIT_Util();
	    List<DiffEntry> dobjlist = null;
		try {
			dobjlist = util.modifyFilelist(fCommitId, tCommitId);
		} catch (ModuleException e) {
			e.printStackTrace();
		}
	    CMN_PackageDto dto = new CMN_PackageDto();
	    dobjlist.forEach((diffEntry) -> {
	    	if (ChangeType.ADD == diffEntry.getChangeType() || ChangeType.MODIFY == diffEntry.getChangeType()) {
	    		logger.info("get the modify file is " + diffEntry.getNewPath());
	    		dto.addChangeFile(diffEntry.getNewPath());
	        }
	    });
	    CMN_PackageXMLWriter writer = new CMN_PackageXMLWriter();
	    writer.create(CMN_Const.GIT_MANIFEST_FILE_PATH, dto);
	    
	    logger.info("End to create the manifest file...  ");
	}
}

