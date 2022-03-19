# spark-test-more

here is all the files included to test the disk.

- Benchmark_Parameters.sh # load some running parameter.
- HS-fullVersion.sh # commad entrance to test the disk.
- HS-finalVersion.jar # the jar file where the compiled .class files are packed.
- VERSION.txt # test-kit version

the Spark-test-disk-guide.md is in the document folder.
And the environment prepare is also in the document folder.

1. before you can test spark programs. Be sure you have prepared the environment below:
 - java 8
 - Scala 2.12.15 or scala 2.12.x
 - Hadoop 3.2.2
 - spark 3.0.3
-----------------------------------------------------------------------------------------
2. you are advised to mount your disk to the base direcory before testing. it is advised to mount a high-speed disk to a directory as base-directory exclussively. And the disk is only used for spark programs to write/read(Avoiding the impact of other unrelative programs on such disk)

# in my test. The directory is mount on /dev/sda3

root@smfast:/hdfs-fast# df /hdfs-fast
Filesystem     1K-blocks     Used Available Use% Mounted on
/dev/sda3      232766464 39028732 193238004  17% /

# the hdfs-fast is the base-directory,the sub-directory is listed below:
# **ATTENTION: the base-directory is only needed to created manually while the sub-direcory will generated automatically. **

root@smfast:/hdfs-fast# ll

# checkpoint data is stored here
drwxr-xr-x. 1 root root  144 Jan  9 14:20 checkpointdir/ 
# write/read data to/from disk with hadoop API
drwxr-xr-x. 1 root root 2942 Jan  9 14:28 input/ 
# first read the data from the input directory then sort them in momory, Lastly write the sorted data to such output directory`output`. This part is used to test the write and read performance together.
drwxr-xr-x. 1 root root 2942 Jan  9 14:33 output/
# the `persistdir` is used to persist data in disk Temporarily. The data will be removed when all the tasks are done while in checkpoint mode the data will not removed.
drwxr-xr-x. 1 root root    0 Jan  9 14:24 persistdir/

it is showed below in a more clear way:
/dev/sda3 --
            |-- /hdfs-fast --
                             |-- checkpointdir
                             |-- input
                             |-- output
                             |-- persistdir


When the environment is OK. You can run cmd below:

The steps above can make you clear how to run the shell to start the spark programs to test the disk:
- ./HS-fullVersion.sh -w -g 1 # write with hadoop API.
- ./HS-fullVersion.sh -r -g 1 # read with hadoop API.
- ./HS-fullVersion.sh -s -g 1 # write and read with Hadoop API.
- ./HS-fullVersion.sh -c -g 1 # checkpoint data to disk.
- ./HS-fullVersion.sh -p -g 1 # persist(DISK_ONLY) data to disk.

