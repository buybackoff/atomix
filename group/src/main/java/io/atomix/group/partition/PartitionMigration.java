/*
 * Copyright 2016 the original author or authors.
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
package io.atomix.group.partition;

import io.atomix.catalyst.util.Assert;
import io.atomix.group.GroupMember;

/**
 * Group partition migration.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
public class PartitionMigration {
  private final GroupMember source;
  private final GroupMember target;
  private final Partition partition;

  PartitionMigration(GroupMember source, GroupMember target, Partition partition) {
    this.source = source;
    this.target = target;
    this.partition = Assert.notNull(partition, "partition");
  }

  /**
   * Returns the migration source.
   *
   * @return The migration source.
   */
  public GroupMember source() {
    return source;
  }

  /**
   * Returns the migration target.
   *
   * @return The migration target.
   */
  public GroupMember target() {
    return target;
  }

  /**
   * Returns the migration partition.
   *
   * @return The migration partition.
   */
  public Partition partition() {
    return partition;
  }

  @Override
  public String toString() {
    return String.format("%s[source=%s, target=%s, partition=%s]", getClass().getSimpleName(), source, target, partition);
  }

}