package org.rxjava.apikit.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author happy
 * 命令行帮助类
 */
@Slf4j
public class CommandUtils {
    public static void main(String[] args) {
        CommandUtils.exec("pwd");
    }

    public static void exec(String command) {
        exec(command, null, null, null);
    }

    public static void exec(String command, @Nullable String dir) {
        exec(command, dir, null, null);
    }

    public static int exec(String command, @Nullable String dir, @Nullable StringBuffer successBuffer, @Nullable StringBuffer errorBuffer) {
        try {
            Process exec;
            if (StringUtils.isEmpty(dir)) {
                exec = Runtime.getRuntime().exec(command);
            } else {
                exec = Runtime.getRuntime().exec(command, new String[]{}, new File(dir));
            }

            // 命令执行出错后返回的消息
            Flux<String> errorFlux = Flux
                    .<String>create(fluxSink -> {
                        BufferedReader input = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
                        try {
                            while (input.readLine() != null) {
                                fluxSink.next(input.readLine());
                            }
                            fluxSink.complete();
                        } catch (Throwable e) {
                            fluxSink.error(e);
                        }
                    })
                    .subscribeOn(Schedulers.elastic())
                    .doOnNext(log::info)
                    .doOnNext(str -> {
                        if (errorBuffer != null) {
                            errorBuffer.append(str);
                        }
                    });

            // 命令执行成功后的返回消息
            Flux<String> successFlux = Flux
                    .<String>create(fluxSink -> {
                        BufferedReader input = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                        String line;
                        try {
                            while ((line = input.readLine()) != null) {
                                fluxSink.next(line);
                            }
                            fluxSink.complete();
                        } catch (Throwable e) {
                            fluxSink.error(e);
                        }
                    })
                    .subscribeOn(Schedulers.elastic())
                    .doOnNext(log::info)
                    .doOnNext(str -> {
                        if (successBuffer != null) {
                            successBuffer.append(str);
                        }
                    });

            return Mono.zip(errorFlux.all(r -> true), successFlux.all(r -> true))
                    .then(Mono.<Integer>create(r -> {
                        try {
                            r.success(exec.waitFor());
                        } catch (InterruptedException e) {
                            r.error(e);
                        }
                    })).blockOptional().orElseThrow(() -> new RuntimeException("exec result not zero"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
