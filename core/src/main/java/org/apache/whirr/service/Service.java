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

package org.apache.whirr.service;

import java.io.IOException;

import org.jclouds.compute.ComputeService;

/**
 * This class represents a service that a client wants to use. This class is
 * used to start and stop clusters that provide the service.
 */
public abstract class Service {
  protected ServiceSpec serviceSpec;

  /**
   * Subclasses must provide a single argument constructor that takes a
   * {@link ServiceSpec} so that they can be constructed by
   * {@link ServiceFactory}.
   * @param serviceSpec
   */
  public Service(ServiceSpec serviceSpec) {
    this.serviceSpec = serviceSpec;
  }
  
  /**
   * Start the cluster described by <code>clusterSpec</code> and block until the
   * cluster is
   * available. It is not guaranteed that the service running on the cluster
   * has started when this method returns.
   * @param clusterSpec
   * @return an object representing the running cluster
   * @throws IOException if there is a problem while starting the cluster. The
   * cluster may or may not have started.
   */
  public abstract Cluster launchCluster(ClusterSpec clusterSpec)
    throws IOException;
  
  /**
   * Stop the cluster and destroy all reseouces associated with it.
   * @throws IOException if there is a problem while stopping the cluster. The
   * cluster may or may not have been stopped.
   */
  public void destroyCluster() throws IOException {
    ComputeService computeService = ComputeServiceBuilder.build(serviceSpec);
    computeService.destroyNodesWithTag(serviceSpec.getClusterName());
  }

}