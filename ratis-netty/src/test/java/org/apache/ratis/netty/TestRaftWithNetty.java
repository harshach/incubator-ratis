/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ratis.netty;

import org.apache.log4j.Level;
import org.apache.ratis.RaftBasicTests;
import org.apache.ratis.client.RaftClient;
import org.apache.ratis.server.impl.BlockRequestHandlingInjection;
import org.apache.ratis.server.impl.RaftServerImpl;
import org.apache.ratis.util.RaftUtils;
import org.junit.Test;

import java.io.IOException;

public class TestRaftWithNetty extends RaftBasicTests {
  static {
    RaftUtils.setLogLevel(RaftServerImpl.LOG, Level.DEBUG);
    RaftUtils.setLogLevel(RaftClient.LOG, Level.DEBUG);
  }

  private final MiniRaftClusterWithNetty cluster;

  public TestRaftWithNetty() throws IOException {
    cluster = new MiniRaftClusterWithNetty(NUM_SERVERS, getProperties());
  }

  @Override
  public MiniRaftClusterWithNetty getCluster() {
    return cluster;
  }

  @Override
  @Test
  public void testEnforceLeader() throws Exception {
    super.testEnforceLeader();

    MiniRaftClusterWithNetty.sendServerRequest.clear();
    BlockRequestHandlingInjection.getInstance().unblockAll();
  }

  @Override
  @Test
  public void testWithLoad() throws Exception {
    super.testWithLoad();
    BlockRequestHandlingInjection.getInstance().unblockAll();
  }
}
