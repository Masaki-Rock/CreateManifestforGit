package com.tools.git.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotCommit;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotLane;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.tools.git.dto.CMN_MigrationDto;
import com.tools.git.exception.ModuleException;


/**
 * Git操作ユーティリティークラス
 * Date --- Author ---- Subject
 * 20160503 M.Kawaguchi プログラムリリース
 **/
public class GIT_Util {

    /** デフォルトコンストラクタ */
    public GIT_Util() { }

    /**
     * リポジトリ取得メソッド
     * @return リポジトリインスタンス
     * @throws ModuleException モジュール例外
     */
    private Repository getRepository() throws ModuleException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
        try {
            repository = builder.setGitDir(new File(CMN_Const.GIT_FILE_PATH)).readEnvironment()
                    .findGitDir().build();
        } catch (IOException e) {
            throw new ModuleException(e);
        }
        return repository;
    }

    /**
     * ObjectId取得処理
     * (例)
     * refs/heads/master
     * refs/remotes/origin/master
     * HEAD
     * @param refs レフス
     * @return ObjectID
     * @throws ModuleException モジュール例外
     */
    public ObjectId getObjectId(String refs) throws ModuleException {

        ObjectId id = null;
        Repository repository = getRepository();
        try {
            id = repository.resolve(refs);
        } catch (RevisionSyntaxException e) {
            throw new ModuleException(e);
        } catch (AmbiguousObjectException e) {
            throw new ModuleException(e);
        } catch (IncorrectObjectTypeException e) {
            throw new ModuleException(e);
        } catch (IOException e) {
            throw new ModuleException(e);
        }
        return id;
    }

    /**
     * カレントブランチのコミットメッセージ一覧を表示
     * (例)
     * refs/heads/master
     * refs/remotes/origin/master
     * HEAD
     * @param refs レフス
     * @return コミット情報リスト
     * @throws ModuleException モジュール例外
     */
    public List<PlotCommit<PlotLane>> getCommitMsglist(String refs) throws ModuleException {

        Repository repository = getRepository();
        PlotWalk revWalk = new PlotWalk(repository);
        ObjectId rootId;
        try {
            rootId = repository.resolve(refs);
        } catch (RevisionSyntaxException e) {
            revWalk.close();
            throw new ModuleException(e);
        } catch (AmbiguousObjectException e) {
            revWalk.close();
            throw new ModuleException(e);
        } catch (IncorrectObjectTypeException e) {
            revWalk.close();
            throw new ModuleException(e);
        } catch (IOException e) {
            revWalk.close();
            throw new ModuleException(e);
        }
        RevCommit root;
        try {
            root = revWalk.parseCommit(rootId);
            revWalk.markStart(root);
        } catch (MissingObjectException e) {
            revWalk.close();
            throw new ModuleException(e);
        } catch (IncorrectObjectTypeException e) {
            revWalk.close();
            throw new ModuleException(e);
        } catch (IOException e) {
            revWalk.close();
            throw new ModuleException(e);
        }

        PlotCommitList<PlotLane> pobjlist = new PlotCommitList<PlotLane>();
        pobjlist.source(revWalk);
        try {
            pobjlist.fillTo(Integer.MAX_VALUE);
        } catch (MissingObjectException e) {
            throw new ModuleException(e);
        } catch (IncorrectObjectTypeException e) {
            throw new ModuleException(e);
        } catch (IOException e) {
            throw new ModuleException(e);
        }
        List<PlotCommit<PlotLane>> commitlist = new ArrayList<>();
        for (PlotCommit<PlotLane> cobj : pobjlist) {
            commitlist.add(cobj);
        }
        return commitlist;
    }

    /**
     * 特定リビジョンのメタ情報確認処理
     * @param ObjectID
     * @return 関連ID情報
     * @throws ModuleException モジュール例外
     */
    public String getRevisionFilelist(ObjectId fileId) throws ModuleException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Repository repository = getRepository();
        ObjectLoader loader;
        try {
            loader = repository.open(fileId);
            loader.copyTo(os);
        } catch (MissingObjectException e) {
            throw new ModuleException(e);
        } catch (IOException e) {
            throw new ModuleException(e);
        }
        try {
            return os.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new ModuleException(e);
        }
    }

    /**
     * 変更ファイル一覧取得処理
     * @param fromTree 開始Tree情報
     * @param toTree 終了Tree情報
     * @return 変更情報リスト
     * @throws ModuleException モジュール例外
     */
    public List<DiffEntry> modifyFilelist(String fromRef, String toRef) throws ModuleException {

        Repository repository = getRepository();
        RevWalk walk = new RevWalk(repository);
        RevCommit fromCommit = null;
        RevCommit toCommit = null;
        try {
            fromCommit = walk.parseCommit(repository.resolve(fromRef));
            toCommit = walk.parseCommit(repository.resolve(toRef));
        } catch (RevisionSyntaxException e) {
            throw new ModuleException(e);
        } catch (MissingObjectException e) {
            throw new ModuleException(e);
        } catch (IncorrectObjectTypeException e) {
            throw new ModuleException(e);
        } catch (AmbiguousObjectException e) {
            throw new ModuleException(e);
        } catch (IOException e) {
            throw new ModuleException(e);
        } finally {
            walk.close();
        }
        RevTree fromTree = fromCommit.getTree();
        RevTree toTree = toCommit.getTree();
        List<DiffEntry> dfflist = modifyFilelist(fromTree, toTree);
        return dfflist;
    }

    /**
     * 変更ファイル一覧取得処理
     * @param fromTree 開始Tree情報
     * @param toTree 終了Tree情報
     * @return 変更情報リスト
     * @throws ModuleException モジュール例外
     */
    public List<DiffEntry> modifyFilelist(RevTree fromTree, RevTree toTree) throws ModuleException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Repository repository = getRepository();
        DiffFormatter diffFormatter = new DiffFormatter(bos);
        diffFormatter.setRepository(repository);
        RevWalk walk = new RevWalk(repository);
        List<DiffEntry> list;
        try {
            list = diffFormatter.scan(fromTree, toTree);
        } catch (IOException e) {
            walk.dispose();
            diffFormatter.close();
            walk.close();
            throw new ModuleException(e);
        }
        walk.dispose();
        diffFormatter.close();
        walk.close();
        return list;
    }
}
