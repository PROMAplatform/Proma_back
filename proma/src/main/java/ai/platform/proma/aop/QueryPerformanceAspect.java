package ai.platform.proma.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class QueryPerformanceAspect {
    private static final Logger logger = LoggerFactory.getLogger(QueryPerformanceAspect.class);

    // ThreadLocal에 각 쿼리 실행 시간과 메소드 이름을 저장
    private final ThreadLocal<List<Pair<String, Long>>> queryLogs = ThreadLocal.withInitial(ArrayList::new);

    @Pointcut("@within(org.springframework.stereotype.Repository)")
    public void allRepositoryMethods() {}

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void allControllerMethods() {}

    @Around("allRepositoryMethods()")
    public Object measureQueryPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 각 쿼리의 실행 시간과 메소드 이름을 ThreadLocal 리스트에 저장
        queryLogs.get().add(Pair.of(joinPoint.getSignature().toShortString(), duration));

        logger.info("Method {} executed in {} ms", joinPoint.getSignature(), duration);

        return result;
    }

    // HTTP 요청이 끝난 후, 모든 쿼리 실행 시간을 합산하여 로그 출력
    @AfterReturning("allControllerMethods()")
    public void logAllQueries() {
        List<Pair<String, Long>> logs = queryLogs.get();

        if (!logs.isEmpty()) {
            long totalDuration = logs.stream().mapToLong(Pair::getSecond).sum(); // 모든 쿼리 시간의 합산
            logger.info("====== Query Performance Logs ======");
            logger.info("Total Query Execution Time: {} ms", totalDuration);
            logs.forEach(log -> logger.info("Query Execution Time: {} ms, Method: {}", log.getSecond(), log.getFirst()));
            logger.info("====================================");

            // 요청 완료 후 ThreadLocal 초기화
            queryLogs.remove();
        }
    }
}
