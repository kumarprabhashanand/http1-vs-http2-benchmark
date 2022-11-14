package com.httptest.spring.benchmark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import com.httptest.spring.client.BenchmarkClient;

@State(Scope.Thread)
public class BenchMarkApp {

    private BenchmarkClient client = new BenchmarkClient();
    List<Long> resList = new ArrayList<>();

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(value = 1, warmups = 1)
    public void benchMarkHttp1() {
        long res = client.makeRestCallWithHttpV1(20000);
        resList.add(res);
    }

    @Benchmark
    @BenchmarkMode(Mode.All)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(value = 1, warmups = 1)
    public void benchMarkHttp2() {
        long res = client.makeRestCallWithHttpV2(20000);
        resList.add(res);
    }

}
