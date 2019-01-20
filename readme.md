# Git用マニュフェストファイル作成ツール
## 背景
単純な組織間のメタデータ移送を、一つ一つメタデータを選択していては余計な時間がかかります。
移送元の変更履歴から範囲を指定し一括して移送元へ移送できたら、変更管理者にとって楽になりますね。
## メリット
移送元組織の変更履から期間を指定しマニュフェストファイルを作成します。
次に、作成したマニュフェストファイルで、メタデータを取得し移送先にデプロイするだけで組織間の同期をすることができます。
## 実行方法
- jarファイルは、デプロイ時に参照しやすい位置に配置してください。
- java -jar build/libs/CreateManifestfileTool {from Commit ID} {to Commit ID} (※必要に応じてバッチファイルを作成してください。)
- コミット間で、Diffを取得し差分があるメタデータをマニュフェストファイルに出力します。
- 引数
    - 第一引数：開始CommitID
    - 第二引数：終了CommitID
## 関連ツール
- Ant
- Ant Migration Tool
- Git
- 提供しているAutobackupToolに含まれる
