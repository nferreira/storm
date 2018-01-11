/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.storm.metrics2;

import com.codahale.metrics.Counter;
import org.apache.storm.task.WorkerTopologyContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TaskMetrics {
    ConcurrentMap<String, Counter> ackedByStream = new ConcurrentHashMap<>();
    ConcurrentMap<String, Counter> failedByStream = new ConcurrentHashMap<>();
    ConcurrentMap<String, Counter> emittedByStream = new ConcurrentHashMap<>();
    ConcurrentMap<String, Counter> transferredByStream = new ConcurrentHashMap<>();

    private String topologyId;
    private String componentId;
    private Integer taskId;
    private Integer workerPort;

    public TaskMetrics(WorkerTopologyContext context, String componentId, Integer taskid){
        this.topologyId = context.getStormId();
        this.componentId = componentId;
        this.taskId = taskid;
        this.workerPort = context.getThisWorkerPort();
    }

    public Counter getAcked(String streamId) {
        Counter c = this.ackedByStream.get(streamId);
        if (c == null) {
            c = StormMetricRegistry.counter("acked", this.topologyId, this.componentId, this.taskId, this.workerPort, streamId);
            this.ackedByStream.put(streamId, c);
        }
        return c;
    }

    public Counter getFailed(String streamId) {
        Counter c = this.ackedByStream.get(streamId);
        if (c == null) {
            c = StormMetricRegistry.counter("failed", this.topologyId, this.componentId, this.taskId, this.workerPort, streamId);
            this.failedByStream.put(streamId, c);
        }
        return c;
    }

    public Counter getEmitted(String streamId) {
        Counter c = this.emittedByStream.get(streamId);
        if (c == null) {
            c = StormMetricRegistry.counter("emitted", this.topologyId, this.componentId, this.taskId, this.workerPort, streamId);
            this.emittedByStream.put(streamId, c);
        }
        return c;
    }

    public Counter getTransferred(String streamId) {
        Counter c = this.transferredByStream.get(streamId);
        if (c == null) {
            c = StormMetricRegistry.counter("transferred", this.topologyId, this.componentId, this.taskId, this.workerPort, streamId);
            this.transferredByStream.put(streamId, c);
        }
        return c;
    }
}
