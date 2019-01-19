package com.tools.git.dto;

import org.eclipse.jgit.diff.DiffEntry.ChangeType;

/**
 * 移行情報DTOクラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class CMN_MigrationDto {

    /** デフォルトコンストラクタ */
    public CMN_MigrationDto() {
    }

    /** メタ追加XML */
    private CMN_PackageDto packagexml;

    /** メタ削除XML */
    private CMN_PackageDto destructiveChangesxml;

    public CMN_PackageDto getPackagexml() {
        return packagexml;
    }

    public void setPackagexml(CMN_PackageDto packagexml) {
        this.packagexml = packagexml;
    }

    public CMN_PackageDto getDestructiveChangesxml() {
        return destructiveChangesxml;
    }

    public void setDestructiveChangesxml(CMN_PackageDto destructiveChangesxml) {
        this.destructiveChangesxml = destructiveChangesxml;
    }

    /**
     * XMLデータを作成します
     * @param type 変更状態
     * @param newPath フルパス 変更後ファイル名
     * @param oldPath フルパス変更前ファイル名
     */
    public void addFullClassName(ChangeType type, String newPath, String oldPath) {
        if (ChangeType.ADD == type || ChangeType.MODIFY == type) {
            if (this.packagexml == null) {
                this.packagexml = new CMN_PackageDto();
            }
            this.packagexml.addChangeFile(newPath);
            return;
        }
        if (ChangeType.DELETE == type) {
            if (this.destructiveChangesxml == null) {
                this.destructiveChangesxml = new CMN_PackageDto();
            }
            this.destructiveChangesxml.addChangeFile(newPath);
            return;
        }
    }

}
