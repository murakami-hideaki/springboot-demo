#!/bin/bash

# WindowsホストのIPをip routeから自動取得
# Windows上のWSL2環境で動かすことを想定しているため、WSLから見たWindowsホストのIPを取得している。
# 本番では不要
WINDOWS_HOST=$(ip route | grep default | awk '{print $3}')

JAR_PATH="/mnt/c/pleiades/2025-09/workspace_job_sample/BatchSample/target/BatchSample.jar"

# -----------------------------------------------
# プロファイル設定
# ローカル：以下の変数で切り替える
# 本番：JP1の環境変数 SPRING_PROFILES_ACTIVE で定義すること
# -----------------------------------------------
SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE:-dev}

# -----------------------------------------------
# DB接続URL
# ローカル：WSLからWindowsホストのIPを自動取得して設定
# 本番：JP1の環境変数 SPRING_DATASOURCE_URL で定義すること
# -----------------------------------------------
SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL:-jdbc:postgresql://${WINDOWS_HOST}:5432/batch_db}

# -----------------------------------------------
# 起動モードの判定
# 引数なし → 常駐スケジューラーモード
#   ・@Scheduled が有効になり、ジョブ要求テーブルを監視し続ける
#   ・spring.batch.job.enabled=false でジョブの自動実行はしない
# 引数あり → JP1バッチモード（指定ジョブを1回実行して終了）
#   ・scheduler.enabled=false で @Scheduled を無効化すること！
#   ・これをしないとJP1バッチ起動時にスケジュールジョブも同時に動いてしまう
# -----------------------------------------------
if [ -z "$1" ]; then
  # 常駐スケジューラーモード
  echo "===== スケジューラー起動 profile=[${SPRING_PROFILES_ACTIVE}] ====="
  java -jar ${JAR_PATH} \
    --spring.profiles.active=${SPRING_PROFILES_ACTIVE} \
    --spring.datasource.url=${SPRING_DATASOURCE_URL} \
    --spring.batch.job.enabled=false \
    --scheduler.enabled=true
else
  # JP1バッチモード（ジョブ名指定）
  # 注意：scheduler.enabled=false を必ず指定すること！
  #       指定しないと @Scheduled も同時に動いてしまう！
  JOB_NAME=$1
  echo "===== バッチ開始 [${JOB_NAME}] profile=[${SPRING_PROFILES_ACTIVE}] ====="
  java -jar ${JAR_PATH} \
    --spring.profiles.active=${SPRING_PROFILES_ACTIVE} \
    --spring.datasource.url=${SPRING_DATASOURCE_URL} \
    --spring.batch.job.enabled=true \
    --spring.batch.job.name=${JOB_NAME} \
    --scheduler.enabled=false
  echo "===== バッチ終了 [${JOB_NAME}] ====="
fi
