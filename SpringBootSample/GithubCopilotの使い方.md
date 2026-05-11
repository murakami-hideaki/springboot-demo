# VSCodeを利用したGitHub Copilotの利用方法

---

## 事前準備

### 日本語化

VSCodeは初期状態では英語表示のため、日本語化の拡張機能をインストールします。

1. VSCodeを起動する 2. 左サイドバーの「拡張機能」アイコンをクリック（または Ctrl + Shift + X） 3. 検索欄に Japanese Language Pack と入力 4. 「Japanese Language Pack for Visual Studio Code」（Microsoft製）をインストール 5. 右下に「Restart」ボタンが表示されるのでクリックして再起動 6. 再起動後、VSCodeが日本語表示に切り替わる

---

### 拡張機能のインストール

以下の拡張機能をインストールします。

拡張機能名	用途	必須/推奨
Japanese Language Pack for Visual Studio Code	VSCode日本語化	必須
GitHub Copilot Chat	AIチャット・コード補完・Agent機能	必須
Extension Pack for Java	Javaの構文認識強化	推奨
Spring Boot Extension Pack	SpringBoot固有のコード解析精度向上	推奨
注意： 「GitHub Copilot」（旧単体拡張機能）は現在非推奨です。「GitHub Copilot Chat」をインストールすれば、コード補完・チャット・Agent機能がすべて利用できます。

---

## GitHub Copilotの基本設定

### GitHub Copilotへのサインイン

GitHub CopilotはGitHubアカウントと紐づけることで利用できます。

1. VSCode左下の「人型アイコン（アカウント）」をクリック 2. 「GitHub でサインイン」をクリック 3. ブラウザが自動で開き、GitHubの認証画面が表示される 4. 「Authorize Visual Studio Code」ボタンをクリック 5. 「VSCodeを開く」の確認ダイアログ → 「開く」をクリック 6. VSCode右下のステータスバーにCopilotアイコンが表示されれば完了

GitHubでのCopilot有効化確認： 以下のURLでCopilotが有効になっているか確認できます。 https://github.com/settings/copilot

---

### Gitのローカルリポジトリをワークスペースとして開く

ワークスペースとは： VSCodeで「File → Open Folder」で開いたフォルダそのものをワークスペースと呼びます。Eclipseのような専用の管理フォルダは不要で、開いたフォルダが即作業対象になります。

ポイント： .git フォルダが存在するリポジトリのルートフォルダを開くことが重要です。サブフォルダを開いてしまうとGitとして認識されず、ブランチ名がステータスバーに表示されません。

手順： VSCode上部メニュー →「ファイル」→「フォルダーを開く」→ Gitリポジトリのルートフォルダを選択（例：C:\Users\murak\git\springboot-demo）→「フォルダーの選択」ボタンをクリック

確認方法： VSCode右下のステータスバーに現在のブランチ名（例：main）が表示されれば正常です。

ブランチ切り替えとVSCode・Copilotの自動認識： Gitのブランチを切り替えるとディスク上のファイルが物理的に書き換わるため、VSCodeもCopilot Chatも特別な操作なしに切り替え後のブランチの内容を自動認識します。

EclipseとVSCodeを併用している場合の注意点： VSCode側でブランチを切り替えた場合、Eclipse側は自動認識しません。Eclipseでプロジェクトを右クリック →「Refresh（F5）」で更新してください。

---

## GitHub Copilotの機能概要

### チャットモードの説明

GitHub Copilot Chatには以下の4つのモードがあります。チャットパネル入力欄左側のドロップダウンから切り替えできます。

モード	概要	主な用途
Ask	質問・解析・説明に特化したモード	コードの説明、設計の質問、ドキュメント生成
Edit	指定したファイルを直接編集するモード	特定ファイルのリファクタリング、コード修正
Agent	プロジェクト全体を横断的に解析・操作するモード	複数ファイルにまたがる実装、全体構成の把握
Plan	実装計画を立案してから実行するモード	大規模な変更前の計画立案と確認
各モードの使い分け：

ソースコードの説明や質問 → Ask モード
特定ファイルの修正 → Edit モード
プロジェクト全体の解析・実装 → Agent モード（@workspace と組み合わせると効果的）
大きな改修の前に計画を確認したい → Plan モード
---

### 利用可能なAIモデルとプレミアムリクエスト

GitHub Copilot Chatでは複数のAIモデルを選択して利用できます。チャットパネル入力欄右側のモデル選択ドロップダウンから切り替えできます。

モデル	特徴	プレミアムリクエスト消費（有料プラン）
GPT-4o	バランスの良い汎用モデル	消費なし（included model）
GPT-4.1	高精度・コード生成に強い	消費なし（included model）
GPT-5 mini	軽量・高速モデル	消費なし（included model）
Claude Sonnet 4.6	文章理解・要約に強い	消費あり（乗数あり）
Gemini 2.5 Pro	長いコンテキスト処理に強い	消費あり（乗数あり）
GPT-5 / GPT-5.5	最新・最高性能モデル	消費あり（高乗数）
⚠️ プレミアムリクエストについての注意点

Ask / Edit / Agent / Plan のすべてのモードにおいて、チャットの送信はプレミアムリクエストを消費します。モードに関わらず、選択しているAIモデルの乗数に応じて消費量が変わります。

GPT-4o / GPT-4.1 / GPT-5 mini（included models）を使用している場合は消費なし
Claude・Gemini・GPT-5系など上位モデルを使用している場合は乗数に応じて消費
消費量はモデルの複雑さ・性能によって異なる（乗数は変更される場合あり）
未使用のリクエストは翌月に繰り越しされません（毎月1日にリセット）
📖 プレミアムリクエストの公式説明：https://docs.github.com/en/copilot/concepts/billing/copilot-requests

---

### カスタムエージェントとは

カスタムエージェントとは、特定の開発タスクや役割に特化したAIの振る舞いを定義する仕組みです。セキュリティレビュー担当・プランナー・ソリューションアーキテクトなど、特定のペルソナをAIに持たせることができます。各カスタムエージェントには独自の指示（instructions）、利用可能なツール、モデルを設定できます。

またハンドオフ機能により、エージェント間でシームレスに処理を引き継ぐワークフローを構築できます。例えば、計画エージェントで実装計画を立案した後、実装エージェントに引き渡してコーディングを行う、という一連の流れを定義できます。

カスタムエージェントと旧称について： カスタムエージェントは以前「カスタムチャットモード（Custom Chat Modes）」と呼ばれていました。機能は同じですが名称が変更されています。既存の .chatmode.md ファイルがある場合は .agent.md にリネームしてください。

---

### カスタムエージェントの構成方法

カスタムエージェントは .agent.md という拡張子のMarkdownファイルで定義します。

ファイルの保存場所：

スコープ	保存場所	説明
ワークスペース（プロジェクト固有）	（ワークスペースルート）/.github/agents/	現在開いているGitリポジトリ直下に作成する固定フォルダ名。チームで共有される
ユーザープロファイル（全ワークスペース共通）	~/.copilot/agents/	Windowsでは C:\Users\ユーザー名.copilot\agents\ に相当。個人専用でどのプロジェクトでも使える
保存場所についての補足：

.github/agents/ はGitリポジトリの有無に関わらず固定のフォルダ名です。 現在 C:\Users\murak\git\springboot-demo をワークスペースとして開いている場合、ワークスペース用のエージェントファイルの置き場所は C:\Users\murak\git\springboot-demo.github\agents\ になります。

.github フォルダはGitHubが定めた慣例的な設定フォルダです（GitHub ActionsのワークフローYAMLなども同じ場所に置きます）
このフォルダ以下に置いたエージェントファイルはGitでコミット・プッシュすることでチームメンバー全員が共有できます
個人専用（他のプロジェクトでも使い回したい）の場合は ~/.copilot/agents/ に置きます
ファイル構成のサンプル（計画専用エージェントの例）：

ファイル名：.github/agents/planner.agent.md

--- description: 実装計画を立案する（コード変更なし） name: Planner tools: ['codebase', 'search', 'web/fetch'] model: GPT-4.1 handoffs:

label: 実装を開始する agent: agent prompt: 上記の計画に基づいて実装してください。 send: false --- # プランニング指示
あなたはプランニング専門のエージェントです。 コードの変更は一切行わず、実装計画の立案のみを行ってください。

計画には以下のセクションを含めること：

概要
要件
実装手順
テスト方針
主なフロントマターのフィールド：

フィールド	説明
description	エージェントの概要。チャット入力欄にプレースホルダーとして表示される
name	エージェント名（省略時はファイル名が使われる）
tools	使用を許可するツールのリスト（codebase, edit, search, web/fetch など）
model	使用するAIモデル（省略時はモデルピッカーで選択中のモデルが使われる）
handoffs	別エージェントへの引き継ぎボタンの定義（省略可）
カスタムエージェントの作成手順：

1. Copilot Chatパネルの「Configure Chat」（歯車アイコン）をクリック → 「Agents」タブを選択 2. 「New Agent (Workspace)」または「New Agent (User)」を選択 3. ファイル名を入力して .agent.md ファイルが作成される 4. フロントマターと本文に指示内容を記述して保存

コマンドパレットから作成することもできます：Ctrl + Shift + P → Chat: New Custom Agent

AIを使ってカスタムエージェントを自動生成することも可能です： AgentモードのチャットでAgentモードのチャットで /create-agent セキュリティレビューを行うエージェントを作成してください と入力するだけで自動生成されます。

カスタムエージェントの活用例（SpringBootプロジェクト向け）：

セキュリティレビューエージェント（ファイル名：.github/agents/security-reviewer.agent.md）

--- description: コードのセキュリティ脆弱性を検出してレビューする name: SecurityReviewer tools: ['codebase', 'search'] model: Claude Sonnet 4.6 --- あなたはセキュリティ専門のコードレビュアーです。 SQLインジェクション、XSS、認証の不備などの脆弱性を検出し、 改善案を具体的に提示してください。コードの変更は行わないこと。

SpringBoot構成解析エージェント（ファイル名：.github/agents/springboot-analyzer.agent.md）

--- description: SpringBootプロジェクトの構成を解析して説明する name: SpringBootAnalyzer tools: ['codebase', 'search'] model: GPT-4.1 --- あなたはSpringBootの専門家です。 Controller・Service・Repositoryの依存関係、Bean定義、 トランザクション管理を中心にプロジェクト構成を日本語で説明してください。

---

## よく利用するチャットコマンド一覧

### スコープ指定コマンド

コマンド	用途
@workspace	開いているフォルダ（プロジェクト全体）をコンテキストに含める
#file:ファイル名	特定のファイルを指定してチャット
#selection	エディタで現在選択中のコードを対象にする
### スラッシュコマンド

コマンド	用途
/explain	選択したコードの詳細説明を求める
/doc	選択したコードのドキュメントコメントを生成する
/fix	選択したコードのバグ修正を提案する
/tests	選択したコードに対するテストコードを生成する
/new	新しいファイルやプロジェクトの雛形を生成する
/create-agent	AIを使ってカスタムエージェントを自動生成する
### チャット指示の例

プロジェクト全体の構成把握：@workspace このSpringBootプロジェクトの全体構成を説明してください
依存ライブラリの確認：@workspace pom.xmlを読み取って使用しているライブラリとバージョンを一覧で教えてください
コントローラーの一覧：@workspace コントローラークラスの一覧とそれぞれのエンドポイントをまとめてください
特定ファイルの解析：#file:UserService.java このサービスクラスのビジネスロジックを日本語で説明してください
セキュリティ設定の確認：@workspace セキュリティ関連の設定はどこで行われていますか？該当箇所を示してください
---

## VSCodeのよく使うショートカット・コマンド

### 基本操作

操作	ショートカット
ターミナルを開く / 閉じる	Ctrl + @（バッククォート）
コマンドパレットを開く	Ctrl + Shift + P
拡張機能パネルを開く	Ctrl + Shift + X
ファイル検索	Ctrl + P
プロジェクト内テキスト全文検索	Ctrl + Shift + F
設定を開く	Ctrl + ,
ウィンドウのリロード	Ctrl + Shift + P → Developer: Reload Window
### Copilot Chat操作

操作	ショートカット
Copilot Chatパネルを開く	Ctrl + Alt + I
インラインチャットを開く（エディタ上）	Ctrl + I
### ブランチ切り替え（VSCode GUI）

VSCode右下のステータスバー → ブランチ名をクリック → 上部にブランチ一覧が表示される → 切り替えたいブランチを選択

---

## 参考リンク

リンク	説明
https://github.com/settings/copilot	GitHub Copilot有効化・プラン確認
https://docs.github.com/en/copilot/concepts/billing/copilot-requests	プレミアムリクエストの公式説明
https://docs.github.com/copilot/reference/ai-models/supported-models	対応AIモデル一覧と乗数
https://code.visualstudio.com/docs/copilot/customization/custom-agents	カスタムエージェントの公式ドキュメント