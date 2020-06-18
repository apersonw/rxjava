package org.rxjava.apikit.tool.utils;

import org.apache.maven.plugin.logging.Log;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * @author happy
 */
public class ExecUtils {
    public static int exec(String command, Log log) {
        return exec(command, log, null, null, null);
    }

    public static int exec(String command, Log log, @Nullable String dir) {
        return exec(command, log, dir, null, null);
    }

    public static int exec(String command, Log log, @Nullable String dir, @Nullable StringBuffer stringBuffer, @Nullable StringBuffer errStringBuffer) {
        try {
            Process exec;
            if (dir == null) {
                exec = Runtime.getRuntime().exec(command);
            } else {
                exec = Runtime.getRuntime().exec(command, new String[]{}, new File(dir));
            }

            Flux<String> inputFlux = Flux
                    .<String>create(fluxSink -> {
                        BufferedReader input = new BufferedReader(new InputStreamReader(exec.getErrorStream()));
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
                        if (errStringBuffer != null) {
                            errStringBuffer.append(str);
                        }
                    });


            Flux<String> inputErrorFlux = Flux
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
                        if (stringBuffer != null) {
                            stringBuffer.append(str);
                        }
                    });

            return Mono.zip(inputFlux.all(r -> true), inputErrorFlux.all(r -> true))
                    .then(Mono.<Integer>create(r -> {
                        try {
                            r.success(exec.waitFor());
                        } catch (InterruptedException e) {
                            r.error(e);
                        }
                    })).blockOptional().orElse(-1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
