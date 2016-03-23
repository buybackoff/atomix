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
package io.atomix.group.election;

import io.atomix.group.GroupMember;

/**
 * Group election term.
 *
 * @author <a href="http://github.com/kuujo>Jordan Halterman</a>
 */
public class Term {
  private final long term;
  private final GroupMember leader;

  Term(long term, GroupMember leader) {
    this.term = term;
    this.leader = leader;
  }

  /**
   * Returns the group term.
   *
   * @return The group term.
   */
  public long term() {
    return term;
  }

  /**
   * Returns the group leader.
   *
   * @return The group leader.
   */
  public GroupMember leader() {
    return leader;
  }

}