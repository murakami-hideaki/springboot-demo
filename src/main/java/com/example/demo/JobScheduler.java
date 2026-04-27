package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(name = "scheduler.enabled", havingValue = "true", matchIfMissing = true)
public class JobScheduler {

    @Autowired
    private JobLauncher jobLauncher;

    // ③ @Scheduled から起動されるジョブを明示的に指定(暫定）
/*
 *  
 *  ジョブ要求テーブルからジョブ名取得する場合は以下のようになる。
    // @Autowired
    private ApplicationContext applicationContext;
	// ジョブ名からJobを動的に取得して起動
 	public void runJob(String jobName) {
     Job job = applicationContext.getBean(jobName, Job.class);
     jobLauncher.run(job, params);
 	}
*/
    @Autowired
    @Qualifier("helloScheduledJob")
    private Job helloScheduledJob;

    // 5秒ごとに helloScheduledJob を起動
    @Scheduled(fixedDelay = 5000)
    public void runScheduledJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLocalDateTime("runAt", java.time.LocalDateTime.now())
                    .toJobParameters();

            log.info("===== helloScheduledJob 起動 =====");
            jobLauncher.run(helloScheduledJob, params);
            log.info("===== helloScheduledJob 完了 =====");

        } catch (Exception e) {
            log.error("helloScheduledJob 実行エラー", e);
        }
    }
}
