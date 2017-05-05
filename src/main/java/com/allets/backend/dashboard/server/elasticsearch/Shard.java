package com.allets.backend.dashboard.server.elasticsearch;

/**
 * Created by claude on 2015/12/9.
 */
public class Shard {
    private Integer total;
    private Integer successful;
    private Integer failed;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccessful() {
        return successful;
    }

    public void setSuccessful(Integer successful) {
        this.successful = successful;
    }

    public Integer getFailed() {
        return failed;
    }

    public void setFailed(Integer failed) {
        this.failed = failed;
    }

    public Shard(Integer total, Integer successful, Integer failed) {
        this.total = total;
        this.successful = successful;
        this.failed = failed;
    }

    public Shard()
    {
        super();
    }
    @Override
    public String toString() {
        return "_shards{" +
                "total=" + total +
                ", successful=" + successful +
                ", failed=" + failed +
                '}';
    }
}
