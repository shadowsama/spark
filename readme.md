hadoop spark kafka adv java ...

[root@master 58-yarn-NODEMANAGER]# ps -ef |grep java
496        2956   2272  0 12:42 ?        00:01:57 /usr/java/jdk1.8.0_131/bin/java -server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Dmgmt.log.file=mgmt-cmf-mgmt-ALERTPUBLISHER-master.log.out -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Xms268435456 -Xmx268435456 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/mgmt_mgmt-ALERTPUBLISHER-7668215de4ba70df35efd5580b42ee47_pid2956.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -cp /opt/cm-5.12.0/run/cloudera-scm-agent/process/6-cloudera-mgmt-ALERTPUBLISHER:/usr/share/java/mysql-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/postgresql-9.0-801.jdbc4.jar:/usr/share/java/oracle-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/*: com.cloudera.enterprise.alertpublisher.AlertPublisher
496        4324   2272  7 12:43 ?        00:24:19 /usr/java/jdk1.8.0_131/bin/java -server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Dmgmt.log.file=mgmt-cmf-mgmt-SERVICEMONITOR-master.log.out -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfirehose.schema.dir=/opt/cm-5.12.0/share/cmf/schema -XX:PermSize=128m -Dsun.rmi.transport.tcp.handshakeTimeout=10000 -Dsun.rmi.transport.tcp.responseTimeout=10000 -Dlibrary.leveldbjni.path=/opt/cm-5.12.0/run/cloudera-scm-agent/process/8-cloudera-mgmt-SERVICEMONITOR -Xms365953024 -Xmx365953024 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/mgmt_mgmt-SERVICEMONITOR-7668215de4ba70df35efd5580b42ee47_pid4324.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -cp /opt/cm-5.12.0/run/cloudera-scm-agent/process/8-cloudera-mgmt-SERVICEMONITOR:/usr/share/java/mysql-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/postgresql-9.0-801.jdbc4.jar:/usr/share/java/oracle-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/*: com.cloudera.cmon.firehose.Main --pipeline-type SERVICE_MONITORING --mgmt-home /opt/cm-5.12.0/share/cmf
496        4375   2272  6 12:43 ?        00:18:31 /usr/java/jdk1.8.0_131/bin/java -server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Dmgmt.log.file=mgmt-cmf-mgmt-HOSTMONITOR-master.log.out -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfirehose.schema.dir=/opt/cm-5.12.0/share/cmf/schema -Dlibrary.leveldbjni.path=/opt/cm-5.12.0/run/cloudera-scm-agent/process/4-cloudera-mgmt-HOSTMONITOR -Xms365953024 -Xmx365953024 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/mgmt_mgmt-HOSTMONITOR-7668215de4ba70df35efd5580b42ee47_pid4375.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -cp /opt/cm-5.12.0/run/cloudera-scm-agent/process/4-cloudera-mgmt-HOSTMONITOR:/usr/share/java/mysql-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/postgresql-9.0-801.jdbc4.jar:/usr/share/java/oracle-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/*: com.cloudera.cmon.firehose.Main --pipeline-type HOST_MONITORING --mgmt-home /opt/cm-5.12.0/share/cmf
496        4444   2272  7 12:43 ?        00:23:56 /usr/java/jdk1.8.0_131/bin/java -server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Dmgmt.log.file=mgmt-cmf-mgmt-ACTIVITYMONITOR-master.log.out -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfirehose.schema.dir=/opt/cm-5.12.0/share/cmf/schema -Xms365953024 -Xmx365953024 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/mgmt_mgmt-ACTIVITYMONITOR-7668215de4ba70df35efd5580b42ee47_pid4444.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -cp /opt/cm-5.12.0/run/cloudera-scm-agent/process/7-cloudera-mgmt-ACTIVITYMONITOR:/usr/share/java/mysql-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/postgresql-9.0-801.jdbc4.jar:/usr/share/java/oracle-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/*: com.cloudera.cmon.firehose.Main --pipeline-type ACTIVITY_MONITORING_TREE --mgmt-home /opt/cm-5.12.0/share/cmf
496        4491   2272  3 12:43 ?        00:10:15 /usr/java/jdk1.8.0_131/bin/java -server -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Dmgmt.log.file=mgmt-cmf-mgmt-EVENTSERVER-master.log.out -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Xms365953024 -Xmx365953024 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/mgmt_mgmt-EVENTSERVER-7668215de4ba70df35efd5580b42ee47_pid4491.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -cp /opt/cm-5.12.0/run/cloudera-scm-agent/process/5-cloudera-mgmt-EVENTSERVER:/usr/share/java/mysql-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/postgresql-9.0-801.jdbc4.jar:/usr/share/java/oracle-connector-java.jar:/opt/cm-5.12.0/share/cmf/lib/*: com.cloudera.cmf.eventcatcher.server.EventCatcherService
root      37033      1 10 14:51 pts/1    00:18:14 /usr/java/jdk1.8.0_131/bin/java -cp .:lib/*:/usr/share/java/mysql-connector-java.jar:/usr/share/java/oracle-connector-java.jar -server -Dlog4j.configuration=file:/opt/cm-5.12.0/etc/cloudera-scm-server/log4j.properties -Dfile.encoding=UTF-8 -Dcmf.root.logger=INFO,LOGFILE -Dcmf.log.dir=/opt/cm-5.12.0/log/cloudera-scm-server -Dcmf.log.file=cloudera-scm-server.log -Dcmf.jetty.threshhold=WARN -Dcmf.schema.dir=/opt/cm-5.12.0/share/cmf/schema -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dpython.home=/opt/cm-5.12.0/share/cmf/python -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+HeapDumpOnOutOfMemoryError -Xmx2G -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp -XX:OnOutOfMemoryError=kill -9 %p com.cloudera.server.cmf.Main
hdfs      52250   2272  0 15:18 ?        00:01:05 /usr/java/jdk1.8.0_131/bin/java -Dproc_secondarynamenode -Xmx1000m -Dhdfs.audit.logger=INFO,RFAAUDIT -Dsecurity.audit.logger=INFO,RFAS -Djava.net.preferIPv4Stack=true -Dhadoop.log.dir=/var/log/hadoop-hdfs -Dhadoop.log.file=hadoop-cmf-hdfs-SECONDARYNAMENODE-master.log.out -Dhadoop.home.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop -Dhadoop.id.str=hdfs -Dhadoop.root.logger=INFO,RFA -Djava.library.path=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/lib/native -Dhadoop.policy.file=hadoop-policy.xml -Djava.net.preferIPv4Stack=true -Xms365953024 -Xmx365953024 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/hdfs_hdfs-SECONDARYNAMENODE-7668215de4ba70df35efd5580b42ee47_pid52250.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -Dhadoop.security.logger=INFO,RFAS org.apache.hadoop.hdfs.server.namenode.SecondaryNameNode
hdfs      52252   2272  1 15:18 ?        00:02:41 /usr/java/jdk1.8.0_131/bin/java -Dproc_namenode -Xmx1000m -Dhdfs.audit.logger=INFO,RFAAUDIT -Dsecurity.audit.logger=INFO,RFAS -Djava.net.preferIPv4Stack=true -Dhadoop.log.dir=/var/log/hadoop-hdfs -Dhadoop.log.file=hadoop-cmf-hdfs-NAMENODE-master.log.out -Dhadoop.home.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop -Dhadoop.id.str=hdfs -Dhadoop.root.logger=INFO,RFA -Djava.library.path=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/lib/native -Dhadoop.policy.file=hadoop-policy.xml -Djava.net.preferIPv4Stack=true -Xms365953024 -Xmx365953024 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/hdfs_hdfs-NAMENODE-7668215de4ba70df35efd5580b42ee47_pid52252.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -Dhadoop.security.logger=INFO,RFAS org.apache.hadoop.hdfs.server.namenode.NameNode
hdfs      52309   2272  0 15:18 ?        00:01:30 /usr/java/jdk1.8.0_131/bin/java -Dproc_datanode -Xmx1000m -Dhdfs.audit.logger=INFO,RFAAUDIT -Dsecurity.audit.logger=INFO,RFAS -Djava.net.preferIPv4Stack=true -Dhadoop.log.dir=/var/log/hadoop-hdfs -Dhadoop.log.file=hadoop-cmf-hdfs-DATANODE-master.log.out -Dhadoop.home.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop -Dhadoop.id.str=hdfs -Dhadoop.root.logger=INFO,RFA -Djava.library.path=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/lib/native -Dhadoop.policy.file=hadoop-policy.xml -Djava.net.preferIPv4Stack=true -server -Xms365953024 -Xmx365953024 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/hdfs_hdfs-DATANODE-7668215de4ba70df35efd5580b42ee47_pid52309.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -Dhadoop.security.logger=INFO,RFAS org.apache.hadoop.hdfs.server.datanode.DataNode
yarn      52740   2272  2 15:19 ?        00:03:23 /usr/java/jdk1.8.0_131/bin/java -Dproc_resourcemanager -Xmx1000m -Djava.net.preferIPv4Stack=true -Xms134217728 -Xmx134217728 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -Dlibrary.leveldbjni.path=/opt/cm-5.12.0/run/cloudera-scm-agent/process/57-yarn-RESOURCEMANAGER -Dhadoop.event.appender=,EventCatcher -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/yarn_yarn-RESOURCEMANAGER-7668215de4ba70df35efd5580b42ee47_pid52740.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -Dhadoop.log.dir=/var/log/hadoop-yarn -Dyarn.log.dir=/var/log/hadoop-yarn -Dhadoop.log.file=hadoop-cmf-yarn-RESOURCEMANAGER-master.log.out -Dyarn.log.file=hadoop-cmf-yarn-RESOURCEMANAGER-master.log.out -Dyarn.home.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn -Dhadoop.home.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn -Dhadoop.root.logger=INFO,RFA -Dyarn.root.logger=INFO,RFA -Djava.library.path=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/lib/native -classpath /opt/cm-5.12.0/run/cloudera-scm-agent/process/57-yarn-RESOURCEMANAGER:/opt/cm-5.12.0/run/cloudera-scm-agent/process/57-yarn-RESOURCEMANAGER:/opt/cm-5.12.0/run/cloudera-scm-agent/process/57-yarn-RESOURCEMANAGER:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-hdfs/./:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-hdfs/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-hdfs/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-mapreduce/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-mapreduce/.//*:/opt/cm-5.12.0/share/cmf/lib/plugins/tt-instrumentation-5.12.0.jar:/opt/cm-5.12.0/share/cmf/lib/plugins/event-publish-5.12.0-shaded.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn/lib/*:/opt/cm-5.12.0/run/cloudera-scm-agent/process/57-yarn-RESOURCEMANAGER/rm-config/log4j.properties org.apache.hadoop.yarn.server.resourcemanager.ResourceManager
yarn      52820   2272  1 15:19 ?        00:01:53 /usr/java/jdk1.8.0_131/bin/java -Dproc_nodemanager -Xmx1000m -Djava.net.preferIPv4Stack=true -server -Xms365953024 -Xmx365953024 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -Dlibrary.leveldbjni.path=/opt/cm-5.12.0/run/cloudera-scm-agent/process/58-yarn-NODEMANAGER -Dhadoop.event.appender=,EventCatcher -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/yarn_yarn-NODEMANAGER-7668215de4ba70df35efd5580b42ee47_pid52820.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -Dhadoop.log.dir=/var/log/hadoop-yarn -Dyarn.log.dir=/var/log/hadoop-yarn -Dhadoop.log.file=hadoop-cmf-yarn-NODEMANAGER-master.log.out -Dyarn.log.file=hadoop-cmf-yarn-NODEMANAGER-master.log.out -Dyarn.home.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn -Dhadoop.home.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn -Dhadoop.root.logger=INFO,RFA -Dyarn.root.logger=INFO,RFA -Djava.library.path=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/lib/native -classpath /opt/cm-5.12.0/run/cloudera-scm-agent/process/58-yarn-NODEMANAGER:/opt/cm-5.12.0/run/cloudera-scm-agent/process/58-yarn-NODEMANAGER:/opt/cm-5.12.0/run/cloudera-scm-agent/process/58-yarn-NODEMANAGER:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-hdfs/./:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-hdfs/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-hdfs/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-mapreduce/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-mapreduce/.//*:/opt/cm-5.12.0/share/cmf/lib/plugins/tt-instrumentation-5.12.0.jar:/opt/cm-5.12.0/share/cmf/lib/plugins/event-publish-5.12.0-shaded.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-yarn/lib/*:/opt/cm-5.12.0/run/cloudera-scm-agent/process/58-yarn-NODEMANAGER/nm-config/log4j.properties org.apache.hadoop.yarn.server.nodemanager.NodeManager
mapred    52857   2272  0 15:19 ?        00:01:09 /usr/java/jdk1.8.0_131/bin/java -Dproc_historyserver -Xmx1000m -Dhadoop.log.dir=/var/log/hadoop-mapreduce -Dhadoop.log.file=hadoop-cmf-yarn-JOBHISTORY-master.log.out -Dhadoop.home.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop -Dhadoop.id.str= -Dhadoop.root.logger=INFO,RFA -Djava.library.path=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/lib/native -Dhadoop.policy.file=hadoop-policy.xml -Djava.net.preferIPv4Stack=true -Dhadoop.log.dir=/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop-mapreduce/logs -Dhadoop.log.file=hadoop.log -Dhadoop.root.logger=INFO,console -Dmapred.jobsummary.logger=INFO,RFA -Xms365953024 -Xmx365953024 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+CMSParallelRemarkEnabled -Dlibrary.leveldbjni.path=/opt/cm-5.12.0/run/cloudera-scm-agent/process/59-yarn-JOBHISTORY -Dhadoop.event.appender=,EventCatcher -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/yarn_yarn-JOBHISTORY-7668215de4ba70df35efd5580b42ee47_pid52857.hprof -XX:OnOutOfMemoryError=/opt/cm-5.12.0/lib64/cmf/service/common/killparent.sh -Dhadoop.security.logger=INFO,RFAS org.apache.hadoop.mapreduce.v2.hs.JobHistoryServer
spark     72532   2272  0 16:13 ?        00:00:34 /usr/java/jdk1.8.0_131/bin/java -cp /opt/cm-5.12.0/run/cloudera-scm-agent/process/68-spark2_on_yarn-SPARK2_YARN_HISTORY_SERVER/spark2-conf/:/opt/cloudera/parcels/SPARK2-2.1.0.cloudera1-1.cdh5.7.0.p0.120904/lib/spark2/jars/*:/opt/cm-5.12.0/run/cloudera-scm-agent/process/68-spark2_on_yarn-SPARK2_YARN_HISTORY_SERVER/yarn-conf/:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/activation-1.1.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/aopalliance-1.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/apacheds-i18n-2.0.0-M15.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/apacheds-kerberos-codec-2.0.0-M15.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/api-asn1-api-1.0.0-M20.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/api-util-1.0.0-M20.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/asm-3.2.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/avro-1.7.6-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/aws-java-sdk-bundle-1.11.134.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/azure-data-lake-store-sdk-2.1.4.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-beanutils-1.9.2.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-beanutils-core-1.8.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-cli-1.2.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-codec-1.4.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-collections-3.2.2.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-compress-1.4.1.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-configuration-1.6.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-daemon-1.0.13.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-digester-1.8.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-el-1.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-httpclient-3.1.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-io-2.4.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-lang-2.6.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-logging-1.1.3.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-math3-3.1.1.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/commons-net-3.1.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/curator-client-2.7.1.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/curator-framework-2.7.1.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/curator-recipes-2.7.1.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/gson-2.2.4.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/guava-11.0.2.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/guice-3.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/guice-servlet-3.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-annotations-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-ant-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-archive-logs-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-archives-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-auth-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-aws-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-azure-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-azure-datalake-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-common-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-common-2.6.0-cdh5.12.0-tests.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-datajoin-2.6.0-cdh5.12.0.jar:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/jars/hadoop-distcp-2.6.0-cdh5.12.0.jar:/opt/cloudera/par
root      75273  75250  1 16:23 pts/1    00:01:35 /usr/java/jdk1.8.0_131/bin/java -cp /opt/cloudera/parcels/SPARK2-2.1.0.cloudera1-1.cdh5.7.0.p0.120904/lib/spark2/conf/:/opt/cloudera/parcels/SPARK2-2.1.0.cloudera1-1.cdh5.7.0.p0.120904/lib/spark2/jars/*:/etc/hadoop/conf/:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/libexec/../../hadoop/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/libexec/../../hadoop/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/libexec/../../hadoop-hdfs/./:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/libexec/../../hadoop-hdfs/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/libexec/../../hadoop-hdfs/.//*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/libexec/../../hadoop-yarn/lib/*:/opt/cloudera/parcels/CDH-5.12.0-1.cdh5.12.0.p0.29/lib/hadoop/libexec/../../hadoop-yarn/.//*:/opt/cloudera/parcels/CDH/lib/hadoop-mapreduce/lib/*:/opt/cloudera/parcels/CDH/lib/hadoop-mapreduce/.//* -Dscala.usejavacp=true -Xmx1g org.apache.spark.deploy.SparkSubmit --class org.apache.spark.repl.Main --name Spark shell spark-shell
