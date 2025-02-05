/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.streampark.console.core.service;

import org.apache.streampark.console.base.exception.ApiDetailException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** log client */
@Slf4j
@Component
public class LogClientService {
  public String rollViewLog(String path, int offset, int limit) {
    try {
      File file = new File(path);
      if (file.exists() && file.isFile()) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
          List<String> lines = stream.skip(offset).limit(limit).collect(Collectors.toList());
          StringBuilder builder = new StringBuilder();
          lines.forEach(line -> builder.append(line).append("\r\n"));
          return builder.toString();
        }
      } else {
        throw new FileNotFoundException("file path: " + path + " not exists ");
      }
    } catch (Exception e) {
      throw new ApiDetailException("roll view log error: " + e);
    }
  }
}
