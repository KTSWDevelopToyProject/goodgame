package com.kt.game.goodgame.util;

import io.r2dbc.proxy.core.QueryExecutionInfo;
import io.r2dbc.proxy.listener.ProxyExecutionListener;
import io.r2dbc.proxy.support.QueryExecutionInfoFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryListener implements ProxyExecutionListener {
    private final QueryExecutionInfoFormatter formatter = QueryExecutionInfoFormatter.showAll();
    @Override
    public void afterQuery(QueryExecutionInfo executionInfo) {
        log.info(this.formatter.format(executionInfo));
    }
}
