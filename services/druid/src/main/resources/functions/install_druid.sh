#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
function install_druid() {

  # Install MySQL
  export DEBIAN_FRONTEND=noninteractive
  sudo debconf-set-selections <<< 'mysql-server-5.1 mysql-server/root_password password diurd'
  sudo debconf-set-selections <<< 'mysql-server-5.1 mysql-server/root_password_again password diurd'
  sudo apt-get -q -y -V --force-yes --reinstall install mysql-server-5.1

  # Setup tables
  mysql -u root -pdiurd -e "GRANT ALL ON druid.* TO 'druid'@'localhost' IDENTIFIED BY 'diurd'; CREATE database druid;" 2>&1 > /dev/null

  install_tarball http://static.druid.io/artifacts/releases/druid-services-0.5.7-bin.tar.gz
}