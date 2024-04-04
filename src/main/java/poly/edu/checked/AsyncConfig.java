package poly.edu.checked;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "seleniumTaskExecutor")
    public Executor seleniumTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // Số lượng luồng cơ bản
        executor.setMaxPoolSize(2); // Số lượng luồng tối đa
        executor.setQueueCapacity(Integer.MAX_VALUE); // Hàng đợi không giới hạn
        executor.setThreadNamePrefix("SeleniumTaskExecutor-");
        executor.setRejectedExecutionHandler((r, e) -> {
            // if (e instanceof TaskRejectedException) {
            //     // Xử lý khi tác vụ bị từ chối
            //     // Có thể thả một Exception hoặc ghi log, thông báo về lỗi,...
            //     // Ví dụ: logger.error("Selenium task has been rejected: {}", r.toString());
            // }
        });
        executor.initialize();
        return executor;
    }
}

