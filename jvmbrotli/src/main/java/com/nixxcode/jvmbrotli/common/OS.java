/*
 * Copyright (c) 2016. MeteoGroup Deutschland GmbH
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.nixxcode.jvmbrotli.common;

enum OS {
  WIN32("win32", "win32", "windows"),
  LINUX("linux", "linux", "unix"),
  OSX("darwin", "darwin", "mac os x", "mac", "osx");

  final String name;
  final String[] aliases;

  OS(String name, String... aliases) {
    this.name = name;
    this.aliases = aliases;
  }

  boolean matches(String aName) {
    for (String alias : aliases) {
      if (aName.contains(alias)) return true;
    }
    return false;
  }

}
