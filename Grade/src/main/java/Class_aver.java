import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import java.io.IOException;
public class Class_aver {
    public static class  MyMapper extends Mapper<Object,Text,Text,IntWritable>{

        private final static IntWritable Score = new IntWritable();
        Text className = new Text();

        protected void map(Object key, Text value,Context context) throws IOException,InterruptedException{
            String str = value.toString();
            String[] vals = str.split(",");
            className.set(vals[2]+" "+vals[0]);//课程名+班级
            Score.set(Integer.parseInt(vals[4]));
            context.write(className,Score);
        }
    }

    public static class MyReducer extends Reducer<Text,IntWritable,Text,FloatWritable>{

        private static FloatWritable averScore = new FloatWritable();
        protected void reduce(Text key,Iterable<IntWritable> values, Context context) throws IOException,InterruptedException{
            int count=0;
            int sum=0;
            for(IntWritable val:values){
                sum +=val.get();
                count++;
            }
            //计算平均成绩
            float score = (float)sum/count;
            averScore.set(score);
            context.write(key,averScore);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, Class_aver.class.getSimpleName());
        //打成jar执行
        job.setJarByClass(Class_aver.class);

        //数据在哪里？
        FileInputFormat.setInputPaths(job, args[0]);
        //使用哪个mapper处理输入的数据？
        job.setMapperClass(MyMapper.class);
        //map输出的数据类型是什么？
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        //使用哪个reducer处理输入的数据？
        job.setReducerClass(MyReducer.class);
        //reduce输出的数据类型是什么？
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FloatWritable.class);
        //数据输出到哪里？
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //交给yarn去执行，直到执行结束才退出本程序
        job.waitForCompletion(true);
    }

}
