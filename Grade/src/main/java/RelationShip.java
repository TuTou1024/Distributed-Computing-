import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RelationShip {
    //自定义的mapper，继承org.apache.hadoop.mapreduce.Mapper
    public static class MyMapper extends Mapper<Object, Text, Text, Text>{

        protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String str = value.toString();
            String[] val = str.split(",");
            //记录父子表
            context.write(new Text(val[0]), new Text("c:"+val[1]));
            //记录子父表
            context.write(new Text(val[1]), new Text("p:"+val[0]));

        }
    }

    public static class MyReducer extends Reducer<Text, Text, Text, Text>{

        List<String> cs = new ArrayList<String>();//保存子辈
        List<String> ps = new ArrayList<String>();//保存父辈
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            cs.clear();
            ps.clear();

            for(Text val:values){
                String s = val.toString();
                String s1 = s.split(":")[0];
                String s2 = s.split(":")[1];
                if (s1.equals("c")){
                    cs.add(s2);
                }
                else {
                    ps.add(s2);
                }
            }
            for (String c:cs){
                for (String p:ps){
                    context.write(new Text(c), new Text(p));
                }
            }


        }
    }

    //客户端代码，写完交给ResourceManager框架去执行
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, RelationShip.class.getSimpleName());
        //打成jar执行
        job.setJarByClass(RelationShip.class);

        //数据在哪里？
        FileInputFormat.setInputPaths(job, args[0]);
        //使用哪个mapper处理输入的数据？
        job.setMapperClass(RelationShip.MyMapper.class);
        //map输出的数据类型是什么？
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        //使用哪个reducer处理输入的数据？
        job.setReducerClass(RelationShip.MyReducer.class);
        //reduce输出的数据类型是什么？
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        //数据输出到哪里？
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //交给yarn去执行，直到执行结束才退出本程序
        job.waitForCompletion(true);
    }
}