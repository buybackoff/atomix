/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package io.atomix.examples.election;

import io.atomix.Atomix;
import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.NettyTransport;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.group.DistributedGroup;
import io.atomix.group.LocalGroupMember;

import java.util.ArrayList;
import java.util.List;

/**
 * Leader election example.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
public class LeaderElectionExample {

  /**
   * Starts the server.
   */
  public static void main(String[] args) throws Exception {
    if (args.length < 2)
      throw new IllegalArgumentException("must supply a path and set of host:port tuples");

    // Parse the address to which to bind the server.
    String[] mainParts = args[1].split(":");
    Address address = new Address(mainParts[0], Integer.valueOf(mainParts[1]));

    // Build a list of all member addresses to which to connect.
    List<Address> members = new ArrayList<>();
    for (int i = 1; i < args.length; i++) {
      String[] parts = args[i].split(":");
      members.add(new Address(parts[0], Integer.valueOf(parts[1])));
    }

    // Create a stateful Atomix replica. The replica communicates with other replicas in the cluster
    // to replicate state changes.
    Atomix atomix = AtomixReplica.builder(address, members)
      .withTransport(new NettyTransport())
      .withStorage(new Storage(args[0]))
      .build();

    // Open the replica. Once this operation completes resources can be created and managed.
    atomix.open().join();

    // Create a leader election resource.
    DistributedGroup group = atomix.getGroup("group").get();

    // Join the group.
    LocalGroupMember member = group.join().get();

    // Register a callback to be called when the local member is elected the leader.
    group.election().onElection(term -> {
      if (term.leader().equals(member)) {
        System.out.println("Elected leader!");
      }
    });

    // Block while the replica is open.
    while (atomix.isOpen()) {
      Thread.sleep(1000);
    }
  }

}
